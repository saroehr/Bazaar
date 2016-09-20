/*
 * PersistableNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 16, 2016 at 9:54:12 AM
 */
package org.apache.bazaar;

/**
 * PersistableNotFoundException extends PersistableException
 * and provides an abstract base class to be subclassed by specific
 * exception types for {@link Persistable} implementors
 */
public class PersistableNotFoundException extends PersistableException {

	// declare members
	private static final long serialVersionUID = -7192302872854005121L;

	// declare constructors

	/**
	 * Constructor for PersistableNotFoundException
	 */
	public PersistableNotFoundException() {
		super();
	}

	/**
	 * Constructor for PersistableNotFoundException
	 * 
	 * @param message The exception message
	 */
	public PersistableNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for PersistableNotFoundException
	 * 
	 * @param cause The exception cause
	 */
	public PersistableNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for PersistableNotFoundException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public PersistableNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods
}
