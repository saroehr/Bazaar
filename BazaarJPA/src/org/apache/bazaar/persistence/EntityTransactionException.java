/*
 * EntityTransactionException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 28, 2016 at 9:25:32 PM
 */
package org.apache.bazaar.persistence;

/**
 * EntityTransactionException extends RuntimeException and
 * provides a non checked exception type for transparent
 * transaction handling.
 */
public final class EntityTransactionException extends RuntimeException {

	// declare members

	private static final long serialVersionUID = -4501711391231385426L;

	// declare constructors

	/**
	 * Constructor for EntityTransactionException
	 */
	public EntityTransactionException() {
		super();
	}

	/**
	 * Constructor for EntityTransactionException
	 * 
	 * @param message The exception message
	 */
	public EntityTransactionException(final String message) {
		super(message);
	}

	/**
	 * Constructor for EntityTransactionException
	 * 
	 * @param cause The exception cause
	 */
	public EntityTransactionException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for EntityTransactionException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public EntityTransactionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods


}
