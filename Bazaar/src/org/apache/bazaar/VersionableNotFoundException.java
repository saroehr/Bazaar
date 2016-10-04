/*
 * VersionableNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 23, 2016 at 9:58:12 AM
 */
package org.apache.bazaar;

/**
 * VersionableNotFoundException extends VersionableException to provide a
 * specific sub type for {@link Versionable}
 */
public final class VersionableNotFoundException extends VersionableException {

	// declare members
	private static final long serialVersionUID = -1366666014728268682L;

	// declare constructors

	/**
	 * Constructor for VersionableNotFoundException
	 */
	public VersionableNotFoundException() {
		super();
	}

	/**
	 * Constructor for VersionableNotFoundException
	 *
	 * @param message The exception message
	 */
	public VersionableNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for VersionableNotFoundException
	 *
	 * @param cause The exception cause
	 */
	public VersionableNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for VersionableNotFoundException
	 *
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public VersionableNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
