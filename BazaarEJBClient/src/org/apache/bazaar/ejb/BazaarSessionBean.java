/*
 * BazaarSessionBean.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 5:21:02 PM
 */
package org.apache.bazaar.ejb;

import java.util.Calendar;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarNotFoundException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;

/**
 * BazaarSessionBean provides the EJB Local
 * interface used by clients of the bean
 */
public interface BazaarSessionBean {

	// declare members
	/**
	 * The JNDI lookup name for bean
	 */
	public String BEAN_LOOKUP_NAME = "java:app/BazaarEJB/BazaarSessionBean";

	// declare methods

	/**
	 * Creates new Bazaar instance.
	 * 
	 * @param item The Item to be auctioned
	 * @param startDate The start date for auction
	 * @param endDate The end date for auction
	 * @return The Bazaar instance
	 * @throws BazaarException if the instance
	 *         could not be created
	 */
	public @NotNull Bazaar newBazaar(@NotNull final Item item, @NotNull final Calendar startDate,
			@NotNull final Calendar endDate) throws BazaarException;

	/**
	 * Creates new Bazaar instance.
	 * 
	 * @param item The Item to be auctioned
	 * @param startDate The start date for auction
	 * @param endDate The end date for auction
	 * @param reservePrice The reserve price for auction
	 * @return The Bazaar instance
	 * @throws BazaarException if the instance
	 *         could not be created
	 */
	public @NotNull Bazaar newBazaar(@NotNull final Item item, @NotNull final Calendar startDate,
			@NotNull final Calendar endDate, @NotNull final Double reservePrice) throws BazaarException;

	/**
	 * Returns Bazaar instance with identifier
	 * 
	 * @param identifier The identifier for instance
	 * @return The Bazaar instance
	 * @throws BazaarNotFoundException if no auction
	 *         with identifier exists
	 * @throws BazaarException if the operation could
	 *         not be completed
	 */
	public @NotNull Bazaar findBazaar(@NotNull final Identifier identifier)
			throws BazaarNotFoundException, BazaarException;

	/**
	 * Returns set of all bazaars
	 * 
	 * @return The unmodifiable set of all bazaars
	 * @throws BazaarException if the operation could
	 *         not be completed
	 */
	public @NotNull Set<Bazaar> findAllBazaars() throws BazaarException;

}
