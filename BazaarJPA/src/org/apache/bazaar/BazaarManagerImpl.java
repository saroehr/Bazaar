/*
 * BazaarManagerImpl.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.bazaar.Image.MimeType;
import org.apache.bazaar.config.Configuration;
import org.apache.bazaar.i18n.Messages;
import org.apache.bazaar.logging.Logger;

/**
 * BazaarManagerImpl implements @Bazaar to provide a concrete implementation.
 * This implementation is returned via the {@link Bazaar#newInstance()} method
 * using Java reflection.
 */
final class BazaarManagerImpl implements BazaarManager {

	// declare members

	private static final Logger LOGGER = Logger.newInstance(BazaarManager.class);
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = org.apache.bazaar.jpa.EntityManagerFactory
			.newInstance();
	private static final Messages MESSAGES = Messages.newInstance(Locale.getDefault());
	private static final String SELECT_CATEGORY_BY_NAME_QUERY_PARAMETER_NAME = "name";
	private static final String SELECT_CATEGORY_BY_NAME_QUERY = "SELECT category FROM Category category WHERE category.name = :"
			+ BazaarManagerImpl.SELECT_CATEGORY_BY_NAME_QUERY_PARAMETER_NAME;
	private static final String SELECT_ALL_CATEGORIES_QUERY = "SELECT categories FROM Category categories";
	private static final String SELECT_ITEMS_BY_NAME_QUERY_PARAMETER_NAME = "name";
	private static final String SELECT_ITEMS_BY_NAME_QUERY = "SELECT items FROM Item items WHERE item.name = :"
			+ BazaarManagerImpl.SELECT_ITEMS_BY_NAME_QUERY_PARAMETER_NAME;
	private static final String SELECT_ALL_ITEMS_BY_CATEGORY_QUERY_PARAMETER_NAME = "category";
	private static final String SELECT_ALL_ITEMS_BY_CATEGORY_QUERY = "SELECT item FROM Item item WHERE item.category = :"
			+ BazaarManagerImpl.SELECT_ALL_ITEMS_BY_CATEGORY_QUERY_PARAMETER_NAME;
	private static final String SELECT_ALL_ITEMS_QUERY = "SELECT item FROM Item item";
	private static final String SELECT_BIDDER_BY_NAME_QUERY_PARAMETER_NAME = "bidder";
	private static final String SELECT_BIDDER_BY_NAME_QUERY = "SELECT bidder FROM BIDDER bidder where bidder.name = :"
			+ BazaarManagerImpl.SELECT_BIDDER_BY_NAME_QUERY_PARAMETER_NAME;
	private static final String SELECT_ALL_BIDS_QUERY = "SELECT bid FROM Bid bid";
	private static final String SELECT_ALL_BIDS_BY_BIDDER_QUERY_PARAMETER_NAME = "bidder";
	private static final String SELECT_ALL_BIDS_BY_BIDDER_QUERY = "SELECT bid FROM Bid bid WHERE bid.bidder = :"
			+ BazaarManagerImpl.SELECT_ALL_BIDS_BY_BIDDER_QUERY_PARAMETER_NAME;

	private Category rootCategory;

	// declare constructors

	/**
	 * Constructor for BazaarManagerImpl.
	 */
	private BazaarManagerImpl() {
		super();
	}

	// declare methods

	/**
	 * Factory method for obtaining instance
	 * 
	 * @return BazaarManagerImpl instance
	 * @throws BazaarException if the instance could
	 *         not be returned
	 */
	static BazaarManager newInstance() throws BazaarException {
		return new BazaarManagerImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#newAuction(org.apache.bazaar.Item,
	 * java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate) throws BazaarException {
		if (!endDate.after(startDate)) {
			throw new BazaarException(Messages.newInstance(Locale.getDefault())
					.findMessage(Messages.AUCTION_ENDDATE_INVALID_MESSAGE_KEY, new Object[] { endDate, startDate }));
		}
		return new BazaarImpl(item, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#newAuction(org.apache.bazaar.Item,
	 * java.util.Calendar, java.util.Calendar, java.lang.Double)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate,
			final Double reservePrice) throws BazaarException {
		if (!endDate.after(startDate)) {
			throw new BazaarException(Messages.newInstance(Locale.getDefault())
					.findMessage(Messages.AUCTION_ENDDATE_INVALID_MESSAGE_KEY, new Object[] { endDate, startDate }));
		}
		return new BazaarImpl(item, startDate, endDate, reservePrice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newName()
	 */
	@Override
	public Name newName() {
		return new NameImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newName(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Name newName(final String firstName, final String lastName) {
		return new NameImpl(firstName, lastName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newAddress()
	 */
	@Override
	public Address newAddress() {
		return new AddressImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newAddress(java.lang.String,
	 * java.lang.String, org.apache.bazaar.State, java.lang.Integer)
	 */
	@Override
	public Address newAddress(final String street, final String city, final State state, final Integer zipcode) {
		return new AddressImpl(street, city, state, zipcode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newBidder()
	 */
	@Override
	public Bidder newBidder() {
		return new BidderImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newBidder(org.apache.bazaar.Name,
	 * org.apache.bazaar.Address, org.apache.bazaar.Address)
	 */
	@Override
	public Bidder newBidder(final Name name, final Address billingAddress, final Address shippingAddress) {
		return new BidderImpl(name, billingAddress, shippingAddress);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newImage(java.lang.String,
	 * org.apache.bazaar.Image.MimeType,
	 * java.io.InputStream)
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
	 * 
	 * @see org.apache.bazaar.BazaarManager#newItem()
	 */
	@Override
	public Item newItem() {
		return new ItemImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newItem(java.lang.String,
	 * java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Item newItem(final String name, final String description, final Category category) {
		return new ItemImpl(name, description, category);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newCategory()
	 */
	@Override
	public Category newCategory() {
		return new CategoryImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newCategory(java.lang.String,
	 * java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Category newCategory(final String name, final String description, final Category parent) {
		return new CategoryImpl(name, description, parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAuction(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Bazaar findBazaar(final Identifier identifier) throws BazaarNotFoundException, BazaarException {
		BazaarManagerImpl.LOGGER.entering("findAuction", identifier);
		final Bazaar bazaar;
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			bazaar = manager.find(BazaarImpl.class, identifier);
			transaction.commit();
			if (bazaar == null) {
				final BazaarNotFoundException exception = new BazaarNotFoundException();
				BazaarManagerImpl.LOGGER.throwing("findAuction", exception);
				throw exception;
			}
		}
		finally {
			manager.close();
		}
		BazaarManagerImpl.LOGGER.exiting("findAuction", bazaar);
		return bazaar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAllAuctions()
	 */
	@Override
	public Set<Bazaar> findAllBazaars() throws BazaarException {
		BazaarManagerImpl.LOGGER.entering("findAllAuctions");
		final Set<Bazaar> bazaars = new HashSet<Bazaar>(0);
		BazaarManagerImpl.LOGGER.exiting("findAllAuctions", bazaars);
		return Collections.unmodifiableSet(bazaars);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findBid(org.apache.bazaar.Identifier)
	 */
	@Override
	public Bid findBid(final Identifier identifier) throws BidNotFoundException, BazaarException {
		final Bid bid;
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			bid = manager.find(BidImpl.class, identifier);
			transaction.commit();
			if (bid == null) {
				throw new BidNotFoundException(); // TODO localize message
			}
		}
		finally {
			manager.close();

		}
		return bid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAllBids()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Bid> findAllBids() throws BazaarException {
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_ALL_BIDS_QUERY);
		final Set<Bid> bids;
		try {
			transaction.begin();
			bids = new HashSet<Bid>(query.getResultList().size());
			bids.addAll(query.getResultList());
			transaction.commit();
		}
		finally {
			manager.close();
		}
		return Collections.unmodifiableSet(bids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findAllBids(org.apache.bazaar.Bidder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Bid> findAllBids(final Bidder bidder) throws BazaarException {
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_ALL_BIDS_BY_BIDDER_QUERY);
		query.setParameter(BazaarManagerImpl.SELECT_ALL_BIDS_BY_BIDDER_QUERY_PARAMETER_NAME,
				bidder.getIdentifier().getValue());
		final Set<Bid> bids;
		try {
			transaction.begin();
			bids = new HashSet<Bid>(query.getResultList().size());
			bids.addAll(query.getResultList());
			transaction.commit();
		}
		finally {
			manager.close();
		}
		return Collections.unmodifiableSet(bids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findRootCategory()
	 */
	@Override
	public Category findRootCategory() throws BazaarException {
		if (this.rootCategory == null) {
			final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
			final EntityTransaction transaction = manager.getTransaction();
			final Category rootCategory;
			try {
				final Identifier identifier = Identifier
						.fromValue(Configuration.newInstance().getProperty(Configuration.ROOT_CATEGORY_IDENTIFIER));
				transaction.begin();
				rootCategory = manager.find(CategoryImpl.class, identifier);
				transaction.commit();
				if (rootCategory == null) {
					final CategoryNotFoundException exception = new CategoryNotFoundException(BazaarManagerImpl.MESSAGES
							.findMessage(Messages.UNABLE_TO_FIND_CATEGORY_MESSAGE_KEY, new Object[] { identifier }));
					BazaarManagerImpl.LOGGER.throwing("findCategory", exception);
					throw exception;
				}
				this.rootCategory = rootCategory;
			}
			finally {
				manager.close();
			}
		}
		return this.rootCategory;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.bazaar.Bazaar#findCategory(org.apache.bazaar.Identifier)
	 */
	@Override
	public Category findCategory(final Identifier identifier) throws CategoryNotFoundException, BazaarException {
		BazaarManagerImpl.LOGGER.entering("findCategory", identifier);
		final Category category;
		// check for ROOT identifier
		if (Configuration.newInstance().getProperty(Configuration.ROOT_CATEGORY_IDENTIFIER)
				.equals(identifier.getValue()) && (this.rootCategory != null)) {
			category = this.rootCategory;
		}
		else {
			final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
			final EntityTransaction transaction = manager.getTransaction();
			try {
				transaction.begin();
				category = manager.find(CategoryImpl.class, identifier);
				transaction.commit();
				if (category == null) {
					final CategoryNotFoundException exception = new CategoryNotFoundException(
							BazaarManagerImpl.MESSAGES.findMessage(Messages.UNABLE_TO_FIND_CATEGORY_MESSAGE_KEY,
									new Object[] { identifier }));
					BazaarManagerImpl.LOGGER.throwing("findCategory", exception);
					throw exception;
				}
			}
			finally {
				manager.close();
			}
		}
		BazaarManagerImpl.LOGGER.exiting("findCategory", category);
		return category;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findCategory(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Set<Category> findCategories(final String name) throws BazaarException {
		BazaarManagerImpl.LOGGER.entering("findCategories", name);
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_CATEGORY_BY_NAME_QUERY);
		query.setParameter(BazaarManagerImpl.SELECT_CATEGORY_BY_NAME_QUERY_PARAMETER_NAME, name);
		transaction.begin();
		final Set<Category> categories = new HashSet<Category>(query.getResultList().size());
		categories.addAll(query.getResultList());
		transaction.commit();
		manager.close();
		BazaarManagerImpl.LOGGER.exiting("findCategories", categories);
		return Collections.unmodifiableSet(categories);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findAllCategories()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Set<Category> findAllCategories() throws BazaarException {
		BazaarManagerImpl.LOGGER.entering("findAllCategories");
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_ALL_CATEGORIES_QUERY);
		final Set<Category> categories = new HashSet<Category>(query.getResultList().size());
		categories.addAll(query.getResultList());
		transaction.commit();
		manager.close();
		BazaarManagerImpl.LOGGER.exiting("findAllCategories", categories);
		return Collections.unmodifiableSet(categories);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findItem(org.apache.bazaar.Identifier)
	 */
	@Override
	public Item findItem(final Identifier identifier) throws ItemNotFoundException, BazaarException {
		BazaarManagerImpl.LOGGER.entering("findItem", identifier);
		final Item item;
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			item = manager.find(ItemImpl.class, identifier);
			transaction.commit();
			if (item == null) {
				final ItemNotFoundException exception = new ItemNotFoundException(BazaarManagerImpl.MESSAGES
						.findMessage(Messages.UNABLE_TO_FIND_ITEM_MESSAGE_KEY, new Object[] { identifier }));
				BazaarManagerImpl.LOGGER.throwing("findItem", exception);
				throw exception;
			}
		}
		finally {
			manager.close();
		}
		BazaarManagerImpl.LOGGER.exiting("findItem", item);
		return item;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findItem(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Set<Item> findItems(final String name) throws BazaarException {
		BazaarManagerImpl.LOGGER.entering("findItems", name);
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Set<Item> items;
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_ITEMS_BY_NAME_QUERY);
		query.setParameter(BazaarManagerImpl.SELECT_ITEMS_BY_NAME_QUERY_PARAMETER_NAME, name);
		try {
			transaction.begin();
			items = new HashSet<Item>(query.getResultList().size());
			items.addAll(query.getResultList());
			transaction.commit();
		}
		finally {
			manager.close();
		}
		BazaarManagerImpl.LOGGER.exiting("findItems", items);
		return Collections.unmodifiableSet(items);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findItems(org.apache.bazaar.Category)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Set<Item> findItems(final Category category) throws BazaarException {
		BazaarManagerImpl.LOGGER.entering("findItems", category);
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Set<Item> items;
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_ALL_ITEMS_BY_CATEGORY_QUERY);
		query.setParameter(BazaarManagerImpl.SELECT_ALL_ITEMS_BY_CATEGORY_QUERY_PARAMETER_NAME, category);
		try {
			transaction.begin();
			items = new HashSet<Item>(query.getResultList().size());
			items.addAll(query.getResultList());
			transaction.commit();
		}
		finally {
			manager.close();
		}
		BazaarManagerImpl.LOGGER.exiting("findItems", items);
		return Collections.unmodifiableSet(items);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findAllItems()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Set<Item> findAllItems() throws BazaarException {
		BazaarManagerImpl.LOGGER.entering("findAllItems");
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Set<Item> items;
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_ALL_ITEMS_QUERY);
		try {
			transaction.begin();
			items = new HashSet<Item>(query.getResultList().size());
			items.addAll(query.getResultList());
			transaction.commit();
		}
		finally {
			manager.close();
		}
		BazaarManagerImpl.LOGGER.exiting("findAllItems", items);
		return Collections.unmodifiableSet(items);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findBidder(org.apache.bazaar.Identifier)
	 */
	@Override
	public Bidder findBidder(final Identifier identifier) throws BidderNotFoundException, BazaarException {
		BazaarManagerImpl.LOGGER.entering("findBidder", identifier);
		final Bidder bidder;
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			bidder = manager.find(BidderImpl.class, identifier);
			transaction.commit();
			if (bidder == null) {
				final BidderNotFoundException exception = new BidderNotFoundException(BazaarManagerImpl.MESSAGES
						.findMessage(Messages.UNABLE_TO_FIND_BIDDER_MESSAGE_KEY, new Object[] { identifier }));
				BazaarManagerImpl.LOGGER.throwing("findBidder", exception);
				throw exception;
			}
		}
		finally {
			manager.close();
		}
		BazaarManagerImpl.LOGGER.exiting("findBidder", bidder);
		return bidder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findBidder(org.apache.bazaar.Name)
	 */
	@Override
	public Set<Bidder> findBidders(final Name name) throws BazaarException {
		BazaarManagerImpl.LOGGER.entering("findBidders", name);
		final EntityManager manager = BazaarManagerImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Query query = manager.createQuery(BazaarManagerImpl.SELECT_BIDDER_BY_NAME_QUERY);
		query.setParameter(BazaarManagerImpl.SELECT_BIDDER_BY_NAME_QUERY_PARAMETER_NAME, name);
		transaction.begin();
		final Set<Bidder> bidders = new HashSet<Bidder>(query.getResultList().size());
		bidders.addAll(bidders);
		transaction.commit();
		manager.close();
		BazaarManagerImpl.LOGGER.exiting("findBidders", bidders);
		return Collections.unmodifiableSet(bidders);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bazaar#findAllBidders()
	 */
	@Override
	public Set<Bidder> findAllBidders() throws BazaarException {
		return Collections.unmodifiableSet(new HashSet<Bidder>(0));
	}

}
