/*
 * BidNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 11, 2016 at 5:23:25 PM
 */
package org.apache.bazaar;

/**
 * BidNotFoundException extends PersistableNotFoundException and provides
 * a specific exception sub type for when a {@link Bid} is not found.
 */
public final class BidNotFoundException extends PersistableNotFoundException {

	// declare members
	private static final long serialVersionUID = 1512312780602243071L;

	// declare constructors

	/**
	 * Constructor for BidNotFoundException
	 */
	public BidNotFoundException() {
		super();
	}

	/**
	 * Constructor for BidNotFoundException
	 * 
	 * @param message The exception message
	 */
	public BidNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for BidNotFoundException
	 * 
	 * @param cause The exception cause
	 */
	public BidNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for BidNotFoundException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public BidNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
