/*
 * CategoryMessageBodyWriterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 12:13:06 PM
 */
package org.apache.bazaar.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonGenerator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Category;

/**
 * CategoryMessageBodyWriterImpl extends AbstractMessageBodyWriter to provide an
 * implementation for {@link Category} instances
 * 
 * @param <T>
 *            The Type parameter
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public final class CategoryMessageBodyWriterImpl<T extends Category> implements MessageBodyWriter<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for CategoryMessageBodyWriterImpl
	 */
	public CategoryMessageBodyWriterImpl() {
		super();
	}

	// declare methods

	/**
	 * Utility method writes Category instance to JsonObject
	 * 
	 * @param category
	 *            The Category instance
	 * @return The JsonObject representing category
	 * @throws RestWebServiceException
	 *             if the operation could not be completed
	 */
	static JsonObject write(@NotNull final Category category) throws RestWebServiceException {
		final JsonObject jsonObject;
		try {
			final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
			jsonBuilder.add(JsonKeys.IDENTIFIER, category.getIdentifier().getValue());
			jsonBuilder.add(JsonKeys.NAME, category.getName());
			jsonBuilder.add(JsonKeys.DESCRIPTION, category.getDescription());
			jsonBuilder.add(JsonKeys.PARENT, category.getParent().getIdentifier().getValue());
			final Set<Category> children = category.getChildren();
			if (!children.isEmpty()) {
				final JsonArrayBuilder jsonBuilder1 = Json.createArrayBuilder();
				for (final Category child : children) {
					final JsonObjectBuilder jsonBuilder2 = Json.createObjectBuilder();
					jsonBuilder2.add(JsonKeys.IDENTIFIER, child.getIdentifier().getValue());
					jsonBuilder2.add(JsonKeys.NAME, child.getName());
					jsonBuilder2.add(JsonKeys.DESCRIPTION, child.getDescription());
					jsonBuilder1.add(jsonBuilder2.build());
				}
				jsonBuilder.add(JsonKeys.CHILDREN, jsonBuilder1.build());
			}
			jsonObject = jsonBuilder.build();
		} catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return jsonObject;
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @see
	 * org.apache.bazaar.web.AbstractMessageBodyWriter#isWriteable(java.lang.
	 * Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean writeable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Category.class.isAssignableFrom(clazz)) {
			writeable = true;
		}
		return writeable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType,
	 * javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 */
	@Override
	public void writeTo(final T object, final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, Object> map, final OutputStream outputStream)
			throws IOException, WebApplicationException {
		try (final BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(outputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING));
				final JsonGenerator jsonGenerator = Json.createGenerator(writer)) {
			jsonGenerator.writeStartObject();
			jsonGenerator.write(JsonKeys.CATEGORY, CategoryMessageBodyWriterImpl.write(object));
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
		}
	}

}
