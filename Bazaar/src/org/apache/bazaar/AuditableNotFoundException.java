/*
 * AuditableNotFoundException.java
 * Created On: Sep 29, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com
 */
package org.apache.bazaar;

/**
 * AuditableNotFoundException extends {@link AuditableException} to provide a
 * specific exception type for when an auditable can not be found for
 * {@link Persistable}
 */
public class AuditableNotFoundException extends AuditableException {

	// declare members
	private static final long serialVersionUID = 2208801175123316461L;

	// declare constructors

	/**
	 * Constructor for AuditableNotFoundException
	 */
	public AuditableNotFoundException() {
		super();
	}

	/**
	 * Constructor for AuditableNotFoundException
	 *
	 * @param message The exception message
	 */
	public AuditableNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructor for AuditableNotFoundException
	 *
	 * @param cause The exception cause
	 */
	public AuditableNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor for AuditableNotFoundException
	 *
	 * @param message The exception message
	 * @param cause The exception cause
	 */
	public AuditableNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// declare methods

}
