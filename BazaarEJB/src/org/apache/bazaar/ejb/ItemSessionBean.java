package org.apache.bazaar.ejb;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Category;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;
import org.apache.bazaar.ItemNotFoundException;

/**
 * ItemSessionBean provides the EJB Local interface
 * used by clients of the bean.
 */
public interface ItemSessionBean {

	// declare members

	/**
	 * The JNDI Lookup name for the bean
	 */
	public static final String BEAN_LOOKUP_NAME = "java:app/BazaarEJB-0.0.1-SNAPSHOT/ItemSessionBean";

	// declare methods

	/**
	 * Creates new item
	 * 
	 * @return The Item instance
	 * @throws BazaarException if the operation
	 *         could not be completed
	 */
	public @NotNull Item newItem() throws BazaarException;

	/**
	 * Creates new item
	 * 
	 * @param name The name of item
	 * @param description The description of item
	 * @param category The item category
	 * @return The Item instance
	 * @throws BazaarException if the item could
	 *         not be persisted
	 */
	public @NotNull Item newItem(@NotNull @Size(min = 1, max = 255) final String name,
			@NotNull @Size(min = 1, max = 255) final String description, @NotNull final Category category)
			throws BazaarException;

	/**
	 * Finds Item instance by Identifier
	 * 
	 * @param identifier The Identifier for instance
	 * @return Item with identifier
	 * @throws ItemNotFoundException if no Item with
	 *         Identifier can be located
	 * @throws BazaarException if the operation
	 *         could not be completed
	 */
	public @NotNull Item findItem(@NotNull final Identifier identifier) throws ItemNotFoundException, BazaarException;

	/**
	 * Returns All items
	 * 
	 * @return Unmodifiable set of all items
	 * @throws BazaarException if the operation could
	 *         not be completed
	 */
	public @NotNull Set<Item> findAllItems() throws BazaarException;

}
