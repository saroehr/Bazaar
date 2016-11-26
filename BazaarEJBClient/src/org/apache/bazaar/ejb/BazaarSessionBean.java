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
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionException;
import org.apache.bazaar.version.VersionNotFoundException;

/**
 * BazaarSessionBean provides the EJB Local interface used by clients of the
 * bean
 */
public interface BazaarSessionBean {

	// declare members
	/**
	 * The JNDI lookup name for bean
	 */
	public String BEAN_LOOKUP_NAME = "java:app/BazaarEJB-0.0.1-SNAPSHOT/BazaarSessionBean";

	// declare methods

	/**
	 * Creates new Bazaar instance.
	 *
	 * @param item The Item to be Bazaared
	 * @param startDate The start date for Bazaar
	 * @param endDate The end date for Bazaar
	 * @return The Bazaar instance
	 * @throws BazaarException if the instance could not be created
	 */
	public @NotNull Bazaar newBazaar(@NotNull final Item item, @NotNull final Calendar startDate,
			@NotNull final Calendar endDate) throws BazaarException;

	/**
	 * Creates new Bazaar instance.
	 *
	 * @param item The Item for Bazaar
	 * @param startDate The start date for Bazaar
	 * @param endDate The end date for Bazaar
	 * @param reservePrice The reserve price for Bazaar
	 * @return The Bazaar instance
	 * @throws BazaarException if the instance could not be created
	 */
	public @NotNull Bazaar newBazaar(@NotNull final Item item, @NotNull final Calendar startDate,
			@NotNull final Calendar endDate, @NotNull final Double reservePrice) throws BazaarException;

	/**
	 * Returns Bazaar instance with identifier
	 *
	 * @param identifier The identifier for instance
	 * @return The Bazaar instance
	 * @throws BazaarNotFoundException if no Bazaar with identifier exists
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Bazaar findBazaar(@NotNull final Identifier identifier)
			throws BazaarNotFoundException, BazaarException;

	/**
	 * Returns Set of all versions for bazaar
	 *
	 * @param bazaar The bazaar instance
	 * @return Set of {@link Version} for instance
	 * @throws VersionNotFoundException if no versions for instance exist
	 * @throws VersionException if the operation could not be completed
	 * @throws UnsupportedOperationException
	 */
	public @NotNull Set<Version> findAllVersions(@NotNull final Bazaar bazaar)
			throws UnsupportedOperationException, VersionNotFoundException, VersionException;

	/**
	 * Returns set of all bazaars
	 *
	 * @return The unmodifiable set of all bazaars
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Bazaar> findAllBazaars() throws BazaarException;

}
