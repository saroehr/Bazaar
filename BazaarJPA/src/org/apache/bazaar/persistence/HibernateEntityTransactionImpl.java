/*
 * HibernateEntityTransactionImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 5, 2016 at 10:55:46 AM
 */
package org.apache.bazaar.persistence;

import java.sql.Blob;

import javax.persistence.EntityTransaction;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;

/**
 * HibernateEntityTransactionImpl
 */
final class HibernateEntityTransactionImpl extends AbstractEntityTransaction {

	// declare members

	// declare constructors

	/**
	 * Constructor for HibernateEntityTransactionImpl
	 * 
	 * @param entityTransaction The {@link javax.persistence.EntityTransaction}
	 *        instance to be decorated.
	 */
	HibernateEntityTransactionImpl(@NotNull final EntityTransaction entityTransaction) {
		super(entityTransaction);
	}

	/**
	 * Constructor for HibernateEntityTransactionImpl
	 * 
	 * @param userTransaction The {@link javax.transaction.UserTransaction}
	 *        instance to be decorated.
	 */
	HibernateEntityTransactionImpl(@NotNull final UserTransaction userTransaction) {
		super(userTransaction);
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.persistence.AbstractEntityTransaction#newBlob()
	 */
	@Override
	public Blob newBlob() throws EntityTransactionException {
		AbstractEntityTransaction.LOGGER.entering("newBlob");
		final LobCreator lobCreator = Hibernate.getLobCreator(
				(this.getEntityManager().unwrap(Session.class)));
		final Blob blob = lobCreator.createBlob(new byte[AbstractEntityTransaction.DEFAULT_BUFFER_SIZE]);
		AbstractEntityTransaction.LOGGER.exiting("newBlob", new Object[] { blob });
		return blob;
	}

}
