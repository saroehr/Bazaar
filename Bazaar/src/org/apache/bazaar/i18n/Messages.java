/*
 * Messages.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.cache.Cache;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Category;
import org.apache.bazaar.Item;

/**
 * Messages provides a utility class for retrieving
 * localized messages.
 */
public final class Messages {

	// declare members

	/**
	 * The base resource bundle name used by this class
	 */
	public static final String RESOURCE_BUNDLE_BASE_NAME = "org.apache.bazaar.i18n.messages";

	private static final Cache<Locale, Messages> CACHE;
	static {
		final MutableConfiguration<Locale, Messages> configuration = new MutableConfiguration<Locale, Messages>();
		configuration.setStoreByValue(false);
		configuration.setTypes(Locale.class, Messages.class);
		configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_DAY));
		CACHE = org.apache.bazaar.cache.Cache.newInstance(configuration, Messages.class.getName(), Locale.class,
				Messages.class);
	}

	/**
	 * Key for lookup of unable to find category message
	 */
	public static final String UNABLE_TO_FIND_CATEGORY_MESSAGE_KEY = Category.class.getName() + "."
			+ "unabletofindcategory";
	/**
	 * Key for lookup of unable to find item message
	 */
	public static final String UNABLE_TO_FIND_ITEM_MESSAGE_KEY = Item.class.getName() + "." + "unabletofinditem";

	/**
	 * Key for lookup of unable to find bidder message
	 */
	public static final String UNABLE_TO_FIND_BIDDER_MESSAGE_KEY = Bidder.class.getName() + "." + "unabletofindbidder";

	/**
	 * Key for lookup of unable to find bid message
	 */
	public static final String UNABLE_TO_FIND_BID_MESSAGE_KEY = Bid.class.getName() + "." + "unabletofindbid";
	/**
	 * Key for lookup of unable to delete bidder due to auction found
	 * message
	 */
	public static final String UNABLE_TO_DELETE_BIDDER_AUCTION_FOUND_MESSAGE_KEY = Bidder.class.getName() + "."
			+ "auctionfoundexception";
	/**
	 * Key for lookup of unable to find auction message
	 */
	public static final String UNABLE_TO_FIND_AUCTION_MESSAGE_KEY = Bazaar.class.getName() + "."
			+ "unabletofindauction";
	/**
	 * Key for lookup of auction expired message
	 */
	public static final String AUCTION_HAS_EXPIRED_MESSAGE_KEY = Bazaar.class.getName() + "."
			+ "auctionexpiredexception";
	/**
	 * Key for lookup of auction invalid end date message
	 */
	public static final String AUCTION_ENDDATE_INVALID_MESSAGE_KEY = Bazaar.class.getName() + "."
			+ "invalidenddateexception";

	/**
	 * Key for lookup of invalid category message
	 */
	public static final String UNABLE_TO_CREATE_CATEGORY_MESSAGE_KEY = Category.class.getName() + "."
			+ "unabletocreatecategory";

	/**
	 * Key for lookup of transaction failure message
	 */
	public static final String TRANSACTION_FAILURE_MESSAGE_KEY = "org.apache.bazaar.jpa.EntityManager" + "."
			+ "failure";

	/**
	 * Key for lookup of unsupported method exception message
	 */
	public static final String UNSUPPORTED_METHOD_MESSAGE_KEY = "org.apache.bazaar.web.RestWebService.unsupportedmethod";

	private final ResourceBundle resourceBundle;

	// declare constructors

	/**
	 * Constructor for Messages
	 * 
	 * @param locale
	 *        The Locale for bundle instance
	 */
	private Messages(final Locale locale) {
		super();
		this.resourceBundle = ResourceBundle.getBundle(Messages.RESOURCE_BUNDLE_BASE_NAME, locale);
	}

	// declare methods

	/**
	 * Factory method for obtaining instance.
	 * 
	 * @param locale
	 *        The Locale for instance
	 * @return Instance of class
	 */
	public static Messages newInstance(final Locale locale) {
		final Messages messages;
		// check cache
		if (Messages.CACHE.containsKey(locale)) {
			messages = Messages.CACHE.get(locale);
		}
		else {
			messages = new Messages(locale);
			Messages.CACHE.put(locale, messages);
		}
		return messages;
	}

	/**
	 * Returns message instance for key. If no message for key
	 * can be found, then the key itself is returned as the message.
	 * This eliminates runtime exceptions if an incorrect key is specified.
	 * 
	 * @param key
	 *        The key for message
	 * @return The message for key or key value if no value for key can
	 *         be found
	 */
	public String findMessage(final String key) {
		try {
			return this.resourceBundle.getString(key);
		}
		catch (final NullPointerException | MissingResourceException | ClassCastException exception) {
			return key;
		}
	}

	/**
	 * Returns formatted parameterized message instance for key. If no message
	 * for key
	 * can be found, then the key itself is returned as the message.
	 * This eliminates runtime exceptions if an incorrect key is specified
	 * 
	 * @param key
	 *        The key for parameterized
	 * @param parameters
	 *        The parameter values for message
	 * @return The formatted parameterized message for key or key value if no
	 *         value for key can be found
	 */
	public String findMessage(final String key, final Object[] parameters) {
		try {
			return MessageFormat.format(this.resourceBundle.getString(key), parameters);
		}
		catch (final NullPointerException | MissingResourceException | ClassCastException
				| IllegalArgumentException exception) {
			return key;
		}
	}

}
