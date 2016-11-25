/*
 * AuditableException.java
 * Created On: Sep 29, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com
 */
package org.apache.bazaar.audit;

import org.apache.bazaar.BazaarException;

/**
 * AuditableException extends {@link BazaarException} to provide a base
 * exception type for audit issues
 */
public class AuditableException extends BazaarException {

	// declare members
	private static final long serialVersionUID = 2208801175123316460L;

	// declare constructors

	/**
	 * Constructor for AuditableException
	 */
	public AuditableException() {
		super();
	}

	/**
	 * Constructor for AuditableException
	 *
	 * @param message The exception message
	 */
	public AuditableException(final String message) {
		super(message);
	}

	/**
	 * Constructor for AuditableException
	 *
	 * @param cause The exception cause
	 */
	public AuditableException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for AuditableException
	 *
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public AuditableException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare members

}
