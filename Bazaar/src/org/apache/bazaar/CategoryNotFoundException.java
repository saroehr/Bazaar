/*
 * CategoryNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 8, 2016
 */
package org.apache.bazaar;

/**
 * CategoryNotFoundException extends PersistableNotFoundException to provide
 * and exception type for scenarios in which a {@link Category} can not
 * be located
 */
public final class CategoryNotFoundException extends PersistableNotFoundException {

	// declare members

	private static final long serialVersionUID = 879335992568520687L;

	// declare constructors

	/**
	 * Constructor for CategoryNotFoundException
	 */
	public CategoryNotFoundException() {
		super();
	}

	/**
	 * Constructor for CategoryNotFoundException
	 * @param message
	 *            The exception message
	 */
	public CategoryNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for CategoryNotFoundException
	 * @param cause
	 *            The exception cause
	 */
	public CategoryNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for CategoryNotFoundException
	 * @param message
	 *            The exception message
	 * @param cause
	 *            The exception cause
	 */
	public CategoryNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods
}
