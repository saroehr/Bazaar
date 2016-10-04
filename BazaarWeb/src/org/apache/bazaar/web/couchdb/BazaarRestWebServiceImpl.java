/*
 * BazaarRestWebServiceImpl.java
 * Created On: Sep 27, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com
 */
package org.apache.bazaar.web.couchdb;

import javax.ws.rs.core.Response;

import org.apache.bazaar.web.RestWebServiceRequest;

/**
 * BazaarRestWebServiceImpl
 */
public final class BazaarRestWebServiceImpl extends org.apache.bazaar.web.BazaarRestWebServiceImpl {

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
	 * @see
	 * org.apache.bazaar.web.BazaarRestWebServiceImpl#doGet(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doGet(final RestWebServiceRequest request) throws Throwable {
		// TODO Auto-generated method stub
		return super.doGet(request);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.BazaarRestWebServiceImpl#doPut(org.apache.bazaar.
	 * web.RestWebServiceRequest)
	 */
	@Override
	protected Response doPut(final RestWebServiceRequest request) throws Throwable {
		// TODO Auto-generated method stub
		return super.doPut(request);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.BazaarRestWebServiceImpl#doDelete(org.apache.bazaar
	 * .web.RestWebServiceRequest)
	 */
	@Override
	protected Response doDelete(final RestWebServiceRequest request) throws Throwable {
		// TODO Auto-generated method stub
		return super.doDelete(request);
	}

}
