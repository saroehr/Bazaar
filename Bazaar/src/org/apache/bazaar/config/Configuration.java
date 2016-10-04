/*
 * Configuration.java
 * Created On: Sep 30, 2016 at 1:15:25 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;

/**
 * Configuration extends org.apache.bazaar.config.Configuration to encapsulate
 * the additional web provider properties
 */
public class Configuration {

	// declare members

	/**
	 * The image default buffer size key
	 */
	public static final String DEFAULT_BYTE_ARRAY_BUFFER_SIZE = "org.apache.bazaar.bytearray.defaultBufferSize";

	/**
	 * The key for retrieval of the ROOT Category identifier value
	 */
	public static final String ROOT_CATEGORY_IDENTIFIER = "org.apache.bazaar.Category.rootcategoryidentifier";

	/**
	 * Default encoding of UTF-8 UTF-8 encoding string
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * The key for retrieval of the ROOT Category name value
	 */
	public static final String ROOT_CATEGORY_NAME = "org.apache.bazaar.Category.rootcategoryname";

	/**
	 * The key for retrieval of the ROOT Category description value
	 */
	public static final String ROOT_CATEGORY_DESCRIPTION = "org.apache.bazaar.Category.rootcategorydescription";

	protected static final Logger LOGGER = Logger.newInstance(Configuration.class);

	protected static final Properties PROPERTIES;

	static {
		try (final InputStream inputStream = Configuration.class
				.getResourceAsStream("/org/apache/bazaar/config/configuration.properties")) {
			if (inputStream == null) {
				throw new ExceptionInInitializerError();
			}
			PROPERTIES = new Properties();
			Configuration.PROPERTIES
					.load(new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8"))));
		}
		catch (final IOException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private final Properties properties;

	// declare constructors

	/**
	 * Constructor for Configuration
	 *
	 * @param properties The configuration properties
	 */
	protected Configuration(@NotNull final Properties properties) {
		super();
		this.properties = properties;
	}

	// declare methods

	/**
	 * Factory method for obtaining instance
	 *
	 * @return Instance of Configuration with default properties taken from the
	 *         org.apache.bazaar.config.configuration.properties file
	 */
	public static @NotNull Configuration newInstance() {
		return new Configuration(Configuration.PROPERTIES);
	}

	/**
	 * Returns property value for key
	 *
	 * @param key The key for value
	 * @return The value for key
	 * @throws PropertyNotFoundException if no key exists
	 */
	public @NotNull String getProperty(final String key) throws PropertyNotFoundException {
		final String property = this.properties.getProperty(key);
		if (property == null) {
			throw new PropertyNotFoundException(key);
		}
		return property;
	}

}
