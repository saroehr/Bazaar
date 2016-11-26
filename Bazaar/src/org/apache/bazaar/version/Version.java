/*
 * Version.java
 * Created On: Nov 25, 2016 at 5:44:22 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.version;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Identifier;
import org.apache.bazaar.Persistable;

/**
 * Version declares the methods an implementation must provide
 */
public interface Version {

	// declare members

	// declare methods

	/**
	 * Returns version identifier
	 *
	 * @return The version identifier
	 */
	public @NotNull Identifier getVersionIdentifier();

	/**
	 * Returns the persistable associated with version
	 *
	 * @return The Persistable associated with version
	 */
	public @NotNull Persistable getPersistable();

}
