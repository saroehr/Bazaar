/*
 * AbstractPersistable.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 25, 2016 at 8:47:19 AM
 */
package org.apache.bazaar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.MappedSuperclass;
import javax.persistence.RollbackException;
import javax.validation.constraints.NotNull;

import org.apache.bazaar.jpa.EntityManagerFactory;
import org.apache.bazaar.jpa.EntityTransactionException;
import org.apache.bazaar.logging.Logger;

/**
 * AbstractPersistable provides an abstract class. All objects
 * that require persistence to the underlying database must
 * extend from this class.
 */
// @Entity(name = Configuration.PERSISTABLE_ENTITY_NAME)
// @Table(name = Configuration.PERSISTABLE_TABLE_NAME, schema =
// Configuration.DATABASE_SCHEMA_NAME)
// @Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public abstract class AbstractPersistable implements Persistable {

	// declare members

	protected static final Logger LOGGER = Logger.newInstance(Persistable.class);

	@EmbeddedId
	@Access(AccessType.FIELD)
	@Column(name = org.apache.bazaar.jpa.config.Configuration.IDENTIFIABLE_COLUMN_NAME)
	private IdentifierImpl identifier;

	/**
	 * Constructor for AbstractPersistable
	 */
	protected AbstractPersistable() {
		super();
		this.identifier = new IdentifierImpl();
	}

	/**
	 * Method is called by {@link AbstractPersistable#persist()}
	 * to process an exception. Subclasses may override
	 * the method to provide appropriate exception
	 * handling during persistence operations
	 * 
	 * @param exception The exception caught during persist
	 * @throws BazaarException The exception to be thrown
	 */
	protected void processException(@NotNull final Exception exception) throws BazaarException {
		if (exception instanceof RollbackException) {
			throw new TransactionException(exception.getLocalizedMessage(), new EntityTransactionException(exception));
		}
		else {
			throw new BazaarException(exception.getLocalizedMessage(), exception);
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.Identifiable#getIdentifier()
	 */
	@Override
	public final Identifier getIdentifier() {
		return this.identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Persistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		AbstractPersistable.LOGGER.entering("persist");
		final EntityManager manager = EntityManagerFactory.newInstance().createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(manager.merge(this));
			transaction.commit();
		}
		catch (final Exception exception) {
			transaction.rollback();
			this.processException(exception);
		}
		finally {
			manager.close();
		}
		AbstractPersistable.LOGGER.exiting("persist");
	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.Persistable#delete()
	 */
	@Override
	public void delete() throws BazaarException {
		AbstractPersistable.LOGGER.entering("delete");
		final EntityManager manager = EntityManagerFactory.newInstance().createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.remove(manager.merge(this));
			transaction.commit();
		}
		catch (final Exception exception) {
			throw new BazaarException(exception);
		}
		finally {
			manager.close();
		}
		AbstractPersistable.LOGGER.exiting("delete");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		int hashcode = 31;
		hashcode = 31 + this.identifier.hashCode();
		return hashcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof AbstractPersistable)) {
			return false;
		}
		if (this.identifier.equals(((AbstractPersistable)object).getIdentifier())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.identifier });
	}

}
