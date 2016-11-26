/*
 * JsonWriter.java
 * Created On: Nov 26, 2016 at 1:16:00 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;

/**
 * JsonWriter
 * 
 * @param <T> The type being written
 */
public interface JsonWriter<T> {

	// declare members

	// declare methods

	/**
	 * Method writes object instance to JsonObject
	 *
	 * @param object The object instance
	 * @return The object as JsonObject
	 * @throws JsonException if the object can not be written
	 */
	public @NotNull JsonObject write(@NotNull final T object) throws JsonException;

}
