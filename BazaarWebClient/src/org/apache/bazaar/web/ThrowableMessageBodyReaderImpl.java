/*
 * ThrowableMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 7:11:33 PM
 */
package org.apache.bazaar.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 * ThrowableMessageBodyReaderImpl implements MessageBodyReader to provide a
 * custom type for processing exception return messages
 *
 * @param <T> The Type parameter
 */
@Provider
@Consumes(value = { MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
public final class ThrowableMessageBodyReaderImpl<T extends Throwable> implements MessageBodyReader<T> {

	// declare members

	/**
	 * Constructor for ThrowableMessageBodyReaderImpl
	 */
	public ThrowableMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.AbstractMessageBodyReader#isReadable(java.lang.
	 * Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean readable = true;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) || MediaType.TEXT_HTML_TYPE.equals(mediaType)
				|| MediaType.TEXT_PLAIN_TYPE.equals(mediaType) && Throwable.class.isAssignableFrom(type)) {
			readable = true;
		}
		return readable;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.AbstractMessageBodyReader#readFrom(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
	 * java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T readFrom(final Class<T> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream inputStream)
			throws IOException, WebApplicationException {
		T throwable;
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType)) {
				final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.THROWABLE);
				final String clazz = jsonObject.getString(JsonKeys.CLASS);
				throwable = (T)Class.forName(clazz).getDeclaredConstructor(new Class[] { String.class })
						.newInstance(jsonObject.getString(JsonKeys.MESSAGE));
				final List<JsonObject> stackElements = jsonObject.getJsonArray(JsonKeys.STACK)
						.getValuesAs(JsonObject.class);
				final StackTraceElement[] stackTrace = new StackTraceElement[stackElements.size()];
				int index = 0;
				for (final JsonObject stackElement : stackElements) {
					stackTrace[index++] = new StackTraceElement(stackElement.getString(JsonKeys.CLASS),
							stackElement.getString(JsonKeys.METHOD), stackElement.getString(JsonKeys.FILE),
							stackElement.getInt(JsonKeys.LINE));
				}
				throwable.setStackTrace(stackTrace);
			}
			else if (MediaType.TEXT_HTML_TYPE.equals(mediaType)) {
				final Stream<String> stream = reader.lines();
				final StringBuilder builder = new StringBuilder(5000);
				for (final Iterator<String> iterator = stream.iterator(); iterator.hasNext();) {
					builder.append(iterator.next());
				}
				throwable = (T)new RestWebServiceException(builder.toString());
			}
			else if (MediaType.TEXT_PLAIN_TYPE.equals(mediaType)) {
				final Stream<String> stream = reader.lines();
				final StringBuilder builder = new StringBuilder(5000);
				for (final Iterator<String> iterator = stream.iterator(); iterator.hasNext();) {
					builder.append(iterator.next());
				}
				throwable = (T)new RestWebServiceException(builder.toString());
			}
			else {
				throw new RestWebServiceException(); // TODO localize message
			}
		}
		catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| JsonParsingException exception1) {
			throwable = (T)new RestWebServiceException(exception1);
		}
		return throwable;
	}

}
