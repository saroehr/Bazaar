/*
 * BidderMessageBodyWriterImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 10, 2016 at 12:21:52 PM
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

import org.apache.bazaar.Address;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Name;

/**
 * BidderMessageBodyWriterImpl extends AbstractMessageBodyWriter
 * to provide an implementation for {@link Bidder} instances
 * 
 * @param <T> The Type parameters
 */
@Provider
@Produces(value = MediaType.APPLICATION_JSON)
public final class BidderMessageBodyWriterImpl<T extends Bidder> implements MessageBodyWriter<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidderMessageBodyWriterImpl
	 */
	public BidderMessageBodyWriterImpl() {
		super();
	}

	// declare methods

	/**
	 * Utility method writes Bidder
	 * instance to JsonObject
	 * 
	 * @param bidder The Bidder instance
	 * @return The JsonObject representing bidder
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static JsonObject write(@NotNull final Bidder bidder) throws RestWebServiceException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, bidder.getIdentifier().getValue());
		final Name name = bidder.getName();
		JsonObjectBuilder jsonBuilder1 = Json.createObjectBuilder();
		jsonBuilder1.add(JsonKeys.FIRST_NAME, name.getFirstName());
		jsonBuilder1.add(JsonKeys.LAST_NAME, name.getLastName());
		jsonBuilder.add(JsonKeys.NAME, jsonBuilder1.build());
		final Address billingAddress = bidder.getBillingAddress();
		jsonBuilder1 = Json.createObjectBuilder();
		jsonBuilder1.add(JsonKeys.STREET, billingAddress.getStreet());
		jsonBuilder1.add(JsonKeys.CITY, billingAddress.getCity());
		jsonBuilder1.add(JsonKeys.STATE, billingAddress.getState().name());
		jsonBuilder1.add(JsonKeys.ZIPCODE, String.valueOf(billingAddress.getZipcode()));
		jsonBuilder.add(JsonKeys.BILLING_ADDRESS, jsonBuilder1.build());
		final Address shippingAddress = bidder.getShippingAddress();
		jsonBuilder1 = Json.createObjectBuilder();
		jsonBuilder1.add(JsonKeys.STREET, shippingAddress.getStreet());
		jsonBuilder1.add(JsonKeys.CITY, shippingAddress.getCity());
		jsonBuilder1.add(JsonKeys.STATE, shippingAddress.getState().name());
		jsonBuilder1.add(JsonKeys.ZIPCODE, String.valueOf(shippingAddress.getZipcode()));
		jsonBuilder.add(JsonKeys.SHIPPING_ADDRESS, jsonBuilder1.build());
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
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Bidder.class.isAssignableFrom(clazz)) {
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
			jsonGenerator.write(JsonKeys.BIDDER, BidderMessageBodyWriterImpl.write(object));
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
			jsonGenerator.close();
		}
	}

}
