/*
 * UnsupportedOperationException.java
 * Created On: Nov 29, 2016 at 7:14:59 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar;

/**
 * UnsupportedOperationException
 */
public class UnsupportedOperationException extends BazaarException {

	/*
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6664898597432860385L;

	/**
	 * Constructor for UnsupportedOperationException
	 */
	public UnsupportedOperationException() {
	}

	/**
	 * Constructor for UnsupportedOperationException
	 * 
	 * @param message
	 */
	public UnsupportedOperationException(final String message) {
		super(message);
	}

	/**
	 * Constructor for UnsupportedOperationException
	 * 
	 * @param cause
	 */
	public UnsupportedOperationException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for UnsupportedOperationException
	 * 
	 * @param message
	 * @param cause
	 */
	public UnsupportedOperationException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
