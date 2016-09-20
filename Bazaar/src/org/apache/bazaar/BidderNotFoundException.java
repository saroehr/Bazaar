/*
 * BidderNotFoundException.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 9, 2016
 */
package org.apache.bazaar;

/**
 * BidderNotFoundException extends PersistableNotFoundException to provide
 * an exception type for scenarios in which a {@link Bidder} can not be
 * located
 */
public final class BidderNotFoundException extends PersistableNotFoundException {

	// declare members

	private static final long serialVersionUID = -1379997325120850267L;

	// declare constructors

	/**
	 * Constructor for BidderNotFoundException
	 */
	public BidderNotFoundException() {
		super();
	}

	/**
	 * Constructor for BidderNotFoundException
	 * @param message
	 *            The exception message
	 */
	public BidderNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for BidderNotFoundException
	 * @param cause
	 *            The exception cause
	 */
	public BidderNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for BidderNotFoundException
	 * @param message
	 *            The exception message
	 * @param cause
	 *            The exception cause
	 */
	public BidderNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
