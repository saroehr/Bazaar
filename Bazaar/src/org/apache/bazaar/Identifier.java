/*
 * Identifier.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 25, 2016 at 8:07:55 AM
 */
package org.apache.bazaar;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.cache.Cache;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.validation.constraints.NotNull;

/**
 * Identifier encapsulates the attributes of an Identifier instance.
 */
public abstract class Identifier implements Serializable {

	// declare members

	private static final long serialVersionUID = -8553195888916368499L;
	private static final Cache<String, Identifier> CACHE;
	static {
		final MutableConfiguration<String, Identifier> configuration = new MutableConfiguration<String, Identifier>();
		configuration.setStoreByValue(true);
		configuration.setTypes(String.class, Identifier.class);
		configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_DAY));
		CACHE = org.apache.bazaar.cache.Cache.newInstance(configuration, Identifier.class.getName(), String.class,
				Identifier.class);
	}
	private static final Constructor<?> CONSTRUCTOR;
	static {
		try {
			final Class<?> clazz = Class.forName("org.apache.bazaar.IdentifierImpl");
			CONSTRUCTOR = clazz.getDeclaredConstructor(new Class[] { String.class });
		}
		catch (final ClassNotFoundException | NoSuchMethodException | SecurityException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	// declare constructors

	/**
	 * Constructor for Identifier
	 */
	protected Identifier() {
		super();
	}

	// declare methods

	/**
	 * Factory method for obtaining instance
	 *
	 * @return New Identifier instance
	 */

	/**
	 * Constructs instance from value
	 *
	 * @param value The identifier value
	 * @return The Identifier instance
	 * @throws IdentifierException if the instance could not be created
	 */
	public static Identifier fromValue(final String value) throws IdentifierException {
		final Identifier identifier;
		try {
			if (Identifier.CACHE.containsKey(value)) {
				identifier = Identifier.CACHE.get(value);
			}
			else {
				identifier = (Identifier)Identifier.CONSTRUCTOR.newInstance(new Object[] { value });
				Identifier.CACHE.put(value, identifier);
			}
		}
		catch (final SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| InstantiationException exception) {
			throw new IdentifierException(exception);
		}
		return identifier;
	}

	/**
	 * Returns identifier value
	 *
	 * @return The identifier value
	 */
	public abstract @NotNull String getValue();

}
