/*
 * VersionableMessageBodyReader.java
 * Created On: Nov 26, 2016 at 8:10:07 AM
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import javax.ws.rs.ext.MessageBodyReader;

import org.apache.bazaar.version.Versionable;

/**
 * VersionableMessageBodyReader extends {@link MessageBodyReader}
 * 
 * @param <T> The Type parameter
 */
public interface VersionableMessageBodyReader<T extends Versionable> extends MessageBodyReader<T> {

	// declare members

	// declare methods

}
