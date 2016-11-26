package org.apache.bazaar.ejb;

import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.bazaar.Address;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.BidderNotFoundException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Name;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionException;
import org.apache.bazaar.version.VersionNotFoundException;

/**
 * Session Bean implementation class BidderSessionBeanImpl
 */
@Stateless(name = "BidderSessionBean")
@Local(BidderSessionBean.class)
@TransactionManagement(TransactionManagementType.BEAN)
@Resource(name = "java:comp/env/jdbc/Bazaar", lookup = "jdbc/Bazaar", type = javax.sql.XADataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class BidderSessionBeanImpl implements BidderSessionBean {

	// declare members

	// declare constructors

	/**
	 * Default constructor.
	 */
	public BidderSessionBeanImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see BidderSessionBean#findBidder(Identifier)
	 */
	@Override
	public Bidder findBidder(final Identifier identifier) throws BidderNotFoundException, BazaarException {
		return BazaarManager.newInstance().findBidder(identifier);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.ejb.BidderSessionBean#findAllVersions(org.apache.bazaar
	 * .Bidder)
	 */
	@Override
	public Set<Version> findAllVersions(final Bidder bidder)
			throws UnsupportedOperationException, VersionNotFoundException, VersionException {
		return bidder.findAllVersions();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidderSessionBean#findAllBidders()
	 */
	@Override
	public Set<Bidder> findAllBidders() throws BazaarException {
		return BazaarManager.newInstance().findAllBidders();
	}

	/*
	 * (non-Javadoc)
	 * @see BidderSessionBean#newBidder(Name, Address, Address)
	 */
	@Override
	public Bidder newBidder(final Name name, final Address billingAddress, final Address shippingAddress)
			throws BazaarException {
		return BazaarManager.newInstance().newBidder(name, billingAddress, shippingAddress);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidderSessionBean#newName()
	 */
	@Override
	public Name newName() throws BazaarException {
		return BazaarManager.newInstance().newName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.BidderSessionBean#newAddress()
	 */
	@Override
	public Address newAddress() throws BazaarException {
		return BazaarManager.newInstance().newAddress();
	}

}
