/*
 * BazaarApplicationImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 24, 2016 at 12:29:49 PM
 */
package org.apache.bazaar.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.bazaar.web.config.Configuration;
import org.glassfish.jersey.CommonProperties;

/**
 * BazaarApplicationImpl extends Application to provide a JAX-RS Application
 * instance for the Bazaar
 */
// @ApplicationPath("/ejb")
public final class BazaarApplicationImpl extends Application {

	// declare members

	private static final Class<?>[] RESOURCE_CLASSES = new Class<?>[] { BazaarRestWebServiceImpl.class,
			CategoryRestWebServiceImpl.class, ItemRestWebServiceImpl.class, BidderRestWebServiceImpl.class,
			BidRestWebServiceImpl.class };
	private static final Set<Class<?>> CLASSES;

	static {
		final Set<Class<?>> classes = new HashSet<Class<?>>(
				Configuration.PROVIDER_CLASSES.length + BazaarApplicationImpl.RESOURCE_CLASSES.length);
		classes.addAll(Arrays.asList(Configuration.PROVIDER_CLASSES));
		classes.addAll(Arrays.asList(BazaarApplicationImpl.RESOURCE_CLASSES));
		CLASSES = Collections.unmodifiableSet(classes);
	}

	private static final Map<String, Object> PROPERTIES;

	static {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
		PROPERTIES = Collections.unmodifiableMap(properties);
	}

	// declare constructors

	/**
	 * Constructor for BazaarApplicationImpl
	 */
	public BazaarApplicationImpl() {
		super();

	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		return BazaarApplicationImpl.CLASSES;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Application#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return BazaarApplicationImpl.PROPERTIES;
	}

}
