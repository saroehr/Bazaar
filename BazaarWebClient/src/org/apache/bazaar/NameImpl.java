/*
 * NameImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 19, 2016 at 12:08:25 AM
 */
package org.apache.bazaar;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;

/**
 * NameImpl implements {@link Name} to provide
 * a concrete implementation.
 */
final class NameImpl implements Name {

	// declare members

	private static final long serialVersionUID = -6512136692602016603L;

	private String firstName;
	private String lastName;

	// declare constructors

	/**
	 * Constructor for NameImpl
	 */
	NameImpl() {
		super();
	}

	/**
	 * Constructor for NameImpl
	 * 
	 * @param firstName The first name for name
	 * @param lastName The last name for name
	 */
	NameImpl(@NotNull final String firstName, @NotNull final String lastName) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Name#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return this.firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Name#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Name#getLastName()
	 */
	@Override
	public String getLastName() {
		return this.lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Name#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.firstName == null) ? 0 : this.firstName.hashCode());
		result = (prime * result) + ((this.lastName == null) ? 0 : this.lastName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof NameImpl)) {
			return false;
		}
		final NameImpl other = (NameImpl)object;
		if (this.firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		}
		else if (!this.firstName.equals(other.firstName)) {
			return false;
		}
		if (this.lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		}
		else if (!this.lastName.equals(other.lastName)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.firstName, this.lastName });
	}

}
