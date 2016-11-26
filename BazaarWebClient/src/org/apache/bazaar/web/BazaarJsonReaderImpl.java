/*
 * BazaarJsonReaderImpl.java
 * Created On: Nov 26, 2016 at 11:36:48 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import java.util.Calendar;

import javax.json.JsonObject;

import org.apache.bazaar.AbstractPersistable;
import org.apache.bazaar.Bazaar;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Identifier;

/**
 * BazaarJsonReaderImpl implements {@link VersionableJsonReader}
 */
final class BazaarJsonReaderImpl implements VersionableJsonReader<Bazaar> {

	// declare members

	// declare constructors

	/**
	 * Constructor for BazaarJsonReaderImpl
	 */
	BazaarJsonReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.VersionableJsonReader#read(javax.json.JsonObject)
	 */
	@Override
	public Bazaar read(final JsonObject jsonObject) throws JsonException {
		final Bazaar bazaar;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			final Calendar startDate = Calendar.getInstance();
			startDate.setTimeInMillis(Long.valueOf(jsonObject.getString(JsonKeys.START)).longValue());
			final Calendar endDate = Calendar.getInstance();
			endDate.setTimeInMillis(Long.valueOf(jsonObject.getString(JsonKeys.END)).longValue());
			bazaar = bazaarManager.newBazaar(
					bazaarManager.findItem(Identifier.fromValue(jsonObject.getString(JsonKeys.ITEM))), startDate,
					endDate);
			((AbstractPersistable)bazaar)
					.setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return bazaar;
	}

}
