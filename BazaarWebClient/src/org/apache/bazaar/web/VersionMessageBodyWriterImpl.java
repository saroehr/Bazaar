/*
 * VersionMessageBodyWriterImpl.java
 * Created On: Nov 26, 2016 at 7:42:54 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Category;
import org.apache.bazaar.Item;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.Versionable;

/**
 * VersionMessageBodyWriterImpl implements {@link VersionableMessageBodyWriter}
 * to provide a writer for Version instances
 */
public final class VersionMessageBodyWriterImpl implements MessageBodyWriter<Version> {

	// declare members

	// declare constructors

	/**
	 * Constructor for VersionMessageBodyWriterImpl
	 */
	public VersionMessageBodyWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object,
	 * java.lang.Class, java.lang.reflect.Type,
	 * java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public long getSize(final Version version, final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType) {
		return -1;
	}

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
		if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType) && Version.class.isAssignableFrom(clazz)) {
			writeable = true;
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
	public void writeTo(final Version version, final Class<?> clazz, final Type type, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, Object> map, final OutputStream outputStream)
			throws IOException, WebApplicationException {
		try (final BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(outputStream, org.apache.bazaar.config.Configuration.DEFAULT_ENCODING));
				final JsonGenerator jsonGenerator = Json.createGenerator(writer)) {
			jsonGenerator.writeStartObject();
			final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
			jsonBuilder.add(JsonKeys.IDENTIFIER, version.getIdentifier().getValue());
			final Class<? extends Versionable> clazz1 = version.getVersionable().getClass();
			if (Bazaar.class.isAssignableFrom(clazz1)) {
				jsonBuilder.add(JsonKeys.VERSIONABLE,
						new BazaarJsonWriterImpl().write((Bazaar)version.getVersionable()));
			}
			else if (Bidder.class.isAssignableFrom(clazz1)) {
				jsonBuilder.add(JsonKeys.VERSIONABLE,
						new BidderJsonWriterImpl().write((Bidder)version.getVersionable()));
			}
			else if (Bid.class.isAssignableFrom(clazz1)) {
				jsonBuilder.add(JsonKeys.VERSIONABLE, new BidJsonWriterImpl().write((Bid)version.getVersionable()));
			}
			else if (Category.class.isAssignableFrom(clazz1)) {
				jsonBuilder.add(JsonKeys.VERSIONABLE,
						new CategoryJsonWriterImpl().write((Category)version.getVersionable()));
			}
			else if (Item.class.isAssignableFrom(clazz1)) {
				jsonBuilder.add(JsonKeys.VERSIONABLE, new ItemJsonWriterImpl().write((Item)version.getVersionable()));
			}
			else {
				throw new JsonException(); // TODO localize message
			}
			jsonGenerator.write(JsonKeys.VERSION, jsonBuilder.build());
			jsonGenerator.writeEnd();
			jsonGenerator.flush();
		}
	}

}
