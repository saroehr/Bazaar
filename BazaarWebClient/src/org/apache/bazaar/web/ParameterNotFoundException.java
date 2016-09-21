/*
 * ParameterNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 9:46:21 AM
 */
package org.apache.bazaar.web;

/**
 * ParameterNotFoundException extends RestWebServiceException
 */
public final class ParameterNotFoundException extends RestWebServiceException {

	// declare members

	private static final long serialVersionUID = -5567551202317013956L;

	// declare constructors

	/**
	 * Constructor for ParameterNotFoundException
	 */
	public ParameterNotFoundException() {
		super();
	}

	/**
	 * Constructor for ParameterNotFoundException
	 * 
	 * @param message The exception message
	 */
	public ParameterNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for ParameterNotFoundException
	 * 
	 * @param cause The exception cause
	 */
	public ParameterNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for ParameterNotFoundException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public ParameterNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
