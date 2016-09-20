/*
 * Identifiable.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 25, 2016 at 8:45:20 AM
 */
package org.apache.bazaar;

import javax.validation.constraints.NotNull;

/**
 * Identifiable declares the methods
 * any implementation must provide
 */
public interface Identifiable {

	// declare members

	// declare methods

	/**
	 * Returns the {@link Identifier}
	 * associated with instance
	 * 
	 * @return The identifier associated with
	 *         instance
	 */
	public @NotNull Identifier getIdentifier();

}
