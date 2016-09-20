/*
 * BazaarException.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import javax.validation.constraints.NotNull;

/**
 * BazaarException extends Exception to provide the base Exception type.
 */
public class BazaarException extends Exception {

	// declare members

	private static final long serialVersionUID = 8839985775007136004L;

	// declare constructors

	/**
	 * Constructor for BazaarException
	 */
	public BazaarException() {
		super();
	}

	/**
	 * Constructor for BazaarException
	 * @param message
	 *            The exception message
	 */
	public BazaarException(@NotNull final String message) {
		super(message);
	}

	/**
	 * Constructor for BazaarException
	 * @param cause
	 *            The cause of exception
	 */
	public BazaarException(@NotNull final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for BazaarException
	 * @param message
	 *            The exception message
	 * @param cause
	 *            The exception cause
	 */
	public BazaarException(@NotNull final String message, @NotNull final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
