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

/**
 * BazaarTests provides JUnit tests for {@link Bazaar}
 */
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
			item.persist();
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
			bidder.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2016, 50, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2016, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			bazaar.newBid(bidder, new Double(100.00)).persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() == 1);
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
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
			item.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 52, DayOfWeek.FRIDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			final Bidder bidder = manager.newBidder(manager.newName("testGetBids", "testGetBids"),
					manager.newAddress("testGetBids", "testGetBids", State.Illinois, 60102),
					manager.newAddress("testGetBids", "testGetBids", State.Illinois, 60102));
			bidder.persist();
			bazaar.newBid(bidder, 10.00D).persist();
			bazaar.newBid(bidder, 100.00D).persist();
			bazaar.persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() == 2);

		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
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
			item.persist();
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
			Assert.fail(exception.getLocalizedMessage());
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
			item.persist();
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
			Assert.fail(exception.getLocalizedMessage());
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
			Assert.assertSame(bazaar, Bazaar1);
			Assert.assertEquals(bazaar, Bazaar1);
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
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
			final Category category = manager.findRootCategory();
			item.setCategory(category);
			item.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2016, 50, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2016, 51, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			Assert.assertNotNull(manager.findBazaar(bazaar.getIdentifier()));
			final Set<Version> versions = bazaar.findAllVersions();
			Assert.assertNotNull(versions);
			Assert.assertEquals(1, versions.size());
			boolean foundVersion = false;
			for (final Version version : versions) {
				if (version.getVersionable().equals(bazaar)) {
					foundVersion = true;
				}
			}
			Assert.assertTrue(foundVersion);
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
		}
	}

}
