/*
 * BidderTests.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 8, 2016 at 2:04:37 PM
 */
package org.apache.bazaar;

import java.time.DayOfWeek;
import java.util.Calendar;

import org.apache.bazaar.Address;
import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.BidderNotFoundException;
import org.apache.bazaar.Item;
import org.apache.bazaar.State;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.ConcurrentJunitRunner;

import junit.framework.TestCase;

/**
 * BidderTests provides JUnit tests for {@link Bidder}
 */
@RunWith(ConcurrentJunitRunner.class)
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
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(2017, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(2017, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate);
			bazaar.newBid(bidder, new Double(100.00));
			bazaar.newBid(bidder, new Double(200.00));
			bazaar.persist();
			Assert.assertNotNull(bazaar.findAllBids());
			Assert.assertTrue(bazaar.findAllBids().size() == 2);
			bazaar.delete();
			manager.findBidder(bidder.getIdentifier());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			TestCase.fail(exception.getLocalizedMessage());
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
			final Calendar startDate = Calendar.getInstance();
			startDate.setWeekDate(3000, 1, DayOfWeek.MONDAY.getValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setWeekDate(3000, 52, DayOfWeek.MONDAY.getValue());
			final Bazaar bazaar = manager.newBazaar(item, startDate, endDate, new Double(1000.00));
			bazaar.newBid(bidder, new Double(100.00));
			bazaar.persist();
			try {
				// should fail since auction exists with bids
				bidder.delete();
			}
			catch (final BazaarException exception) {
				Assert.assertNotNull(exception);
			}
			bazaar.delete();
			// should pass since auction and bids deleted
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
			TestCase.fail(exception.getLocalizedMessage());
		}
	}

}
