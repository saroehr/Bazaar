/*
 * NameImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 15, 2016 at 4:09:09 PM
 */
package org.apache.bazaar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * NameImpl implements {@link Name} to provide
 * a concrete implementation.
 */
@Embeddable
@Cacheable
public class NameImpl implements Name {

	// declare members

	private static final long serialVersionUID = 5799229168503193886L;

	@Column(name = "FIRSTNAME", nullable = false, length = 255)
	private String firstName;
	@Column(name = "LASTNAME", nullable = false, length = 255)
	private String lastName;

	// declare constructors

	/**
	 * Constructor for NameImpl
	 */
	protected NameImpl() {
		super();
	}

	/**
	 * Constructor for NameImpl
	 * 
	 * @param firstName The first name
	 * @param lastName The last name
	 */
	NameImpl(@NotNull @Size(min = 1, max = 255) final String firstName,
			@NotNull @Size(min = 1, max = 255) final String lastName) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.Name#getFirstName()
	 */
	@Override
	public @NotNull @Size(min = 1, max = 255) String getFirstName() {
		return this.firstName;
	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.Name#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(final @NotNull @Size(min = 1, max = 255) String firstName) {
		this.firstName = firstName;

	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.Name#getLastName()
	 */
	@Override
	public @NotNull @Size(min = 1, max = 255) String getLastName() {
		return this.lastName;
	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.Name#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(final @NotNull @Size(min = 1, max = 255) String lastName) {
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
	public boolean equals(final Object objectj) {
		if (this == objectj) {
			return true;
		}
		if (objectj == null) {
			return false;
		}
		if (!(objectj instanceof NameImpl)) {
			return false;
		}
		final NameImpl other = (NameImpl)objectj;
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
		return org.apache.bazaar.logging.Logger.toString(this, new Object[] { this.firstName, this.lastName });
	}

}
