/*
 * BidJsonReaderImpl.java
 * Created On: Nov 26, 2016 at 12:28:04 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;

import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bid;
import org.apache.bazaar.BidImpl;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;

/**
 * BidJsonReaderImpl implements {@link VersionableJsonReader}
 */
final class BidJsonReaderImpl implements VersionableJsonReader<Bid> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidJsonReaderImpl
	 */
	BidJsonReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.VersionableJsonReader#read(javax.json.JsonObject)
	 */
	@Override
	public Bid read(final JsonObject jsonObject) throws JsonException {
		final Bid bid;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			final Bazaar bazaar = bazaarManager.findBazaar(Identifier.fromValue(jsonObject.getString(JsonKeys.BAZAAR)));
			final Bidder bidder = bazaarManager.findBidder(Identifier.fromValue(jsonObject.getString(JsonKeys.BIDDER)));
			bid = bazaar.newBid(bidder, Double.valueOf(jsonObject.getString(JsonKeys.PRICE)));
			((BidImpl)bid).setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return bid;
	}

}
