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

import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.version.AbstractVersionable;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionException;
import org.apache.bazaar.version.VersionNotFoundException;
import org.apache.bazaar.web.RequestParameters;
import org.apache.bazaar.web.RestWebClient;
import org.apache.bazaar.web.config.Configuration;

/**
 * BazaarImpl extends AbstractVersionable and implements Bazaar to provide a
 * concrete implementation
 */
public class BazaarImpl extends AbstractVersionable implements Bazaar {

	// declare members
	private Item item;
	private Calendar startDate;
	private Calendar endDate;
	private Double reservePrice;

	// declare constructors

	/**
	 * Constructor for BazaarImpl
	 */
	protected BazaarImpl() {
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
	 * @see org.apache.bazaar.Bazaar#newBid(org.apache.bazaar.Bidder,
	 * java.lang.Double)
	 */
	@Override
	public Bid newBid(final Bidder bidder, final Double price) {
		return new BidImpl(this, bidder, price);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bazaar#getStartDate()
	 */
	@Override
	public Calendar getStartDate() {
		return this.startDate;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bazaar#getEndDate()
	 */
	@Override
	public Calendar getEndDate() {
		return this.endDate;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bazaar#getItem()
	 */
	@Override
	public Item getItem() {
		return this.item;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bazaar#getReservePrice()
	 */
	@Override
	public Double getReservePrice() {
		return this.reservePrice;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bazaar#findBids(org.apache.bazaar.Bidder)
	 */
	@Override
	public Set<Bid> findBids(final Bidder bidder) throws BazaarException {
		final Set<Bid> bids;
		final WebTarget webTarget = RestWebClient.newInstance()
				.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.BAZAAR, this.getIdentifier().getValue())
				.queryParam(RequestParameters.BIDDER, bidder.getIdentifier().getValue());
		final Response response = webTarget.request().accept(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		bids = Collections.unmodifiableSet(RestWebClient.processResponse(new GenericType<Set<Bid>>() {
		}, response));
		// add to cache
		((BazaarManagerImpl)BazaarManager.newInstance()).addToCache(bids);
		return bids;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bazaar#findAllBids()
	 */
	@Override
	public Set<Bid> findAllBids() throws BazaarException {
		final Set<Bid> bids;
		final WebTarget webTarget = RestWebClient.newInstance()
				.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.BAZAAR, this.getIdentifier().getValue());
		final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
		bids = Collections.unmodifiableSet(RestWebClient.processResponse(new GenericType<Set<Bid>>() {
		}, response));
		// add to cache
		((BazaarManagerImpl)BazaarManager.newInstance()).addToCache(bids);
		return bids;

	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.version.AbstractVersionable#findAllVersions()
	 */
	@Override
	public Set<Version> findAllVersions()
			throws UnsupportedOperationException, VersionNotFoundException, VersionException {
		final Set<Version> versions;
		try {
			final WebTarget webTarget = RestWebClient.newInstance()
					.target(Configuration.newInstance().getProperty(Configuration.BAZAAR_REST_WEB_SERVICE_URL))
					.path(this.getIdentifier().getValue()).queryParam(RequestParameters.VERSIONS, true);
			final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
			versions = RestWebClient.processResponse(new GenericType<Set<Version>>() {
			}, response);
		}
		catch (final BazaarException exception) {
			if (exception instanceof VersionNotFoundException) {
				throw (VersionNotFoundException)exception;
			}
			else if (exception instanceof VersionException) {
				throw (VersionException)exception;
			}
			else {
				throw new VersionException(exception);
			}
		}
		return versions;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Persistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		final WebTarget webTarget = RestWebClient.newInstance()
				.target(Configuration.newInstance().getProperty(Configuration.BAZAAR_REST_WEB_SERVICE_URL))
				.path(this.getIdentifier().getValue())
				.queryParam(RequestParameters.ITEM, this.item.getIdentifier().getValue())
				.queryParam(RequestParameters.START_DATE, this.startDate.getTimeInMillis())
				.queryParam(RequestParameters.END_DATE, this.endDate.getTimeInMillis())
				.queryParam(RequestParameters.RESERVE, this.reservePrice);
		RestWebClient.processResponse(new GenericType<Bazaar>() {
		}, webTarget.request().accept(MediaType.APPLICATION_JSON_TYPE)
				.buildPut(Entity.entity(this, MediaType.APPLICATION_JSON_TYPE)).invoke());
		super.persist();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Persistable#delete()
	 */
	@Override
	public void delete() throws BazaarException {
		super.delete();
		final WebTarget webTarget = RestWebClient.newInstance()
				.target(Configuration.newInstance().getProperty(Configuration.BAZAAR_REST_WEB_SERVICE_URL))
				.path(this.getIdentifier().getValue());
		RestWebClient.processResponse(new GenericType<Bazaar>() {
		}, webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildDelete().invoke());
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractPersistable#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this,
				new Object[] { this.getIdentifier(), this.item, this.startDate, this.endDate, this.reservePrice });
	}

}
