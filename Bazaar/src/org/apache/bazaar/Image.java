/*
 * Image.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 3, 2016 at 1:04:56 PM
 */
package org.apache.bazaar;

import java.io.InputStream;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Image declares the programming interface implementations
 * must provide.
 */
public interface Image extends Serializable {

	// declare members

	/**
	 * Available Mime Types
	 * MimeType
	 */
	public enum MimeType {

		/**
		 * 
		 */
		JPEG,

		/**
		 * 
		 */
		JPG,

		/**
		 * 
		 */
		GIF,
		/**
		 * 
		 */
		BMP
	}

	// declare methods

	/**
	 * Returns the image name.
	 * 
	 * @return The image name
	 */
	public @NotNull @Size(min = 1, max = 255) String getName();

	/**
	 * Returns the mime-type for image
	 * 
	 * @return The mime-type for image
	 */
	public @NotNull MimeType getMimeType();

	/**
	 * Returns the image
	 * 
	 * @return The image {@link InputStream}
	 * @throws BazaarException if
	 *         the content could not be retrieved
	 */
	public @NotNull InputStream getImage() throws BazaarException;

}
