/*
 * BidSessionBean.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 11, 2016 at 9:19:20 AM
 */
package org.apache.bazaar.ejb;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Bid;
import org.apache.bazaar.BidNotFoundException;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionException;
import org.apache.bazaar.version.VersionNotFoundException;

/**
 * BidSessionBean provides the EJB Local interface used by clients of the bean
 */
public interface BidSessionBean {

	// declare members

	/**
	 * The JNDI lookup name for bean
	 */
	public String BEAN_LOOKUP_NAME = "java:app/BazaarEJB-0.0.1-SNAPSHOT/BidSessionBean";

	// declare methods

	/**
	 * Returns bid by identifier
	 *
	 * @param identifier The identifier for bid
	 * @return Bid with identifier
	 * @throws BidNotFoundException if no bid for identifier can be found
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Bid findBid(@NotNull final Identifier identifier) throws BidNotFoundException, BazaarException;

	/**
	 * Returns Set of versions for Bid instance
	 *
	 * @param bid The Bid instance to retrieve versions for
	 * @return Set of all Versions of Bid
	 * @throws UnsupportedOperationException if the implementation does not
	 *         support versions
	 * @throws VersionNotFoundException if no versions exist for bid
	 * @throws VersionException if the operation could not be completed
	 */
	public @NotNull Set<Version> findAllVersions(@NotNull final Bid bid)
			throws UnsupportedOperationException, VersionNotFoundException, VersionException;

	/**
	 * Returns set of all bids
	 *
	 * @return The unmodifiable set of all bids
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Bid> findAllBids() throws BazaarException;

	/**
	 * Returns all bids associated with Bazaar.
	 *
	 * @param bazaar The Bazaar to retrieve bids for
	 * @return Set of all Bids for Bazaar. Set is unmodifiable
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Bid> findAllBids(@NotNull final Bazaar bazaar) throws BazaarException;

	/**
	 * Returns all bids associated with bidder
	 *
	 * @param bidder The bidder to retrieve bids for
	 * @return bids The unmodifiable set of all bids by bidder
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Bid> findAllBids(@NotNull final Bidder bidder) throws BazaarException;

	/**
	 * Returns all bids by bidder associated with Bazaar
	 *
	 * @param bazaar The Bazaar to retrieve bids by bidder for
	 * @param bidder The bidder to retrieve bids for
	 * @return Set of all Bids for Bazaar by Bidder. Set is unmodifiable
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Bid> findBids(@NotNull final Bazaar bazaar, @NotNull final Bidder bidder)
			throws BazaarException;

	/**
	 * Creates new Bid by Bidder for Bazaar.
	 *
	 * @param bazaar The Bazaar to create bid for
	 * @param bidder The bidder to create bid for
	 * @param price The bid price
	 * @return The Bid instance
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Bid newBid(@NotNull final Bazaar bazaar, @NotNull final Bidder bidder, @NotNull final Double price)
			throws BazaarException;

}
