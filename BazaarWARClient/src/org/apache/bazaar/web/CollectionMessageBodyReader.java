/*
 * CollectionMessageBodyReader.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 1, 2016 at 10:21:18 AM
 */
package org.apache.bazaar.web;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.Persistable;

/**
 * CollectionMessageBodyReader extends MessageBodyReader
 * to provide a type for processing of collections
 * 
 * @param <E> The type of collection element
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public interface CollectionMessageBodyReader<E extends Persistable> extends MessageBodyReader<Collection<E>> {

	// declare members

	// declare methods

}
