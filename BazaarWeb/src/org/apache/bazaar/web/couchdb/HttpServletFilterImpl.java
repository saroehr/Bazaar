/*
 * HttpServletFilterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 11:58:47 AM
 */
package org.apache.bazaar.web.couchdb;

import java.io.IOException;
import java.util.Locale.Category;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.web.RestWebClient;
import org.apache.bazaar.web.couchdb.config.Configuration;

import com.sun.mail.imap.protocol.Item;

/**
 * HttpServletFilterImpl implements Filter to provide an abstract class suitable
 * for sub class implementations
 */
@WebFilter(filterName = "org.apache.bazaar.web.couchdb.HttpServletFilterImpl", urlPatterns = {
		"/couchdb/*" }, initParams = {
				@WebInitParam(name = "javax.servlet.jsp.jstl.fmt.localizationContext", value = "org.apache.bazaar.nls.messages") })
public final class HttpServletFilterImpl implements Filter {

	// declare members

	private static final Logger LOGGER = Logger.newInstance(HttpServletFilterImpl.class);

	// declare constructors

	/**
	 * Constructor for HttpServletFilterImpl
	 */
	public HttpServletFilterImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(final FilterConfig config) throws ServletException {
		// initialize the couchdb database
		try {
			final RestWebClient client = RestWebClient.newInstance();
			final WebTarget webTarget = client
					.target(Configuration.newInstance().getProperty(Configuration.COUCHDB_URL))
					.path(Bazaar.class.getName());
			Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.buildPut(Entity.json(Bazaar.class.getName())).invoke();
			if (Status.OK.equals(response.getStatus()) || Status.PRECONDITION_FAILED.equals(response.getStatus())) {
				HttpServletFilterImpl.LOGGER.config("CouchDB " + Bazaar.class.getName() + " database created");
			}
			webTarget.path(Category.class.getName());
			response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.buildPut(Entity.json(Category.class.getName())).invoke();
			if (Status.OK.equals(response.getStatus()) || Status.PRECONDITION_FAILED.equals(response.getStatus())) {
				HttpServletFilterImpl.LOGGER.config("CouchDB " + Category.class.getName() + " database created");
			}
			webTarget.path(Item.class.getName());
			response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildPut(Entity.json(Item.class.getName()))
					.invoke();
			if (Status.OK.equals(response.getStatus()) || Status.PRECONDITION_FAILED.equals(response.getStatus())) {
				HttpServletFilterImpl.LOGGER.config("CouchDB " + Item.class.getName() + " database created");
			}
			webTarget.path(Bidder.class.getName());
			response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildPut(Entity.json(Bidder.class.getName()))
					.invoke();
			if (Status.OK.equals(response.getStatus()) || Status.PRECONDITION_FAILED.equals(response.getStatus())) {
				HttpServletFilterImpl.LOGGER.config("CouchDB " + Bidder.class.getName() + " database created");
			}
			webTarget.path(Bid.class.getName());
			response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildPut(Entity.json(Bid.class.getName()))
					.invoke();
			if (Status.OK.equals(response.getStatus()) || Status.PRECONDITION_FAILED.equals(response.getStatus())) {
				HttpServletFilterImpl.LOGGER.config("CouchDB " + Bid.class.getName() + " database created");
			}
		}
		catch (final BazaarException exception) {
			throw new ServletException(exception);
		}
	}

}
