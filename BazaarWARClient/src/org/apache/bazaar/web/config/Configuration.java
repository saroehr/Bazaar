/*
 * Configuration.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 17, 2016 at 9:31:09 PM
 */
package org.apache.bazaar.web.config;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Category;
import org.apache.bazaar.Item;

/**
 * Configuration extends Configuration and declares the methods
 * an implementation must provide
 */
public final class Configuration extends org.apache.bazaar.config.Configuration {

	// declare members

	/**
	 * Default encoding of UTF-8
	 * UTF-8 encoding string
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * The key for retrieval of the ROOT Category
	 * name value
	 */
	public static final String ROOT_CATEGORY_NAME = "org.apache.bazaar.Category.rootcategoryname";

	/**
	 * The key for retrieval of the ROOT Category
	 * description value
	 */
	public static final String ROOT_CATEGORY_DESCRIPTION = "org.apache.bazaar.Category.rootcategorydescription";

	/**
	 * Key for lookup of unsupported method message
	 */
	public static final String UNSUPPORTED_METHOD_MESSAGE = "org.apache.bazaar.RestWebService.unsupportedmethod";

	/**
	 * The key for retrieval of the {@link Bazaar} web service
	 * url value
	 */
	public static final String BAZAAR_REST_WEB_SERVICE_URL = Bazaar.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Item} web service
	 * url value
	 */
	public static final String ITEM_REST_WEB_SERVICE_URL = Item.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Category} web service
	 * url value
	 */
	public static final String CATEGORY_REST_WEB_SERVICE_URL = Category.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Bidder} web service
	 * url value
	 */
	public static final String BIDDER_REST_WEB_SERVICE_URL = Bidder.class.getName() + "." + "restwebservice.url";

	/**
	 * The key for retrieval of the {@link Bid} web service
	 * url value
	 */
	public static final String BID_REST_WEB_SERVICE_URL = Bid.class.getName() + "." + "restwebservice.url";

	// declare constructors

	/**
	 * Constructor for Configuration
	 */
	private Configuration() {
		super();
	}

	// declare methods

}
