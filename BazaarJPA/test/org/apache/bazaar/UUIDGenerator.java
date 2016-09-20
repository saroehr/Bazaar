/*
 * UUIDGenerator.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 26, 2016 at 9:31:15 AM
 */
package org.apache.bazaar;

import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * UUIDGenerator
 */
public final class UUIDGenerator {

	// declare members;
	private final UUID uuid;

	// declare constructors

	/**
	 * Constructor for UUIDGenerator
	 */
	private UUIDGenerator() {
		super();
		this.uuid = UUID.randomUUID();
	}

	// declare methods

	/**
	 * Returns uuid as string
	 * 
	 * @return The uuid as string
	 */
	public @NotNull String getValue() {
		return this.uuid.toString();
	}

	/**
	 * Main execution thread
	 * 
	 * @param args The execution arguments
	 */
	public static void main(@NotNull final String[] args) {
		final UUIDGenerator generator = new UUIDGenerator();
		System.out.println(generator.getValue());

	}

}
