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
import org.apache.bazaar.CategoryNotFoundException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionException;
import org.apache.bazaar.version.VersionNotFoundException;

/**
 * Session Bean implementation class CategorySessionBeanImpl
 */
@Stateless(name = "CategorySessionBean")
@Local(CategorySessionBean.class)
@TransactionManagement(TransactionManagementType.BEAN)
@Resource(name = "java:comp/env/jdbc/Bazaar", lookup = "jdbc/Bazaar", type = javax.sql.XADataSource.class, shareable = true, authenticationType = AuthenticationType.CONTAINER)
public class CategorySessionBeanImpl implements CategorySessionBean {

	// declare members

	// declare constructors

	/**
	 * Default constructor.
	 */
	public CategorySessionBeanImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.CategorySessionBean#findRootCategory()
	 */
	@Override
	public Category findRootCategory() throws BazaarException {
		return BazaarManager.newInstance().findRootCategory();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.CategorySessionBean#findCategory(org.
	 * apache.Bazaar.Identifier)
	 */
	@Override
	public Category findCategory(final Identifier identifier) throws CategoryNotFoundException, BazaarException {
		return BazaarManager.newInstance().findCategory(identifier);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.ejb.CategorySessionBean#findAllVersions(org.apache.
	 * bazaar.Category)
	 */
	@Override
	public Set<Version> findAllVersions(final Category category)
			throws UnsupportedOperationException, VersionNotFoundException, VersionException {
		return category.findAllVersions();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.ejb.CategorySessionBean#findAllCategories()
	 */
	@Override
	public Set<Category> findAllCategories() throws BazaarException {
		return BazaarManager.newInstance().findAllCategories();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.ejb.CategorySessionBean#newCategory(java.lang.String,
	 * java.lang.String, org.apache.bazaar.Category)
	 */
	@Override
	public Category newCategory(final String name, final String description, final Category parent)
			throws BazaarException {
		return BazaarManager.newInstance().newCategory(name, description, parent);
	}

}
