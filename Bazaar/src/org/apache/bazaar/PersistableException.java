/*
 * PersistableException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 16, 2016 at 10:08:05 AM
 */
package org.apache.bazaar;

/**
 * PersistableException extends BazaarException to provide
 * a base abstract class suitable for sub classing for sub type
 * exceptions related to Persistable operations
 */
abstract class PersistableException extends BazaarException {

	// declare members
	private static final long serialVersionUID = -1491154045950954164L;

	// declare constructors

	/**
	 * Constructor for PersistableException
	 */
	public PersistableException() {
		super();
	}


	/**
	 * Constructor for PersistableException
	 * 
	 * @param message The exception message
	 */
	public PersistableException(final String message) {
		super(message);
	}

	/**
	 * Constructor for PersistableException
	 * 
	 * @param cause The exception cause
	 */
	public PersistableException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for PersistableException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public PersistableException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
