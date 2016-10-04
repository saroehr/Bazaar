/*
 * DatabaseMessageBodyReaderImpl.java
 * Created On: Sep 30, 2016 at 10:04:22 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web.couchdb;

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

import org.apache.bazaar.web.JsonKeys;
import org.apache.bazaar.web.RestWebServiceException;

/**
 * DatabaseMessageBodyReaderImpl implements MessageBodyReader to provide a
 * reader for database creation operations
 */
@Provider
@Consumes(value = MediaType.APPLICATION_JSON)
public class DatabaseMessageBodyReaderImpl implements MessageBodyReader<String> {

	// declare members

	// declare constructors

	/**
	 * Constructor for DatabaseMessageBodyReaderImpl
	 */
	public DatabaseMessageBodyReaderImpl() {
		super();
	}

	// declare methods

	/**
	 * Method reads bazaar instance from corresponding JsonObject
	 *
	 * @param The {@link JsonObject} representing bazaar
	 * @return The Bazaar instance
	 * @throws RestWebServiceException if the operation could not be completed
	 */
	static String read(@NotNull final JsonObject jsonObject) throws RestWebServiceException {
		return jsonObject.getString(JsonKeys.OK);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyReader#isReadable(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {
		boolean isReadable = false;
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && String.class.isAssignableFrom(type)) {
			isReadable = true;
		}
		return isReadable;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyReader#readFrom(java.lang.Class,
	 * java.lang.reflect.Type, java.lang.annotation.Annotation[],
	 * javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
	 * java.io.InputStream)
	 */
	@Override
	public String readFrom(final Class<String> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream inputStream)
			throws IOException, WebApplicationException {
		final String string;
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING))) {
			final JsonObject jsonObject = Json.createReader(reader).readObject().getJsonObject(JsonKeys.OK);
			string = DatabaseMessageBodyReaderImpl.read(jsonObject);
		}
		return string;
	}

}
