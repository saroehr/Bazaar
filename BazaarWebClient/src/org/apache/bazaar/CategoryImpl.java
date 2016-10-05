/*
 * CategoryImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 16, 2016 at 9:49:38 PM
 */
package org.apache.bazaar;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.web.RequestParameters;
import org.apache.bazaar.web.config.Configuration;

/**
 * CategoryImpl extends AbstractPersistable and implements {@link Category} to
 * provide a concrete implementation
 */
public class CategoryImpl extends AbstractPersistable implements org.apache.bazaar.Category {

	// declare members

	private static final long serialVersionUID = -4124386919835684727L;
	private String name;
	private String description;
	private org.apache.bazaar.Category parent;
	private final Set<Category> children;

	/**
	 * Constructor for CategoryImpl
	 */
	CategoryImpl() {
		super();
		this.children = new HashSet<Category>(10);
	}

	/**
	 * Constructor for CategoryImpl
	 *
	 * @param name The name for category
	 * @param description The description for category
	 * @param parent The parent for category
	 */
	CategoryImpl(@NotNull final String name, @NotNull final String description, @NotNull final Category parent) {
		this();
		this.name = name;
		this.description = description;
		this.parent = parent;
		((CategoryImpl)this.parent).addChild(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#addChild(org.apache.bazaar.Category)
	 */
	protected void addChild(final Category child) {
		this.children.add(child);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#getParent()
	 */
	@Override
	public org.apache.bazaar.Category getParent() {
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#setParent(org.apache.bazaar.Category)
	 */
	@Override
	public void setParent(final org.apache.bazaar.Category parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Category#getChildren()
	 */
	@Override
	public Set<Category> getChildren() throws BazaarException {
		return Collections.unmodifiableSet(this.children);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Persistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		boolean persisted = true;
		final BazaarManager manager = BazaarManager.newInstance();
		try {
			manager.findCategory(this.getIdentifier());
		}
		catch (final CategoryNotFoundException exception) {
			persisted = false;
		}
		final WebTarget webTarget = ((BazaarManagerImpl)manager).newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.CATEGORY_REST_WEB_SERVICE_URL))
				.path(this.getIdentifier().getValue()).queryParam(RequestParameters.NAME, this.name)
				.queryParam(RequestParameters.DESCRIPTION, this.description)
				.queryParam(RequestParameters.PARENT, this.parent.getIdentifier().getValue());
		if (persisted) {
			BazaarManagerImpl.processResponse(new GenericType<Category>() {
			}, webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.buildPost(Entity.entity(this, MediaType.APPLICATION_JSON_TYPE)).invoke());
		}
		else {
			BazaarManagerImpl.processResponse(new GenericType<Category>() {
			}, webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.buildPut(Entity.entity(this, MediaType.APPLICATION_JSON_TYPE)).invoke());
		}
		super.persist();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Persistable#delete()
	 */
	@Override
	public void delete() throws BazaarException {
		super.delete();
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.CATEGORY_REST_WEB_SERVICE_URL))
				.path(this.getIdentifier().getValue());
		BazaarManagerImpl.processResponse(new GenericType<Category>() {
		}, webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildDelete().invoke());
		((CategoryImpl)this.parent).children.remove(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.getIdentifier(), this.name, this.description, this.parent });
	}

}
