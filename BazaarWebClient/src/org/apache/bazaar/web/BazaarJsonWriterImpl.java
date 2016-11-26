/*
 * BazaarJsonWriterImpl.java
 * Created On: Nov 26, 2016 at 11:01:45 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.bazaar.Bazaar;

/**
 * BazaarJsonWriterImpl implements {@link VersionableJsonWriter}
 */
final class BazaarJsonWriterImpl implements VersionableJsonWriter<Bazaar> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarJsonWriterImpl
	 */
	BazaarJsonWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.VersionableJsonWriter#write(org.apache.bazaar.
	 * Persistable)
	 */
	@Override
	public JsonObject write(final Bazaar bazaar) throws JsonException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, bazaar.getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.ITEM, bazaar.getItem().getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.START, String.valueOf(bazaar.getStartDate().getTimeInMillis()));
		jsonBuilder.add(JsonKeys.END, String.valueOf(bazaar.getEndDate().getTimeInMillis()));
		if (bazaar.getReservePrice() != null) {
			jsonBuilder.add(JsonKeys.RESERVE, String.valueOf(bazaar.getReservePrice()));
		}
		return jsonBuilder.build();
	}

}
