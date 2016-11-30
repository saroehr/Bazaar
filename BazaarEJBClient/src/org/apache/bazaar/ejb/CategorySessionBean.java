package org.apache.bazaar.ejb;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Category;
import org.apache.bazaar.CategoryNotFoundException;
import org.apache.bazaar.Identifier;

/**
 * CategorySessionBean provides the EJB Local interface used by clients of the
 * bean
 */
public interface CategorySessionBean {

	// declare members

	/**
	 * The JNDI lookup name for bean
	 */
	public String BEAN_LOOKUP_NAME = "java:app/BazaarEJB-0.0.1-SNAPSHOT/CategorySessionBean";

	// declare methods

	/**
	 * Finds root category
	 *
	 * @return The root category instance
	 * @throws BazaarException if the operation could not be completed.
	 */
	public @NotNull Category findRootCategory() throws BazaarException;

	/**
	 * Finds Category by Identifier
	 *
	 * @param identifier The Identifier for category
	 * @return The Category with identifier
	 * @throws CategoryNotFoundException if no category with identifier exists
	 * @throws BazaarException if the operation could not be completed.
	 */
	public @NotNull Category findCategory(@NotNull final Identifier identifier)
			throws CategoryNotFoundException, BazaarException;

	/**
	 * Finds all categories
	 *
	 * @return The unmodifiable set of all categories
	 * @throws BazaarException if the operation could not be completed
	 */
	public @NotNull Set<Category> findAllCategories() throws BazaarException;

	/**
	 * Creates new Category
	 *
	 * @param name The category name
	 * @param description The category description
	 * @param parent The category parent
	 * @return The Category instance
	 * @throws BazaarException if the instance could not be created
	 */
	public @NotNull Category newCategory(@NotNull @Size(min = 1, max = 255) final String name,
			@NotNull @Size(min = 1, max = 255) final String description, @NotNull final Category parent)
			throws BazaarException;

}
