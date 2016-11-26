/*
 * ItemJsonReaderImpl.java
 * Created On: Nov 26, 2016 at 12:29:34 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;

import org.apache.bazaar.AbstractPersistable;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Identifier;
import org.apache.bazaar.Item;

/**
 * ItemJsonReaderImpl implements {@link VersionableJsonReader}
 */
final class ItemJsonReaderImpl implements VersionableJsonReader<Item> {

	// declare members

	// declare constructors

	/**
	 * Constructor for ItemJsonReaderImpl
	 */
	ItemJsonReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.VersionableJsonReader#read(javax.json.JsonObject)
	 */
	@Override
	public Item read(final JsonObject jsonObject) throws JsonException {
		final Item item;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			item = bazaarManager.newItem();
			((AbstractPersistable)item).setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
			item.setName(jsonObject.getString(JsonKeys.NAME));
			item.setDescription(jsonObject.getString(JsonKeys.DESCRIPTION));
			item.setCategory(bazaarManager.findCategory(Identifier.fromValue(jsonObject.getString(JsonKeys.CATEGORY))));
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return item;
	}

}
