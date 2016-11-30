/*
 * VersionableCollectionMessageBodyWriter.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 1, 2016 at 10:26:07 AM
 */
package org.apache.bazaar.web;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.version.Versionable;

/**
 * VersionableCollectionMessageBodyWriter extends VersionableMessageBodyWriter
 * to provide a type for processing of collections
 *
 * @param <E> The type of collection elements
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public interface VersionableCollectionMessageBodyWriter<E extends Versionable> extends CollectionMessageBodyWriter<E> {

	// declare members

	// declare methods

}
