/*
 * ItemJsonWriterImpl.java
 * Created On: Nov 26, 2016 at 11:18:59 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.bazaar.Item;

/**
 * ItemJsonWriterImpl implements {@link VersionableJsonWriter}
 */
final class ItemJsonWriterImpl implements VersionableJsonWriter<Item> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ItemJsonWriterImpl
	 */
	ItemJsonWriterImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.VersionableJsonWriter#write(org.apache.bazaar.
	 * Persistable)
	 */
	@Override
	public JsonObject write(final Item item) throws JsonException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, item.getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.NAME, item.getName());
		jsonBuilder.add(JsonKeys.DESCRIPTION, item.getDescription());
		jsonBuilder.add(JsonKeys.CATEGORY, item.getCategory().getIdentifier().getValue());
		return jsonBuilder.build();
	}

}
