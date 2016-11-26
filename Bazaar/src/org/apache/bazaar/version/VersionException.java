/*
 * VersionException.java
 * Created On: Sep 29, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.version;

import org.apache.bazaar.BazaarException;

/**
 * VersionException extends {@link BazaarException} to provide a base exception
 * type for audit issues
 */
public class VersionException extends BazaarException {

	// declare members
	private static final long serialVersionUID = 2208801175123316460L;

	// declare constructors

	/**
	 * Constructor for VersionException
	 */
	public VersionException() {
		super();
	}

	/**
	 * Constructor for VersionException
	 *
	 * @param message The exception message
	 */
	public VersionException(final String message) {
		super(message);
	}

	/**
	 * Constructor for VersionException
	 *
	 * @param cause The exception cause
	 */
	public VersionException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for VersionException
	 *
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public VersionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare members

}
