/*
 * AbstractVersionable.java
 * Created On: Nov 25, 2016 at 6:36:22 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.version;

import java.util.Set;

import org.apache.bazaar.AbstractPersistable;

/**
 * AbstractVersionable extends {@link AbstractPersistable} and implements
 * {@link Versionable}
 */
public abstract class AbstractVersionable extends AbstractPersistable implements Versionable {

	// declare members

	// declare constructors

	/**
	 * Constructor for AbstractVersionable
	 */
	protected AbstractVersionable() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.version.Versionable#findAllVersions()
	 */
	@Override
	public abstract Set<Version> findAllVersions()
			throws org.apache.bazaar.UnsupportedOperationException, VersionNotFoundException, VersionException;

}
