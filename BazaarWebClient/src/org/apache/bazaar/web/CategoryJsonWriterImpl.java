/*
 * CategoryJsonWriterImpl.java
 * Created On: Nov 26, 2016 at 11:09:42 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Category;

/**
 * CategoryJsonWriterImpl implements VersionableJsonWriter
 */
final class CategoryJsonWriterImpl implements VersionableJsonWriter<Category> {

	// declare members

	// declare constructors

	/**
	 * Constructor for CategoryJsonWriterImpl
	 */
	CategoryJsonWriterImpl() {
		super();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.web.VersionableJsonWriter#write(org.apache.bazaar.
	 * Persistable)
	 */
	@Override
	public JsonObject write(final Category category) throws JsonException {
		final JsonObject jsonObject;
		try {
			final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
			jsonBuilder.add(JsonKeys.IDENTIFIER, category.getIdentifier().getValue());
			jsonBuilder.add(JsonKeys.NAME, category.getName());
			jsonBuilder.add(JsonKeys.DESCRIPTION, category.getDescription());
			jsonBuilder.add(JsonKeys.PARENT, category.getParent().getIdentifier().getValue());
			final Set<Category> children = category.getChildren();
			if (!children.isEmpty()) {
				final JsonArrayBuilder jsonBuilder1 = Json.createArrayBuilder();
				for (final Category child : children) {
					final JsonObjectBuilder jsonBuilder2 = Json.createObjectBuilder();
					jsonBuilder2.add(JsonKeys.IDENTIFIER, child.getIdentifier().getValue());
					jsonBuilder2.add(JsonKeys.NAME, child.getName());
					jsonBuilder2.add(JsonKeys.DESCRIPTION, child.getDescription());
					jsonBuilder1.add(jsonBuilder2.build());
				}
				jsonBuilder.add(JsonKeys.CHILDREN, jsonBuilder1.build());
			}
			jsonObject = jsonBuilder.build();
		}
		catch (final BazaarException exception) {
			throw new RestWebServiceException(exception);
		}
		return jsonObject;
	}

}
