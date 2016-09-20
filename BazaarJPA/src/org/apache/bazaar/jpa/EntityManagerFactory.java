/*
 * EntityManagerFactory.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 31, 2016 at 10:04:51 AM
 */
package org.apache.bazaar.jpa;

/**
 * EntityManagerFactory extends {@link javax.persistence.EntityManagerFactory}
 * to provide a factory method for instance retrieval. This instance
 * provides the decorator pattern implementation and shields the persistence
 * unit
 * from being required for retrieval
 */
public interface EntityManagerFactory extends javax.persistence.EntityManagerFactory {

	// declare members

	// declare methods

	/**
	 * Factory method for obtaining instance
	 * 
	 * @return Instance of EntityManagerFactory
	 */
	public static EntityManagerFactory newInstance() {
		return EntityManagerFactoryImpl.newInstance();
	}

}
