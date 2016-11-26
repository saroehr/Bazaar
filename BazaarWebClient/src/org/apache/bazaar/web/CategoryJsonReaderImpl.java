/*
 * CategoryJsonReaderImpl.java
 * Created On: Nov 26, 2016 at 12:28:47 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;

import org.apache.bazaar.AbstractPersistable;
import org.apache.bazaar.BazaarException;
import org.apache.bazaar.BazaarManager;
import org.apache.bazaar.Category;
import org.apache.bazaar.Identifier;

/**
 * CategoryJsonReaderImpl implements {@link VersionableJsonReader}
 */
final class CategoryJsonReaderImpl implements VersionableJsonReader<Category> {

	// declare members

	// declare constructors

	/**
	 * Constructor for CategoryJsonReaderImpl
	 */
	CategoryJsonReaderImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.bazaar.web.VersionableJsonReader#read(javax.json.JsonObject)
	 */
	@Override
	public Category read(final JsonObject jsonObject) throws JsonException {
		final Category category;
		try {
			final BazaarManager bazaarManager = BazaarManager.newInstance();
			final Identifier rootIdentifier = Identifier.fromValue(org.apache.bazaar.config.Configuration.newInstance()
					.getProperty(org.apache.bazaar.config.Configuration.ROOT_CATEGORY_IDENTIFIER));
			if (rootIdentifier.equals(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)))) {
				category = bazaarManager.findRootCategory();
			}
			else {
				category = bazaarManager.newCategory();
				((AbstractPersistable)category)
						.setIdentifier(Identifier.fromValue(jsonObject.getString(JsonKeys.IDENTIFIER)));
				category.setName(jsonObject.getString(JsonKeys.NAME));
				category.setDescription(jsonObject.getString(JsonKeys.DESCRIPTION));
				if (rootIdentifier.equals(Identifier.fromValue(jsonObject.getString(JsonKeys.PARENT)))) {
					category.setParent(bazaarManager.findRootCategory());
				}
				else {
					category.setParent(
							bazaarManager.findCategory(Identifier.fromValue(jsonObject.getString(JsonKeys.PARENT))));
				}
			}
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return category;
	}

}
