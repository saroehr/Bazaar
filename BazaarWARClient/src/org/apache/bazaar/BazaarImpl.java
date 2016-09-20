/*
 * BazaarImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 19, 2016 at 12:02:53 AM
 */
package org.apache.bazaar;

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.bazaar.config.Configuration;
import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.web.RequestParameters;

/**
 * BazaarImpl extends AbstractPersistable and implements Bazaar
 * to provide a concrete implementation
 */
final class BazaarImpl extends AbstractPersistable implements Bazaar {

	// declare members
	private Item item;
	private Calendar startDate;
	private Calendar endDate;
	private Double reservePrice;

	// declare constructors

	/**
	 * Constructor for BazaarImpl
	 */
	BazaarImpl() {
		super();
	}

	/**
	 * Constructor for BazaarImpl
	 * 
	 * @param item The item for bazaar
	 * @param startDate The start date for bazaar
	 * @param endDate the end date for bazaar
	 */
	BazaarImpl(@NotNull final Item item, @NotNull final Calendar startDate, @NotNull final Calendar endDate) {
		this();
		this.item = item;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor for BazaarImpl
	 * 
	 * @param item The item for bazaar
	 * @param startDate The start date for bazaar
	 * @param endDate the end date for bazaar
	 * @param resertPrice The reservePrice price for bazaar
	 */
	BazaarImpl(@NotNull final Item item, @NotNull final Calendar startDate, @NotNull final Calendar endDate,
			@NotNull final Double reservePrice) {
		super();
		this.item = item;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservePrice = reservePrice;
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#newBid(org.apache.bazaar.Bidder,
	 * java.lang.Double)
	 */
	@Override
	public Bid newBid(final Bidder bidder, final Double price) {
		return new BidImpl(this, bidder, price);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getStartDate()
	 */
	@Override
	public Calendar getStartDate() {
		return this.startDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getEndDate()
	 */
	@Override
	public Calendar getEndDate() {
		return this.endDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getItem()
	 */
	@Override
	public Item getItem() {
		return this.item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getReservePrice()
	 */
	@Override
	public Double getReservePrice() {
		return this.reservePrice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#findBids(org.apache.bazaar.Bidder)
	 */
	@Override
	public Set<Bid> findBids(final Bidder bidder) throws BazaarException {
		final Set<Bid> bids;
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newClient()
				.target(Configuration.newInstance()
						.getProperty(org.apache.bazaar.web.config.Configuration.BID_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.BAZAAR, this.getIdentifier().getValue())
				.queryParam(RequestParameters.BIDDER, bidder.getIdentifier().getValue());
		bids = Collections.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Bid>>() {
		}, webTarget.request().accept(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke()));
		// add to cache
		((BazaarManagerImpl)BazaarManager.newInstance()).addToCache(bids);
		return bids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#findAllBids()
	 */
	@Override
	public Set<Bid> findAllBids() throws BazaarException {
		final Set<Bid> bids;
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newClient()
				.target(org.apache.bazaar.config.Configuration.newInstance()
						.getProperty(org.apache.bazaar.web.config.Configuration.BID_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.BAZAAR, this.getIdentifier().getValue());
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		bids = Collections.unmodifiableSet(BazaarManagerImpl.processResponse(new GenericType<Set<Bid>>() {
		}, response));
		// add to cache
		((BazaarManagerImpl)BazaarManager.newInstance()).addToCache(bids);
		return bids;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Persistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newClient()
				.target(Configuration.newInstance()
						.getProperty(org.apache.bazaar.web.config.Configuration.BAZAAR_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.IDENTIFIER, this.getIdentifier().getValue())
				.queryParam(RequestParameters.ITEM, this.item.getIdentifier().getValue())
				.queryParam(RequestParameters.START_DATE, this.startDate.getTimeInMillis())
				.queryParam(RequestParameters.END_DATE, this.endDate.getTimeInMillis())
				.queryParam(RequestParameters.RESERVE, this.reservePrice);
		BazaarManagerImpl.processResponse(new GenericType<Bazaar>() {
		}, webTarget.request().accept(MediaType.APPLICATION_JSON_TYPE)
				.buildPut(Entity.entity(this, MediaType.APPLICATION_JSON_TYPE)).invoke());
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
						.getProperty(org.apache.bazaar.web.config.Configuration.BAZAAR_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.IDENTIFIER, this.getIdentifier().getValue());
		BazaarManagerImpl.processResponse(new GenericType<Bazaar>() {
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
				new Object[] { this.getIdentifier(), this.item, this.startDate, this.endDate, this.reservePrice });
	}

}
