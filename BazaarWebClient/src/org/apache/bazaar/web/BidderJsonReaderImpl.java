/*
 * BidderJsonReaderImpl.java
 * Created On: Nov 26, 2016 at 12:27:27 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;

import org.apache.bazaar.AbstractPersistable;
import org.apache.bazaar.Address;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Name;
import org.apache.bazaar.State;

/**
 * BidderJsonReaderImpl implemenents {@link VersionableJsonReader}
 */
final class BidderJsonReaderImpl implements VersionableJsonReader<Bidder> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidderJsonReaderImpl
	 */
	BidderJsonReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.VersionableJsonReader#read(javax.json.JsonObject)
	 */
	@Override
	public Bidder read(final JsonObject jsonObject) throws JsonException {
		final Bidder bidder;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			bidder = bazaarManager.newBidder();
			((AbstractPersistable)bidder)
					.setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
			final Name name = bazaarManager.newName();
			JsonObject jsonObject1 = jsonObject.getJsonObject(JsonKeys.NAME);
			name.setFirstName(jsonObject1.getString(JsonKeys.FIRST_NAME));
			name.setLastName(jsonObject1.getString(JsonKeys.LAST_NAME));
			bidder.setName(name);
			jsonObject1 = jsonObject.getJsonObject(JsonKeys.BILLING_ADDRESS);
			final Address billingAddress = bazaarManager.newAddress();
			billingAddress.setStreet(jsonObject1.getString(JsonKeys.STREET));
			billingAddress.setCity(jsonObject1.getString(JsonKeys.CITY));
			billingAddress.setState(State.valueOf(jsonObject1.getString(JsonKeys.STATE)));
			billingAddress.setZipcode(Integer.valueOf(jsonObject1.getString(JsonKeys.ZIPCODE)));
			bidder.setBillingAddress(billingAddress);
			jsonObject1 = jsonObject.getJsonObject(JsonKeys.SHIPPING_ADDRESS);
			final Address shippingAddress = bazaarManager.newAddress();
			shippingAddress.setStreet(jsonObject1.getString(JsonKeys.STREET));
			shippingAddress.setCity(jsonObject1.getString(JsonKeys.CITY));
			shippingAddress.setState(State.valueOf(jsonObject1.getString(JsonKeys.STATE)));
			shippingAddress.setZipcode(Integer.valueOf(jsonObject1.getString(JsonKeys.ZIPCODE)));
			bidder.setShippingAddress(shippingAddress);
		}
		catch (final BazaarException exception) {
			throw new JsonException(exception);
		}
		return bidder;
	}

}
