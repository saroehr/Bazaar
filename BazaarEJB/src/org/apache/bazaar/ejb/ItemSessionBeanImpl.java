package org.apache.bazaar.ejb;

import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Category;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;
import org.apache.bazaar.ItemNotFoundException;

/**
 * Session Bean implementation class ItemSessionBeanImpl
 */
@Stateless(name = "ItemSessionBean")
@Local(ItemSessionBean.class)
@TransactionManagement(TransactionManagementType.BEAN)
@Resource(name = "java:comp/env/jdbc/Bazaar", lookup = "jdbc/Bazaar", type = javax.sql.XADataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class ItemSessionBeanImpl implements ItemSessionBean {

	// declare members

	// declare methods

	/**
	 * Default constructor.
	 */
	public ItemSessionBeanImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.ejb.ItemSessionBean#newItem()
	 */
	@Override
	public Item newItem() throws BazaarException {
		return BazaarManager.newInstance().newItem();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.ejb.ItemSessionBean#newItem(java.lang.
	 * String, java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Item newItem(final String name, final String description, final Category category) throws BazaarException {
		return BazaarManager.newInstance().newItem(name, description, category);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.ejb.ItemSessionBean#findItem(org.apache.
	 * auction.Identifier)
	 */
	@Override
	public Item findItem(final Identifier identifier) throws ItemNotFoundException, BazaarException {
		return BazaarManager.newInstance().findItem(identifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.ejb.ItemSessionBean#findAllItems()
	 */
	@Override
	public Set<Item> findAllItems() throws BazaarException {
		return BazaarManager.newInstance().findAllItems();
	}

}
