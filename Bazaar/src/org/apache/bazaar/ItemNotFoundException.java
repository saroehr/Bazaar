/*
 * ItemNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 8, 2016
 */
package org.apache.bazaar;

/**
 * ItemNotFoundException extends PersistableNotFoundException to provide
 * an exception type for scenarios in which an {@link Item} can not
 * be located
 */
public final class ItemNotFoundException extends PersistableNotFoundException {

	// declare members

	private static final long serialVersionUID = -6000127524508863947L;

	// declare constructors

	/**
	 * Constructor for ItemNotFoundException
	 */
	public ItemNotFoundException() {
		super();
	}

	/**
	 * Constructor for ItemNotFoundException
	 *
	 * @param message
	 *            The exception message
	 */
	public ItemNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for ItemNotFoundException
	 *
	 * @param cause
	 *            The cause of exception
	 */
	public ItemNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for ItemNotFoundException
	 *
	 * @param message
	 *            The exception message
	 * @param cause
	 *            The cause of exception
	 */
	public ItemNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
