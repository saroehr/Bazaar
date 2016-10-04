/*
 * ThrowableExceptionMapperImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 29, 2016 at 2:25:41 PM
 */
package org.apache.bazaar.web;

import java.io.StringWriter;
import java.util.Locale;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.PersistableNotFoundException;

/**
 * ThrowableExceptionMapperImpl
 * 
 * @param <T> The Throwable type
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public final class ThrowableExceptionMapperImpl<T extends Throwable> implements ExceptionMapper<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ThrowableExceptionMapperImpl
	 */
	public ThrowableExceptionMapperImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(final T exception) {
		final JsonObjectBuilder builder = Json.createObjectBuilder();
		final JsonObjectBuilder builder1 = Json.createObjectBuilder();
		builder1.add(JsonKeys.CLASS, exception.getClass().getName());
		builder1.add(JsonKeys.MESSAGE, exception.getLocalizedMessage());
		final JsonArrayBuilder builder2 = Json.createArrayBuilder();
		final StackTraceElement[] stackTraceElements = exception.getStackTrace();
		for (final StackTraceElement stackTraceElement : stackTraceElements) {
			final JsonObjectBuilder builder3 = Json.createObjectBuilder();
			builder3.add(JsonKeys.CLASS,
					stackTraceElement.getClassName() == null ? "null" : stackTraceElement.getClassName());
			builder3.add(JsonKeys.METHOD,
					stackTraceElement.getMethodName() == null ? "null" : stackTraceElement.getClassName());
			builder3.add(JsonKeys.FILE,
					stackTraceElement.getFileName() == null ? "null" : stackTraceElement.getFileName());
			builder3.add(JsonKeys.LINE, stackTraceElement.getLineNumber());
			builder3.add(JsonKeys.NATIVE, stackTraceElement.isNativeMethod());
			builder2.add(builder3);
		}
		builder1.add(JsonKeys.STACK, builder2);
		builder.add(JsonKeys.THROWABLE, builder1);
		final StringWriter writer = new StringWriter(5000);
		Json.createWriter(writer).writeObject(builder.build());
		final Response response;
		if ((exception.getCause() != null)
				&& PersistableNotFoundException.class.isAssignableFrom(exception.getCause().getClass())) {
			response = Response.status(Status.NOT_FOUND).language(Locale.getDefault())
					.encoding(org.apache.bazaar.config.Configuration.DEFAULT_ENCODING)
					.type(MediaType.APPLICATION_JSON).entity(writer.toString()).build();
		}
		else {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).language(Locale.getDefault())
					.encoding(org.apache.bazaar.config.Configuration.DEFAULT_ENCODING)
					.type(MediaType.APPLICATION_JSON).entity(writer.toString()).build();
		}
		return response;
	}

}
