/*
 * RestWebServiceRequest.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 1, 2016 at 7:16:28 PM
 */
package org.apache.bazaar.web;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * RestWebServiceRequest encapsulates the request information
 * sent to the RestWebService service invocation
 */
public final class RestWebServiceRequest {

	// declare members

	private final SecurityContext securityContext;
	private final HttpHeaders httpHeaders;
	private final UriInfo uriInfo;

	// declare constructors

	/**
	 * Constructor for RestWebServiceRequest
	 * 
	 * @param securityContext The {@link SecurityContext} instance
	 * @param httpHeaders The {@link HttpHeaders} instance
	 * @param uriInfo The {@link UriInfo} instance
	 */
	RestWebServiceRequest(@NotNull final SecurityContext securityContext, @NotNull final HttpHeaders httpHeaders,
			@NotNull final UriInfo uriInfo) {
		super();
		this.securityContext = securityContext;
		this.httpHeaders = httpHeaders;
		this.uriInfo = uriInfo;
	}

	/**
	 * Returns The {@link HttpHeaders} associated
	 * with request
	 * 
	 * @return The {@link HttpHeaders} associated
	 *         with request
	 */
	public @NotNull HttpHeaders getHttpHeaders() {
		return this.httpHeaders;
	}

	/**
	 * Returns the {@link SecurityContext} associated
	 * with request
	 * 
	 * @return The {@link SecurityContext} associated
	 *         with request
	 */
	public @NotNull SecurityContext getSecurityContext() {
		return this.securityContext;
	}

	/**
	 * Returns the {@link UriInfo} associated with
	 * request
	 * 
	 * @return The {@link UriInfo} associated with
	 *         request
	 */
	public @NotNull UriInfo getUriInfo() {
		return this.uriInfo;
	}

}
