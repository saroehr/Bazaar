/*
 * BazaarManager.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Set;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.Image.MimeType;
import org.apache.bazaar.cache.Cache;

/**
 * BazaarManager
 */
public interface BazaarManager {

	// declare members

	// declare methods

	/**
	 * Returns new instance
	 * 
	 * @return Instance of BazaarManager
	 * @throws BazaarException if the instance could
	 *         not be returned.
	 */
	public static BazaarManager newInstance() throws BazaarException {
		final BazaarManager bazaarManager;
		final MutableConfiguration<Long, BazaarManager> configuration = new MutableConfiguration<Long, BazaarManager>();
		configuration.setStoreByValue(false);
		configuration.setTypes(Long.class, BazaarManager.class);
		configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
		final Cache<Long, BazaarManager> cache = Cache.newInstance(configuration, BazaarManager.class.getName(),
				Long.class, BazaarManager.class);
		if (cache.containsKey(Thread.currentThread().getId())) {
			bazaarManager = cache.get(Thread.currentThread().getId());
		}
		else {
			try {
				final Class<?> bazaarManagerClass = Class.forName("org.apache.bazaar.BazaarManagerImpl");
				final Method method = bazaarManagerClass.getDeclaredMethod("newInstance", new Class[] {});
				method.setAccessible(true);
				bazaarManager = (BazaarManager)method.invoke(new Object[] {});
				cache.put(Thread.currentThread().getId(), bazaarManager);
			}
			catch (final NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | ClassNotFoundException exception) {
				throw new BazaarException(exception);
			}
		}
		return bazaarManager;
	}

	/**
	 * Factory method creates new Bazaar.
	 * End date must be beyond start date.
	 * 
	 * @param item The item to be auctioned
	 * @param startDate The auction start date
	 * @param endDate The auction end date
	 * @return Instance of Bazaar
	 * @throws BazaarException if the instance could
	 *         not be created
	 */
	public @NotNull Bazaar newBazaar(@NotNull final Item item, @NotNull @Future final Calendar startDate,
			@Future final Calendar endDate) throws BazaarException;

	/**
	 * Factory method creates new Bazaar with reserve price
	 * End date must be beyond start date.
	 * 
	 * @param item The item to be auctioned
	 * @param startDate The auction start date
	 * @param endDate The auction end date
	 * @param reservePrice The auction reserve price
	 * @return Instance of Bazaar with reserve price
	 * @throws BazaarException if the instance could not be created
	 */
	public @NotNull Bazaar newBazaar(@NotNull final Item item, @NotNull @Future final Calendar startDate,
			@NotNull @Future final Calendar endDate, @NotNull final Double reservePrice) throws BazaarException;

	/**
	 * Factory method creates a new Name
	 * 
	 * @return The Name instance
	 */
	public @NotNull Name newName();

	/**
	 * Factory method creates a new Name
	 * 
	 * @param firstName The first name
	 * @param lastName The last name
	 * @return The Name instance
	 */
	public @NotNull Name newName(@NotNull @Size(min = 1, max = 255) final String firstName,
			@NotNull @Size(min = 1, max = 255) final String lastName);

	/**
	 * Factory method creates a new Address
	 * 
	 * @return The Address instance
	 */
	public @NotNull Address newAddress();

	/**
	 * Factory method creates a new Address
	 * 
	 * @param street The address street
	 * @param city The address city
	 * @param state The address State
	 * @param zipcode The address zipcode
	 * @return The Address instance
	 */
	public @NotNull Address newAddress(@NotNull @Size(min = 1, max = 255) final String street,
			@NotNull @Size(min = 1, max = 255) final String city, @NotNull final State state, @NotNull Integer zipcode);

	/**
	 * Factory method creates a new Bidder
	 * 
	 * @return The Bidder instance
	 */
	public @NotNull Bidder newBidder();

	/**
	 * Factory method creates a new Bidder
	 * 
	 * @param name The bidder name
	 * @param billingAddress The billing address of bidder
	 * @param shippingAddress The shipping address of bidder
	 * @return The Bidder instance
	 */
	public @NotNull Bidder newBidder(@NotNull final Name name, @NotNull final Address billingAddress,
			@NotNull final Address shippingAddress);

	/**
	 * Factory method creates a new Image
	 * 
	 * @param name The image name
	 * @param mimeType The mime type of image
	 * @param inputStream The inputStream to read data from
	 * @return The Image instance
	 * @throws BazaarException if the instance
	 *         could not be created
	 */
	public @NotNull Image newImage(@NotNull @Size(min = 1, max = 255) final String name,
			@NotNull final MimeType mimeType, @NotNull final InputStream inputStream) throws BazaarException;

	/**
	 * Factory method creates a new Item
	 * 
	 * @return The Item instance
	 */
	public @NotNull Item newItem();

	/**
	 * Factory method creates a new Item
	 * 
	 * @param name The name of item
	 * @param description The description of item
	 * @param category The category for item
	 * @return The Item instance
	 */
	public @NotNull Item newItem(@NotNull @Size(min = 1, max = 255) final String name,
			@NotNull @Size(min = 1, max = 255) final String description, @NotNull final Category category);

	/**
	 * Factory method creates a new Category
	 * 
	 * @return The Category instance
	 */
	public @NotNull Category newCategory();

	/**
	 * Factory method creates a new Category
	 * 
	 * @param name The category name
	 * @param description The category description
	 * @param parent The parent category
	 * @return The Category instance
	 */
	public @NotNull Category newCategory(@NotNull @Size(min = 1, max = 255) final String name,
			@NotNull @Size(min = 1, max = 255) final String description, @NotNull final Category parent);

	/**
	 * Returns auction with identifier
	 * 
	 * @param identifier The identifier of auction
	 * @return The auction with identifier
	 * @throws BazaarNotFoundException if no auction
	 *         with identifier exists
	 * @throws BazaarException if the operation could not be completed
	 */
	public Bazaar findBazaar(@NotNull final Identifier identifier) throws BazaarNotFoundException, BazaarException;

	/**
	 * Returns all auctions
	 * 
	 * @return The unmodifiable set of all auctions
	 * @throws BazaarException if the operation
	 *         could not be completed
	 */
	public @NotNull Set<Bazaar> findAllBazaars() throws BazaarException;

	/**
	 * Returns root category. The root category
	 * is the parent of all categories. It is
	 * the only category whose parent category
	 * is itself
	 * 
	 * @return The root category instance.
	 * @throws BazaarException if the operation
	 *         could not be completed
	 */
	public @NotNull Category findRootCategory() throws BazaarException;

	/**
	 * Returns category with identifier
	 * 
	 * @param identifier
	 *        The identifier of category
	 * @return The category with identifier
	 * @throws CategoryNotFoundException
	 *         if no category with identifier exists
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Category findCategory(@NotNull final Identifier identifier)
			throws CategoryNotFoundException, BazaarException;

	/**
	 * Returns set of categories with name
	 * 
	 * @param name
	 *        The name of categories to find
	 * @return The unmodifiable set of all categories with name
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Set<Category> findCategories(@NotNull @Size(min = 1, max = 255) final String name)
			throws BazaarException;

	/**
	 * Returns set of all categories
	 * 
	 * @return The unmodifiable set of all categories
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Set<Category> findAllCategories() throws BazaarException;

	/**
	 * Returns item with identifier.
	 *
	 * @param identifier
	 *        The identifier of item to return
	 * @return The Item with identifier
	 * @throws ItemNotFoundException
	 *         if no item with identifier exists
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Item findItem(@NotNull final Identifier identifier) throws ItemNotFoundException, BazaarException;

	/**
	 * Returns set of all items with name
	 * 
	 * @param name
	 *        The name of items to find
	 * @return The unmodifiable set of all items with name or empty set
	 *         if no items with name found
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Item> findItems(@NotNull @Size(min = 1, max = 255) final String name) throws BazaarException;

	/**
	 * Returns set of all items with category
	 * 
	 * @param category The category to find items
	 * @return The unmodifiable set of all items with category
	 *         or empty set if no items with category found
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Item> findItems(@NotNull final Category category) throws BazaarException;

	/**
	 * Returns set of all items
	 * 
	 * @return The unmodifiable set of all items or empty set
	 *         if no items
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Set<Item> findAllItems() throws BazaarException;

	/**
	 * Returns bidder with identifier
	 * 
	 * @param identifier
	 *        The identifier of bidder to return
	 * @return The bidder with identifier
	 * @throws BidderNotFoundException
	 *         if no bidder with identifier exists
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Bidder findBidder(@NotNull Identifier identifier) throws BidderNotFoundException, BazaarException;

	/**
	 * Returns set of bidders with name
	 * 
	 * @param name
	 *        The name of bidders to find
	 * @return The unmodifiable set of all bidders with name
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Set<Bidder> findBidders(@NotNull final Name name) throws BazaarException;

	/**
	 * Returns set of all bidders
	 * 
	 * @return The unmodifiable set of all bidders
	 * @throws BazaarException
	 *         if the operation could not be completed
	 */
	public @NotNull Set<Bidder> findAllBidders() throws BazaarException;

	/**
	 * Returns bid with identifier.
	 * 
	 * @param identifier The identifier for bid
	 * @return The Bid with identifier
	 * @throws BidNotFoundException if no bid with
	 *         identifier exists
	 * @throws BazaarException if the operation could not
	 *         be completed.
	 */
	public @NotNull Bid findBid(@NotNull final Identifier identifier) throws BidNotFoundException, BazaarException;

	/**
	 * Returns set of all bids
	 * 
	 * @return The unmodifiable set of all bids
	 * @throws BazaarException if the operation could not
	 *         be completed
	 */
	public @NotNull Set<Bid> findAllBids() throws BazaarException;

	/**
	 * Returns set of all bids by bidder
	 * 
	 * @param bidder The bidder to find bids for
	 * @return The unmodifiable set of all bids for
	 *         bidder
	 * @throws BazaarException if the operation could
	 *         not be completed
	 */
	public @NotNull Set<Bid> findAllBids(@NotNull final Bidder bidder) throws BazaarException;
}
