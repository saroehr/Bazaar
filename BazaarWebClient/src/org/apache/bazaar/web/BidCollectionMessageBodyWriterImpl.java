/*
 * BidCollectionMessageBodyWriterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 2, 2016 at 12:20:35 PM
 */
package org.apache.bazaar.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.bazaar.Bid;

/**
 * BidCollectionMessageBodyWriterImpl
 * 
 * @param <E> The collection element type
 */
public final class BidCollectionMessageBodyWriterImpl<E extends Bid> implements CollectionMessageBodyWriter<E> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidCollectionMessageBodyWriterImpl
	 */
	public BidCollectionMessageBodyWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean writeable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Collection.class.isAssignableFrom(clazz)) {
			if (type instanceof ParameterizedType) {
				if (Arrays.asList(((ParameterizedType)type).getActualTypeArguments()).contains(Bid.class)) {
					writeable = true;
				}
			}
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
	public void writeTo(final Collection<E> collection, final Class<?> clazz, final Type type,
			final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> map,
			final OutputStream outputStream) throws IOException, WebApplicationException {
		try (final BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(outputStream, org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING))) {
			final JsonGenerator jsonGenerator = Json.createGenerator(writer);
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStartArray(JsonKeys.BIDS);
			for (final Bid bid : collection) {
				// jsonGenerator.writeStartObject(JsonKeys.BID);
				jsonGenerator.write(BidMessageBodyWriterImpl.write(bid));
				// jsonGenerator.writeEnd();
			}
			jsonGenerator.writeEnd();
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
			jsonGenerator.close();
		}
	}

}
