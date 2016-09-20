/*
 * BidRestWebServiceImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 4:21:52 PM
 */
package org.apache.bazaar.web;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.ejb.BazaarSessionBean;
import org.apache.bazaar.ejb.BidSessionBean;
import org.apache.bazaar.ejb.BidderSessionBean;

/**
 * BidRestWebServiceImpl extends AbstractRestWebService and provides
 * a web service type for handling {@link Bid} requests
 */
@Path("/Bid")
public class BidRestWebServiceImpl extends AbstractRestWebService {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidRestWebServiceImpl
	 */
	public BidRestWebServiceImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.AbstractRestWebService#doGet(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doGet(final RestWebServiceRequest request) throws Throwable {
		final Response response;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final BidSessionBean bidSessionBean = (BidSessionBean)this.lookup(BidSessionBean.BEAN_LOOKUP_NAME);
		final BazaarSessionBean bazaarSessionBean = (BazaarSessionBean)this.lookup(BazaarSessionBean.BEAN_LOOKUP_NAME);
		final BidderSessionBean bidderSessionBean = (BidderSessionBean)this.lookup(BidderSessionBean.BEAN_LOOKUP_NAME);
		if (queryParameters.hasParameter(RequestParameters.IDENTIFIER)) {
			final Bid bid = bidSessionBean
					.findBid(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)));
			response = AbstractRestWebService.newResponse(new GenericEntity<Bid>(bid) {
			}).build();
		}
		else if (queryParameters.hasParameter(RequestParameters.BAZAAR)) {
			if (queryParameters.hasParameter(RequestParameters.BIDDER)) {
				final Bazaar bazaar = bazaarSessionBean
						.findBazaar(Identifier.fromValue(queryParameters.getParameter(RequestParameters.BAZAAR)));
				final Bidder bidder = bidderSessionBean
						.findBidder(Identifier.fromValue(queryParameters.getParameter(RequestParameters.BIDDER)));
				final Set<Bid> bids = bidSessionBean.findBids(bazaar, bidder);
				response = AbstractRestWebService.newResponse(new GenericEntity<Set<Bid>>(bids) {
				}).build();
			}
			else {
				final Bazaar bazaar = bazaarSessionBean
						.findBazaar(Identifier.fromValue(queryParameters.getParameter(RequestParameters.BAZAAR)));
				final Set<Bid> bids = bazaar.findAllBids();
				response = AbstractRestWebService.newResponse(new GenericEntity<Set<Bid>>(bids) {
				}).build();
			}
		}
		else if (queryParameters.hasParameter(RequestParameters.BIDDER)) {
			final Set<Bid> bids = bidSessionBean.findAllBids(bidderSessionBean
					.findBidder(Identifier.fromValue(queryParameters.getParameter(RequestParameters.BIDDER))));
			response = AbstractRestWebService.newResponse(new GenericEntity<Set<Bid>>(bids) {
			}).build();
		}
		else {
			final Set<Bid> bids = bidSessionBean.findAllBids();
			response = AbstractRestWebService.newResponse(new GenericEntity<Set<Bid>>(bids) {
			}).build();
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.AbstractRestWebService#doPut(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doPut(final RestWebServiceRequest request) throws Throwable {
		final Bid bid;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final BidSessionBean bidSessionBean = (BidSessionBean)this.lookup(BidSessionBean.BEAN_LOOKUP_NAME);
		final BazaarSessionBean bazaarSessionBean = (BazaarSessionBean)this.lookup(BazaarSessionBean.BEAN_LOOKUP_NAME);
		final BidderSessionBean bidderSessionBean = (BidderSessionBean)this.lookup(BidderSessionBean.BEAN_LOOKUP_NAME);
		final Bazaar bazaar = bazaarSessionBean
				.findBazaar(Identifier.fromValue(queryParameters.getParameter(RequestParameters.BAZAAR)));
		final Bidder bidder = bidderSessionBean
				.findBidder(Identifier.fromValue(queryParameters.getParameter(RequestParameters.BIDDER)));
		final Double price = Double.valueOf(queryParameters.getParameter(RequestParameters.PRICE));
		bid = bidSessionBean.newBid(bazaar, bidder, price);
		AbstractRestWebService
				.setIdentifier(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)), bid);
		bid.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Bid>(bid) {
		}).location(new URI(bid.getIdentifier().getValue())).build();
	}

}
