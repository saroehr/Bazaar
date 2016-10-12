/*
 * AbstractRestWebService.java
 * Created On: Sep 27, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com
 */
package org.apache.bazaar.web.couchdb;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarNotFoundException;
import org.apache.bazaar.Bid;
import org.apache.bazaar.BidNotFoundException;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.BidderNotFoundException;
import org.apache.bazaar.Category;
import org.apache.bazaar.CategoryNotFoundException;
import org.apache.bazaar.Item;
import org.apache.bazaar.ItemNotFoundException;
import org.apache.bazaar.web.RestWebServiceException;

/**
 * AbstractRestWebService extends
 * {@link org.apache.bazaar.web.AbstractRestWebService} to provide a type
 * suitable for sub class creation.
 */
abstract class AbstractRestWebService extends org.apache.bazaar.web.AbstractRestWebService {

	// declare members

	// declare constructors

	/**
	 * Constructor for AbstractRestWebService
	 */
	public AbstractRestWebService() {
		super();
	}

	// declare methods

	/**
	 * Utility method processes couchdb rest request json response into the json
	 * response form required by the generic client framework. If the couchdb
	 * response is an exception, then that is processed and thrown for handling
	 * by the framework
	 *
	 * @param type The GenericType to be included into response
	 * @param response The CouchDB request response
	 * @return The new Response object to be sent back to client
	 * @throws BazaarException if the conversion could not be completed or the
	 *         couchdb response is an exception
	 */
	protected static @NotNull <T> Response processResponse(@NotNull final GenericType<T> type,
			@NotNull final Response response) throws BazaarException {
		final Response response1;
		if (MediaType.APPLICATION_JSON_TYPE.equals(response.getMediaType()) && response.hasEntity()) {
			if (Response.Status.Family.CLIENT_ERROR.equals(Response.Status.Family.familyOf(response.getStatus()))
					|| Response.Status.Family.SERVER_ERROR
							.equals(Response.Status.Family.familyOf(response.getStatus()))) {
				if (Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()
						|| Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
					if (Bazaar.class.isAssignableFrom(type.getRawType())) {
						throw new BazaarNotFoundException();
					}
					if (Category.class.isAssignableFrom(type.getRawType())) {
						throw new CategoryNotFoundException();
					}
					else if (Item.class.isAssignableFrom(type.getRawType())) {
						throw new ItemNotFoundException();
					}
					else if (Bidder.class.isAssignableFrom(type.getRawType())) {
						throw new BidderNotFoundException();
					}
					else if (Bid.class.isAssignableFrom(type.getRawType())) {
						throw new BidNotFoundException();
					}
					else {
						throw new RestWebServiceException(response.getStatusInfo().getReasonPhrase());
					}
				}
			}
			response1 = null;
		}
		else {
			throw new RestWebServiceException(response.getStatusInfo().getReasonPhrase());
		}
		return response1;
	}

}
