/*
 * BidderSessionBean.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 3:41:15 PM
 */
package org.apache.bazaar.ejb;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Address;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.BidderNotFoundException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Name;

/**
 * BidderSessionBean provides the EJB Local interface
 * used by clients of the bean
 */
public interface BidderSessionBean {

	// declare members

	/**
	 * The JNDI lookup name for bean
	 */
	public String BEAN_LOOKUP_NAME = "java:app/BazaarEJB-0.0.1-SNAPSHOT/BidderSessionBean";

	// declare methods

	/**
	 * Finds Bidder by Identifier
	 * 
	 * @param identifier The identifier for Bidder
	 * @return The Bidder with identifier
	 * @throws BidderNotFoundException if no Bidder
	 *         with identifier can be found
	 * @throws BazaarException if the operation could
	 *         not be completed
	 */
	public @NotNull Bidder findBidder(@NotNull final Identifier identifier)
			throws BidderNotFoundException, BazaarException;

	/**
	 * Returns set of all Bidders
	 * 
	 * @return The set of all Bidders
	 */

	/**
	 * Creates new Bidder. Bidder is persisted.
	 * 
	 * @param name The name for bidder
	 * @param billingAddress The billing Address for bidder
	 * @param shippingAddress The shipping Address for bidder
	 * @return The Bidder instance
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Bidder newBidder(@NotNull final Name name, @NotNull final Address billingAddress,
			@NotNull final Address shippingAddress) throws BazaarException;

	/**
	 * Returns new Name instance.
	 * 
	 * @return The Name instance
	 * @throws BazaarException if the operation
	 *         could not be completed
	 */
	public @NotNull Name newName() throws BazaarException;

	/**
	 * Returns new Address instance
	 * 
	 * @return The Address instance
	 * @throws BazaarException if the operation could
	 *         not be completed
	 */
	public @NotNull Address newAddress() throws BazaarException;

}
