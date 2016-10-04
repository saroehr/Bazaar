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

import org.apache.bazaar.web.couchdb.DatabaseMessageBodyReaderImpl;

/**
 * Configuration extends org.apache.bazaar.config.Configuration to encapsulate
 * the additional couchdb provider properties
 */
public final class Configuration extends org.apache.bazaar.web.config.Configuration {

	// declare members

	/**
	 * Key for couchdb url
	 */
	public static final String COUCHDB_URL = "org.apache.bazaar.couchdb.url";

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
