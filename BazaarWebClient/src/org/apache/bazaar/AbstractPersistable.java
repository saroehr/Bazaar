/*
 * AbstractPersistable.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 16, 2016 at 9:07:25 PM
 */
package org.apache.bazaar;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;

/**
 * AbstractPersistable implements {@link Persistable} to provide an abstract
 * class suitable for sub classing.
 */
public abstract class AbstractPersistable implements Persistable {

	// declare members

	private Identifier identifier;

	// declare constructors

	/**
	 * Constructor for AbstractPersistable
	 */
	protected AbstractPersistable() {
		super();
		this.identifier = new IdentifierImpl();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Persistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		// add to cache
		BazaarManagerImpl.addToCache(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Persistable#delete()
	 */
	@Override
	public void delete() throws BazaarException {
		// remove from cache
		BazaarManagerImpl.removeFromCache(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Identifiable#getIdentifier()
	 */
	@Override
	public final Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * Utility method sets identifier
	 *
	 * @param identifier The {@link Identifier} instance to set
	 */
	public final void setIdentifier(@NotNull final Identifier identifier) {
		this.identifier = identifier;
	}

	/*
	 * (non-Javadoc)
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.identifier });
	}

}
