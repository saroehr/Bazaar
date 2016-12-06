/*
 * BidTests.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 8, 2016 at 9:47:41 AM
 */
package org.apache.bazaar;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.ConcurrentJunitRunner;

/**
 * BidTests provides JUnit tests for {@link Bid}
 */
@RunWith(ConcurrentJunitRunner.class)
public final class BidTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidTests
	 */
	public BidTests() {
		super();
	}

	/**
	 * Test for {@link Bid#getBazaar()}
	 */
	@Test
	public void testGetBazaar() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Bidder bidder = manager.newBidder();
			bidder.setName(manager.newName("testGetBazaar", "testGetBazaar"));
			final Address address = manager.newAddress("testGetBazaar", "testGetBazaar", State.Illinois, 60102);
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			bidder.persist();
			final Category category = manager.newCategory("testGetBazaar", "testGetBazaar",
					manager.findRootCategory());
			final Item item = manager.newItem("testGetBazaar", "testGetBazaar", category);
			item.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2020, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2020, 2, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			final Bid bid = bazaar.newBid(bidder, new Double(10000.10));
			bazaar.persist();
			Assert.assertEquals(bazaar, bid.getBazaar());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Bid#getBidder()}
	 */
	@Test
	public void testGetBidder() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Bidder bidder = manager.newBidder();
			bidder.setName(manager.newName("testGetBidder", "testGetBidder"));
			bidder.setBillingAddress(manager.newAddress("testGetBidder", "testGetBidder", State.Illinois, 60102));
			bidder.setShippingAddress(manager.newAddress("testGetBidder", "testGetBidder", State.Illinois, 60102));
			bidder.persist();
			Assert.assertNotNull(manager.findBidder(bidder.getIdentifier()));
			final Item item = manager.newItem("testGetBidder", "testGetBidder",
					manager.newCategory("testGetBidder", "testGetBidder", manager.findRootCategory()));
			item.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2020, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2020, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate, new Double(1000.00));
			bazaar.persist();
			final Bid bid = bazaar.newBid(bidder, new Double(1500.99));
			bid.persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() != 0);
			Assert.assertTrue(bazaar.findAllBids().contains(bid));
			Assert.assertNotNull(bid.getBidder());
			Assert.assertEquals(bidder, bid.getBidder());
			final Set<Bid> bids = bazaar.findBids(bidder);
			Assert.assertNotNull(bids);
			Assert.assertTrue(bids.size() == 1);
			Assert.assertTrue(bids.contains(bid));
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Bid#getPrice()}
	 */
	@Test
	public void testGetPrice() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Bidder bidder = manager.newBidder();
			bidder.setName(manager.newName("testGetPrice", "testGetPrice"));
			final Address address = manager.newAddress("testGetPrice", "testGetPrice", State.Illinois, 60102);
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			bidder.persist();
			final Category category = manager.newCategory("testGetPrice", "testGetPrice", manager.findRootCategory());
			final Item item = manager.newItem("testGetPrice", "testGetPrice", category);
			item.persist();
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2020, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2020, 2, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			Assert.assertNotNull(manager.findItem(item.getIdentifier()));
			Assert.assertNotNull(manager.findBidder(bidder.getIdentifier()));
			final Bid bid = bazaar.newBid(bidder, new Double(1000.99));
			bid.persist();
			Assert.assertNotNull(bazaar.findBids(bidder));
			Assert.assertTrue(bazaar.findBids(bidder).contains(bid));
			Assert.assertEquals(new Double(1000.99), bid.getPrice());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}


}
