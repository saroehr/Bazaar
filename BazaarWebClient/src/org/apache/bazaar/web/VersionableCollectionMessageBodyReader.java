/*
 * VersionableCollectionMessageBodyReader.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 1, 2016 at 10:21:18 AM
 */
package org.apache.bazaar.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.version.Versionable;

/**
 * VersionableCollectionMessageBodyReader extends MessageBodyReader to provide a
 * type for processing of collections
 *
 * @param <E> The type of collection element
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public interface VersionableCollectionMessageBodyReader<E extends Versionable> extends CollectionMessageBodyReader<E> {

	// declare members

	// declare methods

}
