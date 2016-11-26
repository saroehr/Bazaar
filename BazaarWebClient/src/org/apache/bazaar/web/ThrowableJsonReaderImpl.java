/*
 * ThrowableJsonReaderImpl.java
 * Created On: Nov 26, 2016 at 1:24:36 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;

/**
 * ThrowableJsonReaderImpl implements {@link JsonReader}
 */
final class ThrowableJsonReaderImpl implements JsonReader<Throwable> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ThrowableJsonReaderImpl
	 */
	ThrowableJsonReaderImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.JsonReader#read(javax.json.JsonObject)
	 */
	@Override
	public Throwable read(final JsonObject object) throws JsonException {
		return null;
	}

}
