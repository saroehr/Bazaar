/*
 * Auditable.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 23, 2016 at 10:20:54 AM
 */
package org.apache.bazaar.audit;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.Identifier;

/**
 * Auditable declares the methods an implementation must provide. This interface
 * is a marker interface that declares an implementation as supporting auditing
 * functions
 */
public interface Auditable {

	// declare members

	// declare methods

	/**
	 * Returns {@link Identifier} for revision of this Persistable
	 *
	 * @return The revision identifier for this persistable
	 * @throws UnsupportedOperationException if the underlying implementation
	 *         does not support this feature
	 * @throws AuditableNotFoundException if this persistable has no audits
	 *         associated with it
	 */
	public @NotNull Identifier getRevisionIdentifier() throws UnsupportedOperationException, AuditableNotFoundException;

	/**
	 * Returns true if and only if the persistable has revisions
	 *
	 * @return True if and only if the persistable has revisions
	 * @throws UnsupportedOperationException if the underlying implementation
	 *         does not support this feature
	 * @throws AuditableException if the operation could not be completed
	 * @throws UnsupportedOperationException if the underlying implementation
	 *         does not support this feature
	 */
	public boolean hasRevisions() throws UnsupportedOperationException, AuditableException;

	/**
	 * Returns revision with identifier
	 *
	 * @param identifier The Identifier for Revision to retrieve
	 * @return The Persistable with revision identifier
	 * @throws UnsupportedOperationException if the underlying implementation
	 *         does not support this feature
	 * @throws AuditableNotFoundException if the revision with identifier can
	 *         not be found
	 * @throws AuditableException if the operation could not be completed
	 */
	public @NotNull Auditable getRevision(@NotNull final Identifier identifier)
			throws UnsupportedOperationException, AuditableNotFoundException, AuditableException;

	/**
	 * Returns Set of all revisions to this persistable
	 *
	 * @return Set of all revisions to this persistable
	 * @throws UnsupportedOperationException if the underlying implementation
	 *         does not support this feature
	 * @throws AuditableNotFoundException if no revisions for persistable exist
	 * @throws AuditableException if the operation could not be completed
	 */
	public @NotNull Set<Auditable> getAllRevisions()
			throws UnsupportedOperationException, AuditableNotFoundException, AuditableException;

}
