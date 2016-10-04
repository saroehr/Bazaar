/*
 * BidMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 9:21:49 AM
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

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bid;
import org.apache.bazaar.BidImpl;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;

/**
 * BidMessageBodyReaderImpl extends AbstractMessageBodyReader to provide
 * a concrete implementation for handling {@link Bid}
 * processing
 * 
 * @param <T> The Type parameters
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public final class BidMessageBodyReaderImpl<T extends Bid> implements MessageBodyReader<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidMessageBodyReaderImpl
	 */
	public BidMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/**
	 * Method reads bid instance from corresponding
	 * JsonObject
	 * 
	 * @param The {@link JsonObject} representing bid
	 * @return The Bid instance
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static Bid read(@NotNull final JsonObject jsonObject) throws RestWebServiceException {
		final Bid bid;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			final Bazaar bazaar = bazaarManager.findBazaar(Identifier.fromValue(jsonObject.getString(JsonKeys.BAZAAR)));
			final Bidder bidder = bazaarManager.findBidder(Identifier.fromValue(jsonObject.getString(JsonKeys.BIDDER)));
			bid = bazaar.newBid(bidder, Double.valueOf(jsonObject.getString(JsonKeys.PRICE)));
			((BidImpl)bid).setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return bid;
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
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Bid.class.isAssignableFrom(type)) {
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
		final T bid;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING))) {
			final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.BID);
			bid = (T)BidMessageBodyReaderImpl.read(jsonObject);
		}
		return bid;
	}

}
