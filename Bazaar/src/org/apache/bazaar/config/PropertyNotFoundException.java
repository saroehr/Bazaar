/*
 * PropertyNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar.config;

/**
 * PropertyNotFoundException extends ConfigurationException
 * to provide an exception for when a configuration attribute
 * can not be located.
 */
public class PropertyNotFoundException extends ConfigurationException {

	// declare members

	private static final long serialVersionUID = 8471977040957534466L;

	// declare constructors

	/**
	 * Constructor for PropertyNotFoundException
	 */
	public PropertyNotFoundException() {
		super();
	}

	/**
	 * Constructor for PropertyNotFoundException
	 * @param message
	 *            The exception message
	 */
	public PropertyNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for PropertyNotFoundException
	 * @param cause
	 *            The exception cause
	 */
	public PropertyNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for PropertyNotFoundException
	 * @param message
	 *            The exception message
	 * @param cause
	 *            The exception cause
	 */
	public PropertyNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
