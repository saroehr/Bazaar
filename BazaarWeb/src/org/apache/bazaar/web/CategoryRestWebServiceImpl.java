/*
 * CategoryRestWebServiceImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 3:41:48 PM
 */
package org.apache.bazaar.web;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.bazaar.Category;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.config.Configuration;
import org.apache.bazaar.ejb.CategorySessionBean;

/**
 * CategoryRestWebServiceImpl extends AbstractRestWebService and
 * provides a web service for handling {@link Category} requests
 */
@Path("/Category")
public class CategoryRestWebServiceImpl extends AbstractRestWebService {

	// declare members

	// declare constructors

	/**
	 * Constructor for CategoryRestWebServiceImpl
	 */
	public CategoryRestWebServiceImpl() {
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
		final CategorySessionBean sessionBean = (CategorySessionBean)this.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		if (queryParameters.hasParameter(RequestParameters.IDENTIFIER)) {
			final Category category;
			if (Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)).equals(Identifier
					.fromValue(Configuration.newInstance().getProperty(Configuration.ROOT_CATEGORY_IDENTIFIER)))) {
				category = sessionBean.findRootCategory();
			}
			else {
				category = sessionBean
						.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)));
			}
			category.getChildren();
			response = AbstractRestWebService.newResponse(new GenericEntity<Category>(category) {
			}).build();
		}
		else {
			final Set<Category> categories = sessionBean.findAllCategories();
			// loop through categories to populate children
			response = AbstractRestWebService.newResponse(new GenericEntity<Set<Category>>(categories) {
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
		final Category category;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final CategorySessionBean sessionBean = (CategorySessionBean)this.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		category = sessionBean
				.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)));
		if (queryParameters.hasParameter(RequestParameters.NAME)) {
			category.setName(queryParameters.getParameter(RequestParameters.NAME));
		}
		if (queryParameters.hasParameter(RequestParameters.DESCRIPTION)) {
			category.setDescription(queryParameters.getParameter(RequestParameters.DESCRIPTION));
		}
		if (queryParameters.hasParameter(RequestParameters.PARENT)) {
			category.setParent(sessionBean
					.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.PARENT))));
		}
		// we need to persist children recursively but right now
		// we don't receive the json representation of the category
		category.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Category>(category) {
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
		final Category category;
		final RequestParameters queryParameters = RequestParameters
				.newInstance(request.getUriInfo().getQueryParameters());
		final CategorySessionBean sessionBean = (CategorySessionBean)this.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		category = sessionBean.newCategory(queryParameters.getParameter(RequestParameters.NAME),
				queryParameters.getParameter(RequestParameters.DESCRIPTION),
				sessionBean.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.PARENT))));
		AbstractRestWebService.setIdentifier(
				Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)), category);
		// we need to persist children recursively but right now
		// we don't receive the json representation of the category
		category.persist();
		return AbstractRestWebService.newResponse(new GenericEntity<Category>(category) {
		}).location(new URI(category.getIdentifier().getValue())).build();
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
		final CategorySessionBean sessionBean = (CategorySessionBean)this.lookup(CategorySessionBean.BEAN_LOOKUP_NAME);
		final Category category = sessionBean
				.findCategory(Identifier.fromValue(queryParameters.getParameter(RequestParameters.IDENTIFIER)));
		response = AbstractRestWebService.newResponse(new GenericEntity<Category>(category) {
		}).build();
		category.delete();
		return response;
	}

}
