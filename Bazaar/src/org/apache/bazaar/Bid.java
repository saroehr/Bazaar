/*
 * Bid.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.io.Serializable;

// declare imports

import javax.validation.constraints.NotNull;

/**
 * Bid declares the programming interface implementations must provide.
 */
public interface Bid extends Persistable, Serializable {

	// declare members

	// declare methods

	/**
	 * Returns the auction associated with this bid
	 * 
	 * @return The auction associated with this bid
	 */
	public @NotNull Bazaar getAuction();

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
