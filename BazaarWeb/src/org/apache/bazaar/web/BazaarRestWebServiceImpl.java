/*
 * BazaarRestWebServiceImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 4:29:52 PM
 */
package org.apache.bazaar.web;

import java.net.URI;
import java.util.Calendar;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;
import org.apache.bazaar.ejb.BazaarSessionBean;
import org.apache.bazaar.ejb.ItemSessionBean;
import org.apache.bazaar.version.Version;

/**
 * BazaarRestWebServiceImpl extends AbstractRestWebService and provides a web
 * service for handling {@link Bazaar} requests
 */
@Path("/Bazaar")
public class BazaarRestWebServiceImpl extends AbstractRestWebService {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarRestWebServiceImpl
	 */
	public BazaarRestWebServiceImpl() {
		super();
	}

	// declare methods

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
		final BazaarSessionBean sessionBean = (BazaarSessionBean)this.lookup(BazaarSessionBean.BEAN_LOOKUP_NAME);
		if (pathParameters.hasParameter(RequestParameters.IDENTIFIER)) {
			final Bazaar bazaar = sessionBean
					.findBazaar(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
			if (queryParameters.hasParameter(RequestParameters.VERSIONS)
					&& Boolean.valueOf(queryParameters.getParameter(RequestParameters.VERSIONS)).booleanValue()) {
				final Set<Version> versions = sessionBean.findAllVersions(bazaar);
				response = AbstractRestWebService.newResponse(new GenericEntity<Set<Version>>(versions) {
				}).build();
			}
			else {
				response = AbstractRestWebService.newResponse(new GenericEntity<Bazaar>(bazaar) {
				}).build();
			}
		}
		else {
			final Set<Bazaar> bazaars = sessionBean.findAllBazaars();
			response = AbstractRestWebService.newResponse(new GenericEntity<Set<Bazaar>>(bazaars) {
			}).build();
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractRestWebService#doPut(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doPut(final RestWebServiceRequest request) throws Throwable {
		final Bazaar bazaar;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final BazaarSessionBean bazaarSessionBean = (BazaarSessionBean)this.lookup(BazaarSessionBean.BEAN_LOOKUP_NAME);
		final ItemSessionBean itemSessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		final Item item = itemSessionBean
				.findItem(Identifier.fromValue(queryParameters.getParameter(RequestParameters.ITEM)));
		final Calendar startDate = Calendar.getInstance();
		startDate.setTimeInMillis(Long.valueOf(queryParameters.getParameter(RequestParameters.START_DATE)).longValue());
		final Calendar endDate = Calendar.getInstance();
		endDate.setTimeInMillis(Long.valueOf(queryParameters.getParameter(RequestParameters.END_DATE)));
		if (queryParameters.hasParameter(RequestParameters.RESERVE)) {
			bazaar = bazaarSessionBean.newBazaar(item, startDate, endDate,
					Double.valueOf(queryParameters.getParameter(RequestParameters.RESERVE)));
		}
		else {
			bazaar = bazaarSessionBean.newBazaar(item, startDate, endDate);
		}
		AbstractRestWebService
				.setIdentifier(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)), bazaar);
		bazaar.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Bazaar>(bazaar) {
		}).location(new URI(bazaar.getIdentifier().getValue())).build();
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
		final BazaarSessionBean bazaarSessionBean = (BazaarSessionBean)this.lookup(BazaarSessionBean.BEAN_LOOKUP_NAME);
		final Bazaar bazaar = bazaarSessionBean
				.findBazaar(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
		response = AbstractRestWebService.newResponse(new GenericEntity<Bazaar>(bazaar) {
		}).build();
		bazaar.delete();
		return response;
	}

}
