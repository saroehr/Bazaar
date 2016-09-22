/*
 * EntityTransactionTests.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 31, 2016 at 10:29:42 AM
 */
package org.apache.bazaar.jpa;

import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Item;
import org.apache.bazaar.ItemNotFoundException;
import org.apache.bazaar.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * EntityTransactionTests provides JUnit tests for {@link EntityTransaction}
 */
public final class EntityTransactionTests {

	// declare members

	// declare constructors

	/**
	 * Constructor for EntityManagerTests
	 */
	public EntityTransactionTests() {
		super();
	}

	/**
	 * Test for {@link EntityTransaction#begin()}
	 */
	@Test
	public void testBegin() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final Item item = manager.newItem("testBegin", "testBegin", manager.findRootCategory());
			final EntityTransaction transaction = org.apache.bazaar.persistence.EntityManagerFactory.newInstance()
					.createEntityManager().getTransaction();
			transaction.begin();
			item.persist();
			transaction.rollback();
			manager.findItem(item.getIdentifier());
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
	 * Test for {@link EntityTransaction#isActive()}
	 */
	@Test
	public void testIsActive() {
		try {
			final BazaarManager manager = BazaarManager.newInstance();
			final javax.persistence.EntityManager entityManager = Persistence.createEntityManagerFactory(org.apache.bazaar.persistence.config.Configuration.PERSISTENCE_UNIT_NAME).createEntityManager();
			final Item item = manager.newItem("testIsActive", "testIsActive", manager.findRootCategory());
			final javax.persistence.EntityTransaction transaction = entityManager.getTransaction();
			final EntityManager entityManager1 = (EntityManager)org.apache.bazaar.persistence.EntityManagerFactory.newInstance().createEntityManager();
			final org.apache.bazaar.persistence.EntityTransaction transaction1 = (org.apache.bazaar.persistence.EntityTransaction)entityManager1
					.getTransaction();
			transaction.begin();
			Assert.assertTrue(transaction.isActive());
			final javax.persistence.EntityTransaction transaction2 = Persistence
					.createEntityManagerFactory(org.apache.bazaar.persistence.config.Configuration.PERSISTENCE_UNIT_NAME).createEntityManager()
					.getTransaction();
			Assert.assertFalse(transaction2.isActive());
			Assert.assertTrue(transaction1.isActive());
			item.persist();
			Assert.assertTrue(transaction.isActive());
			transaction2.commit();
			transaction1.commit();
			transaction.commit();
			Assert.assertFalse(transaction2.isActive());
			Assert.assertFalse(transaction1.isActive());
			Assert.assertFalse(transaction.isActive());
			entityManager.close();
			entityManager1.close();
		}
		catch (final BazaarException exception) {
			exception.printStackTrace(System.err);
			TestCase.fail(exception.getLocalizedMessage());
		}
	}
}
