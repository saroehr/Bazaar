/*
 * IdentifierImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 25, 2016 at 8:33:52 AM
 */
package org.apache.bazaar;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Converter;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;

/**
 * IdentifierImpl implements {@link Identifier} to provide
 * a concrete implementation. This instance generates
 * identifier values based upon usage of the {@link UUID} class.
 */
@Embeddable
@Converter(autoApply = true)
public class IdentifierImpl extends Identifier implements AttributeConverter<IdentifierImpl, String> {

	// declare members

	private static final long serialVersionUID = 1885084516766977953L;

	@Column(name = org.apache.bazaar.persistence.config.Configuration.IDENTIFIABLE_COLUMN_NAME)
	private String identifier;

	// declare constructors

	/**
	 * Constructor for IdentifierImpl
	 */
	public IdentifierImpl() {
		super();
		this.identifier = UUID.randomUUID().toString();
	}

	/**
	 * Constructor for IdentifierImpl
	 * 
	 * @param value The identifier value
	 */
	public IdentifierImpl(@NotNull final String value) {
		super();
		this.identifier = value;
	}

	/**
	 * Constructor for IdentifierImpl
	 * 
	 * @param uuid The {@link UUID} associated
	 *        with instance.
	 */
	private IdentifierImpl(@NotNull final UUID uuid) {
		super();
		this.identifier = uuid.toString();
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Identifier#getValue()
	 */
	@Override
	public String getValue() {
		return this.identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.
	 * Object)
	 */
	@Override
	public @NotNull String convertToDatabaseColumn(@NotNull final IdentifierImpl identifier) {
		return identifier.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.
	 * Object)
	 */
	@Override
	public @NotNull IdentifierImpl convertToEntityAttribute(@NotNull final String identifier) {
		return new IdentifierImpl(UUID.fromString(identifier));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashcode = 31;
		hashcode = hashcode + this.identifier.hashCode();
		return hashcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Identifier)) {
			return false;
		}
		if (((Identifier)object).getValue().equals(this.identifier)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.identifier });
	}

}
