/*
 * BidMessageBodyWriterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 6:25:52 PM
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

import org.apache.bazaar.Bid;

/**
 * BidMessageBodyWriterImpl extends AbstractMessageBodyWriter to provide
 * an implementation for {@link Bid} instances
 * 
 * @param <T> The Type parameters
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public final class BidMessageBodyWriterImpl<T extends Bid> implements MessageBodyWriter<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidMessageBodyWriterImpl
	 */
	public BidMessageBodyWriterImpl() {
		super();
	}

	// declare methods

	/**
	 * Utility method writes Bid
	 * instance to JsonObject
	 * 
	 * @param item The Bid instance
	 * @return The JsonObject representing Bid
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static JsonObject write(@NotNull final Bid bid) throws RestWebServiceException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, bid.getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.BAZAAR, bid.getAuction().getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.BIDDER, bid.getBidder().getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.PRICE, bid.getPrice().toString());
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
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Bid.class.isAssignableFrom(clazz)) {
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
				new OutputStreamWriter(outputStream, org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING))) {
			final JsonGenerator jsonGenerator = Json.createGenerator(writer);
			jsonGenerator.writeStartObject();
			jsonGenerator.write(JsonKeys.BID, BidMessageBodyWriterImpl.write(object));
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
			jsonGenerator.close();
		}
	}

}
