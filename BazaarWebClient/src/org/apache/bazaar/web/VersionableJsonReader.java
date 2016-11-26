/*
 * VersionableJsonReader.java
 * Created On: Nov 26, 2016 at 11:29:47 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import org.apache.bazaar.version.Versionable;

/**
 * VersionableJsonReader declares the methods an implementation must provide
 *
 * @param <T> The type parameter
 */
public interface VersionableJsonReader<T extends Versionable> extends JsonReader<T> {

	// declare members

	// declare methods

}
