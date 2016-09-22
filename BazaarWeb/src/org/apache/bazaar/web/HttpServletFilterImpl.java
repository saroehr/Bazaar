/*
 * HttpServletFilterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 11:58:47 AM
 */
package org.apache.bazaar.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;

/**
 * HttpServletFilterImpl implements Filter to provide
 * an abstract class suitable for sub class implementations
 */
@Resource(name = "java:comp/env/jdbc/Bazaar", lookup = "jdbc/Bazaar", type = javax.sql.XADataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
@WebFilter(filterName = "HttpServletFilterImpl", urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "javax.servlet.jsp.jstl.fmt.localizationContext", value = "org.apache.bazaar.nls.messages") })
public final class HttpServletFilterImpl implements Filter {

	// declare members

	// declare constructors

	/**
	 * Constructor for HttpServletFilterImpl
	 */
	public HttpServletFilterImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		try {
			BazaarManager.newInstance();
		}
		catch (final BazaarException exception) {
			throw new ServletException(exception);
		}
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(final FilterConfig config) throws ServletException {

	}

}
