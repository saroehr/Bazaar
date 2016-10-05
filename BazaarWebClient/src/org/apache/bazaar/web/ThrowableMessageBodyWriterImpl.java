/*
 * ThrowableMessageBodyWriterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 4:51:53 PM
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
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 * ThrowableMessageBodyWriterImpl implements MessageBodyWriter to provide an
 * implementation for writing error json returns
 *
 * @param <T> The type parameters
 */
@Provider
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
public final class ThrowableMessageBodyWriterImpl<T extends Throwable> implements MessageBodyWriter<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ThrowableMessageBodyWriterImpl
	 */
	public ThrowableMessageBodyWriterImpl() {
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
	public long getSize(final T object, final Class<?> clazz, final Type type, final Annotation[] annotations,
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
	public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean writeable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) || MediaType.TEXT_HTML_TYPE.equals(mediaType)
				|| MediaType.TEXT_PLAIN_TYPE.equals(mediaType) && Throwable.class.isAssignableFrom(type)) {
			writeable = true;
		}
		return writeable;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.AbstractMessageBodyWriter#writeTo(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType,
	 * javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 */
	@Override
	public void writeTo(final T object, final Class<?> clazz, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, Object> map, final OutputStream outputStream)
			throws IOException, WebApplicationException {
		try (final BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(outputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING));
				final JsonGenerator jsonGenerator = Json.createGenerator(writer)) {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStartObject(JsonKeys.THROWABLE);
			jsonGenerator.write(JsonKeys.CLASS, object.getClass().getName());
			jsonGenerator.write(JsonKeys.MESSAGE,
					object.getLocalizedMessage() != null ? object.getLocalizedMessage() : "");
			jsonGenerator.writeStartArray(JsonKeys.STACK);
			final StackTraceElement[] stackTraceElements = object.getStackTrace();
			for (final StackTraceElement stackTraceElement : stackTraceElements) {
				jsonGenerator.writeStartObject();
				jsonGenerator.write(JsonKeys.CLASS,
						stackTraceElement.getClassName() == null ? "null" : stackTraceElement.getClassName());
				jsonGenerator.write(JsonKeys.METHOD,
						stackTraceElement.getMethodName() == null ? "null" : stackTraceElement.getMethodName());
				jsonGenerator.write(JsonKeys.FILE,
						stackTraceElement.getFileName() == null ? "null" : stackTraceElement.getFileName());
				jsonGenerator.write(JsonKeys.LINE, stackTraceElement.getLineNumber());
				jsonGenerator.write(JsonKeys.NATIVE, stackTraceElement.isNativeMethod());
				jsonGenerator.writeEnd();
			}
			jsonGenerator.writeEnd();
			jsonGenerator.writeEnd();
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
		}

	}

}
