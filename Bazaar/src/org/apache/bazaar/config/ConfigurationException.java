/*
 * ConfigurationException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar.config;

import org.apache.bazaar.BazaarException;

/**
 * ConfigurationException
 *
 */
public class ConfigurationException extends BazaarException {

	// declare members

	private static final long serialVersionUID = -3995555519118109784L;

	// declare constructors

	/**
	 * Constructor for ConfigurationException
	 */
	public ConfigurationException() {
		super();
	}

	/**
	 * Constructor for ConfigurationException
	 * @param message
	 *            The exception message
	 */
	public ConfigurationException(final String message) {
		super(message);
	}

	/**
	 * Constructor for ConfigurationException
	 * @param cause
	 *            The exception cause
	 */
	public ConfigurationException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for ConfigurationException
	 * @param message
	 *            The exception message
	 * @param cause
	 *            The exception cause
	 */
	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
