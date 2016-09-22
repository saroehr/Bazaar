/*
 * BazaarManagerImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 21, 2016 at 3:33:34 PM
 */
package org.apache.bazaar;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Set;

import org.apache.bazaar.Image.MimeType;

/**
 * BazaarManagerImpl implements {@link BazaarManager}
 */
final class BazaarManagerImpl implements BazaarManager {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarManagerImpl
	 */
	private BazaarManagerImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newBazaar(org.apache.bazaar.Item,
	 * java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate) throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newBazaar(org.apache.bazaar.Item,
	 * java.util.Calendar, java.util.Calendar, java.lang.Double)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate,
			final Double reservePrice) throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newName()
	 */
	@Override
	public Name newName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newName(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Name newName(final String firstName, final String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newAddress()
	 */
	@Override
	public Address newAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newAddress(java.lang.String,
	 * java.lang.String, org.apache.bazaar.State, java.lang.Integer)
	 */
	@Override
	public Address newAddress(final String street, final String city, final State state, final Integer zipcode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newBidder()
	 */
	@Override
	public Bidder newBidder() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newBidder(org.apache.bazaar.Name,
	 * org.apache.bazaar.Address, org.apache.bazaar.Address)
	 */
	@Override
	public Bidder newBidder(final Name name, final Address billingAddress, final Address shippingAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newImage(java.lang.String,
	 * org.apache.bazaar.Image.MimeType, java.io.InputStream)
	 */
	@Override
	public Image newImage(final String name, final MimeType mimeType, final InputStream inputStream)
			throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newItem()
	 */
	@Override
	public Item newItem() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newItem(java.lang.String,
	 * java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Item newItem(final String name, final String description, final Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newCategory()
	 */
	@Override
	public Category newCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#newCategory(java.lang.String,
	 * java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Category newCategory(final String name, final String description, final Category parent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findBazaar(org.apache.bazaar.Identifier)
	 */
	@Override
	public Bazaar findBazaar(final Identifier identifier) throws BazaarNotFoundException, BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAllBazaars()
	 */
	@Override
	public Set<Bazaar> findAllBazaars() throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findRootCategory()
	 */
	@Override
	public Category findRootCategory() throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findCategory(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Category findCategory(final Identifier identifier) throws CategoryNotFoundException, BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findCategories(java.lang.String)
	 */
	@Override
	public Set<Category> findCategories(final String name) throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAllCategories()
	 */
	@Override
	public Set<Category> findAllCategories() throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findItem(org.apache.bazaar.Identifier)
	 */
	@Override
	public Item findItem(final Identifier identifier) throws ItemNotFoundException, BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findItems(java.lang.String)
	 */
	@Override
	public Set<Item> findItems(final String name) throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findItems(org.apache.bazaar.Category)
	 */
	@Override
	public Set<Item> findItems(final Category category) throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAllItems()
	 */
	@Override
	public Set<Item> findAllItems() throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findBidder(org.apache.bazaar.Identifier)
	 */
	@Override
	public Bidder findBidder(final Identifier identifier) throws BidderNotFoundException, BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findBidders(org.apache.bazaar.Name)
	 */
	@Override
	public Set<Bidder> findBidders(final Name name) throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAllBidders()
	 */
	@Override
	public Set<Bidder> findAllBidders() throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findBid(org.apache.bazaar.Identifier)
	 */
	@Override
	public Bid findBid(final Identifier identifier) throws BidNotFoundException, BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.BazaarManager#findAllBids()
	 */
	@Override
	public Set<Bid> findAllBids() throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.BazaarManager#findAllBids(org.apache.bazaar.Bidder)
	 */
	@Override
	public Set<Bid> findAllBids(final Bidder bidder) throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

}
