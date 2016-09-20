/*
 * BazaarExpiredException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 3, 2016 at 5:37:23 PM
 */
package org.apache.bazaar;

/**
 * BazaarExpiredException
 */
public final class BazaarExpiredException extends BazaarException {

	// declare members

	private static final long serialVersionUID = 5069383900171504863L;

	// declare constructors

	/**
	 * Constructor for BazaarExpiredException
	 */
	public BazaarExpiredException() {
		super();
	}

	/**
	 * Constructor for BazaarExpiredException
	 * 
	 * @param message The exception message
	 */
	public BazaarExpiredException(final String message) {
		super(message);
	}

	/**
	 * Constructor for BazaarExpiredException
	 * 
	 * @param cause The exception cause
	 */
	public BazaarExpiredException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for BazaarExpiredException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public BazaarExpiredException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
