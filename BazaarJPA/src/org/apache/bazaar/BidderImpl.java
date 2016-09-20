/*
 * BidderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;

/**
 * BidderImpl implements {@link Bidder} to provide
 * a concrete implementation.
 */
@Entity(name = org.apache.bazaar.jpa.config.Configuration.BIDDER_ENTITY_NAME)
@Table(name = org.apache.bazaar.jpa.config.Configuration.BIDDER_TABLE_NAME, schema = org.apache.bazaar.jpa.config.Configuration.DATABASE_SCHEMA_NAME)
// @PrimaryKeyJoinColumn(name = Configuration.IDENTIFIABLE_COLUMN_NAME)
public class BidderImpl extends AbstractPersistable implements Bidder {

	// declare members

	private static final long serialVersionUID = -8715522531140280838L;

	@Embedded
	private NameImpl name;
	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "street", column = @Column(name = "BILLINGSTREET", length = 255, nullable = false, updatable = true)),
			@AttributeOverride(name = "city", column = @Column(name = "BILLINGCITY", length = 255, nullable = false, updatable = true)),
			@AttributeOverride(name = "state", column = @Column(name = "BILLINGSTATE", length = 255, nullable = false, updatable = true)),
			@AttributeOverride(name = "zipcode", column = @Column(name = "BILLINGZIPCODE", nullable = false, updatable = true)) })
	private AddressImpl billingAddress;
	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "street", column = @Column(name = "SHIPPINGSTREET", length = 255, nullable = false, updatable = true)),
			@AttributeOverride(name = "city", column = @Column(name = "SHIPPINGCITY", length = 255, nullable = false, updatable = true)),
			@AttributeOverride(name = "state", column = @Column(name = "SHIPPINGSTATE", length = 255, nullable = false, updatable = true)),
			@AttributeOverride(name = "zipcode", column = @Column(name = "SHIPPINGZIPCODE", nullable = false, updatable = true)) })
	private AddressImpl shippingAddress;

	// declare constructors

	/**
	 * Constructor for BidderImpl
	 */
	protected BidderImpl() {
		super();
	}

	/**
	 * Constructor for BidderImpl
	 * 
	 * @param name The bidder name
	 * @param billingAddress The billing address of bidder
	 * @param shippingAddress The shipping address of bidder
	 */
	BidderImpl(@NotNull final Name name, @NotNull final Address billingAddress,
			@NotNull final Address shippingAddress) {
		this();
		this.name = (NameImpl)name;
		this.billingAddress = (AddressImpl)billingAddress;
		this.shippingAddress = (AddressImpl)shippingAddress;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bidder#getName()
	 */
	@Override
	public Name getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bidder#setName(org.apache.bazaar.Name)
	 */
	@Override
	public void setName(final Name name) {
		this.name = (NameImpl)name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bidder#getBillingAddress()
	 */
	@Override
	public Address getBillingAddress() {
		return this.billingAddress;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.bazaar.Bidder#setBillingAddress(org.apache.bazaar.Address)
	 */
	@Override
	public void setBillingAddress(final Address address) {
		this.billingAddress = (AddressImpl)address;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bidder#getShippingAddress()
	 */
	@Override
	public Address getShippingAddress() {
		return this.shippingAddress;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.bazaar.Bidder#setShippingAddress(org.apache.bazaar.Address)
	 */
	@Override
	public void setShippingAddress(final Address address) {
		this.shippingAddress = (AddressImpl)address;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this,
				new Object[] { this.getIdentifier(), this.name, this.shippingAddress, this.billingAddress });
	}

}
