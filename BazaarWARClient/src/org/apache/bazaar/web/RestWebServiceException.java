/*
 * RestWebServiceException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 4:59:57 PM
 */
package org.apache.bazaar.web;

import javax.ws.rs.WebApplicationException;

/**
 * RestWebServiceException extends WebApplicationException to
 * provide a specific exception sub type for processing
 * Rest related operations
 */
public class RestWebServiceException extends WebApplicationException {

	// declare members

	private static final long serialVersionUID = -2692978144363250326L;

	// declare constructors

	/**
	 * Constructor for RestWebServiceException
	 */
	public RestWebServiceException() {
		super();
	}

	/**
	 * Constructor for RestWebServiceException
	 * 
	 * @param message The exception message
	 */
	public RestWebServiceException(final String message) {
		super(message);
	}

	/**
	 * Constructor for RestWebServiceException
	 * 
	 * @param cause The exception cause
	 */
	public RestWebServiceException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for RestWebServiceException
	 * 
	 * @param message
	 * @param cause
	 */
	public RestWebServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
