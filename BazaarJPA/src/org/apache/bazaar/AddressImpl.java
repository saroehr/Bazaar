/*
 * AddressImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 15, 2016 at 8:20:41 PM
 */
package org.apache.bazaar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * AddressImpl
 */
@Embeddable
@Cacheable
public class AddressImpl implements Address {

	// declare members

	private static final long serialVersionUID = 8660582265184422583L;

	@NotNull
	@Column(name = "STREET", nullable = false, length = 255)
	private String street;
	@NotNull
	@Column(name = "CITY", nullable = false, length = 255)
	private String city;
	@NotNull
	@Column(name = "STATE", nullable = false, length = 255)
	@Enumerated(value = EnumType.STRING)
	private State state;
	@NotNull
	@Column(name = "ZIPCODE", nullable = false)
	private Integer zipcode;

	// declare constructors

	/**
	 * Constructor for AddressImpl
	 */
	protected AddressImpl() {
		super();
	}

	/**
	 * Constructor for AddressImpl
	 * 
	 * @param street The street
	 * @param city The city
	 * @param state The state
	 * @param zipcode The zipcode
	 */
	AddressImpl(@NotNull @Size(min = 1, max = 255) final String street,
			@NotNull @Size(min = 1, max = 255) final String city, @NotNull final State state,
			@NotNull final Integer zipcode) {
		this();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#getStreet()
	 */
	@Override
	public String getStreet() {
		return this.street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#setStreet(java.lang.String)
	 */
	@Override
	public void setStreet(final String street) {
		this.street = street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#getCity()
	 */
	@Override
	public String getCity() {
		return this.city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#setCity(java.lang.String)
	 */
	@Override
	public void setCity(final String city) {
		this.city = city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#getState()
	 */
	@Override
	public State getState() {
		return this.state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#setState(java.lang.String)
	 */
	@Override
	public void setState(final State state) {
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#getZipcode()
	 */
	@Override
	public Integer getZipcode() {
		return this.zipcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Address#setZipcode(java.lang.Short)
	 */
	@Override
	public void setZipcode(final Integer zipcode) {
		this.zipcode = zipcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(@NotNull final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof AddressImpl)) {
			return false;
		}
		final AddressImpl other = (AddressImpl)object;
		if (this.city == null) {
			if (other.city != null) {
				return false;
			}
		}
		else if (!this.city.equals(other.city)) {
			return false;
		}
		if (this.state != other.state) {
			return false;
		}
		if (this.street == null) {
			if (other.street != null) {
				return false;
			}
		}
		else if (!this.street.equals(other.street)) {
			return false;
		}
		if (this.zipcode == null) {
			if (other.zipcode != null) {
				return false;
			}
		}
		else if (!this.zipcode.equals(other.zipcode)) {
			return false;
		}
		return true;
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
		result = (prime * result) + ((this.city == null) ? 0 : this.city.hashCode());
		result = (prime * result) + ((this.state == null) ? 0 : this.state.hashCode());
		result = (prime * result) + ((this.street == null) ? 0 : this.street.hashCode());
		result = (prime * result) + ((this.zipcode == null) ? 0 : this.zipcode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return org.apache.bazaar.logging.Logger.toString(this,
				new Object[] { this.street, this.city, this.state, this.zipcode });
	}

}
