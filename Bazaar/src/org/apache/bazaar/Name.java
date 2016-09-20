/*
 * Name.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Name declares the programming interface implementations must provide
 */
public interface Name extends Serializable {

	// declare members

	// declare methods

	/**
	 * Returns the first name
	 *
	 * @return The first name
	 */
	public @NotNull @Size(min = 1, max = 255) String getFirstName();

	/**
	 * Sets the fist name
	 *
	 * @param firstName
	 *            The first name to set
	 */
	public void setFirstName(@NotNull @Size(min = 1, max = 255) final String firstName);

	/**
	 * Returns the last name
	 *
	 * @return The last name
	 */
	public @NotNull @Size(min = 1, max = 255) String getLastName();

	/**
	 * Sets the last name
	 *
	 * @param lastName
	 *            The last name to set
	 */
	public void setLastName(@NotNull @Size(min = 1, max = 255) final String lastName);

}
