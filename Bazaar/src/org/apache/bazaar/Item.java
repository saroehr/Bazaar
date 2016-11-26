/*
 * Item.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.version.Versionable;

/**
 * Item declares the programming interface implementations must provide.
 */
public interface Item extends Versionable {

	// declare members

	// declare methods

	/**
	 * Returns the name of the item
	 *
	 * @return The name of the item
	 */
	public @NotNull @Size(min = 1, max = 255) String getName();

	/**
	 * Sets the name of the item
	 *
	 * @param name The name of item to set
	 */
	public void setName(@NotNull @Size(min = 1, max = 255) final String name);

	/**
	 * Returns the description of the item
	 *
	 * @return The description of the item
	 */
	public @NotNull @Size(min = 0, max = 255) String getDescription();

	/**
	 * Sets description of the item
	 *
	 * @param description the description of item to set
	 */
	public void setDescription(@NotNull @Size(min = 0, max = 255) final String description);

	/**
	 * Returns the category of item
	 *
	 * @return The category of item
	 */
	public @NotNull Category getCategory();

	/**
	 * Sets the category of item
	 *
	 * @param category The category of item to set
	 */
	public void setCategory(@NotNull final Category category);

	/**
	 * Adds image to item
	 *
	 * @param image The image to add
	 */
	public void addImage(@NotNull Image image);

	/**
	 * Returns set of images associated with item
	 *
	 * @return The set of images associated with item
	 */
	public @NotNull Set<Image> getImages();

}
