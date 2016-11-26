/*
 * BazaarTests.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 8, 2016
 */
package org.apache.bazaar;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Set;

import org.apache.bazaar.version.Version;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.ConcurrentJunitRunner;

/**
 * BazaarTests provides JUnit tests for {@link Bazaar}
 */
@RunWith(ConcurrentJunitRunner.class)
public final class BazaarTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarTests
	 */
	public BazaarTests() {
		super();
	}

	/**
	 * Test for {@link Bazaar#newBid(Bidder, Double)}
	 */
	@Test
	public void testNewBid() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem();
			item.setName("testNewBid");
			item.setDescription("testNewBid");
			item.setCategory(manager.findRootCategory());
			final Bidder bidder = manager.newBidder();
			final Name name = manager.newName();
			name.setFirstName("testNewBid");
			name.setLastName("testNewBid");
			bidder.setName(name);
			final Address address = manager.newAddress();
			address.setStreet("testNewBid");
			address.setCity("testNewBid");
			address.setState(State.Illinois);
			address.setZipcode(60102);
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2016, 50, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2016, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.newBid(bidder, new Double(100.00));
			bazaar.persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() == 1);
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Bazaar#findAllBids()}
	 */
	@Test
	public void testFindAllBids() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem("testGetBids", "testGetBids", manager.findRootCategory());
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 52, DayOfWeek.FRIDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			final Bidder bidder = manager.newBidder(manager.newName("testGetBids", "testGetBids"),
					manager.newAddress("testGetBids", "testGetBids", State.Illinois, 60102),
					manager.newAddress("testGetBids", "testGetBids", State.Illinois, 60102));
			bazaar.newBid(bidder, 10.00D);
			bazaar.newBid(bidder, 100.00D);
			bazaar.persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() == 2);

		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Bazaar#persist()}
	 */
	@Test
	public void testPersist() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem();
			item.setName("testPersist");
			item.setDescription("testPersist");
			final Category category = manager.findRootCategory();
			item.setCategory(category);
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2016, 50, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2016, 51, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			Assert.assertNotNull(manager.findBazaar(bazaar.getIdentifier()));
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Bazaar#delete()}
	 */
	@Test
	public void testDelete() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem("testDelete", "testDelete", manager.findRootCategory());
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 2, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			Assert.assertNotNull(manager.findBazaar(bazaar.getIdentifier()));
			bazaar.delete();
			try {
				manager.findBazaar(bazaar.getIdentifier());
			}
			catch (final BazaarNotFoundException exception) {
				Assert.assertNotNull(exception);
			}
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Bazaar#equals(Object)}
	 */
	@Test
	public void testEquals() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem("testEquals", "testEquals", manager.findRootCategory());
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 25, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			final Bazaar Bazaar1 = bazaar;
			junit.framework.Assert.assertSame(bazaar, Bazaar1);
			junit.framework.Assert.assertEquals(bazaar, Bazaar1);
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for invalid end date
	 */
	@Test
	public void testInvalidEndDate() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem("testInvalidEndDate", "testInvalidEndDate", manager.findRootCategory());
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 2, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2016, 1, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
		}
		catch (final BazaarExpiredException exception) {
			Assert.assertNotNull(exception);
		}
		catch (final BazaarException exception) {
			Assert.assertNotNull(exception);
		}

	}

	/**
	 * Test for findAllVersions
	 */
	@Test
	public void testFindAllVersions() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem();
			item.setName("testFindAllVersions");
			item.setDescription("testFindAllVersions");
			item.setCategory(manager.findRootCategory());
			final Bidder bidder = manager.newBidder();
			final Name name = manager.newName();
			name.setFirstName("testFindAllVersions");
			name.setLastName("testFindAllVersions");
			bidder.setName(name);
			final Address address = manager.newAddress();
			address.setStreet("testFindAllVersions");
			address.setCity("testFindAllVersions");
			address.setState(State.Illinois);
			address.setZipcode(60102);
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2016, 50, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2016, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			Set<Version> versions = bazaar.findAllVersions();
			Assert.assertNotNull(versions);
			Assert.assertEquals(1, versions.size());
			boolean foundBazaar = false;
			for (final Version version : versions) {
				if (version.getPersistable().equals(bazaar)) {
					foundBazaar = true;
				}
			}
			Assert.assertTrue(foundBazaar);
			bazaar.newBid(bidder, new Double(100.00));
			bazaar.persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() == 1);
			versions = bazaar.findAllVersions();
			Assert.assertNotNull(versions);
			Assert.assertEquals(2, versions.size());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

}
