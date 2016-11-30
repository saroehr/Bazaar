/*
 * CategoryImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 8, 2016
 */
package org.apache.bazaar;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.nls.Messages;
import org.apache.bazaar.persistence.EntityManagerFactory;
import org.apache.bazaar.persistence.config.Configuration;
import org.apache.bazaar.version.AbstractVersionable;
import org.hibernate.envers.Audited;

/**
 * CategoryImpl implements {@link Category} to provide a concrete
 * implementation.
 */
@Entity(name = org.apache.bazaar.persistence.config.Configuration.CATEGORY_ENTITY_NAME)
@Table(name = org.apache.bazaar.persistence.config.Configuration.CATEGORY_TABLE_NAME, schema = org.apache.bazaar.persistence.config.Configuration.DATABASE_SCHEMA_NAME)
// @PrimaryKeyJoinColumn(name = Configuration.IDENTIFIABLE_COLUMN_NAME)
@Audited
public class CategoryImpl extends AbstractVersionable implements Category {

	// declare members

	private static final long serialVersionUID = 8868571475475761876L;

	private static final Messages MESSAGES = Messages.newInstance(Locale.getDefault());

	@ManyToOne(targetEntity = CategoryImpl.class, optional = false)
	@JoinColumn(name = "PARENT", referencedColumnName = org.apache.bazaar.persistence.config.Configuration.IDENTIFIABLE_COLUMN_NAME, nullable = false)
	private Category parent;
	@Column(name = "NAME", nullable = false, updatable = true, length = 255)
	private String name;
	@Column(name = "DESCRIPTION", nullable = false, updatable = true, length = 255)
	private String description;
	@OneToMany(targetEntity = CategoryImpl.class, mappedBy = "parent", fetch = FetchType.EAGER, cascade = {
			CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
	private Set<Category> children;

	// declare constructors

	/**
	 * Constructor for CategoryImpl
	 */
	protected CategoryImpl() {
		super();
		this.children = new HashSet<Category>(10);
	}

	/**
	 * Constructor for CategoryImpl
	 * 
	 * @param name The name of category
	 * @param description The description of category
	 * @param parent The parent category
	 */
	CategoryImpl(@NotNull @Size(min = 1, max = 255) final String name,
			@NotNull @Size(min = 1, max = 255) final String description, @NotNull final Category parent) {
		this();
		this.name = name;
		this.description = description;
		this.parent = parent;
		((CategoryImpl)this.parent).children.add(this);
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.AbstractPersistable#processException(java.lang.
	 * Exception)
	 */
	@Override
	protected void processException(final Exception exception) throws BazaarException {
		if (exception instanceof EntityNotFoundException) {
			throw new CategoryNotFoundException(exception.getLocalizedMessage(), exception);
		}
		super.processException(exception);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Category#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Category#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Category#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Category#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Category#getParent()
	 */
	@Override
	public Category getParent() {
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Category#setParent(org.apache.bazaar.Category)
	 */
	@Override
	public void setParent(final Category parent) {
		this.parent = parent;
		((CategoryImpl)parent).children.add(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Category#getChildren()
	 */
	@Override
	public Set<Category> getChildren() throws BazaarException {
		return Collections.unmodifiableSet(this.children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.AbstractPersistable#delete()
	 */
	@Override
	public void delete() throws BazaarException {
		// override to handle removal of this from parents children
		AbstractPersistable.LOGGER.entering("delete");
		final EntityManager manager = EntityManagerFactory.newInstance().createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.remove(manager.merge(this));
			transaction.commit();
			((CategoryImpl)this.parent).children.remove(this);
		}
		finally {
			manager.close();
		}
		AbstractPersistable.LOGGER.exiting("delete");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.AbstractPersistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		// check for this equals parent and fail if not
		// root category
		if (this.equals(this.parent) && !this.getIdentifier().getValue()
				.equals(Configuration.newInstance().getProperty(org.apache.bazaar.config.Configuration.ROOT_CATEGORY_IDENTIFIER)) ? true
						: false) {
			throw new BazaarException(
					CategoryImpl.MESSAGES.findMessage(Messages.UNABLE_TO_CREATE_CATEGORY));
		}
		super.persist();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this,
				new Object[] { this.getIdentifier(), this.name, this.description, this.parent.getIdentifier() });
	}

}
