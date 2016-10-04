/*
 * ItemImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 16, 2016 at 9:04:55 PM
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
 * ItemImpl extends AbstractPersistable and implements {@link Item} to provide a
 * concrete implementation
 */
public class ItemImpl extends AbstractPersistable implements Item {

	private String name;
	private String description;
	private Category category;
	private final Set<Image> images;

	// declare constructors

	/**
	 * Constructor for ItemImpl
	 */
	ItemImpl() {
		super();
		this.images = new HashSet<Image>(25);
	}

	/**
	 * Constructor for ItemImpl
	 *
	 * @param name The name for item
	 * @param description The description for item
	 * @param category The category for item
	 */
	ItemImpl(@NotNull final String name, @NotNull final String description, @NotNull final Category category) {
		this();
		this.name = name;
		this.description = description;
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#getCategory()
	 */
	@Override
	public Category getCategory() {
		return this.category;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#setCategory(org.apache.bazaar.Category)
	 */
	@Override
	public void setCategory(final Category category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#addImage(org.apache.bazaar.Image)
	 */
	@Override
	public void addImage(final Image image) {
		this.images.add(image);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Item#getImages()
	 */
	@Override
	public Set<Image> getImages() {
		return Collections.unmodifiableSet(this.images);
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
			manager.findItem(this.getIdentifier());
		}
		catch (final ItemNotFoundException exception) {
			persisted = false;
		}
		final WebTarget webTarget = ((BazaarManagerImpl)BazaarManager.newInstance()).newRestWebClient()
				.target(Configuration.newInstance().getProperty(Configuration.ITEM_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.IDENTIFIER, this.getIdentifier().getValue())
				.queryParam(RequestParameters.NAME, this.name)
				.queryParam(RequestParameters.DESCRIPTION, this.description)
				.queryParam(RequestParameters.CATEGORY, this.category.getIdentifier().getValue());
		if (persisted) {
			BazaarManagerImpl.processResponse(new GenericType<Item>() {
			}, webTarget.request(MediaType.APPLICATION_JSON_TYPE)
					.buildPost(Entity.entity(this, MediaType.APPLICATION_JSON_TYPE)).invoke());
		}
		else {
			BazaarManagerImpl.processResponse(new GenericType<Item>() {
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
				.target(Configuration.newInstance().getProperty(Configuration.ITEM_REST_WEB_SERVICE_URL))
				.queryParam(RequestParameters.IDENTIFIER, this.getIdentifier().getValue());
		BazaarManagerImpl.processResponse(new GenericType<Item>() {
		}, webTarget.request(MediaType.APPLICATION_JSON_TYPE).buildDelete().invoke());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.getIdentifier(), this.name, this.description, this.category });
	}

}
