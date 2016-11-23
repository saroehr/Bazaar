package org.apache.bazaar.ejb;

import java.util.Calendar;
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
import org.apache.bazaar.BazaarNotFoundException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;

/**
 * Session Bean implementation class BazaarSessionBeanImpl
 */
@Stateless(name = "BazaarSessionBean")
@Local(BazaarSessionBean.class)
@TransactionManagement(TransactionManagementType.BEAN)
@Resource(name = "java:comp/env/jdbc/Bazaar", lookup = "jdbc/Bazaar", type = javax.sql.XADataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class BazaarSessionBeanImpl implements BazaarSessionBean {

	// declare members

	// declare constructors

	/**
	 * Default constructor.
	 */
	public BazaarSessionBeanImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.ejb.BazaarSessionBean#newBazaar(org.apache.bazaar.
	 * Item, java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate) throws BazaarException {
		return BazaarManager.newInstance().newBazaar(item, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.ejb.BazaarSessionBean#newBazaar(org.apache.bazaar.
	 * Item, java.util.Calendar, java.util.Calendar, java.lang.Double)
	 */
	@Override
	public Bazaar newBazaar(final Item item, final Calendar startDate, final Calendar endDate,
			final Double reservePrice) throws BazaarException {
		return BazaarManager.newInstance().newBazaar(item, startDate, endDate, reservePrice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.ejb.BazaarSessionBean#findBazaar(org.apache.bazaar.
	 * Identifier)
	 */
	@Override
	public Bazaar findBazaar(final Identifier identifier) throws BazaarNotFoundException, BazaarException {
		return BazaarManager.newInstance().findBazaar(identifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.ejb.BazaarSessionBean#findAllBazaars()
	 */
	@Override
	public Set<Bazaar> findAllBazaars() throws BazaarException {
		return BazaarManager.newInstance().findAllBazaars();
	}

}
