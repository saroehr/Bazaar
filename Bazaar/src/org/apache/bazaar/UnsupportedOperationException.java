/*
 * UnsupportedOperationException.java
 * Created On: Nov 29, 2016 at 7:14:59 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar;

/**
 * UnsupportedOperationException extends BazaarException to provide an exception
 * type that may be used to support optional features.
 */
public class UnsupportedOperationException extends BazaarException {

	// declare members

	/*
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6664898597432860385L;

	// declare constructors

	/**
	 * Constructor for UnsupportedOperationException
	 */
	public UnsupportedOperationException() {
		super();
	}

	/**
	 * Constructor for UnsupportedOperationException
	 *
	 * @param message The exception message
	 */
	public UnsupportedOperationException(final String message) {
		super(message);
	}

	/**
	 * Constructor for UnsupportedOperationException
	 *
	 * @param cause The exception cause
	 */
	public UnsupportedOperationException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for UnsupportedOperationException
	 *
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public UnsupportedOperationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
