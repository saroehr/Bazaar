/*
 * Bazaar.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.util.Calendar;
import java.util.Set;

import javax.validation.constraints.NotNull;

/**
 * Bazaar declares the programming interface implementations must provide.
 */
public interface Bazaar extends Persistable {

	// declare members

	// declare methods

	/**
	 * Creates new Bid instance
	 * 
	 * @param bidder The Bidder making Bid for Item
	 * @param price The bid price
	 * 
	 * @return The Bid instance
	 */
	public @NotNull Bid newBid(@NotNull final Bidder bidder, @NotNull final Double price);

	/**
	 * Returns the start date for the auction.
	 * 
	 * @return The start date for auction
	 */
	public @NotNull Calendar getStartDate();

	/**
	 * Returns the end date for the auction
	 * 
	 * @return The end date for auction
	 */
	public @NotNull Calendar getEndDate();

	/**
	 * Returns the item associated with auction
	 * 
	 * @return The Item being auctioned
	 */
	public @NotNull Item getItem();

	/**
	 * Returns the reserve price associated with auction
	 * 
	 * @return The reserve price associated with auction
	 *         or null if no reserve price has been specified.
	 */
	public Double getReservePrice();

	/**
	 * Returns set of bids on Item by Bidder. The returned
	 * set is immutable.
	 * 
	 * @param bidder The bidder to search for
	 * @return The set of all bids by bidder or empty set
	 *         if no bids made
	 * @throws BazaarException if the operation could
	 *         not be completed
	 */
	public @NotNull Set<Bid> findBids(@NotNull final Bidder bidder) throws BazaarException;

	/**
	 * Returns the set of bids on item. The returned
	 * set is immutable.
	 *
	 * @return The set of bids on item
	 * @throws BazaarException if the operation could not be completed.
	 */
	public @NotNull Set<Bid> findAllBids() throws BazaarException;

}
