/*
 * BidderJsonWriterImpl.java
 * Created On: Nov 26, 2016 at 11:17:07 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.bazaar.Address;
import org.apache.bazaar.Bidder;
import org.apache.bazaar.Name;

/**
 * BidderJsonWriterImpl implements {@link VersionableJsonWriter}
 */
final class BidderJsonWriterImpl implements VersionableJsonWriter<Bidder> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BidderJsonWriterImpl
	 */
	BidderJsonWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.VersionableJsonWriter#write(org.apache.bazaar.
	 * Persistable)
	 */
	@Override
	public JsonObject write(final Bidder bidder) throws JsonException {
		final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add(JsonKeys.IDENTIFIER, bidder.getIdentifier().getValue());
		final Name name = bidder.getName();
		JsonObjectBuilder jsonBuilder1 = Json.createObjectBuilder();
		jsonBuilder1.add(JsonKeys.FIRST_NAME, name.getFirstName());
		jsonBuilder1.add(JsonKeys.LAST_NAME, name.getLastName());
		jsonBuilder.add(JsonKeys.NAME, jsonBuilder1.build());
		final Address billingAddress = bidder.getBillingAddress();
		jsonBuilder1 = Json.createObjectBuilder();
		jsonBuilder1.add(JsonKeys.STREET, billingAddress.getStreet());
		jsonBuilder1.add(JsonKeys.CITY, billingAddress.getCity());
		jsonBuilder1.add(JsonKeys.STATE, billingAddress.getState().name());
		jsonBuilder1.add(JsonKeys.ZIPCODE, String.valueOf(billingAddress.getZipcode()));
		jsonBuilder.add(JsonKeys.BILLING_ADDRESS, jsonBuilder1.build());
		final Address shippingAddress = bidder.getShippingAddress();
		jsonBuilder1 = Json.createObjectBuilder();
		jsonBuilder1.add(JsonKeys.STREET, shippingAddress.getStreet());
		jsonBuilder1.add(JsonKeys.CITY, shippingAddress.getCity());
		jsonBuilder1.add(JsonKeys.STATE, shippingAddress.getState().name());
		jsonBuilder1.add(JsonKeys.ZIPCODE, String.valueOf(shippingAddress.getZipcode()));
		jsonBuilder.add(JsonKeys.SHIPPING_ADDRESS, jsonBuilder1.build());
		return jsonBuilder.build();
	}

}
