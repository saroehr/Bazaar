/*
 * RestClientException.java
 * Created On: Sep 29, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.ws.rs.WebApplicationException;

/**
 * RestClientException extends WebApplicationException to provide an exception
 */
public class RestWebClientException extends WebApplicationException {

	// declare members

	/*
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2511002107407128838L;

	// declare constructors

	/**
	 * Constructor for RestWebClientException
	 */
	public RestWebClientException() {
		super();
	}

	/**
	 * Constructor for RestWebClientException
	 *
	 * @param message The exception message
	 */
	public RestWebClientException(final String message) {
		super(message);
	}

	/**
	 * Constructor for RestWebClientException
	 *
	 * @param cause The exception cause
	 */
	public RestWebClientException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for RestClientException
	 *
	 * @param message The exception messages
	 * @param cause The exception cause
	 */
	public RestWebClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
