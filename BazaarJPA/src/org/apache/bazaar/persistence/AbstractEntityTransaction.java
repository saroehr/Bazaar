/*
 * AbstractEntityTransaction.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 28, 2016 at 8:36:29 PM
 */
package org.apache.bazaar.persistence;

import java.sql.Blob;
import java.util.Locale;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;

import org.apache.bazaar.config.ConfigurationException;
import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.nls.Messages;
import org.apache.bazaar.persistence.config.Configuration;
import org.apache.bazaar.persistence.config.Configuration.TransactionType;

/**
 * AbstractEntityTransaction decorates {@link EntityTransaction}
 */
abstract class AbstractEntityTransaction implements EntityTransaction, UserTransaction {

	// declare members

	protected static final Messages MESSAGES = Messages.newInstance(Locale.getDefault());

	protected static final int DEFAULT_BUFFER_SIZE;

	static {
		try {
			DEFAULT_BUFFER_SIZE = Integer.valueOf(
					Configuration.newInstance()
					.getProperty(org.apache.bazaar.config.Configuration.DEFAULT_BYTE_ARRAY_BUFFER_SIZE));
		}
		catch (final ConfigurationException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}
	protected static final Logger LOGGER = Logger.newInstance(EntityTransaction.class);

	static final TransactionType TRANSACTION_TYPE;
	static final String MANAGED_TRANSACTION_NAME;

	static {
		try {
			final Configuration configuration = Configuration.newInstance();
			TRANSACTION_TYPE = org.apache.bazaar.persistence.config.Configuration.TransactionType
					.valueOf(configuration.getProperty(org.apache.bazaar.persistence.config.Configuration.TRANSACTION_TYPE));
			MANAGED_TRANSACTION_NAME = configuration
					.getProperty(org.apache.bazaar.persistence.config.Configuration.MANAGED_TRANSACTION_NAME);
		}
		catch (final ConfigurationException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}
	private final javax.persistence.EntityTransaction entityTransaction;
	private final UserTransaction userTransaction;
	private EntityManager entityManager;
	private int depth;

	// declare constructors

	/**
	 * Constructor for AbstractEntityTransaction
	 * 
	 * @param entityTransaction The EntityTransaction
	 *        instance to be decorated.
	 */
	AbstractEntityTransaction(@NotNull final javax.persistence.EntityTransaction entityTransaction) {
		super();
		this.entityTransaction = entityTransaction;
		this.userTransaction = null;
		this.depth = 0;
	}

	/**
	 * Constructor for AbstractEntityTransaction
	 * 
	 * @param userTransaction The UserTransaction instance
	 *        to be decorated.
	 */
	AbstractEntityTransaction(@NotNull final UserTransaction userTransaction) {
		super();
		this.entityTransaction = null;
		this.userTransaction = userTransaction;
		this.depth = 0;
	}

	// declare methods

	/**
	 * Sets entity manager instance associated with
	 * this transaction
	 * 
	 * @param entityManager The EntityManager instance
	 *        associated with this transaction
	 */
	final void setEntityManager(@NotNull final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Returns entity manager associated with
	 * this transaction
	 * 
	 * @return The EntityManager instance associated
	 *         with this transaction
	 */
	protected final @NotNull EntityManager getEntityManager() {
		return this.entityManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityTransaction#begin()
	 */
	@Override
	public final void begin() {
		AbstractEntityTransaction.LOGGER.entering("begin");
		try {
			this.depth++;
			if (this.getStatus() == Status.STATUS_NO_TRANSACTION) {
				if (this.userTransaction != null) {
					this.userTransaction.begin();
					this.entityManager.joinTransaction();
				}
				else if (this.entityTransaction != null) {
					this.entityTransaction.begin();
				}
			}
		}
		catch (final SystemException | NotSupportedException exception) {
			throw new EntityTransactionException(exception);
		}
		AbstractEntityTransaction.LOGGER.exiting("begin");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityTransaction#commit()
	 */
	@Override
	public final void commit() {
		AbstractEntityTransaction.LOGGER.entering("commit");
		try {
			if (--this.depth == 0) {
				if (this.userTransaction != null) {
					this.userTransaction.commit();
				}
				else if (this.entityTransaction != null) {
					this.entityTransaction.commit();
				}
				else {
					throw new EntityTransactionException(new IllegalStateException());
				}
			}
		}
		catch (final SystemException | IllegalStateException | SecurityException | HeuristicMixedException
				| HeuristicRollbackException | RollbackException exception) {
			throw new EntityTransactionException(exception);
		}
		AbstractEntityTransaction.LOGGER.exiting("commit");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityTransaction#getRollbackOnly()
	 */
	@Override
	public final boolean getRollbackOnly() {
		final boolean rollbackOnly;
		try {
			if (this.userTransaction != null) {
				rollbackOnly = this.userTransaction.getStatus() == Status.STATUS_MARKED_ROLLBACK;
			}
			else if (this.entityTransaction != null) {
				rollbackOnly = this.entityTransaction.getRollbackOnly();
			}
			else {
				throw new EntityTransactionException(new IllegalStateException());
			}
		}
		catch (final SystemException exception) {
			throw new EntityTransactionException(exception);
		}
		return rollbackOnly;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.persistence.EntityTransaction#getTransactionType()
	 */
	@Override
	public final org.apache.bazaar.persistence.config.Configuration.TransactionType getTransactionType() {
		return AbstractEntityTransaction.TRANSACTION_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityTransaction#isActive()
	 */
	@Override
	public final boolean isActive() {
		AbstractEntityTransaction.LOGGER.entering("isActive");
		final boolean isActive;
		try {
			if (this.userTransaction != null) {
				isActive = this.userTransaction.getStatus() == Status.STATUS_ACTIVE;
			}
			else if (this.entityTransaction != null) {
				isActive = this.entityTransaction.isActive();
			}
			else {
				throw new EntityTransactionException(new IllegalStateException());
			}
		}
		catch (final SystemException exception) {
			throw new EntityTransactionException(exception);
		}
		AbstractEntityTransaction.LOGGER.exiting("isActive", new Object[] { Boolean.valueOf(isActive) });
		return isActive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityTransaction#rollback()
	 */
	@Override
	public final void rollback() {
		AbstractEntityTransaction.LOGGER.entering("rollback");
		try {
			if (this.userTransaction != null) {
				this.userTransaction.rollback();
			}
			else if (this.entityTransaction != null) {
				this.entityTransaction.rollback();
			}
			else {
				throw new EntityTransactionException(new IllegalStateException());
			}
		}
		catch (final SystemException exception) {
			throw new EntityTransactionException(exception);
		}
		AbstractEntityTransaction.LOGGER.exiting("rollback");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityTransaction#setRollbackOnly()
	 */
	@Override
	public final void setRollbackOnly() {
		try {
			if (this.userTransaction != null) {
				this.userTransaction.setRollbackOnly();
			}
			else if (this.entityTransaction != null) {
				this.entityTransaction.setRollbackOnly();
			}
			else {
				throw new EntityTransactionException(new IllegalStateException());
			}
		}
		catch (final SystemException exception) {
			throw new EntityTransactionException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.transaction.UserTransaction#getStatus()
	 */
	@Override
	public final int getStatus() throws EntityTransactionException {
		AbstractEntityTransaction.LOGGER.entering("getStatus");
		int status = Status.STATUS_NO_TRANSACTION;
		if (this.userTransaction != null) {
			try {
				status = this.userTransaction.getStatus();
			}
			catch (final SystemException exception) {
				throw new EntityTransactionException(exception);
			}
		}
		else if (this.entityTransaction != null) {
			if (this.entityTransaction.isActive()) {
				if (this.entityTransaction.getRollbackOnly()) {
					status = Status.STATUS_MARKED_ROLLBACK;
				}
				else {
					status = Status.STATUS_ACTIVE;
				}
			}
		}
		else {
			throw new EntityTransactionException(new IllegalStateException());
		}
		AbstractEntityTransaction.LOGGER.exiting("getStatus", new Object[] { Integer.valueOf(status) });
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.transaction.UserTransaction#setTransactionTimeout(int)
	 */
	@Override
	public final void setTransactionTimeout(final int timeoutValue) throws SystemException {
		AbstractEntityTransaction.LOGGER.entering("setTransactionTimeout", new Object[] { timeoutValue });
		if (this.userTransaction != null) {
			this.userTransaction.setTransactionTimeout(timeoutValue);
		}
		AbstractEntityTransaction.LOGGER.exiting("setTransactionTimeout");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.persistence.EntityTransaction#newBlob()
	 */
	@Override
	public abstract Blob newBlob() throws EntityTransactionException;

	/**
	 * Factory method for obtaining instance
	 * 
	 * @param entityTransaction The {@link javax.persistence.EntityTransaction}
	 *        instance to be decorated.
	 * @return Instance of EntityTransaction
	 * @throws EntityTransactionException if the instance
	 *         could not be created.
	 */
	public static EntityTransaction newInstance(@NotNull final javax.persistence.EntityTransaction entityTransaction)
			throws EntityTransactionException {
		final EntityTransaction entityTransaction1;
		try {
			final Configuration configuration = Configuration.newInstance();
			final org.apache.bazaar.persistence.config.Configuration.PersistenceProvider persistenceProvider = org.apache.bazaar.persistence.config.Configuration.PersistenceProvider
					.valueOf(configuration
							.getProperty(org.apache.bazaar.persistence.config.Configuration.PERSISTENCE_PROVIDER_NAME));
			if (org.apache.bazaar.persistence.config.Configuration.PersistenceProvider.HIBERNATE.equals(persistenceProvider)) {
				entityTransaction1 = new HibernateEntityTransactionImpl(entityTransaction);
			}
			else if (org.apache.bazaar.persistence.config.Configuration.PersistenceProvider.ECLIPSELINK
					.equals(persistenceProvider)) {
				entityTransaction1 = new EclipseLinkEntityTransactionImpl(entityTransaction);
			}
			else {
				throw new EntityTransactionException(
						AbstractEntityTransaction.MESSAGES.findMessage(Messages.TRANSACTION_FAILURE));
			}
		}
		catch (final ConfigurationException exception) {
			throw new EntityTransactionException(exception);
		}
		return entityTransaction1;
	}

	/**
	 * Factory method for obtaining instance
	 * 
	 * @param userTransaction The {@link javax.transaction.UserTransaction}
	 *        instance to be decorated.
	 * @return Instance of EntityTransaction
	 */
	public static EntityTransaction newInstance(@NotNull final javax.transaction.UserTransaction userTransaction) {
		final EntityTransaction entityTransaction1;
		try {
			final Configuration configuration = org.apache.bazaar.persistence.config.Configuration.newInstance();
			final org.apache.bazaar.persistence.config.Configuration.PersistenceProvider persistenceProvider = org.apache.bazaar.persistence.config.Configuration.PersistenceProvider
					.valueOf(configuration
							.getProperty(org.apache.bazaar.persistence.config.Configuration.PERSISTENCE_PROVIDER_NAME));
			if (org.apache.bazaar.persistence.config.Configuration.PersistenceProvider.HIBERNATE.equals(persistenceProvider)) {
				entityTransaction1 = new HibernateEntityTransactionImpl(userTransaction);
			}
			else if (org.apache.bazaar.persistence.config.Configuration.PersistenceProvider.ECLIPSELINK
					.equals(persistenceProvider)) {
				entityTransaction1 = new EclipseLinkEntityTransactionImpl(userTransaction);
			}
			else {
				throw new EntityTransactionException(
						AbstractEntityTransaction.MESSAGES.findMessage(Messages.TRANSACTION_FAILURE));
			}
		}
		catch (final ConfigurationException exception) {
			throw new EntityTransactionException(exception);
		}
		return entityTransaction1;
	}

}
