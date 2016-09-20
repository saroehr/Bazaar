/*
 * ImageImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 19, 2016 at 9:28:00 AM
 */
package org.apache.bazaar;

import java.io.InputStream;

import org.apache.bazaar.BazaarException;
import org.apache.bazaar.Image;

/**
 * ImageImpl implements Image to
 * provide a concrete implementation
 */
final class ImageImpl implements Image {
	// declare members
	private static final long serialVersionUID = -557405116691620796L;

	private final String name;
	private final MimeType mimeType;

	// declare constructors
	/**
	 * Constructor for ImageImpl
	 * 
	 * @param name The image name
	 * @param mimeType The MimeType of the image
	 */
	ImageImpl(final String name, final MimeType mimeType) {
		super();
		this.name = name;
		this.mimeType = mimeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Image#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Image#getMimeType()
	 */
	@Override
	public MimeType getMimeType() {
		return this.mimeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Image#getImage()
	 */
	@Override
	public InputStream getImage() throws BazaarException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets image
	 * 
	 * @param inputStream The inputStream to read from
	 */
	void setImage(final InputStream inputStream) throws BazaarException {

	}

}
