/*
 * VersionMessageBodyWriterImpl.java
 * Created On: Nov 26, 2016 at 7:42:54 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.bazaar.version.Version;

/**
 * VersionMessageBodyWriterImpl implements {@link VersionableMessageBodyWriter}
 * to provide a writer for Version instances
 */
public final class VersionMessageBodyWriterImpl implements MessageBodyWriter<Version> {

	// declare members

	// declare constructors

	/**
	 * Constructor for VersionMessageBodyWriterImpl
	 */
	public VersionMessageBodyWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public long getSize(final Version version, final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean writeable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Version.class.isAssignableFrom(clazz)) {
			writeable = true;
		}
		return writeable;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType,
	 * javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 */
	@Override
	public void writeTo(final Version version, final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, Object> map, final OutputStream outputStream)
			throws IOException, WebApplicationException {
		try (final BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(outputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING));
				final JsonGenerator jsonGenerator = Json.createGenerator(writer)) {
			jsonGenerator.writeStartObject();
			jsonGenerator.write(JsonKeys.VERSION, new VersionJsonWriterImpl().write(version));
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
		}
	}

}
