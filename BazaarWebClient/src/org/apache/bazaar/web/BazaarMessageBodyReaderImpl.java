/*
 * BazaarMessageBodyReaderImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 15, 2016 at 9:16:17 AM
 */
package org.apache.bazaar.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Calendar;

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
import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Identifier;

/**
 * BazaarMessageBodyReaderImpl extends AbstractMessageBodyReader to provide
 * a concrete implementation for handling {@link Bazaar}
 * processing
 * 
 * @param <T> The Type parameters
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public final class BazaarMessageBodyReaderImpl<T extends Bazaar> implements MessageBodyReader<T> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarMessageBodyReaderImpl
	 */
	public BazaarMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/**
	 * Method reads bazaar instance from corresponding
	 * JsonObject
	 * 
	 * @param The {@link JsonObject} representing bazaar
	 * @return The Bazaar instance
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	static Bazaar read(@NotNull final JsonObject jsonObject) throws RestWebServiceException {
		final Bazaar bazaar;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			final Calendar startDate = Calendar.getInstance();
			startDate.setTimeInMillis(Long.valueOf(jsonObject.getString(JsonKeys.START)).longValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setTimeInMillis(Long.valueOf(jsonObject.getString(JsonKeys.END)).longValue());
			bazaar = bazaarManager.newBazaar(
					bazaarManager.findItem(Identifier.fromValue(jsonObject.getString(JsonKeys.ITEM))), startDate,
					endDate);
			((AbstractPersistable)bazaar)
					.setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return bazaar;
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
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Bazaar.class.isAssignableFrom(type)) {
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
		final Bazaar bazaar;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING))) {
			final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.BAZAAR);
			bazaar = BazaarMessageBodyReaderImpl.read(jsonObject);
		}
		return (T)bazaar;
	}

}
