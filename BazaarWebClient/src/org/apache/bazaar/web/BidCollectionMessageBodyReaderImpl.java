/*
 * BidCollectionMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 2, 2016 at 12:19:42 PM
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.Bid;

/**
 * BidCollectionMessageBodyReaderImpl
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public final class BidCollectionMessageBodyReaderImpl implements VersionableCollectionMessageBodyReader<Bid> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidCollectionMessageBodyReaderImpl
	 */
	public BidCollectionMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
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
				if (Arrays.asList(((ParameterizedType)type).getActualTypeArguments()).contains(Bid.class)) {
					readable = true;
				}
			}
		}
		return readable;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyReader#readFrom(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
	 * java.io.InputStream)
	 */
	@Override
	public Collection<Bid> readFrom(final Class<Collection<Bid>> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, String> map, final InputStream inputStream)
			throws IOException, WebApplicationException {
		final Set<Bid> bids;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING))) {
			final JsonArray jsonArray = Json.createReader(reader).readObject().getJsonArray(JsonKeys.BIDS);
			bids = new HashSet<Bid>(jsonArray.size());
			final VersionableJsonReader<Bid> reader1 = new BidJsonReaderImpl();
			for (final JsonValue jsonValue : jsonArray) {
				final JsonObject jsonObject1 = (JsonObject)jsonValue;
				bids.add(reader1.read(jsonObject1));
			}
		}
		return Collections.unmodifiableSet(bids);
	}

}
