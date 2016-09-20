/*
 * RestWebService.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 2:22:42 PM
 */
package org.apache.bazaar.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RestWebService declares the web service interface implementation
 * provide. This type of service will consume and produce
 * {@MediaType.APPLICATION_JSON_TYPE}
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RestWebService {

	// declare members

	// declare methods

	/**
	 * Processes DELETE requests
	 * 
	 * @return The Response result
	 */
	@DELETE
	public Response doDelete();

	/**
	 * Processes GET requests
	 * 
	 * @return The Response result
	 */
	@GET
	public Response doGet();

	/**
	 * Processes POST requests
	 * 
	 * @return The Response result
	 */
	@POST
	public Response doPost();

	/**
	 * Processes PUT requests
	 * 
	 * @return The Response result
	 */
	@PUT
	public Response doPut();

}
