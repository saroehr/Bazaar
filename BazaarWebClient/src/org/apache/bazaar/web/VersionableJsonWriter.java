/*
 * VersionableJsonWriter.java
 * Created On: Nov 26, 2016 at 10:52:56 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import org.apache.bazaar.version.Versionable;

/**
 * VersionableJsonWriter declares the methods that must be implemented
 * 
 * @param <T> The type parameter
 */
public interface VersionableJsonWriter<T extends Versionable> extends JsonWriter<T> {

	// declare members

	// declare methods

}
