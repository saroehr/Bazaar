/*
 * CategoryMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 9:20:42 AM
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
import org.apache.bazaar.Category;
import org.apache.bazaar.Identifier;

/**
 * CategoryMessageBodyReaderImpl extends AbstractMessageBodyReader to provide
 * a concrete implementation for handling {@link Category}
 * processing
 * 
 * @param <T> The Type parameter
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public final class CategoryMessageBodyReaderImpl<T extends Category> implements MessageBodyReader<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for CategoryMessageBodyReaderImpl
	 */
	public CategoryMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/**
	 * Method reads category instance from corresponding
	 * JsonObject
	 * 
	 * @param The {@link JsonObject} representing category
	 * @return The Category instance
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static Category read(@NotNull final JsonObject jsonObject) throws RestWebServiceException {
		final Category category;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			final Identifier rootIdentifier = Identifier.fromValue(org.apache.bazaar.config.Configuration.newInstance()
					.getProperty(org.apache.bazaar.config.Configuration.ROOT_CATEGORY_IDENTIFIER));
			if (rootIdentifier.equals(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)))) {
				category = bazaarManager.findRootCategory();
			}
			else {
				category = bazaarManager.newCategory();
				((AbstractPersistable)category)
						.setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
				category.setName(jsonObject.getString(JsonKeys.NAME));
				category.setDescription(jsonObject.getString(JsonKeys.DESCRIPTION));
				if (rootIdentifier.equals(Identifier.fromValue(jsonObject.getString(JsonKeys.PARENT)))) {
					category.setParent(bazaarManager.findRootCategory());
				}
				else {
					category.setParent(
							bazaarManager.findCategory(Identifier.fromValue(jsonObject.getString(JsonKeys.PARENT))));
				}
			}
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return category;
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
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Category.class.isAssignableFrom(type)) {
			readable = true;
		}
		return readable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyReader#readFrom(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
	 * java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T readFrom(final Class<T> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType,
			final MultivaluedMap<String, String> map, final InputStream inputStream)
			throws IOException, WebApplicationException {
		final T category;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING))) {
			final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.CATEGORY);
			category = (T)CategoryMessageBodyReaderImpl.read(jsonObject);
		}
		return category;
	}

}
