/*
 * ItemTests.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 8, 2016
 */
package org.apache.bazaar;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Category;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Image;
import org.apache.bazaar.Image.MimeType;
import org.apache.bazaar.Item;
import org.apache.bazaar.ItemNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycila.junit.concurrent.ConcurrentJunitRunner;

import junit.framework.TestCase;

/**
 * ItemTests provides JUnit tests for @{link Item}
 */
@RunWith(ConcurrentJunitRunner.class)
public final class ItemTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for ItemTests
	 */
	public ItemTests() {
		super();
	}

	// declare methods

	/**
	 * Test for {@link Item#persist()}
	 */
	@Test
	public void testPersist() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Category parent = manager.findRootCategory();
			final Category category = manager.newCategory();
			category.setName("testPersist");
			category.setDescription("testPersist");
			category.setParent(parent);
			// manager.findItem(2L);
			final Item item = manager.newItem();
			item.setName("testPersist");
			item.setDescription("testPersist");
			item.setCategory(category);
			final Image image = manager.newImage("testPersist" + System.currentTimeMillis(), MimeType.JPG,
					this.getClass().getResourceAsStream("/META-INF/image.jpg"));
			item.addImage(image);
			// test initial persist
			item.persist();
			// test update
			item.setName("testPersistUpdated");
			item.persist();
			Assert.assertEquals("testPersistUpdated", item.getName());
			Assert.assertNotNull(item.getImages());
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			TestCase.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Item#delete()}
	 */
	@Test
	public void testDelete() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Category category = manager.findRootCategory();
			final Item item = manager.newItem();
			item.setName("testDelete");
			item.setDescription("testDelete");
			item.setCategory(category);
			item.persist();
			final Identifier identifier = item.getIdentifier();
			item.delete();
			manager.findItem(identifier);
		}
		catch (final ItemNotFoundException exception) {
			Assert.assertNotNull(exception);
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			TestCase.fail(exception.getLocalizedMessage());
		}
	}

	/**
	 * Test for {@link Item#getImages()}
	 */
	@Test
	public void testGetImages() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Category category = manager.findRootCategory();
			final Item item = manager.newItem("testGetImages", "testGetImages", category);
			final String name = "testGetImages" + System.currentTimeMillis();
			final Image image = manager.newImage(name, MimeType.JPG,
					this.getClass().getResourceAsStream("/META-INF/image.jpg"));
			item.addImage(image);
			item.persist();
			Assert.assertNotNull(item.getImages());
			boolean foundImage = false;
			for (final Image image1 : item.getImages()) {
				if (image1.getName().equals(name)) {
					foundImage = true;
				}
			}
			Assert.assertTrue(foundImage);
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			TestCase.fail(exception.getLocalizedMessage());
		}
	}

}
