/*
 * Versionable.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 23, 2016 at 9:57:33 AM
 */
package org.apache.bazaar;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

/**
 * Versionable extends {@link Persistable} and declares the methods any
 * implementation must provide
 */
public interface Versionable extends Persistable {

	// declare members

	// declare methods

	/**
	 * Returns revision number as {@Identifier}
	 *
	 * @return The revision number
	 */
	public @NotNull Identifier getVersion();

	/**
	 * Returns the created date
	 * 
	 * @return The creation date
	 */
	public @NotNull Calendar getCreationDate();

	/**
	 * Returns the creator
	 * 
	 * @return The creator
	 */
	public @NotNull Bidder getCreator();

	/**
	 * Returns the modification date
	 *
	 * @return The modification date
	 */
	public @NotNull Calendar getModifiedDate();

	/**
	 * Returns last modifier
	 *
	 * @return The last modifier
	 */
	public @NotNull Bidder getModifier();

}
