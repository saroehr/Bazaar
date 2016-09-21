/*
 * BidderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 18, 2016 at 11:45:04 PM
 */
package org.apache.bazaar;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.bazaar.config.Configuration;
import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.web.RequestParameters;

/**
 * BidderImpl extends AbstractPersistable and implements Bidder
 * to provide a concrete implementation.
 */
final class BidderImpl extends AbstractPersistable implements Bidder {

	// declare members
	private static final long serialVersionUID = -510811290366725532L;

	private Name name;
	private Address billingAddress;
	private Address shippingAddress;

	// declare constructors

	/**
	 * Constructor for BidderImpl
	 */
	BidderImpl() {
		super();
	}

	/**
	 * Constructor for BidderImpl
	 * 
	 * @param name The name for bidder
	 * @param billingAddress The billing address for bidder
	 * @param shippingAddress The shipping address for bidder
	 */
	BidderImpl(@NotNull final Name name, @NotNull final Address billingAddress,
			@NotNull final Address shippingAddress) {
		this();
		this.name = name;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
	}

	// declare methods

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
		this.name = name;
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
	public void setBillingAddress(final Address billingAddress) {
		this.billingAddress = billingAddress;
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
	public void setShippingAddress(final Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Persistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		boolean persisted = true;
		try {
			BazaarManager.newInstance().findBidder(this.getIdentifier());
		}
		catch (final BidderNotFoundException exception) {
			persisted = false;
		}
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newClient()
				.target(Configuration.newInstance()
						.getProperty(org.apache.bazaar.web.config.Configuration.BIDDER_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.IDENTIFIER, this.getIdentifier().getValue())
				.queryParam(RequestParameters.FIRST_NAME, this.name.getFirstName())
				.queryParam(RequestParameters.LAST_NAME, this.name.getLastName())
				.queryParam(RequestParameters.BILLING_ADDRESS_STREET, this.billingAddress.getStreet())
				.queryParam(RequestParameters.BILLING_ADDRESS_CITY, this.billingAddress.getCity())
				.queryParam(RequestParameters.BILLING_ADDRESS_STATE, this.billingAddress.getState())
				.queryParam(RequestParameters.BILLING_ADDRESS_ZIPCODE, this.billingAddress.getZipcode())
				.queryParam(RequestParameters.SHIPPING_ADDRESS_STREET, this.shippingAddress.getStreet())
				.queryParam(RequestParameters.SHIPPING_ADDRESS_CITY, this.shippingAddress.getCity())
				.queryParam(RequestParameters.SHIPPING_ADDRESS_STATE, this.shippingAddress.getState())
				.queryParam(RequestParameters.SHIPPING_ADDRESS_ZIPCODE, this.shippingAddress.getZipcode());
		if (persisted) {
			BazaarManagerImpl.processResponse(new GenericType<Bidder>() {
			}, webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.buildPost(Entity.entity(this, MediaType.APPLICATION_JSON_TYPE)).invoke());
		}
		else {
			BazaarManagerImpl.processResponse(new GenericType<Bidder>() {
			}, webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.buildPut(Entity.entity(this, MediaType.APPLICATION_JSON_TYPE)).invoke());
		}
		super.persist();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Persistable#delete()
	 */
	@Override
	public void delete() throws BazaarException {
		super.delete();
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newClient()
				.target(Configuration.newInstance()
						.getProperty(org.apache.bazaar.web.config.Configuration.BIDDER_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.IDENTIFIER, this.getIdentifier().getValue());
		BazaarManagerImpl.processResponse(new GenericType<Bidder>() {
		}, webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildDelete().invoke());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.AbstractPersistable#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this,
				new Object[] { this.getIdentifier(), this.name, this.billingAddress, this.shippingAddress });
	}

}
