/*
 * Versionable.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 23, 2016 at 10:20:54 AM
 */
package org.apache.bazaar.version;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Persistable;

/**
 * This interface extends {@link Persistable} and is a marker interface that
 * declares an object as auditable
 */
public interface Versionable extends Persistable {

	// declare members

	// declare methods

	/**
	 * Returns all versions for Versionable instance
	 * 
	 * @return Set of all versions instances of Versionable
	 * @throws UnsupportedOperationException if the underlying implementation
	 *         does not support version functions
	 * @throws VersionNotFoundException if no versions exist for instance
	 * @throws VersionException if the operation could not be completed
	 */
	public @NotNull Set<Version> findAllVersions()
			throws UnsupportedOperationException, VersionNotFoundException, VersionException;

}
