/*
 * ItemRestWebServiceImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 9:53:52 AM
 */
package org.apache.bazaar.web;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.bazaar.Category;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;
import org.apache.bazaar.ejb.CategorySessionBean;
import org.apache.bazaar.ejb.ItemSessionBean;

/**
 * ItemRestWebServiceImpl provides a JAX-RS WebService implementation handling
 * {@link Item} instances
 */
@Path("/Item")
public class ItemRestWebServiceImpl extends AbstractRestWebService {

	// declare members

	// declare constructors

	/**
	 * Constructor for ItemRestWebServiceImpl
	 */
	public ItemRestWebServiceImpl() {
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
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final ItemSessionBean itemSessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		if (pathParameters.hasParameter(RequestParameters.IDENTIFIER)) {
			final Item item = itemSessionBean
					.findItem(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
			response = AbstractRestWebService.newResponse(new GenericEntity<Item>(item) {
			}).build();
		}
		else {
			final Set<Item> items = itemSessionBean.findAllItems();
			response = AbstractRestWebService.newResponse(new GenericEntity<Set<Item>>(items) {
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
		final Item item;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final ItemSessionBean itemSessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		final CategorySessionBean categorySessionBean = (CategorySessionBean)this
				.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		item = itemSessionBean
				.findItem(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
		if (queryParameters.hasParameter(RequestParameters.NAME)) {
			item.setName(queryParameters.getParameter(RequestParameters.NAME));
		}
		if (queryParameters.hasParameter(RequestParameters.DESCRIPTION)) {
			item.setDescription(queryParameters.getParameter(RequestParameters.DESCRIPTION));
		}
		if (queryParameters.hasParameter(RequestParameters.CATEGORY)) {
			item.setCategory(categorySessionBean
					.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.CATEGORY))));
		}
		item.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Item>(item) {
		}).build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.AbstractRestWebService#doPut(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doPut(final RestWebServiceRequest request) throws Throwable {
		final Item item;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final RequestParameters pathParameters = RequestParameters
				.newInstance(request.getUriInfo().getPathParameters());
		final ItemSessionBean itemSessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		final CategorySessionBean categorySessionBean = (CategorySessionBean)this
				.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		final Category category = categorySessionBean
				.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.CATEGORY)));
		item = itemSessionBean.newItem(queryParameters.getParameter(RequestParameters.NAME),
				queryParameters.getParameter(RequestParameters.DESCRIPTION), category);
		AbstractRestWebService
				.setIdentifier(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)), item);
		item.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Item>(item) {
		}).location(new URI(item.getIdentifier().getValue())).build();
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
		final ItemSessionBean sessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		final Item item = sessionBean
				.findItem(Identifier.fromValue(pathParameters.getParameter(RequestParameters.IDENTIFIER)));
		response = AbstractRestWebService.newResponse(new GenericEntity<Item>(item) {
		}).build();
		item.delete();
		return response;
	}

}
