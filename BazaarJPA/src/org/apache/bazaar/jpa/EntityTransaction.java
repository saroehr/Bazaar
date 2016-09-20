/*
 * EntityTransaction.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 31, 2016 at 1:28:26 PM
 */
package org.apache.bazaar.jpa;

import java.sql.Blob;

import javax.validation.constraints.NotNull;

/**
 * EntityTransaction extends {@link javax.persistence.EntityTransaction}
 */
public interface EntityTransaction extends javax.persistence.EntityTransaction {

	// declare members

	// declare methods

	/**
	 * Returns the TransactionType
	 * 
	 * @return The {@link org.apache.bazaar.jpa.config.Configuration.TransactionType}
	 * @throws EntityTransactionException if the type
	 *         could not be determined.
	 */
	public @NotNull org.apache.bazaar.jpa.config.Configuration.TransactionType getTransactionType()
			throws EntityTransactionException;

	/**
	 * Returns instance of {@link Blob}.
	 * Blob must be used within context
	 * of an active transaction.
	 * 
	 * @return Blob instance
	 * @throws EntityTransactionException if the instance
	 *         could not be created
	 */
	public @NotNull Blob newBlob() throws EntityTransactionException;

	/**
	 * Returns Transaction status
	 * 
	 * @return The Transaction status value.
	 * @throws EntityTransactionException if the status
	 *         could not be determined
	 */
	public int getStatus();

}
