/*
 * CacheException.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 17, 2016 at 8:36:54 PM
 */
package org.apache.bazaar.cache;

/**
 * CacheException extends RuntimeException to provide
 * a base exception type for cache related operation
 * failures
 */
public class CacheException extends RuntimeException {

	// declare members
	private static final long serialVersionUID = 419227822107237354L;

	// declare constructors

	/**
	 * Constructor for CacheException
	 */
	public CacheException() {
		super();
	}

	/**
	 * Constructor for CacheException
	 * 
	 * @param message The exception message
	 */
	public CacheException(final String message) {
		super(message);
	}

	/**
	 * Constructor for CacheException
	 * 
	 * @param cause The exception cause
	 */
	public CacheException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for CacheException
	 * 
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public CacheException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
