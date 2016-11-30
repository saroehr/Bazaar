/*
 * ItemImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 8, 2016
 */
package org.apache.bazaar;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.version.AbstractVersionable;
import org.hibernate.envers.Audited;

/**
 * ItemImpl implements @Item to provide a concrete implementation
 */
@Entity(name = org.apache.bazaar.persistence.config.Configuration.ITEM_ENTITY_NAME)
@Table(name = org.apache.bazaar.persistence.config.Configuration.ITEM_TABLE_NAME, schema = org.apache.bazaar.persistence.config.Configuration.DATABASE_SCHEMA_NAME)
// @PrimaryKeyJoinColumn(name = Configuration.IDENTIFIABLE_COLUMN_NAME)
@Audited
public class ItemImpl extends AbstractVersionable implements Item {

	// declare members

	@Column(name = "NAME", nullable = false, updatable = true)
	private String name;
	@Column(name = "DESCRIPTION", nullable = false, updatable = true)
	private String description;
	@ManyToOne(targetEntity = CategoryImpl.class, optional = false, fetch = FetchType.EAGER, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "CATEGORY", referencedColumnName = org.apache.bazaar.persistence.config.Configuration.IDENTIFIABLE_COLUMN_NAME, unique = false, nullable = false, updatable = true)
	private Category category;
	@ElementCollection(targetClass = ImageImpl.class, fetch = FetchType.LAZY)
	@CollectionTable(name = org.apache.bazaar.persistence.config.Configuration.IMAGE_TABLE_NAME, joinColumns = {
			@JoinColumn(name = "IDENTIFIER", referencedColumnName = org.apache.bazaar.persistence.config.Configuration.IDENTIFIABLE_COLUMN_NAME, insertable = true, nullable = false, updatable = false) })
	private Set<Image> images;

	// declare constructors

	/**
	 * Constructor for ItemImpl
	 */
	protected ItemImpl() {
		super();
		this.images = new HashSet<Image>(10);
	}

	/**
	 * Constructor for ItemImpl
	 * 
	 * @param name The name of item
	 * @param description The description of item
	 * @param category The category of item
	 */
	ItemImpl(@NotNull @Size(min = 1, max = 255) final String name,
			@NotNull @Size(min = 1, max = 255) final String description, @NotNull final Category category) {
		this();
		this.name = name;
		this.description = description;
		this.category = category;
		this.images = new HashSet<Image>(10);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Item#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Item#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Item#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Item#getCategory()
	 */
	@Override
	public Category getCategory() {
		return this.category;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Item#setCategory(org.apache.bazaar.Category)
	 */
	@Override
	public void setCategory(final Category category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Item#addImage(org.apache.bazaar.Image)
	 */
	@Override
	public void addImage(final Image image) {
		this.images.add(image);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Item#getImages()
	 */
	@Override
	public Set<Image> getImages() {
		return Collections.unmodifiableSet(this.images);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this,
				new Object[] { this.getIdentifier(), this.name, this.description, this.category });
	}

}
