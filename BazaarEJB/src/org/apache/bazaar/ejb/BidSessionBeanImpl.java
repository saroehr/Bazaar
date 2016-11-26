package org.apache.bazaar.ejb;

import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bid;
import org.apache.bazaar.BidNotFoundException;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionException;
import org.apache.bazaar.version.VersionNotFoundException;

/**
 * Session Bean implementation class BidSessionBeanImpl
 */
@Stateless(name = "BidSessionBean")
@Local(BidSessionBean.class)
@TransactionManagement(TransactionManagementType.BEAN)
@Resource(name = "java:comp/env/jdbc/Bazaar", lookup = "jdbc/Bazaar", type = javax.sql.XADataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class BidSessionBeanImpl implements BidSessionBean {

	// declare members

	// declare methods

	/**
	 * Default constructor.
	 */
	public BidSessionBeanImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidSessionBean#findBid(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Bid findBid(final Identifier identifier) throws BidNotFoundException, BazaarException {
		return BazaarManager.newInstance().findBid(identifier);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.ejb.BidSessionBean#findAllVersions(org.apache.bazaar.
	 * Bid)
	 */
	@Override
	public Set<Version> findAllVersions(final Bid bid)
			throws UnsupportedOperationException, VersionNotFoundException, VersionException {
		return bid.findAllVersions();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidSessionBean#findAllBids(org.apache.bazaar.
	 * Bazaar)
	 */
	@Override
	public Set<Bid> findAllBids(final Bazaar bazaar) throws BazaarException {
		return bazaar.findAllBids();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidSessionBean#findAllBids()
	 */
	@Override
	public Set<Bid> findAllBids() throws BazaarException {
		return BazaarManager.newInstance().findAllBids();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidSessionBean#findAllBids(org.apache.bazaar.
	 * Bidder)
	 */
	@Override
	public Set<Bid> findAllBids(final Bidder bidder) throws BazaarException {
		return BazaarManager.newInstance().findAllBids(bidder);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidSessionBean#findAllBids(org.apache.bazaar.
	 * Bazaar, org.apache.bazaar.Bidder)
	 */
	@Override
	public Set<Bid> findBids(final Bazaar bazaar, final Bidder bidder) throws BazaarException {
		return bazaar.findBids(bidder);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.ejb.BidSessionBean#newBid(org.apache.bazaar.Bazaar,
	 * org.apache.bazaar.Bidder, java.lang.Double)
	 */
	@Override
	public Bid newBid(final Bazaar bazaar, final Bidder bidder, final Double price) throws BazaarException {
		return bazaar.newBid(bidder, price);
	}

}
