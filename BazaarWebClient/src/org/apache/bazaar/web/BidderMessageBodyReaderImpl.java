/*
 * BidderMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 9:18:41 AM
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
import org.apache.bazaar.Address;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Name;
import org.apache.bazaar.State;

/**
 * BidderMessageBodyReaderImpl extends AbstractMessageBodyReader to provide
 * a concrete implementation for handling {@link Bidder}
 * processing
 * 
 * @param <T> The Type parameters
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public final class BidderMessageBodyReaderImpl<T extends Bidder> implements MessageBodyReader<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidderMessageBodyReaderImpl
	 */
	public BidderMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/**
	 * Method reads bidder instance from corresponding
	 * JsonObject
	 * 
	 * @param The {@link JsonObject} representing bidder
	 * @return The Bidder instance
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static Bidder read(@NotNull final JsonObject jsonObject) throws RestWebServiceException {
		final Bidder bidder;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			bidder = bazaarManager.newBidder();
			((AbstractPersistable)bidder)
					.setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
			final Name name = bazaarManager.newName();
			JsonObject jsonObject1 = jsonObject.getJsonObject(JsonKeys.NAME);
			name.setFirstName(jsonObject1.getString(JsonKeys.FIRST_NAME));
			name.setLastName(jsonObject1.getString(JsonKeys.LAST_NAME));
			bidder.setName(name);
			jsonObject1 = jsonObject.getJsonObject(JsonKeys.BILLING_ADDRESS);
			final Address billingAddress = bazaarManager.newAddress();
			billingAddress.setStreet(jsonObject1.getString(JsonKeys.STREET));
			billingAddress.setCity(jsonObject1.getString(JsonKeys.CITY));
			billingAddress.setState(State.valueOf(jsonObject1.getString(JsonKeys.STATE)));
			billingAddress.setZipcode(Integer.valueOf(jsonObject1.getString(JsonKeys.ZIPCODE)));
			bidder.setBillingAddress(billingAddress);
			jsonObject1 = jsonObject.getJsonObject(JsonKeys.SHIPPING_ADDRESS);
			final Address shippingAddress = bazaarManager.newAddress();
			shippingAddress.setStreet(jsonObject1.getString(JsonKeys.STREET));
			shippingAddress.setCity(jsonObject1.getString(JsonKeys.CITY));
			shippingAddress.setState(State.valueOf(jsonObject1.getString(JsonKeys.STATE)));
			shippingAddress.setZipcode(Integer.valueOf(jsonObject1.getString(JsonKeys.ZIPCODE)));
			bidder.setShippingAddress(shippingAddress);
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return bidder;
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
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Bidder.class.isAssignableFrom(type)) {
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
		final T bidder;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING))) {
			final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.BIDDER);
			bidder = (T)BidderMessageBodyReaderImpl.read(jsonObject);
		}
		return bidder;
	}

}
