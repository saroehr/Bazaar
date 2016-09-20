/*
 * Address.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.io.Serializable;

// declare imports

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Address declares the programming interface implementations must provide.
 */
public interface Address extends Serializable {

	// declare members

	// declare methods

	/**
	 * Returns the street address
	 *
	 * @return The street address
	 */
	public @NotNull @Size(min = 1, max = 255) String getStreet();

	/**
	 * Sets the street address
	 *
	 * @param street
	 *            The street address to set
	 */
	public void setStreet(@NotNull @Size(min = 1, max = 255) final String street);

	/**
	 * Returns the city
	 *
	 * @return The city
	 */
	public @NotNull @Size(min = 1, max = 255) String getCity();

	/**
	 * Sets the city
	 *
	 * @param city
	 *            The city to set
	 */
	public void setCity(@NotNull @Size(min = 1, max = 255) final String city);

	/**
	 * Returns the state
	 *
	 * @return The state
	 */
	public @NotNull State getState();

	/**
	 * Sets the state
	 *
	 * @param state
	 *        The state to set
	 */
	public void setState(@NotNull final State state);

	/**
	 * Returns the zip code
	 *
	 * @return The zip code
	 */
	public @NotNull Integer getZipcode();

	/**
	 * Sets the zip code
	 *
	 * @param zipcode
	 *            The zip code to set
	 */
	public void setZipcode(@NotNull final Integer zipcode);

}
