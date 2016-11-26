/*
 * Bidder.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.version.Versionable;

/**
 * Bidder declares the programming interface implementations must provide.
 */
public interface Bidder extends Versionable, Serializable {

	// declare members

	// declare methods

	/**
	 * Returns the name of bidder
	 *
	 * @return The name of bidder
	 */
	public @NotNull Name getName();

	/**
	 * Sets the name of bidder
	 *
	 * @param name The name of bidder to be set
	 */
	public void setName(@NotNull final Name name);

	/**
	 * Returns the billing address of bidder
	 *
	 * @return The billing address of bidder
	 */
	public @NotNull Address getBillingAddress();

	/**
	 * Sets the billing address of bidder
	 *
	 * @param address The billing address to be set
	 */
	public void setBillingAddress(@NotNull final Address address);

	/**
	 * Returns the shipping address of bidder
	 *
	 * @return The shipping address of bidder
	 */
	public @NotNull Address getShippingAddress();

	/**
	 * Sets the shipping address of bidder
	 *
	 * @param address The shipping address to be set
	 */
	public void setShippingAddress(@NotNull final Address address);

}
