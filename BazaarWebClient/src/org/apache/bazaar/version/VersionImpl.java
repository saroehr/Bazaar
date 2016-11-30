/*
 * VersionImpl.java
 * Created On: Nov 25, 2016 at 5:51:38 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.version;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Identifier;
import org.apache.bazaar.logging.Logger;

/**
 * VersionImpl implements {@link Version} to provide a concrete implementation
 */
public final class VersionImpl implements Version {

	// declare members
	private final Identifier identifier;
	private final Versionable versionable;

	// declare constructors

	/**
	 * Constructor for VersionImpl
	 *
	 * @param identifier The version number
	 * @param versionable The versionable associated with version;
	 */
	public VersionImpl(@NotNull final Identifier identifier, @NotNull final Versionable versionable) {
		super();
		this.identifier = identifier;
		this.versionable = versionable;
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.version.Version#getVersionNumber()
	 */
	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.version.Version#getPersistable()
	 */
	@Override
	public Versionable getVersionable() {
		return this.versionable;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.versionable == null ? 0 : this.versionable.hashCode());
		result = prime * result + (this.identifier == null ? 0 : this.identifier.hashCode());
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
		if (this.versionable == null) {
			if (other.versionable != null) {
				return false;
			}
		}
		else if (!this.versionable.equals(other.versionable)) {
			return false;
		}
		if (this.identifier == null) {
			if (other.identifier != null) {
				return false;
			}
		}
		else if (!this.identifier.equals(other.identifier)) {
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
		return Logger.toString(this, new Object[] { this.identifier, this.versionable });
	}

}
