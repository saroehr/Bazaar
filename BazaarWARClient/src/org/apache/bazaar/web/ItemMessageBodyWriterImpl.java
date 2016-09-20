/*
 * ItemMessageBodyWriterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 11:01:08 AM
 */
package org.apache.bazaar.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
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

import org.apache.bazaar.Item;

/**
 * ItemMessageBodyWriterImpl extends AbstractMessageBodyWriter to provide
 * an implementation for {@link Item} instances.
 * 
 * @param <T> The type argument
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public final class ItemMessageBodyWriterImpl<T extends Item> implements MessageBodyWriter<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ItemMessageBodyWriterImpl
	 */
	public ItemMessageBodyWriterImpl() {
		super();
	}

	// declare methods

	/**
	 * Utility method writes Item
	 * instance to JsonObject
	 * 
	 * @param item The Item instance
	 * @return The JsonObject representing item
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static JsonObject write(@NotNull final Item item) throws RestWebServiceException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, item.getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.NAME, item.getName());
		jsonBuilder.add(JsonKeys.DESCRIPTION, item.getDescription());
		jsonBuilder.add(JsonKeys.CATEGORY, item.getCategory().getIdentifier().getValue());
		return jsonBuilder.build();
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
	 * Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean writeable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Item.class.isAssignableFrom(clazz)) {
			writeable = true;
		}
		return writeable;
	}

	/*
	 * (non-Javadoc)
	 * 
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
				new OutputStreamWriter(outputStream, org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING))) {
			final JsonGenerator jsonGenerator = Json.createGenerator(writer);
			jsonGenerator.writeStartObject();
			jsonGenerator.write(JsonKeys.ITEM, ItemMessageBodyWriterImpl.write(object));
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
			jsonGenerator.close();
		}
	}

}
