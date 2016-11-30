/*
 * AbstractVersionable.java
 * Created On: Nov 25, 2016 at 4:18:56 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.version;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.bazaar.AbstractPersistable;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.IdentifierException;
import org.apache.bazaar.config.PropertyNotFoundException;
import org.apache.bazaar.persistence.EntityManagerFactory;
import org.apache.bazaar.persistence.config.Configuration;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

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
	 * @see
	 * org.apache.bazaar.version.Versionable#findAllVersions(org.apache.bazaar.
	 * version.Versionable)
	 */
	@Override
	public final Set<Version> findAllVersions()
			throws org.apache.bazaar.UnsupportedOperationException, VersionNotFoundException, VersionException {
		final Set<Version> versions;
		try {
			// check version function support
			if (!Configuration.PersistenceProvider.HIBERNATE.equals(Configuration.PersistenceProvider
					.valueOf(Configuration.newInstance().getProperty(Configuration.PERSISTENCE_PROVIDER_NAME)))) {
				throw new org.apache.bazaar.UnsupportedOperationException();
			}
			final AuditReader auditReader = AuditReaderFactory
					.get(EntityManagerFactory.newInstance().createEntityManager());
			final List<Number> identifiers = auditReader.getRevisions(this.getClass(), this.getIdentifier());
			if (identifiers.isEmpty()) {
				throw new VersionNotFoundException();
			}
			versions = new HashSet<Version>(identifiers.size());
			for (final Number identifier : identifiers) {
				final Versionable version = auditReader.find(this.getClass(), this.getIdentifier(), identifier);
				if (version != null) {
					versions.add(new VersionImpl(Identifier.fromValue(identifier.toString()), version));
				}
			}
		}
		catch (final PropertyNotFoundException exception) {
			throw new VersionException(exception);
		}
		catch (final IdentifierException exception) {
			throw new VersionException(exception);
		}
		return versions;
	}

}
