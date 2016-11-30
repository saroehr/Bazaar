/*
 * VersionJsonWriterImpl.java
 * Created On: Nov 29, 2016 at 2:43:54 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.Bid;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Category;
import org.apache.bazaar.Item;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.Versionable;

/**
 * VersionJsonWriterImpl implements {@link JsonWriter}
 */
final class VersionJsonWriterImpl implements JsonWriter<Version> {

	// declare members

	// declare constructors

	/**
	 * Constructor for VersionJsonWriterImpl
	 */
	VersionJsonWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.JsonWriter#write(java.lang.Object)
	 */
	@Override
	public JsonObject write(final Version version) throws JsonException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, version.getIdentifier().getValue());
		final Class<? extends Versionable> clazz1 = version.getVersionable().getClass();
		final JsonArrayBuilder jsonBuilder1 = Json.createArrayBuilder();
		final JsonObjectBuilder jsonBuilder2 = Json.createObjectBuilder();
		if (Bazaar.class.isAssignableFrom(clazz1)) {
			jsonBuilder2.add(JsonKeys.BAZAAR, new BazaarJsonWriterImpl().write((Bazaar)version.getVersionable()));
		}
		else if (Bidder.class.isAssignableFrom(clazz1)) {
			jsonBuilder2.add(JsonKeys.BIDDER, new BidderJsonWriterImpl().write((Bidder)version.getVersionable()));
		}
		else if (Bid.class.isAssignableFrom(clazz1)) {
			jsonBuilder2.add(JsonKeys.BID, new BidJsonWriterImpl().write((Bid)version.getVersionable()));
		}
		else if (Category.class.isAssignableFrom(clazz1)) {
			jsonBuilder2.add(JsonKeys.CATEGORY, new CategoryJsonWriterImpl().write((Category)version.getVersionable()));
		}
		else if (Item.class.isAssignableFrom(clazz1)) {
			jsonBuilder2.add(JsonKeys.ITEM, new ItemJsonWriterImpl().write((Item)version.getVersionable()));
		}
		else {
			throw new JsonException(); // TODO localize message
		}
		jsonBuilder.add(JsonKeys.VERSIONABLE, jsonBuilder1.add(jsonBuilder2.build()).build());
		return jsonBuilder.build();
	}

}
