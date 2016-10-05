/*
 * CategoryRestWebServiceImpl.java
 * Created On: Sep 27, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com
 */
package org.apache.bazaar.web.couchdb;

import javax.ws.rs.Path;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.bazaar.web.RequestParameters;
import org.apache.bazaar.web.RestWebClient;
import org.apache.bazaar.web.RestWebServiceRequest;
import org.apache.bazaar.web.couchdb.config.Configuration;

/**
 * CategoryRestWebServiceImpl
 */
@Path(value = "/Category")
public final class CategoryRestWebServiceImpl extends AbstractRestWebService {

	// declare members

	// declare constructors

	/**
	 * Constructor for CategoryRestWebServiceImpl
	 */
	public CategoryRestWebServiceImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.AbstractRestWebService#doGet(org.apache.bazaar.web.
	 * RestWebServiceRequest)
	 */
	@Override
	protected Response doGet(final RestWebServiceRequest request) throws Throwable {
		final Response response;
		try (final RestWebClient client = RestWebClient.newInstance();) {
			final RequestParameters queryParameters = RequestParameters
					.newInstance(request.getUriInfo().getQueryParameters());
			final UriBuilder builder = UriBuilder
					.fromPath(Configuration.newInstance().getProperty(Configuration.CATEGORY_COUCHDB_URL));
			builder.path(queryParameters.getParameter(RequestParameters.IDENTIFIER));
			final WebTarget webTarget = client.target(builder);
			response = webTarget.request(MediaType.APPLICATION_JSON).buildGet().invoke();
			System.out.println(response);
		}
		return response;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.AbstractRestWebService#doPost(org.apache.bazaar.web
	 * .RestWebServiceRequest)
	 */
	@Override
	protected Response doPost(final RestWebServiceRequest request) throws Throwable {
		return super.doPost(request);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.AbstractRestWebService#doPut(org.apache.bazaar.web.
	 * RestWebServiceRequest)
	 */
	@Override
	protected Response doPut(final RestWebServiceRequest request) throws Throwable {
		final Response response;
		try (final RestWebClient client = RestWebClient.newInstance();) {
			final RequestParameters queryParameters = RequestParameters
					.newInstance(request.getUriInfo().getQueryParameters());
			final UriBuilder builder = UriBuilder
					.fromPath(Configuration.newInstance().getProperty(Configuration.CATEGORY_COUCHDB_URL));
			builder.path(queryParameters.getParameter(RequestParameters.IDENTIFIER));
			final WebTarget webTarget = client.target(builder);
			response = webTarget.request(MediaType.APPLICATION_JSON).buildGet().invoke();
			System.out.println(response);
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.AbstractRestWebService#doDelete(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doDelete(final RestWebServiceRequest request) throws Throwable {
		return super.doDelete(request);
	}

}
