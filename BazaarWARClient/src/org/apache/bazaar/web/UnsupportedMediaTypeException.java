/*
 * UnsupportedMediaTypeException.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 1, 2016 at 1:29:36 PM
 */
package org.apache.bazaar.web;

/**
 * UnsupportedMediaTypeException extends RestWebServiceException
 * to provide an exception type for when an unsupported media type
 * is provided.
 */
public class UnsupportedMediaTypeException extends RestWebServiceException {

	// declare members
	private static final long serialVersionUID = -1732740555160699193L;

	// declare constructors

	/**
	 * Constructor for UnsupportedMediaTypeException
	 */
	public UnsupportedMediaTypeException() {
		super();
	}

	/**
	 * Constructor for UnsupportedMediaTypeException
	 * 
	 * @param message The exception message
	 */
	public UnsupportedMediaTypeException(final String message) {
		super(message);
	}

	/**
	 * Constructor for UnsupportedMediaTypeException
	 * 
	 * @param cause The exception cause
	 */
	public UnsupportedMediaTypeException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for UnsupportedMediaTypeException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public UnsupportedMediaTypeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
