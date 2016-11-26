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

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.bazaar.Bazaar;

/**
 * BazaarMessageBodyReaderImpl extends AbstractMessageBodyReader to provide a
 * concrete implementation for handling {@link Bazaar} processing
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public final class BazaarMessageBodyReaderImpl implements VersionableMessageBodyReader<Bazaar> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarMessageBodyReaderImpl
	 */
	public BazaarMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
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
	 * @see
	 * org.apache.bazaar.web.AbstractMessageBodyReader#readFrom(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
	 * java.io.InputStream)
	 */
	@Override
	public Bazaar readFrom(final Class<Bazaar> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream inputStream)
			throws IOException, WebApplicationException {
		final Bazaar bazaar;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING))) {
			final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.BAZAAR);
			bazaar = new BazaarJsonReaderImpl().read(jsonObject);
		}
		return bazaar;
	}

}
