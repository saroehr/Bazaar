/*
 * Configuration.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Configuration defines the support provided for runtime configuration
 * attributes
 * to be specified externally.
 */
public abstract class Configuration {

	// declare members

	/**
	 * The image default buffer size key
	 */
	public static final String DEFAULT_BYTE_ARRAY_BUFFER_SIZE = "org.apache.bazaar.bytearray.defaultBufferSize";

	/**
	 * The key for retrieval of the ROOT Category
	 * identifier value
	 */
	public static final String ROOT_CATEGORY_IDENTIFIER = "org.apache.bazaar.Category.rootcategoryidentifier";

	private static final Configuration INSTANCE;

	static {
		try (final InputStream inputStream = Configuration.class
				.getResourceAsStream("/org/apache/bazaar/config/configuration.properties")) {
			if (inputStream == null) {
				throw new ExceptionInInitializerError();
			}
			final Properties properties = new Properties();
			properties.load(new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8"))));
			INSTANCE = new PropertiesConfigurationImpl(properties);
		}
		catch (final IOException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	// declare constructors

	/**
	 * Constructor for Configuration
	 */
	protected Configuration() {
		super();
	}

	// declare methods

	/**
	 * Factory method returns instance.
	 * 
	 * @return Instance of Configuration
	 * @throws ConfigurationException if instance could not be created.
	 */
	public static Configuration newInstance() throws ConfigurationException {
		return Configuration.INSTANCE;
	}

	/**
	 * Returns property value by key.
	 * 
	 * @param key The property key to lookup value for
	 * @return Property value for key.
	 * @throws NullPointerException if key provided is null
	 * @throws PropertyNotFoundException if no property can be found with
	 *         provided key
	 */
	public String getProperty(final String key) throws PropertyNotFoundException {
		return Configuration.INSTANCE.getProperty(key);
	}

}
