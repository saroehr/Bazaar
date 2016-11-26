/*
 * VersionImpl.java
 * Created On: Nov 25, 2016 at 5:51:38 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.version;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Identifier;
import org.apache.bazaar.Persistable;
import org.apache.bazaar.logging.Logger;

/**
 * VersionImpl implements {@link Version} to provide a concrete implementation
 */
final class VersionImpl implements Version {

	// declare members
	private final Identifier versionNumber;
	private final Persistable persistable;

	// declare constructors

	/**
	 * Constructor for VersionImpl
	 * 
	 * @param versionNumber The version number
	 * @param persistable The persistable associated with version;
	 */
	VersionImpl(@NotNull final Identifier versionNumber, @NotNull final Persistable persistable) {
		super();
		this.versionNumber = versionNumber;
		this.persistable = persistable;
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.version.Version#getVersionNumber()
	 */
	@Override
	public Identifier getVersionIdentifier() {
		return this.versionNumber;
	}

	/* (non-Javadoc)
	 * @see org.apache.bazaar.version.Version#getPersistable()
	 */
	@Override
	public Persistable getPersistable() {
		return this.persistable;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.persistable == null) ? 0 : this.persistable.hashCode());
		result = (prime * result) + ((this.versionNumber == null) ? 0 : this.versionNumber.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof VersionImpl)) {
			return false;
		}
		final VersionImpl other = (VersionImpl)object;
		if (this.persistable == null) {
			if (other.persistable != null) {
				return false;
			}
		}
		else if (!this.persistable.equals(other.persistable)) {
			return false;
		}
		if (this.versionNumber == null) {
			if (other.versionNumber != null) {
				return false;
			}
		}
		else if (!this.versionNumber.equals(other.versionNumber)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.versionNumber, this.persistable });
	}

}
