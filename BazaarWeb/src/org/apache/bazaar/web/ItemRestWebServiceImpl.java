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
import org.apache.bazaar.CategoryNotFoundException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;
import org.apache.bazaar.ejb.CategorySessionBean;
import org.apache.bazaar.ejb.ItemSessionBean;

/**
 * ItemRestWebServiceImpl provides a JAX-RS WebService
 * implementation handling {@link Item}
 * instances
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
		final ItemSessionBean itemSessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		if (queryParameters.hasParameter(RequestParameters.IDENTIFIER)) {
			response = AbstractRestWebService.newResponse(new GenericEntity<Item>(itemSessionBean
					.findItem(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)))) {
			}).build();
		}
		else {
			response = AbstractRestWebService.newResponse(new GenericEntity<Set<Item>>(itemSessionBean.findAllItems()) {
			}).build();
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.AbstractRestWebService#doPost(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doPost(final RestWebServiceRequest request) throws Throwable {
		final Item item;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final ItemSessionBean itemSessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		final CategorySessionBean categorySessionBean = (CategorySessionBean)this
				.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		item = itemSessionBean
				.findItem(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)));
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
		else {
			item.setCategory(
					categorySessionBean.newCategory("doPost", "doPost", categorySessionBean.findRootCategory()));
		}
		item.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Item>(item) {
		}).build();
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
		final Item item;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final ItemSessionBean itemSessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		final CategorySessionBean categorySessionBean = (CategorySessionBean)this
				.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		Category category;
		// final Category category = categorySessionBean.findCategory(
		// Identifier.fromValue(requestParameters.getParameter(RequestParameters.CATEGORY)));
		try {
			category = categorySessionBean
					.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.CATEGORY)));
			// we need to update the category and persist
		}
		catch (final CategoryNotFoundException exception) {
			// we need to persist the category recursively
			// but again we need the json representation to do
			// this
			category = categorySessionBean.newCategory("doPut", "doPut", categorySessionBean.findRootCategory());
		}
		item = itemSessionBean.newItem(queryParameters.getParameter(RequestParameters.NAME),
				queryParameters.getParameter(RequestParameters.DESCRIPTION), category);
		AbstractRestWebService
				.setIdentifier(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)), item);
		item.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Item>(item) {
		}).location(new URI(item.getIdentifier().getValue())).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.AbstractRestWebService#doDelete(org.apache.bazaar
	 * .web.RestWebServiceRequest)
	 */
	@Override
	protected Response doDelete(final RestWebServiceRequest request) throws Throwable {
		final Response response;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final ItemSessionBean sessionBean = (ItemSessionBean)this.lookup(ItemSessionBean.BEAN_LOOKUP_NAME);
		final Item item = sessionBean
				.findItem(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)));
		response = AbstractRestWebService.newResponse(new GenericEntity<Item>(item) {
		}).build();
		item.delete();
		return response;
	}

}
