/*
 * VersionJsonReaderImpl.java
 * Created On: Nov 29, 2016 at 2:47:00 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.version.Version;
import org.apache.bazaar.version.VersionImpl;
import org.apache.bazaar.version.Versionable;

/**
 * VersionJsonReaderImpl implements {@link JsonReader}
 */
final class VersionJsonReaderImpl implements JsonReader<Version> {

	// declare members

	// declare constructors

	/**
	 * Constructor for VersionJsonReaderImpl
	 */
	VersionJsonReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.JsonReader#read(javax.json.JsonObject)
	 */
	@Override
	public Version read(final JsonObject jsonObject) throws JsonException {
		final Version version;
		try {
			final Identifier identifier = Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER));
			final JsonArray jsonArray = jsonObject.getJsonArray(JsonKeys.VERSIONABLE);
			final JsonObject jsonObject1 = jsonArray.getJsonObject(0);
			final Versionable versionable;
			if (jsonObject1.containsKey(JsonKeys.BAZAAR)) {
				versionable = new BazaarJsonReaderImpl().read(jsonObject1.getJsonObject(JsonKeys.BAZAAR));
			}
			else if (jsonObject1.containsKey(JsonKeys.BIDDER)) {
				versionable = new BidderJsonReaderImpl().read(jsonObject1.getJsonObject(JsonKeys.BIDDER));
			}
			else if (jsonObject1.containsKey(JsonKeys.BID)) {
				versionable = new BidJsonReaderImpl().read(jsonObject1.getJsonObject(JsonKeys.BID));
			}
			else if (jsonObject1.containsKey(JsonKeys.CATEGORY)) {
				versionable = new CategoryJsonReaderImpl().read(jsonObject1.getJsonObject(JsonKeys.CATEGORY));
			}
			else if (jsonObject1.containsKey(JsonKeys.ITEM)) {
				versionable = new ItemJsonReaderImpl().read(jsonObject1.getJsonObject(JsonKeys.ITEM));
			}
			else {
				throw new JsonException(); // TODO localize message
			}
			version = new VersionImpl(identifier, versionable);
		}
		catch (final BazaarException exception) {
			throw new JsonException(exception);
		}
		return version;
	}

}
