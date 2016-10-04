/*
 * CategoryTests.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 9, 2016
 */
package org.apache.bazaar;

import java.util.Set;

import org.apache.bazaar.persistence.config.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.ConcurrentJunitRunner;

/**
 * CategoryTests provides JUnit tests for {@link Category}.
 */
@RunWith(ConcurrentJunitRunner.class)
public final class CategoryTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for CategoryTests
	 */
	public CategoryTests() {
		super();
	}

	// declare methods

	/**
	 * Test for {@link Category#getParent()}
	 */
	@Test
	public void testGetParent() {
		try {
			final Category parent = BazaarManager.newInstance().findRootCategory();
			final Category category = BazaarManager.newInstance().newCategory();
			category.setName("testGetParent");
			category.setDescription("testGetParent");
			category.setParent(parent);
			category.persist();
			Assert.assertNotNull(category.getParent());
			Assert.assertEquals(category.getParent().getIdentifier(),
					BazaarManager.newInstance().findRootCategory().getIdentifier());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Category#getChildren()}
	 */
	@Test
	public void testGetChildren() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Category category = manager.newCategory("testGetChildren", "testGetChildren",
					manager.findRootCategory());
			final Category category1 = manager.newCategory("testGetChildren", "testGetChildren",
					manager.findRootCategory());
			manager.findRootCategory().persist();
			final Set<Category> children = manager.findRootCategory().getChildren();
			Assert.assertNotNull(children);
			Assert.assertTrue(!children.isEmpty());
			Assert.assertTrue(children.contains(category));
			Assert.assertTrue(children.contains(category1));
			final Category category2 = manager.newCategory("testGetChildren", "testGetChildren", category1);
			category1.persist();
			Assert.assertNotNull(category1.getChildren());
			Assert.assertTrue(!category1.getChildren().isEmpty());
			Assert.assertTrue(category1.getChildren().contains(category2));
			Assert.assertTrue(!manager.findRootCategory().getChildren().contains(category2));
			category.delete();
			Assert.assertTrue(!manager.findRootCategory().getChildren().contains(category));
			Assert.assertTrue(manager.findRootCategory().getChildren().contains(category1));

		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Category#persist()}
	 */
	@Test
	public void testPersist() {
		try {
			final Category parent = BazaarManager.newInstance().findRootCategory();
			Category category = BazaarManager.newInstance().newCategory();
			category.setName("testPersist");
			category.setDescription("testPersist");
			category.setParent(parent);
			category.persist();
			category = BazaarManager.newInstance().findCategory(category.getIdentifier());
			Assert.assertNotNull(category);
			Assert.assertEquals(category.getName(), "testPersist");
			final Category category1 = BazaarManager.newInstance().newCategory();
			category1.setName("testPersist");
			category1.setDescription("testPersist");
			category1.setParent(category);
			final Category category2 = BazaarManager.newInstance().newCategory();
			category2.setName("testPersist");
			category2.setDescription("testPersist");
			category2.setParent(category1);
			// persist child should fail because parent has
			// not been persisted
			boolean failed = false;
			try {
				// will only fail with HIBERNATE persistence provider
				category2.persist();
			}
			catch (final BazaarException exception) {
				failed = true;
				Assert.assertEquals(CategoryNotFoundException.class, exception.getClass());
			}
			if (Configuration.PersistenceProvider.HIBERNATE.equals(Configuration.PersistenceProvider
					.valueOf(Configuration.newInstance().getProperty(Configuration.PERSISTENCE_PROVIDER_NAME)))) {
				Assert.assertTrue(failed);
			}
			// persist parent
			category1.persist();
			// verify child has been persisted
			BazaarManager.newInstance().findCategory(category2.getIdentifier());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Category#delete}
	 */
	@Test
	public void testDelete() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Category parent = manager.findRootCategory();
			final Category category = manager.newCategory("testDelete", "testDelete", parent);
			category.persist();
			category.delete();
			try {
				manager.findCategory(category.getIdentifier());
			}
			catch (final CategoryNotFoundException exception) {
				Assert.assertNotNull(exception);
			}
			// test cascade child delete
			final Category category1 = manager.newCategory("testDelete", "testDelete", parent);
			final Category category2 = manager.newCategory("testDelete", "testDelete", category1);
			category1.persist();
			// should find category2
			manager.findCategory(category2.getIdentifier());
			// delete parent
			category1.delete();
			try {
				// child should be gone
				manager.findCategory(category2.getIdentifier());
			}
			catch (final CategoryNotFoundException exception) {
				Assert.assertNotNull(exception);
			}
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			junit.framework.Assert.fail(exception.getLocalizedMessage());
		}
	}

}
