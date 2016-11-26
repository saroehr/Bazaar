/*
 * BidderCollectionMessageBodyWriterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Sep 2, 2016 at 11:47:05 AM
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
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;

/**
 * BidderCollectionMessageBodyWriterImpl
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public final class BidderCollectionMessageBodyWriterImpl implements VersionableCollectionMessageBodyWriter<Bidder> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidderCollectionMessageBodyWriterImpl
	 */
	public BidderCollectionMessageBodyWriterImpl() {
		super();
	}

	// declare members

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean writeable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Collection.class.isAssignableFrom(clazz)) {
			if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Collection.class.isAssignableFrom(clazz)) {
				if (type instanceof ParameterizedType) {
					if (Arrays.asList(((ParameterizedType)type).getActualTypeArguments()).contains(Bid.class)) {
						writeable = true;
					}
				}
			}
		}
		return writeable;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType,
	 * javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 */
	@Override
	public void writeTo(final Collection<Bidder> collection, final Class<?> clazz, final Type type,
			final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> map,
			final OutputStream outputStream) throws IOException, WebApplicationException {
		try (final BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(outputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING));
				final JsonGenerator jsonGenerator = Json.createGenerator(writer)) {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStartArray(JsonKeys.BIDDERS);
			final VersionableJsonWriter<Bidder> writer1 = new BidderJsonWriterImpl();
			for (final Bidder bidder : collection) {
				jsonGenerator.write(writer1.write(bidder));
			}
			jsonGenerator.writeEnd();
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
		}
	}

}
