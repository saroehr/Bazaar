/*
 * EclipseLinkEntityTransactionImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 5, 2016 at 12:37:44 PM
 */
package org.apache.bazaar.jpa;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javax.persistence.EntityTransaction;
import javax.transaction.UserTransaction;

import org.apache.bazaar.i18n.Messages;

/**
 * EclipseLinkEntityTransactionImpl extends AbstractEntityTransaction
 * to provide a type for the EclipseLink persistence provider support.
 */
final class EclipseLinkEntityTransactionImpl extends AbstractEntityTransaction {

	// declare members

	private static final Messages MESSAGES = Messages.newInstance(Locale.getDefault());

	// declare constructors

	/**
	 * Constructor for EclipseLinkEntityTransactionImpl
	 * 
	 * @param entityTransaction The {@link javax.persistence.EntityTransaction}
	 *        instance to be decorated.
	 */
	EclipseLinkEntityTransactionImpl(final EntityTransaction entityTransaction) {
		super(entityTransaction);
	}

	/**
	 * Constructor for EclipseLinkEntityTransactionImpl
	 * 
	 * @param userTransaction The {@link javax.transaction.UserTransaction}
	 *        instance to be decorated.
	 */
	EclipseLinkEntityTransactionImpl(final UserTransaction userTransaction) {
		super(userTransaction);
	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.jpa.AbstractEntityTransaction#newBlob()
	 */
	@Override
	public Blob newBlob() throws EntityTransactionException {
		final Blob blob;
		try {
			final Connection connection = this.getEntityManager().unwrap(Connection.class);
			if (connection == null) {
				throw new EntityTransactionException(EclipseLinkEntityTransactionImpl.MESSAGES
						.findMessage(Messages.TRANSACTION_FAILURE_MESSAGE_KEY));
			}
			blob = connection.createBlob();
		}
		catch (final SQLException exception) {
			throw new EntityTransactionException(exception);
		}
		return blob;
	}

}
