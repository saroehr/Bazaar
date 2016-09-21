/*
 * Messages.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar.web.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.cache.Cache;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Messages provides a utility class for retrieving
 * localized messages.
 */
public final class Messages {

	// declare members

	/**
	 * The base resource bundle name used by this class
	 */
	public static final String RESOURCE_BUNDLE_BASE_NAME = "org.apache.bazaar.web.i18n.messages";

	/**
	 * The key for lookup of welcome page title
	 */
	public static final String WELCOME_JSP_TITLE = "org.apache.bazaar.web.welcome.title";

	/**
	 * The key for lookup of welcome page header
	 */
	public static final String WELCOME_JSP_HEADER = "org.apache.bazaar.web.welcome.header";

	/**
	 * The key for lookup of welcome page login header
	 */
	public static final String WELCOME_JSP_LOGIN_HEADER = "org.apache.bazaar.web.welcome.login.header";

	private static final Cache<Locale, Messages> CACHE;
	static {
		final MutableConfiguration<Locale, Messages> configuration = new MutableConfiguration<Locale, Messages>();
		configuration.setStoreByValue(false);
		configuration.setTypes(Locale.class, Messages.class);
		configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_DAY));
		CACHE = org.apache.bazaar.cache.Cache.newInstance(configuration, Messages.class.getName(), Locale.class,
				Messages.class);
	}

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
