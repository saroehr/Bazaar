/*
 * VersionableCollectionMessageBodyWriter.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 1, 2016 at 10:26:07 AM
 */
package org.apache.bazaar.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.version.Versionable;

/**
 * VersionableCollectionMessageBodyWriter extends VersionableMessageBodyWriter to provide a type for
 * processing of collections
 *
 * @param <E> The type of collection elements
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public interface VersionableCollectionMessageBodyWriter<E extends Versionable> extends MessageBodyWriter<Collection<E>> {

	// declare members

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public default long getSize(final Collection<E> collection, final Class<?> clazz, final Type type,
			final Annotation[] annotations, final MediaType mediaType) {
		return -1;
	}

}
