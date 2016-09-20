/*
 * Category.java
 * Created by: Scott A. Roehrig
 * Created on: July 8th, 2016
 */
package org.apache.bazaar;

import java.io.Serializable;
import java.util.Set;

// declare imports

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Category declares the programming interface implementations must provide.
 */
public interface Category extends Persistable, Serializable {

	// declare members

	// declare methods

	/**
	 * Returns the category name
	 *
	 * @return The category name
	 */
	public @NotNull @Size(min = 1, max = 255) String getName();

	/**
	 * Sets the category name
	 *
	 * @param name
	 *        The category name to set
	 */
	public void setName(@NotNull @Size(min = 1, max = 255) final String name);

	/**
	 * Returns the category description
	 *
	 * @return The category description
	 */
	public @NotNull @Size(min = 0, max = 255) String getDescription();

	/**
	 * Sets the category description
	 *
	 * @param description
	 *        The category description to set
	 */
	public void setDescription(@NotNull @Size(min = 0, max = 255) final String description);

	/**
	 * Returns the parent category
	 *
	 * @return The parent category
	 */
	public @NotNull Category getParent();

	/**
	 * Sets the parent category. Parent may not
	 * equal child. This will throw an IllegalArgumentException.
	 * Only the Root category has parent equal to itself.
	 *
	 * @param parent
	 *        the parent category to set
	 */
	public void setParent(@NotNull final Category parent);

	/**
	 * Returns the set of children categories.
	 * 
	 * @return Set of child categories
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Category> getChildren() throws BazaarException;

}
