/*
 * VersionableException.java
 * Created On: Sep 27, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com
 */
package org.apache.bazaar;

/**
 * VersionableException extends BazaarException and provides the base exception
 * type for Versionable
 */
public class VersionableException extends BazaarException {

	// declare members

	private static final long serialVersionUID = -4203457124771686337L;

	// declare constructors

	/**
	 * Constructor for VersionableException
	 */
	public VersionableException() {
		super();
	}

	/**
	 * Constructor for VersionableException
	 *
	 * @param message The exception message
	 */
	public VersionableException(final String message) {
		super(message);
	}

	/**
	 * Constructor for VersionableException
	 *
	 * @param cause The exception cause
	 */
	public VersionableException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for VersionableException
	 *
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public VersionableException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
