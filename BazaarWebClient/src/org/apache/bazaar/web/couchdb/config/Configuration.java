/*
 * Configuration.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 17, 2016 at 9:31:09 PM
 */
package org.apache.bazaar.web.couchdb.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Category;
import org.apache.bazaar.Item;
import org.apache.bazaar.web.couchdb.DatabaseMessageBodyReaderImpl;

/**
 * Configuration extends org.apache.bazaar.config.Configuration to encapsulate
 * the additional couchdb provider properties
 */
public final class Configuration extends org.apache.bazaar.web.config.Configuration {

	// declare members

	/**
	 * Key for retrieval of value for initialization of couchdb datastores if
	 * value is true
	 */
	public static final String INITIALIZE_COUCHDB = "org.apache.bazaar.couchdb.initialize";

	/**
	 * The key for retrieval of the {@link Bazaar} web service url value
	 */
	public static final String BAZAAR_COUCHDB_URL = Bazaar.class.getName() + "." + "couchdb.url";

	/**
	 * The key for retrieval of the {@link Item} web service url value
	 */
	public static final String ITEM_COUCHDB_URL = Item.class.getName() + "." + "couchdb.url";

	/**
	 * The key for retrieval of the {@link Category} web service url value
	 */
	public static final String CATEGORY_COUCHDB_URL = Category.class.getName() + "." + "couchdb.url";

	/**
	 * The key for retrieval of the {@link Bidder} web service url value
	 */
	public static final String BIDDER_COUCHDB_URL = Bidder.class.getName() + "." + "couchdb.url";

	/**
	 * The key for retrieval of the {@link Bid} web service url value
	 */
	public static final String BID_COUCHDB_URL = Bid.class.getName() + "." + "couchdb.url";
	/**
	 * List of provider classes
	 */
	public static final Class<?>[] PROVIDER_CLASSES;

	static {
		final Set<Class<?>> providers = new HashSet<Class<?>>(
				org.apache.bazaar.web.config.Configuration.PROVIDER_CLASSES.length);
		providers.addAll(Arrays.asList(org.apache.bazaar.web.config.Configuration.PROVIDER_CLASSES));
		providers.add(DatabaseMessageBodyReaderImpl.class);
		PROVIDER_CLASSES = providers.toArray(new Class<?>[] {});
	}

	private static final Properties PROPERTIES;

	static {
		try (final InputStream inputStream = Configuration.class
				.getResourceAsStream("/org/apache/bazaar/web/couchdb/config/configuration.properties")) {
			if (inputStream == null) {
				throw new ExceptionInInitializerError();
			}
			PROPERTIES = new Properties(org.apache.bazaar.web.config.Configuration.PROPERTIES);
			Configuration.PROPERTIES.load(new BufferedReader(new InputStreamReader(inputStream,
					Charset.forName(org.apache.bazaar.config.Configuration.DEFAULT_ENCODING))));
		}
		catch (final IOException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	// declare constructors

	/**
	 * Constructor for Configuration
	 *
	 * @param properties The configuration properties
	 */
	private Configuration(@NotNull final Properties properties) {
		super(properties);
	}

	// declare methods

	/**
	 * Factory method for obtaining instance
	 *
	 * @return Instance of configuration
	 */
	public static @NotNull org.apache.bazaar.config.Configuration newInstance() {
		return new Configuration(Configuration.PROPERTIES);
	}

}
