/*
 * BidTests.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 8, 2016 at 9:47:41 AM
 */
package org.apache.bazaar;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Set;

import org.apache.bazaar.Address;
import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Item;
import org.apache.bazaar.State;
import org.junit.Assert;
import org.junit.Test;

/**
 * BidTests provides JUnit tests for {@link Bid}
 */
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
	 * Test for {@link Bid#getAuction()}
	 */
	@Test
	public void testGetAuction() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Bidder bidder = manager.newBidder();
			bidder.setName(manager.newName("testGetAuction", "testGetAuction"));
			final Address address = manager.newAddress("testGetAuction", "testGetAuction", State.Illinois, 60102);
			bidder.setBillingAddress(address);
			bidder.setShippingAddress(address);
			final Category category = manager.newCategory("testGetAuction", "testGetAuction",
					manager.findRootCategory());
			final Item item = manager.newItem("testGetAuction", "testGetAuction", category);
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 2, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			final Bid bid = bazaar.newBid(bidder, new Double(10000.10));
			bazaar.persist();
			Assert.assertEquals(bazaar, bid.getAuction());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
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
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 52, DayOfWeek.MONDAY.getValue());
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
			Assert.fail(exception.getLocalizedMessage());
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
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 2, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.persist();
			Assert.assertNotNull(manager.findItem(item.getIdentifier()));
			Assert.assertNotNull(manager.findBidder(bidder.getIdentifier()));
			final Bid bid = bazaar.newBid(bidder, new Double(1000.99));
			Assert.assertNotNull(bazaar.findBids(bidder));
			Assert.assertTrue(bazaar.findBids(bidder).contains(bid));
			Assert.assertEquals(new Double(1000.99), bid.getPrice());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			Assert.fail(exception.getLocalizedMessage());
		}
	}

}