/*
 * BidJsonWriterImpl.java
 * Created On: Nov 26, 2016 at 11:20:38 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.bazaar.Bid;

/**
 * BidJsonWriterImpl implements {@link VersionableJsonWriter}
 */
final class BidJsonWriterImpl implements VersionableJsonWriter<Bid> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidJsonWriterImpl
	 */
	BidJsonWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.VersionableJsonWriter#write(org.apache.bazaar.
	 * Persistable)
	 */
	@Override
	public JsonObject write(final Bid bid) throws JsonException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, bid.getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.BAZAAR, bid.getBazaar().getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.BIDDER, bid.getBidder().getIdentifier().getValue());
		jsonBuilder.add(JsonKeys.PRICE, bid.getPrice().toString());
		return jsonBuilder.build();
	}

}
