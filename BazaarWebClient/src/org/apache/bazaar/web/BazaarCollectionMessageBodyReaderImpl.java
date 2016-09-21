/*
 * BazaarCollectionMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 2, 2016 at 12:21:28 PM
 */
package org.apache.bazaar.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.bazaar.Bazaar;

/**
 * BazaarCollectionMessageBodyReaderImpl
 * 
 * @param <E> The collection element type
 */
public final class BazaarCollectionMessageBodyReaderImpl<E extends Bazaar> implements CollectionMessageBodyReader<E> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarCollectionMessageBodyReaderImpl
	 */
	public BazaarCollectionMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyReader#isReadable(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isReadable(final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean readable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Collection.class.isAssignableFrom(clazz)) {
			if (type instanceof ParameterizedType) {
				if (Arrays.asList(((ParameterizedType)type).getActualTypeArguments()).contains(Bazaar.class)) {
					readable = true;
				}
			}
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
	public Collection<E> readFrom(final Class<Collection<E>> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, String> map, final InputStream inputStream)
			throws IOException, WebApplicationException {
		final Set<Bazaar> bazaars;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING))) {
			final JsonArray jsonArray = Json.createReader(reader).readObject().getJsonArray(JsonKeys.BAZAARS);
			bazaars = new HashSet<Bazaar>(jsonArray.size());
			for (final Iterator<JsonValue> iterator = jsonArray.iterator(); iterator.hasNext();) {
				final JsonObject jsonObject1 = (JsonObject)iterator.next();
				bazaars.add(BazaarMessageBodyReaderImpl.read(jsonObject1));
			}
		}
		return (Collection<E>)bazaars;
	}

}
