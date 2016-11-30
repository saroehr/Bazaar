/*
 * IdentifierImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 25, 2016 at 8:33:52 AM
 */
package org.apache.bazaar;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;

/**
 * IdentifierImpl extends {@link Identifier} to provide a concrete
 * implementation. This instance generates identifier values based upon usage of
 * the {@link UUID} class.
 */
public final class IdentifierImpl extends Identifier {

	// declare members

	private static final long serialVersionUID = 1885084516766977953L;

	private final String identifier;

	// declare constructors

	/**
	 * Constructor for IdentifierImpl
	 */
	public IdentifierImpl() {
		super();
		this.identifier = UUID.randomUUID().toString();
	}

	/**
	 * Constructor for IdentifierImpl
	 *
	 * @param identifier The identifier string
	 */
	public IdentifierImpl(@NotNull final String identifier) {
		super();
		this.identifier = identifier;
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Identifier#getValue()
	 */
	@Override
	public String getValue() {
		return this.identifier;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashcode = 31;
		hashcode = hashcode + this.identifier.hashCode();
		return hashcode;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Identifier)) {
			return false;
		}
		if (((Identifier)object).getValue().equals(this.identifier)) {
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
