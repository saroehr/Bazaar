/*
 * VersionableMessageBodyWriter.java
 * Created On: Nov 26, 2016 at 8:01:37 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.version.Versionable;

/**
 * VersionableMessageBodyWriter extends
 * {@link javax.ws.rs.ext.MessageBodyWriter}
 *
 * @param <T> Type being written
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public interface VersionableMessageBodyWriter<T extends Versionable> extends javax.ws.rs.ext.MessageBodyWriter<T> {

	// declare members

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public default long getSize(final T object, final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		return -1;
	}

}
