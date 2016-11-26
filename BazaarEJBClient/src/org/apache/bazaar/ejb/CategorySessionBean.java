package org.apache.bazaar.ejb;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Category;
import org.apache.bazaar.CategoryNotFoundException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionException;
import org.apache.bazaar.version.VersionNotFoundException;

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
	 * Returns Set of all Version for instance
	 *
	 * @param category The Category to retrieve Versions for
	 * @return Set of all Versions for instance
	 * @throws UnsupportedOperationException if the implementation does not
	 *         support versions
	 * @throws VersionNotFoundException if no versions exist for category
	 * @throws VersionException if the operation could not be completed
	 */
	public @NotNull Set<Version> findAllVersions(@NotNull final Category category)
			throws UnsupportedOperationException, VersionNotFoundException, VersionException;

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
