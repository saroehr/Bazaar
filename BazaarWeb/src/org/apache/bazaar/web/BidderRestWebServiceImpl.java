/*
 * BidderRestWebServiceImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 1:32:43 PM
 */
package org.apache.bazaar.web;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.bazaar.Address;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Name;
import org.apache.bazaar.State;
import org.apache.bazaar.ejb.BidderSessionBean;
import org.apache.bazaar.version.Version;

/**
 * BidderRestWebServiceImpl extends AbstractRestWebService and provides a web
 * service for handling {@link Bidder} requests
 */
@Path("/Bidder")
public final class BidderRestWebServiceImpl extends AbstractRestWebService {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidderRestWebServiceImpl
	 */
	public BidderRestWebServiceImpl() {
		super();
	}

	// declare members

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractRestWebService#doGet(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doGet(final RestWebServiceRequest request) throws Throwable {
		final Response response;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final BidderSessionBean sessionBean = (BidderSessionBean)this.lookup(BidderSessionBean.BEAN_LOOKUP_NAME);
		if (pathParameters.hasParameter(RequestParameters.IDENTIFIER)) {
			final Bidder bidder = sessionBean
					.findBidder(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
			if (queryParameters.hasParameter(RequestParameters.VERSIONS)
					&& Boolean.valueOf(queryParameters.getParameter(RequestParameters.VERSIONS)).booleanValue()) {
				final Set<Version> versions = bidder.findAllVersions();
				response = AbstractRestWebService.newResponse(new GenericEntity<Set<Version>>(versions) {
				}).build();
			}
			else {
				response = AbstractRestWebService.newResponse(new GenericEntity<Bidder>(bidder) {
				}).build();
			}
		}
		else {
			final Set<Bidder> bidders = sessionBean.findAllBidders();
			response = AbstractRestWebService.newResponse(new GenericEntity<Set<Bidder>>(bidders) {
			}).build();
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractRestWebService#doPost(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doPost(final RestWebServiceRequest request) throws Throwable {
		final Bidder bidder;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final BidderSessionBean sessionBean = (BidderSessionBean)this.lookup(BidderSessionBean.BEAN_LOOKUP_NAME);
		bidder = sessionBean
				.findBidder(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
		if (queryParameters.hasParameter(RequestParameters.FIRST_NAME)) {
			bidder.getName().setFirstName(queryParameters.getParameter(RequestParameters.FIRST_NAME));

		}
		if (queryParameters.hasParameter(RequestParameters.LAST_NAME)) {
			bidder.getName().setLastName(queryParameters.getParameter(RequestParameters.LAST_NAME));
		}
		if (queryParameters.hasParameter(RequestParameters.BILLING_ADDRESS_STREET)) {
			bidder.getBillingAddress()
					.setStreet(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_STREET));
		}
		if (queryParameters.hasParameter(RequestParameters.BILLING_ADDRESS_CITY)) {
			bidder.getBillingAddress().setCity(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_CITY));
		}
		if (queryParameters.hasParameter(RequestParameters.BILLING_ADDRESS_STATE)) {
			bidder.getBillingAddress()
					.setState(State.valueOf(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_STATE)));
		}
		if (queryParameters.hasParameter(RequestParameters.BILLING_ADDRESS_ZIPCODE)) {
			bidder.getBillingAddress().setZipcode(
					Integer.valueOf(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_ZIPCODE)));
		}
		if (queryParameters.hasParameter(RequestParameters.SHIPPING_ADDRESS_STREET)) {
			bidder.getShippingAddress()
					.setStreet(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_STREET));
		}
		if (queryParameters.hasParameter(RequestParameters.SHIPPING_ADDRESS_CITY)) {
			bidder.getShippingAddress().setCity(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_CITY));
		}
		if (queryParameters.hasParameter(RequestParameters.SHIPPING_ADDRESS_STATE)) {
			bidder.getShippingAddress()
					.setState(State.valueOf(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_STATE)));
		}
		if (queryParameters.hasParameter(RequestParameters.SHIPPING_ADDRESS_ZIPCODE)) {
			bidder.getShippingAddress().setZipcode(
					Integer.valueOf(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_ZIPCODE)));
		}
		bidder.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Bidder>(bidder) {
		}).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractRestWebService#doPut(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doPut(final RestWebServiceRequest request) throws Throwable {
		final Bidder bidder;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final BidderSessionBean sessionBean = (BidderSessionBean)this.lookup(BidderSessionBean.BEAN_LOOKUP_NAME);
		final Name name = sessionBean.newName();
		name.setFirstName(queryParameters.getParameter(RequestParameters.FIRST_NAME));
		name.setLastName(queryParameters.getParameter(RequestParameters.LAST_NAME));
		final Address billingAddress = sessionBean.newAddress();
		billingAddress.setStreet(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_STREET));
		billingAddress.setCity(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_CITY));
		billingAddress.setState(State.valueOf(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_STATE)));
		billingAddress
				.setZipcode(Integer.valueOf(queryParameters.getParameter(RequestParameters.BILLING_ADDRESS_ZIPCODE)));
		final Address shippingAddress = sessionBean.newAddress();
		shippingAddress.setStreet(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_STREET));
		shippingAddress.setCity(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_CITY));
		shippingAddress.setState(State.valueOf(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_STATE)));
		shippingAddress
				.setZipcode(Integer.valueOf(queryParameters.getParameter(RequestParameters.SHIPPING_ADDRESS_ZIPCODE)));
		bidder = sessionBean.newBidder(name, billingAddress, shippingAddress);
		AbstractRestWebService
				.setIdentifier(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)), bidder);
		bidder.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Bidder>(bidder) {
		}).location(new URI(bidder.getIdentifier().getValue())).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractRestWebService#doDelete(org.apache.bazaar
	 * .web.RestWebServiceRequest)
	 */
	@Override
	protected Response doDelete(final RestWebServiceRequest request) throws Throwable {
		final Response response;
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final BidderSessionBean sessionBean = (BidderSessionBean)this.lookup(BidderSessionBean.BEAN_LOOKUP_NAME);
		final Bidder bidder = sessionBean
				.findBidder(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
		response = AbstractRestWebService.newResponse(new GenericEntity<Bidder>(bidder) {
		}).build();
		bidder.delete();
		return response;
	}

}
