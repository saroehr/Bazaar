/*
 * Bid.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.io.Serializable;

// declare imports

import javax.validation.constraints.NotNull;

import org.apache.bazaar.version.Versionable;

/**
 * Bid declares the programming interface implementations must provide.
 */
public interface Bid extends Versionable, Serializable {

	// declare members

	// declare methods

	/**
	 * Returns the Bazaar associated with this bid
	 *
	 * @return The Bazaar associated with this bid
	 */
	public @NotNull Bazaar getBazaar();

	/**
	 * Returns the bid price
	 *
	 * @return The bid price
	 */
	public @NotNull Double getPrice();

	/**
	 * Returns the bidder associated with this bid
	 *
	 * @return The bidder associated with this bid
	 */
	public @NotNull Bidder getBidder();

}
