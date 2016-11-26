/*
 * ThrowableJsonWriterImpl.java
 * Created On: Nov 26, 2016 at 1:20:33 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;

/**
 * ThrowableJsonWriterImpl implements {@link JsonWriter}
 */
final class ThrowableJsonWriterImpl implements JsonWriter<Throwable> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ThrowableJsonWriterImpl
	 */
	ThrowableJsonWriterImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.JsonWriter#write(java.lang.Object)
	 */
	@Override
	public JsonObject write(final Throwable object) throws JsonException {
		return null;
	}

}
