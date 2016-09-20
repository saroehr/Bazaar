/*
 * IdentifierException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 25, 2016 at 10:00:21 AM
 */
package org.apache.bazaar;

/**
 * IdentifierException extends BazaarException to provide
 * a specific exception type for dealing with {@link Identifier}
 * instances.
 */
public final class IdentifierException extends BazaarException {

	// declare members
	private static final long serialVersionUID = -3867789487361911170L;

	// declare constructors

	/**
	 * Constructor for IdentifierException
	 */
	public IdentifierException() {
		super();
	}

	/**
	 * Constructor for IdentifierException
	 * 
	 * @param message The exception message
	 */
	public IdentifierException(final String message) {
		super(message);
	}

	/**
	 * Constructor for IdentifierException
	 * 
	 * @param cause The exception cause
	 */
	public IdentifierException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for IdentifierException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public IdentifierException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
