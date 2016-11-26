/*
 * JsonReader.java
 * Created On: Nov 26, 2016 at 1:21:39 PM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;

/**
 * JsonReader declares the methods an implementation must provide
 * 
 * @param <T> The type variable
 */
public interface JsonReader<T> {

	// declare members

	// declare methods

	/**
	 * @param object The JsonObject representing type
	 * @return Instance of type
	 * @throws JsonException if the instance could not be created
	 */
	public @NotNull T read(@NotNull final JsonObject object) throws JsonException;

}
