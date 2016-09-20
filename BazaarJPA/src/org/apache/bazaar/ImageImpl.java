/*
 * ImageImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 3, 2016 at 1:08:12 PM
 */
package org.apache.bazaar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bazaar.config.Configuration;
import org.apache.bazaar.config.ConfigurationException;
import org.apache.bazaar.logging.Logger;

/**
 * ImageImpl implements {@link Image} and provides
 * a concrete implementation
 */
@Embeddable
@Cacheable
public class ImageImpl implements Image {

	// declare members

	private static final long serialVersionUID = -2891488131406833373L;
	private static final int DEFAULT_BUFFER_SIZE;

	static {
		try {
			final Configuration configuration = org.apache.bazaar.config.Configuration.newInstance();
			DEFAULT_BUFFER_SIZE = Integer
					.valueOf(configuration.getProperty(Configuration.DEFAULT_BYTE_ARRAY_BUFFER_SIZE));
		}
		catch (final ConfigurationException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "NAME", nullable = false, updatable = true)
	private String name;
	@NotNull
	@Column(name = "MIMETYPE", nullable = false, updatable = true)
	@Enumerated(value = EnumType.STRING)
	private MimeType mimeType;
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "IMAGE", nullable = true, updatable = true)
	@Lob
	private Blob image;


	// declare constructors

	/**
	 * Constructor for ImageImpl
	 */
	protected ImageImpl() {
		super();
	}

	/**
	 * Constructor for ImageImpl
	 * 
	 * @param name The image name
	 * @param mimeType The image mimeType
	 */
	ImageImpl(@NotNull @Size(min = 1, max = 255) final String name, @NotNull final MimeType mimeType) {
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

	/**
	 * Sets MimeType for image
	 * 
	 * @param mimeType The MimeType of image
	 */
	void setMimeType(@NotNull final MimeType mimeType) {
		this.mimeType = mimeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Image#getImage()
	 */
	@Override
	public InputStream getImage() throws BazaarException {
		try {
			return new BufferedInputStream(this.image.getBinaryStream());
		}
		catch (final SQLException exception) {
			throw new BazaarException(exception);
		}
	}

	/**
	 * Sets image content
	 * 
	 * @param inputStream The InputStream
	 *        to read from
	 * @throws BazaarException if an error
	 *         is encountered reading data from the stream
	 */
	void setImage(final InputStream inputStream) throws BazaarException {
		final javax.persistence.EntityManager manager = org.apache.bazaar.jpa.EntityManagerFactory.newInstance()
				.createEntityManager();
		final javax.persistence.EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			this.image = ((org.apache.bazaar.jpa.EntityTransaction)transaction).newBlob();
			final OutputStream outputStream = this.image.setBinaryStream(1);
			while (inputStream.available() > 0) {
				final byte[] bytes = new byte[ImageImpl.DEFAULT_BUFFER_SIZE];
				outputStream.write(inputStream.read(bytes));
			}
			transaction.commit();
		}
		catch (final SQLException exception) {
			throw new BazaarException(exception);
		}
		catch (final IOException exception) {
			throw new BazaarException(exception);
		}
		finally {
			manager.close();
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.mimeType == null) ? 0 : this.mimeType.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ImageImpl)) {
			return false;
		}
		final ImageImpl other = (ImageImpl)obj;
		if (this.mimeType != other.mimeType) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.name, this.mimeType });
	}
}

