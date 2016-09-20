/*
 * PropertiesConfigurationImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 12, 2016
 */
package org.apache.bazaar.config;

import java.util.Properties;

/**
 * PropertiesConfigurationImpl extends {@link Configuration} and
 * provides an instance backed by a properties configuration file.
 */
final class PropertiesConfigurationImpl extends Configuration {

	// declare members

	private final Properties properties;

	// declare constructors

	/**
	 * Constructor for PropertiesConfigurationImpl
	 * 
	 * @param properties
	 *        The {@link java.util.Properties} instance
	 */
	PropertiesConfigurationImpl(final Properties properties) {
		super();
		this.properties = properties;
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.bazaar.config.Configuration#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(final String key) throws PropertyNotFoundException {
		final String property = this.properties.getProperty(key);
		if (property == null) {
			throw new PropertyNotFoundException(key);
		}
		return property;
	}

}
