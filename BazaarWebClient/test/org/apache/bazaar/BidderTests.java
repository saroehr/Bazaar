/*
 * BidderTests.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 8, 2016 at 2:04:37 PM
 */
package org.apache.bazaar;

import java.time.DayOfWeek;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * BidderTests provides JUnit tests for {@link Bidder}
 */
public final class BidderTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidderTests
	 */
	public BidderTests() {
		super();
	}

	/**
	 * Test for {@link Bidder#persist()}
	 */
	@Test
	public void testPersist() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Bidder bidder = manager.newBidder();
			final Address address = manager.newAddress("testPersist", "testPersist", State.Illinois, 60102);
			bidder.setName(manager.newName("testPersist", "testPersist"));
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			bidder.persist();
			Assert.assertNotNull(manager.findBidder(bidder.getIdentifier()));
			final Item item = manager.newItem("testPersist", "testPersist", manager.findRootCategory());
			item.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2020, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2020, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			bazaar.newBid(bidder, new Double(100.00)).persist();
			bazaar.newBid(bidder, new Double(200.00)).persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() == 2);
			bazaar.delete();
			manager.findBidder(bidder.getIdentifier());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Bidder#delete()}
	 */
	@Test
	public void testDelete() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Bidder bidder = manager.newBidder();
			final Address address = manager.newAddress("testDelete", "testDelete", State.Illinois, 60102);
			bidder.setName(manager.newName("testDelete", "testDelete"));
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			bidder.persist();
			final Item item = manager.newItem("testDelete", "testDelete", manager.findRootCategory());
			item.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(3000, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(3000, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate, new Double(1000.00));
			bazaar.persist();
			bazaar.newBid(bidder, new Double(100.00)).persist();
			try {
				// should fail since Bazaar exists with bids
				bidder.delete();
			}
			catch (final BazaarException exception) {
				Assert.assertNotNull(exception);
			}
			bazaar.delete();
			// should pass since Bazaar and bids deleted
			bidder.delete();
			try {
				manager.findBidder(bidder.getIdentifier());
			}
			catch (final BidderNotFoundException exception) {
				Assert.assertNotNull(exception);
			}
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
		}
	}

}
