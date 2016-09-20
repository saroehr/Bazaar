/*
 * BidFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 8, 2016 at 3:27:25 PM
 */
package org.apache.bazaar;

/**
 * BidFoundException extends BazaarException and provides
 * a specific exception type for operations prohibited
 * due to bid constraints.
 */
public final class BidFoundException extends BazaarException {

	// declare members

	private static final long serialVersionUID = 811922685693909147L;

	// declare constructors

	/**
	 * Constructor for BidFoundException();
	 */
	public BidFoundException() {
		super();
	}

	/**
	 * Constructor for BidFoundException
	 * 
	 * @param message The exception message
	 */
	public BidFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for BidFoundException
	 * 
	 * @param cause The exception cause
	 */
	public BidFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for BidFoundException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public BidFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
