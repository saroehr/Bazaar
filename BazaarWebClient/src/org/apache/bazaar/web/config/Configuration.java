/*
 * Configuration.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 17, 2016 at 9:31:09 PM
 */
package org.apache.bazaar.web.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Category;
import org.apache.bazaar.Item;
import org.apache.bazaar.web.BazaarCollectionMessageBodyReaderImpl;
import org.apache.bazaar.web.BazaarCollectionMessageBodyWriterImpl;
import org.apache.bazaar.web.BazaarMessageBodyReaderImpl;
import org.apache.bazaar.web.BazaarMessageBodyWriterImpl;
import org.apache.bazaar.web.BidCollectionMessageBodyReaderImpl;
import org.apache.bazaar.web.BidCollectionMessageBodyWriterImpl;
import org.apache.bazaar.web.BidMessageBodyReaderImpl;
import org.apache.bazaar.web.BidMessageBodyWriterImpl;
import org.apache.bazaar.web.BidderCollectionMessageBodyReaderImpl;
import org.apache.bazaar.web.BidderCollectionMessageBodyWriterImpl;
import org.apache.bazaar.web.BidderMessageBodyReaderImpl;
import org.apache.bazaar.web.BidderMessageBodyWriterImpl;
import org.apache.bazaar.web.CategoryCollectionMessageBodyReaderImpl;
import org.apache.bazaar.web.CategoryCollectionMessageBodyWriterImpl;
import org.apache.bazaar.web.CategoryMessageBodyReaderImpl;
import org.apache.bazaar.web.CategoryMessageBodyWriterImpl;
import org.apache.bazaar.web.ItemCollectionMessageBodyReaderImpl;
import org.apache.bazaar.web.ItemCollectionMessageBodyWriterImpl;
import org.apache.bazaar.web.ItemMessageBodyReaderImpl;
import org.apache.bazaar.web.ItemMessageBodyWriterImpl;
import org.apache.bazaar.web.ThrowableMessageBodyReaderImpl;
import org.apache.bazaar.web.ThrowableMessageBodyWriterImpl;
import org.apache.bazaar.web.VersionCollectionMessageBodyReaderImpl;
import org.apache.bazaar.web.VersionCollectionMessageBodyWriterImpl;
import org.apache.bazaar.web.VersionMessageBodyReaderImpl;
import org.apache.bazaar.web.VersionMessageBodyWriterImpl;

/**
 * Configuration extends Configuration and declares the methods an
 * implementation must provide
 */
public class Configuration extends org.apache.bazaar.config.Configuration {

	// declare members

	/**
	 * The key for retrieval of the {@link Bazaar} web service url value
	 */
	public static final String BAZAAR_REST_WEB_SERVICE_URL = Bazaar.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Item} web service url value
	 */
	public static final String ITEM_REST_WEB_SERVICE_URL = Item.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Category} web service url value
	 */
	public static final String CATEGORY_REST_WEB_SERVICE_URL = Category.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Bidder} web service url value
	 */
	public static final String BIDDER_REST_WEB_SERVICE_URL = Bidder.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Bid} web service url value
	 */
	public static final String BID_REST_WEB_SERVICE_URL = Bid.class.getName() + "." + "restwebservice.url";

	/**
	 * List of public provider classes
	 */
	public static final Class<?>[] PROVIDER_CLASSES = new Class<?>[] { BazaarMessageBodyReaderImpl.class,
			BazaarMessageBodyWriterImpl.class, BazaarCollectionMessageBodyReaderImpl.class,
			BazaarCollectionMessageBodyWriterImpl.class, CategoryMessageBodyReaderImpl.class,
			CategoryMessageBodyWriterImpl.class, CategoryCollectionMessageBodyReaderImpl.class,
			CategoryCollectionMessageBodyWriterImpl.class, ItemMessageBodyReaderImpl.class,
			ItemMessageBodyWriterImpl.class, ItemCollectionMessageBodyReaderImpl.class,
			ItemCollectionMessageBodyWriterImpl.class, BidderMessageBodyReaderImpl.class,
			BidderMessageBodyWriterImpl.class, BidderCollectionMessageBodyReaderImpl.class,
			BidderCollectionMessageBodyWriterImpl.class, BidMessageBodyReaderImpl.class, BidMessageBodyWriterImpl.class,
			BidCollectionMessageBodyReaderImpl.class, BidCollectionMessageBodyWriterImpl.class,
			VersionMessageBodyWriterImpl.class, VersionMessageBodyReaderImpl.class,
			VersionCollectionMessageBodyWriterImpl.class, VersionCollectionMessageBodyReaderImpl.class,
			ThrowableMessageBodyReaderImpl.class, ThrowableMessageBodyWriterImpl.class };

	protected static final Properties PROPERTIES;

	static {
		try (final InputStream inputStream = Configuration.class
				.getResourceAsStream("/org/apache/bazaar/web/config/configuration.properties")) {
			if (inputStream == null) {
				throw new ExceptionInInitializerError();
			}
			PROPERTIES = new Properties(org.apache.bazaar.config.Configuration.PROPERTIES);
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
	protected Configuration(@NotNull final Properties properties) {
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
