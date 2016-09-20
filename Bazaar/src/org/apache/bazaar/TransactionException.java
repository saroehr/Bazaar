/*
 * TransactionException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 19, 2016 at 5:37:40 AM
 */
package org.apache.bazaar;

/**
 * TransactionException extends BazaarException
 * to provide an exception type to be used
 * during transaction related failures.
 */
public final class TransactionException extends BazaarException {

	// declare members
	private static final long serialVersionUID = 5831882870070338387L;

	// declare constructors

	/**
	 * Constructor for TransactionException
	 */
	public TransactionException() {
		super();
	}

	/**
	 * Constructor for TransactionException
	 * 
	 * @param message The exception message
	 */
	public TransactionException(final String message) {
		super(message);
	}

	/**
	 * Constructor for TransactionException
	 * 
	 * @param cause The exception cause
	 */
	public TransactionException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for TransactionException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public TransactionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
