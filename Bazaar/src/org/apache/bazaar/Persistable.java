/*
 * Persistable.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 8, 2016
 */
package org.apache.bazaar;

/**
 * Persistable declares the implementor to
 * be persistable to permanent storage.
 */
public interface Persistable extends Identifiable {

	// declare members

	// declare methods

	/**
	 * Persists changes made to permanent storage
	 * 
	 * @throws BazaarException
	 *             if the operation could not be completed
	 */
	public void persist() throws BazaarException;

	/**
	 * Deletes the instance from permanent storage
	 * 
	 * @throws BazaarException
	 *             if the operation could not be completed
	 */
	public void delete() throws BazaarException;

}
