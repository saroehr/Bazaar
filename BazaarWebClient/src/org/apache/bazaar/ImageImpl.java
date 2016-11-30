/*
 * ImageImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 19, 2016 at 9:28:00 AM
 */
package org.apache.bazaar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ImageImpl implements Image to provide a concrete implementation
 */
public final class ImageImpl implements Image {
	// declare members
	private static final long serialVersionUID = -557405116691620796L;

	private final String name;
	private final MimeType mimeType;
	private byte[] image;

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
	 * @see org.apache.bazaar.Image#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Image#getMimeType()
	 */
	@Override
	public MimeType getMimeType() {
		return this.mimeType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.bazaar.Image#getImage()
	 */
	@Override
	public InputStream getImage() throws BazaarException {
		final ByteArrayInputStream inputStream;
		if (this.image != null) {
			inputStream = new ByteArrayInputStream(this.image);
		}
		else {
			inputStream = new ByteArrayInputStream(new byte[] {});
		}
		return inputStream;
	}

	/**
	 * Sets image
	 *
	 * @param inputStream The inputStream to read from
	 * @throws BazaarException if the operation could not be completed
	 */
	void setImage(final InputStream inputStream) throws BazaarException {
		final ByteArrayOutputStream outputStream;
		try {
			outputStream = new ByteArrayOutputStream(Integer.valueOf(org.apache.bazaar.config.Configuration
					.newInstance().getProperty(org.apache.bazaar.config.Configuration.DEFAULT_BYTE_ARRAY_BUFFER_SIZE)));
			while (inputStream.available() != 0) {
				outputStream.write(inputStream.read());
			}
			this.image = outputStream.toByteArray();
		}
		catch (final IOException exception) {
			throw new BazaarException(exception);
		}
	}

}
