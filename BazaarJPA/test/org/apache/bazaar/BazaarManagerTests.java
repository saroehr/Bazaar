/*
 * BazaarManagerTests.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 20, 2016 at 9:43:48 AM
 */
package org.apache.bazaar;

import java.time.DayOfWeek;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.ConcurrentJunitRunner;

/**
 * BazaarManagerTests provides JUnit tests for {@link BazaarManager}
 */
@RunWith(ConcurrentJunitRunner.class)
public final class BazaarManagerTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarManagerTests
	 */
	public BazaarManagerTests() {
		super();
	}

	// declare methods

	/**
	 * Test for
	 * {@link BazaarManager#newBazaar(Item, java.util.Calendar, java.util.Calendar)}
	 */
	@Test
	public void testNewBazaarItemCalendarCalendar() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Category parent = manager.findRootCategory();
			final Category category = manager.newCategory();
			category.setName("testNewBazaarItemCalendarCalendar");
			category.setDescription("testNewItemCalendarCalendar");
			category.setParent(parent);
			category.persist();
			final Item item = manager.newItem();
			item.setName("testNewBazaarItemCalendarCalendar");
			item.setDescription("testNewBazaarItemCalendarCalendar");
			item.setCategory(category);
			item.persist();
			final Bidder bidder = manager.newBidder();
			final Name name = manager.newName();
			name.setFirstName("testNewBazaarItemCalendarCalendar");
			name.setLastName("testNewBazaarItemCalendarCalendar");
			bidder.setName(name);
			final Address address = manager.newAddress();
			address.setStreet("testNewBazaarItemCalendarCalendar");
			address.setCity("testNewBazaarItemCalendarCalendar");
			address.setState(State.Illinois);
			address.setZipcode(60102);
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			bidder.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2020, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2020, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			bazaar.newBid(bidder, new Double(100.00)).persist();
			final Bazaar Bazaar1 = manager.findBazaar(bazaar.getIdentifier());
			Assert.assertNotNull(Bazaar1);
			Assert.assertEquals(bazaar.getIdentifier(), Bazaar1.getIdentifier());
			Assert.assertNull(bazaar.getReservePrice());
			Assert.assertTrue(!bazaar.findAllBids().isEmpty());

		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}

	}

	/**
	 * Test for
	 * {@link BazaarManager#newBazaar(Item, Calendar, Calendar, Double)}
	 */
	@Test
	public void testNewBazaarItemCalendarCalendarDouble() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Category category = manager.newCategory("testNewBazaarItemCalendarCalendarDouble",
					"testNewBazaarItemCalendarCalendarDouble", manager.findRootCategory());
			final Item item = manager.newItem("testNewBazaarItemCalendarCalendarDouble",
					"testNewBazaarCalendarCalendarDouble", category);
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2020, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2020, 52, DayOfWeek.MONDAY.getValue());
			final Double reservePrice = new Double(100.99);
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate, reservePrice);
			junit.framework.Assert.assertNotNull(bazaar);
			junit.framework.Assert.assertEquals(item, bazaar.getItem());
			junit.framework.Assert.assertEquals(startDate, bazaar.getStartDate());
			junit.framework.Assert.assertEquals(endDate, bazaar.getEndDate());
			junit.framework.Assert.assertEquals(reservePrice, bazaar.getReservePrice());
			bazaar.persist();
			junit.framework.Assert.assertNotNull(manager.findBazaar(bazaar.getIdentifier()));
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link BazaarManager#newName()}
	 */
	@Test
	public void testNewName() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Name name = manager.newName();
			Assert.assertNotNull(name);
			name.setFirstName("testNewName");
			name.setLastName("testNewName");
			Assert.assertEquals("testNewName", name.getFirstName());
			Assert.assertEquals("testNewName", name.getLastName());

		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link BazaarManager#newName(String, String)}
	 */
	@Test
	public void testNewNameStringString() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Name name = manager.newName("testNewNameStringString", "testNewNameStringString");
			Assert.assertNotNull(name);
			Assert.assertEquals("testNewNameStringString", name.getFirstName());
			Assert.assertEquals("testNewNameStringString", name.getLastName());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

}
