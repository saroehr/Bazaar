/*
 * RequestParameters.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 9, 2016 at 7:51:43 PM
 */
package org.apache.bazaar.web;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * RequestParameters encapsulates a @{link Map<String, List<String>}
 */
public final class RequestParameters {

	// declare members
	/**
	 * The Identifier parameter name
	 */
	public static final String IDENTIFIER = JsonKeys.IDENTIFIER;
	/**
	 * The name parameter name
	 */
	public static final String NAME = JsonKeys.NAME;
	/**
	 * 
	 */
	public static final String FIRST_NAME = JsonKeys.FIRST_NAME;
	/**
	 * 
	 */
	public static final String LAST_NAME = JsonKeys.LAST_NAME;
	/**
	 * 
	 */
	public static final String BILLING_ADDRESS_STREET = "billingStreet";
	/**
	 * 
	 */
	public static final String BILLING_ADDRESS_CITY = "billingCity";
	/**
	 * 
	 */
	public static final String BILLING_ADDRESS_STATE = "billingState";
	/**
	 * 
	 */
	public static final String BILLING_ADDRESS_ZIPCODE = "billingZipcode";
	/**
	 * 
	 */
	public static final String SHIPPING_ADDRESS_STREET = "shippingStreet";
	/**
	 * 
	 */
	public static final String SHIPPING_ADDRESS_CITY = "shippingCity";
	/**
	 * 
	 */
	public static final String SHIPPING_ADDRESS_STATE = "shippingState";
	/**
	 * 
	 */
	public static final String SHIPPING_ADDRESS_ZIPCODE = "shippingZipcode";
	/**
	 * 
	 */
	public static final String DESCRIPTION = JsonKeys.DESCRIPTION;
	/**
	 * 
	 */
	public static final String CATEGORY = JsonKeys.CATEGORY;
	/**
	 * 
	 */
	public static final String BAZAAR = JsonKeys.BAZAAR;
	/**
	 * 
	 */
	public static final String START_DATE = JsonKeys.START;
	/**
	 * 
	 */
	public static final String END_DATE = JsonKeys.END;
	/**
	 * 
	 */
	public static final String RESERVE = JsonKeys.RESERVE;
	/**
	 * 
	 */
	public static final String ITEM = JsonKeys.ITEM;
	/**
	 * 
	 */
	public static final String IMAGE = JsonKeys.IMAGE;
	/**
	 * 
	 */
	public static final String BIDDER = JsonKeys.BIDDER;
	/**
	 * 
	 */
	public static final String BID = JsonKeys.BID;
	/**
	 * 
	 */
	public static final String PARENT = JsonKeys.PARENT;
	/**
	 * 
	 */
	public static final String PRICE = JsonKeys.PRICE;

	private final Map<String, List<String>> map;

	// declare constructors

	/**
	 * Constructor for RequestParameters
	 * 
	 * @param map The request parameters as map
	 */
	private RequestParameters(@NotNull final Map<String, List<String>> map) {
		super();
		this.map = map;
	}

	// declare methods

	/**
	 * Factory method for creating instance
	 * 
	 * @param map The parameter map associated
	 *        with instance
	 * @return Instance of RequestParameters
	 */
	public static RequestParameters newInstance(@NotNull final Map<String, List<String>> map) {
		return new RequestParameters(map);
	}

	/**
	 * Returns value for named parameter or null
	 * if parameters not found in request. This method
	 * will extract the first value from the embedded
	 * Map<String, List<String> and return it
	 * 
	 * @param name The parameter name
	 * @return value The value for named parameter
	 * @throws ParameterNotFoundException if named parameter
	 *         not found in request
	 */
	public @NotNull String getParameter(@NotNull final String name) throws ParameterNotFoundException {
		final String value;
		if (this.hasParameter(name)) {
			value = this.map.get(name).get(0);
		}
		else {
			throw new ParameterNotFoundException(name);
		}
		return value;
	}

	/**
	 * Returns true if request contains named parameter
	 * 
	 * @param name The parameter name
	 * @return True if request has named parameter
	 */
	public boolean hasParameter(@NotNull final String name) {
		final boolean hasParameter;
		if (this.map.containsKey(name)) {
			hasParameter = true;
		}
		else {
			hasParameter = false;
		}
		return hasParameter;
	}

}
