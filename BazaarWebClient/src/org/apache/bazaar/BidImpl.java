/*
 * BidImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 18, 2016 at 11:53:10 PM
 */
package org.apache.bazaar;

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
import org.apache.bazaar.web.config.Configuration;

/**
 * BidImpl extends AbstractVersionable and implements Bid to provide a concrete
 * implementation
 */
public final class BidImpl extends AbstractVersionable implements Bid {

	// declare members
	private static final long serialVersionUID = -510722290366725532L;

	private Bazaar bazaar;
	private Bidder bidder;
	private Double price;

	// declare constructors

	/**
	 * Constructor for BidImpl
	 */
	BidImpl() {
		super();
	}

	/**
	 * Constructor for BidImpl
	 *
	 * @param bazaar The bazaar for bid
	 * @param bidder The bidder for bid
	 * @param price The price for bid
	 */
	BidImpl(@NotNull final Bazaar bazaar, @NotNull final Bidder bidder, @NotNull final Double price) {
		this();
		this.bazaar = bazaar;
		this.bidder = bidder;
		this.price = price;
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bid#getBazaar()
	 */
	@Override
	public Bazaar getBazaar() {
		return this.bazaar;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bid#getPrice()
	 */
	@Override
	public Double getPrice() {
		return this.price;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Bid#getBidder()
	 */
	@Override
	public Bidder getBidder() {
		return this.bidder;
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
			final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newRestWebClient()
					.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL))
					.path(this.getIdentifier().getValue()).queryParam(RequestParameters.VERSIONS, true);
			final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();
			versions = BazaarManagerImpl.processResponse(new GenericType<Set<Version>>() {
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
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL))
				.path(this.getIdentifier().getValue())
				.queryParam(RequestParameters.BAZAAR, this.bazaar.getIdentifier().getValue())
				.queryParam(RequestParameters.BIDDER, this.bidder.getIdentifier().getValue())
				.queryParam(RequestParameters.PRICE, this.price);
		BazaarManagerImpl.processResponse(new GenericType<Bid>() {
		}, webTarget.request(MediaType.APPLICATION_JSON_TYPE)
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
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.BID_REST_WEB_SERVICE_URL))
				.path(this.getIdentifier().getValue());
		BazaarManagerImpl.processResponse(new GenericType<Bid>() {
		}, webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildDelete().invoke());
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractPersistable#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.getIdentifier(), this.bazaar, this.bidder, this.price });
	}

}
