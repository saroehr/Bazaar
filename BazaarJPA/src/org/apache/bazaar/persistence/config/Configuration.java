/*
 * Configuration.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 17, 2016 at 8:54:21 PM
 */
package org.apache.bazaar.persistence.config;

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
import org.apache.bazaar.Image;
import org.apache.bazaar.Item;
import org.apache.bazaar.Persistable;

/**
 * Configuration extends org.apache.bazaar.config.Configuration to encapsulate
 * the additional persistence provider properties
 */
public final class Configuration extends org.apache.bazaar.config.Configuration {

	// declare members

	/**
	 * Enumeration of Transaction Types
	 */
	public enum TransactionType {

		/**
		 * Local Transaction Type
		 */
		LOCAL,

		/**
		 * Managed Transaction Type
		 */
		MANAGED

	}

	/**
	 * Enumeration of Persistence Provider
	 */
	public enum PersistenceProvider {

		/**
		 * Hibernate Persistence Provider
		 */
		HIBERNATE, 

		/**
		 * EclipseLink Persistence Provider
		 */
		ECLIPSELINK, 
	}

	/**
	 * The persistence provider key
	 */
	public static final String PERSISTENCE_PROVIDER_NAME = "javax.persistence.provider";

	/**
	 * The key for retrieval of the Managed Transaction
	 * JNDI name
	 */
	public static final String MANAGED_TRANSACTION_NAME = "org.apache.bazaar.persistence.EntityTransaction.managedtransactionjndiname";

	/**
	 * The JPA persistence unit name
	 */
	public static final String PERSISTENCE_UNIT_NAME = "org.apache.bazaar";

	/**
	 * The database name for application
	 */
	public static final String DATABASE_NAME = "BAZAAR";

	/**
	 * The schema name for application
	 */
	public static final String DATABASE_SCHEMA_NAME = "BAZAAR";

	/**
	 * The Column name for the primary key column
	 * used by {@link Persistable}
	 */
	public static final String IDENTIFIABLE_COLUMN_NAME = "IDENTIFIER";

	/**
	 * The Entity name used for the {@link Persistable} class
	 */
	public static final String PERSISTABLE_ENTITY_NAME = "Persistable";

	/**
	 * The Table name used for the {@link Persistable} class
	 */
	public static final String PERSISTABLE_TABLE_NAME = "PERSISTABLE";

	/**
	 * The Entity name used for the {@link Bazaar} class
	 */
	public static final String BAZAAR_ENTITY_NAME = "Bazaar";

	/**
	 * The Table name used for the {@link Bazaar} class
	 */
	public static final String BAZAAR_TABLE_NAME = "BAZAAR";

	/**
	 * The Entity name used for the {@link Bidder} class
	 */
	public static final String BIDDER_ENTITY_NAME = "Bidder";

	/**
	 * The Table name used for the {@link Bidder} class
	 */
	public static final String BIDDER_TABLE_NAME = "BIDDER";

	/**
	 * The Entity name used for the {@link Bid} class
	 */
	public static final String BID_ENTITY_NAME = "Bid";

	/**
	 * The Table name used for the {@link Bid} class
	 */
	public static final String BID_TABLE_NAME = "BID";

	/**
	 * The Entity name used for the {@link Category} class
	 */
	public static final String CATEGORY_ENTITY_NAME = "Category";

	/**
	 * The Table name used for the {@link Category} class
	 */
	public static final String CATEGORY_TABLE_NAME = "CATEGORY";

	/**
	 * The Entity name used for the {@link Item} class
	 */
	public static final String ITEM_ENTITY_NAME = "Item";

	/**
	 * The Table name used for the {@link Item} class
	 */
	public static final String ITEM_TABLE_NAME = "ITEM";

	/**
	 * The Entity name used for the {@link Image} class
	 */
	public static final String IMAGE_ENTITY_NAME = "Image";

	/**
	 * The Table name used for the {@link Image} class
	 */
	public static final String IMAGE_TABLE_NAME = "IMAGE";

	/**
	 * The key for retrieval of the TRANSACTION_TYPE
	 */
	public static final String TRANSACTION_TYPE = "org.apache.bazaar.persistence.EntityTransaction.transactiontype";

	private static final Properties PROPERTIES;

	static {
		try (final InputStream inputStream = Configuration.class
				.getResourceAsStream("/org/apache/bazaar/persistence/config/configuration.properties")) {
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
	private Configuration(@NotNull final Properties properties) {
		super(properties);
	}

	// declare methods

	/**
	 * Factory method for obtaining instance
	 * 
	 * @return The Configuration instance
	 */
	public static @NotNull Configuration newInstance() {
		return new Configuration(Configuration.PROPERTIES);
	}

}
