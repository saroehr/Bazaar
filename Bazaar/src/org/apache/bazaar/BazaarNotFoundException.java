/*
 * BazaarNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 18, 2016 at 12:34:52 PM
 */
package org.apache.bazaar;

/**
 * BazaarNotFoundException extends PersistableNotFoundException to provide
 * a specific exception sub type for when an {@link Bazaar} is not
 * found.
 */
public final class BazaarNotFoundException extends PersistableNotFoundException {

	// declare members

	private static final long serialVersionUID = 9071789299448412337L;

	// declare constructors

	/**
	 * Constructor for BazaarNotFoundException
	 */
	public BazaarNotFoundException() {
		super();
	}

	/**
	 * Constructor for BazaarNotFoundException
	 * 
	 * @param message The exception message
	 */
	public BazaarNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for BazaarNotFoundException
	 * 
	 * @param cause The cause of exception
	 */
	public BazaarNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for BazaarNotFoundException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public BazaarNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare members

}
