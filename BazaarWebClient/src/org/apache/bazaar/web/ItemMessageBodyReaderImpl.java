/*
 * ItemMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 9:17:05 AM
 */
package org.apache.bazaar.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.AbstractPersistable;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;

/**
 * ItemMessageBodyReaderImpl extends AbstractMessageBodyReader to provide
 * a concrete implementation for handling {@link Item}
 * processing
 * 
 * @param <T> The type parameters
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public final class ItemMessageBodyReaderImpl<T extends Item> implements MessageBodyReader<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ItemMessageBodyReaderImpl
	 */
	public ItemMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/**
	 * Method reads item instance from corresponding
	 * JsonObject
	 * 
	 * @param The {@link JsonObject} representing item
	 * @return The Item instance
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static Item read(@NotNull final JsonObject jsonObject) throws RestWebServiceException {
		final Item item;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			item = bazaarManager.newItem();
			((AbstractPersistable)item).setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
			item.setName(jsonObject.getString(JsonKeys.NAME));
			item.setDescription(jsonObject.getString(JsonKeys.DESCRIPTION));
			item.setCategory(bazaarManager.findCategory(Identifier.fromValue(jsonObject.getString(JsonKeys.CATEGORY))));
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.web.AbstractMessageBodyReader#isReadable(java.lang.
	 * Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean readable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Item.class.isAssignableFrom(type)) {
			readable = true;
		}
		return readable;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		final T item;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING))) {
			final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.ITEM);
			item = (T)ItemMessageBodyReaderImpl.read(jsonObject);
		}
		return item;
	}

}
