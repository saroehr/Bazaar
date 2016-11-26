/*
 * JsonException.java
 * Created On: Nov 26, 2016 at 10:57:16 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.ws.rs.WebApplicationException;

/**
 * JsonException extends WebApplicationException to provide a base exception
 * type for json processing exceptions
 */
public final class JsonException extends WebApplicationException {

	// declare members

	/*
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3854876917409732217L;

	// declare constructors

	/**
	 * Constructor for JsonException
	 */
	public JsonException() {
		super();
	}

	/**
	 * Constructor for JsonException
	 *
	 * @param message The exception message
	 */
	public JsonException(final String message) {
		super(message);
	}

	/**
	 * Constructor for JsonException
	 *
	 * @param cause The exception cause
	 */
	public JsonException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for JsonException
	 *
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public JsonException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
