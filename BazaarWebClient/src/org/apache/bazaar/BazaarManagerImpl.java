/*
 * BazaarManagerImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 6:27:47 PM
 */
package org.apache.bazaar;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//
// import javax.cache.configuration.MutableConfiguration;
// import javax.cache.expiry.AccessedExpiryPolicy;
// import javax.cache.expiry.Duration;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.bazaar.Image.MimeType;
import org.apache.bazaar.cache.Cache;
import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.web.RequestParameters;
import org.apache.bazaar.web.RestWebClient;
import org.apache.bazaar.web.RestWebServiceException;
import org.apache.bazaar.web.config.Configuration;

/**
 * BazaarManagerImpl implements {@link BazaarManager}
 */
public final class BazaarManagerImpl implements BazaarManager {

	// declare members

	private static final Logger LOGGER = Logger.newInstance(BazaarManager.class);
	private final Map<Class<? extends Persistable>, Cache<Identifier, ? extends Persistable>> persistableCache;
	private final ThreadLocal<RestWebClient> threadLocal;
	private Category rootCategory;

	// declare constructors

	/**
	 * Constructor for BazaarManagerImpl
	 */
	private BazaarManagerImpl() {
		super();
		final Map<Class<? extends Persistable>, Cache<Identifier, ? extends Persistable>> map = new ConcurrentHashMap<Class<? extends Persistable>, Cache<Identifier, ? extends Persistable>>(
				4);
		map.put(Bazaar.class, Cache.newInstance(Bazaar.class.getName(), Identifier.class, Bazaar.class));
		map.put(Category.class, Cache.newInstance(Category.class.getName(), Identifier.class, Category.class));
		map.put(Item.class, Cache.newInstance(Item.class.getName(), Identifier.class, Item.class));
		map.put(Bidder.class, Cache.newInstance(Bidder.class.getName(), Identifier.class, Bidder.class));
		this.persistableCache = Collections.unmodifiableMap(map);
		this.threadLocal = new ThreadLocal<RestWebClient>();
	}

	// declare methods

	/**
	 * Factory method returns instance
	 *
	 * @return BazaarManagerImpl instance
	 * @throws BazaarException if the instance could not be returned
	 */
	static BazaarManager newInstance() throws BazaarException {
		return new BazaarManagerImpl();
	}

	/**
	 * Method updates cache instance
	 *
	 * @param persistable The persistable to be updated
	 */
	@SuppressWarnings("unchecked")
	void addToCache(@NotNull final Persistable persistable) {
		if (persistable instanceof Bazaar) {
			final Cache<Identifier, Bazaar> cache = (Cache<Identifier, Bazaar>)this.persistableCache.get(Bazaar.class);
			cache.put(persistable.getIdentifier(), (Bazaar)persistable);

		}
		if (persistable instanceof Category) {
			final Cache<Identifier, Category> cache = (Cache<Identifier, Category>)this.persistableCache
					.get(Category.class);
			cache.put(persistable.getIdentifier(), (Category)persistable);
		}
		if (persistable instanceof Item) {
			final Cache<Identifier, Item> cache = (Cache<Identifier, Item>)this.persistableCache.get(Item.class);
			cache.put(persistable.getIdentifier(), (Item)persistable);
		}
		if (persistable instanceof Bidder) {
			final Cache<Identifier, Bidder> cache = (Cache<Identifier, Bidder>)this.persistableCache.get(Bidder.class);
			cache.put(persistable.getIdentifier(), (Bidder)persistable);
		}

	}

	/**
	 * Method updates cache instances
	 *
	 * @param persistables The Set of persistable to be updated
	 */
	<T extends Persistable> void addToCache(@NotNull final Set<T> persistables) {
		for (final Persistable persistable : persistables) {
			this.addToCache(persistable);
		}
	}

	/**
	 * Method removes cache instance
	 *
	 * @param persistable The persistable to be removed
	 */
	@SuppressWarnings("unchecked")
	void removeFromCache(@NotNull final Persistable persistable) {
		if (persistable instanceof Bazaar) {
			final Cache<Identifier, Bazaar> cache = (Cache<Identifier, Bazaar>)this.persistableCache.get(Bazaar.class);
			cache.remove(persistable.getIdentifier(), (Bazaar)persistable);

		}
		if (persistable instanceof Category) {
			final Cache<Identifier, Category> cache = (Cache<Identifier, Category>)this.persistableCache
					.get(Category.class);
			cache.remove(persistable.getIdentifier(), (Category)persistable);
		}
		if (persistable instanceof Item) {
			final Cache<Identifier, Item> cache = (Cache<Identifier, Item>)this.persistableCache.get(Item.class);
			cache.remove(persistable.getIdentifier(), (Item)persistable);
		}
		if (persistable instanceof Bidder) {
			final Cache<Identifier, Bidder> cache = (Cache<Identifier, Bidder>)this.persistableCache.get(Bidder.class);
			cache.remove(persistable.getIdentifier(), (Bidder)persistable);
		}
	}

	/**
	 * Method returns cached instance of type with identifier if exists in
	 * cache; otherwise returns null
	 *
	 * @param identifier The identifier to retrieve
	 * @return Persistable if found within cache; null otherwise
	 */
	@SuppressWarnings("unchecked")
	<T extends Persistable> T getFromCache(@NotNull final Identifier identifier) {
		T persistable = null;
		for (final Entry<Class<? extends Persistable>, Cache<Identifier, ? extends Persistable>> entry : this.persistableCache
				.entrySet()) {
			if (entry.getValue().containsKey(identifier)) {
				persistable = (T)entry.getValue().get(identifier);
				break;
			}
		}
		return persistable;
	}

	/**
	 * Method clears cache
	 */
	void clearAllCaches() {
		this.persistableCache.clear();
	}

	/**
	 * Utility method retrieves {@link Client} instance. The instance is stored
	 * on an {@link ThreadLocal} as the BazaarManager instance is stored as a
	 * singleton on the BazaarManagerFactory and client instances should not be
	 * shared across threads.
	 *
	 * @return The client instance
	 */
	@NotNull
	RestWebClient newRestWebClient() {
		if (this.threadLocal.get() == null) {
			final RestWebClient client = RestWebClient.newInstance();
			this.threadLocal.set(client);
		}
		return this.threadLocal.get();

	}

	/**
	 * Utility method processes response instance
	 *
	 * @param type The type of persistable to be generated
	 * @param response The response instance
	 * @return The Persistable returned
	 * @throws BazaarException if the response was could not be processed
	 */
	@SuppressWarnings("unchecked")
	static <T> T processResponse(@NotNull final GenericType<T> type, @NotNull final Response response)
			throws BazaarException {
		final Object object;
		if (MediaType.APPLICATION_JSON_TYPE.equals(response.getMediaType()) && response.hasEntity()) {
			try {
				if (Response.Status.Family.CLIENT_ERROR.equals(Response.Status.Family.familyOf(response.getStatus()))
						|| Response.Status.Family.SERVER_ERROR
								.equals(Response.Status.Family.familyOf(response.getStatus()))) {
					final Throwable throwable = response.readEntity(Throwable.class);
					if (throwable.getCause() != null && throwable.getCause() instanceof BazaarException) {
						throw (BazaarException)throwable.getCause();
					}
					else if (throwable instanceof BazaarException) {
						throw (BazaarException)throwable;
					}
					else {
						throw new BazaarException(throwable);
					}
				}
				object = response.readEntity(type);
			}
			catch (final ProcessingException exception) {
				throw new RestWebServiceException(exception);
			}
		}
		else {
			throw new BazaarException(new RestWebServiceException(response.getStatusInfo().toString()));
		}
		// close the response instance
		response.close();
		return (T)object;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findRootCategory()
	 */
	@Override
	public Category findRootCategory() throws BazaarException {
		if (this.rootCategory == null) {
			this.rootCategory = this.newCategory();
			final org.apache.bazaar.config.Configuration configuration = org.apache.bazaar.config.Configuration
					.newInstance();
			((CategoryImpl)this.rootCategory).setIdentifier(Identifier.fromValue(
					configuration.getProperty(org.apache.bazaar.config.Configuration.ROOT_CATEGORY_IDENTIFIER)));
			this.rootCategory
					.setName(configuration.getProperty(org.apache.bazaar.config.Configuration.ROOT_CATEGORY_NAME));
			this.rootCategory.setDescription(
					configuration.getProperty(org.apache.bazaar.config.Configuration.ROOT_CATEGORY_DESCRIPTION));
			this.rootCategory.setParent(this.rootCategory);
		}
		return this.rootCategory;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findCategory(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Category findCategory(final Identifier identifier) throws CategoryNotFoundException, BazaarException {
		BazaarManagerImpl.LOGGER.entering("findCategory", identifier);
		final Category category;
		// check for ROOT identifier
		if (org.apache.bazaar.config.Configuration.newInstance()
				.getProperty(org.apache.bazaar.config.Configuration.ROOT_CATEGORY_IDENTIFIER)
				.equals(identifier.getValue()) && this.rootCategory != null) {
			category = this.rootCategory;
		}
		else {
			// check cache
			final Category category1 = this.getFromCache(identifier);
			if (category1 != null) {
				category = category1;
			}
			else {
				final WebTarget webTarget = this.newRestWebClient()
						.target(Configuration.newInstance().getProperty(Configuration.CATEGORY_REST_WEB_SERVICE_URL))
						.path(identifier.getValue());
				final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
				category = BazaarManagerImpl.processResponse(new GenericType<Category>() {
				}, response);
				// add to cache
				this.addToCache(category);
			}
		}
		return category;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findItem(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Item findItem(final Identifier identifier) throws ItemNotFoundException, BazaarException {
		final Item item;
		// check cache
		final Item item1 = this.getFromCache(identifier);
		if (item1 != null) {
			item = item1;
		}
		else {
			final WebTarget webTarget = this.newRestWebClient()
					.target(Configuration.newInstance().getProperty(Configuration.ITEM_REST_WEB_SERVICE_URL))
					.path(identifier.getValue());
			final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
			item = BazaarManagerImpl.processResponse(new GenericType<Item>() {
			}, response);
			// add to cache
			this.addToCache(item);
		}
		return item;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newBazaar(org.apache.bazaar.Item,
	 * java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate) throws BazaarException {
		return new BazaarImpl(item, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newBazaar(org.apache.bazaar.Item,
	 * java.util.Calendar, java.util.Calendar, java.lang.Double)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate,
			final Double reservePrice) throws BazaarException {
		return new BazaarImpl(item, startDate, endDate, reservePrice);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newName()
	 */
	@Override
	public Name newName() {
		return new NameImpl();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newName(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Name newName(final String firstName, final String lastName) {
		return new NameImpl(firstName, lastName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newAddress()
	 */
	@Override
	public Address newAddress() {
		return new AddressImpl();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newAddress(java.lang.String,
	 * java.lang.String, org.apache.bazaar.State, java.lang.Integer)
	 */
	@Override
	public Address newAddress(final String street, final String city, final State state, final Integer zipcode) {
		return new AddressImpl(street, city, state, zipcode);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newBidder()
	 */
	@Override
	public Bidder newBidder() {
		return new BidderImpl();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newBidder(org.apache.bazaar.Name,
	 * org.apache.bazaar.Address, org.apache.bazaar.Address)
	 */
	@Override
	public Bidder newBidder(final Name name, final Address billingAddress, final Address shippingAddress) {
		return new BidderImpl(name, billingAddress, shippingAddress);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newImage(java.lang.String,
	 * org.apache.bazaar.Image.MimeType, java.io.InputStream)
	 */
	@Override
	public Image newImage(final String name, final MimeType mimeType, final InputStream inputStream)
			throws BazaarException {
		final ImageImpl image = new ImageImpl(name, mimeType);
		image.setImage(inputStream);
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newItem()
	 */
	@Override
	public Item newItem() {
		return new ItemImpl();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newItem(java.lang.String,
	 * java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Item newItem(final String name, final String description, final Category category) {
		return new ItemImpl(name, description, category);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newCategory()
	 */
	@Override
	public Category newCategory() {
		return new CategoryImpl();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#newCategory(java.lang.String,
	 * java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Category newCategory(final String name, final String description, final Category parent) {
		return new CategoryImpl(name, description, parent);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findBazaar(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Bazaar findBazaar(final Identifier identifier) throws BazaarNotFoundException, BazaarException {
		final Bazaar bazaar;
		// check cache
		final Bazaar bazaar1 = this.getFromCache(identifier);
		if (bazaar1 != null) {
			bazaar = bazaar1;
		}
		else {
			final WebTarget webTarget = this.newRestWebClient()
					.target(Configuration.newInstance().getProperty(Configuration.BAZAAR_REST_WEB_SERVICE_URL))
					.path(identifier.getValue());
			final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
			bazaar = BazaarManagerImpl.processResponse(new GenericType<Bazaar>() {
			}, response);
			// add to cache
			this.addToCache(bazaar);
		}
		return bazaar;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findAllBazaars()
	 */
	@Override
	public Set<Bazaar> findAllBazaars() throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.BAZAAR_REST_WEB_SERVICE_URL));
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Bazaar> bazaars = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Bazaar>>() {
				}, response));
		// add to cache
		this.addToCache(bazaars);
		return bazaars;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findCategories(java.lang.String)
	 */
	@Override
	public Set<Category> findCategories(final String name) throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.CATEGORY_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.NAME, name);
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Category> categories = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Category>>() {
				}, response));
		// add to cache
		this.addToCache(categories);
		return categories;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findAllCategories()
	 */
	@Override
	public Set<Category> findAllCategories() throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.CATEGORY_REST_WEB_SERVICE_URL));
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Category> categories = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Category>>() {
				}, response));
		// add to cache
		this.addToCache(categories);
		return categories;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findItems(java.lang.String)
	 */
	@Override
	public Set<Item> findItems(final String name) throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.ITEM_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.NAME, name);
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Item> items = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Item>>() {
				}, response));
		// add to cache
		this.addToCache(items);
		return items;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.BazaarManager#findItems(org.apache.bazaar.Category)
	 */
	@Override
	public Set<Item> findItems(final Category category) throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.ITEM_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.CATEGORY, category.getIdentifier().getValue());
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Item> items = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Item>>() {
				}, response));
		// add to cache
		this.addToCache(items);
		return items;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findAllItems()
	 */
	@Override
	public Set<Item> findAllItems() throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.ITEM_REST_WEB_SERVICE_URL));
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Item> items = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Item>>() {
				}, response));
		// add to cache
		this.addToCache(items);
		return items;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findBidder(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Bidder findBidder(final Identifier identifier) throws BidderNotFoundException, BazaarException {
		final Bidder bidder;
		// check cache
		final Bidder bidder1 = this.getFromCache(identifier);
		if (bidder1 != null) {
			bidder = bidder1;
		}
		else {
			final WebTarget webTarget = this.newRestWebClient()
					.target(Configuration.newInstance().getProperty(Configuration.BIDDER_REST_WEB_SERVICE_URL))
					.path(identifier.getValue());
			final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
			bidder = BazaarManagerImpl.processResponse(new GenericType<Bidder>() {
			}, response);
			// add to cache
			this.addToCache(bidder);
		}
		return bidder;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findBidders(org.apache.bazaar.Name)
	 */
	@Override
	public Set<Bidder> findBidders(final Name name) throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.BIDDER_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.NAME, name);
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Bidder> bidders = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Bidder>>() {
				}, response));
		// add to cache
		this.addToCache(bidders);
		return bidders;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findAllBidders()
	 */
	@Override
	public Set<Bidder> findAllBidders() throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.BIDDER_REST_WEB_SERVICE_URL));
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Bidder> bidders = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Bidder>>() {
				}, response));
		// add to cache
		this.addToCache(bidders);
		return bidders;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.BazaarManager#findBid(org.apache.bazaar.Identifier)
	 */
	@Override
	public Bid findBid(final Identifier identifier) throws BidNotFoundException, BazaarException {
		final Bid bid;
		// check cache
		final Bid bid1 = this.getFromCache(identifier);
		if (bid1 != null) {
			bid = bid1;
		}
		else {
			final WebTarget webTarget = this.newRestWebClient()
					.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL))
					.path(identifier.getValue());
			final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
			bid = BazaarManagerImpl.processResponse(new GenericType<Bid>() {
			}, response);
			// add to cache
			this.addToCache(bid);
		}
		return bid;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.BazaarManager#findAllBids()
	 */
	@Override
	public Set<Bid> findAllBids() throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL));
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Bid> bids = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Bid>>() {
				}, response));
		// add to cache
		this.addToCache(bids);
		return bids;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.BazaarManager#findAllBids(org.apache.bazaar.Bidder)
	 */
	@Override
	public Set<Bid> findAllBids(final Bidder bidder) throws BazaarException {
		final WebTarget webTarget = this.newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.BIDDER, bidder.getIdentifier().getValue());
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		final Set<Bid> bids = Collections
				.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Bid>>() {
				}, response));
		// add to cache
		this.addToCache(bids);
		return bids;
	}

}
