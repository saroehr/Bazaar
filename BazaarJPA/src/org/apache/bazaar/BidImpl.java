/*
 * BidImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 9, 2016
 */
package org.apache.bazaar;

import javax.persistence.CascadeType;

// declare imports

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;

/**
 * BidImpl implements {@link Bid} to provide a concrete implementation
 */
@Entity(name = org.apache.bazaar.persistence.config.Configuration.BID_ENTITY_NAME)
@Table(name = org.apache.bazaar.persistence.config.Configuration.BID_TABLE_NAME, schema = org.apache.bazaar.persistence.config.Configuration.DATABASE_SCHEMA_NAME)
// @PrimaryKeyJoinColumn(name = Configuration.IDENTIFIABLE_COLUMN_NAME)
public class BidImpl extends AbstractPersistable implements Bid {

	// declare members

	private static final long serialVersionUID = -7524536562095086760L;

	@ManyToOne(targetEntity = BazaarImpl.class, optional = false)
	@JoinColumn(name = "BAZAAR", referencedColumnName = org.apache.bazaar.persistence.config.Configuration.IDENTIFIABLE_COLUMN_NAME, nullable = false, updatable = false)
	private Bazaar bazaar;
	@Column(name = "PRICE")
	private Double price;
	@ManyToOne(targetEntity = BidderImpl.class, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "BIDDER", referencedColumnName = org.apache.bazaar.persistence.config.Configuration.IDENTIFIABLE_COLUMN_NAME, nullable = false, updatable = false)
	private Bidder bidder;

	// declare constructors

	/**
	 * Constructor for BidImpl
	 */
	protected BidImpl() {
		super();
	}

	/**
	 * Constructor for BidImpl
	 * 
	 * @param bidder The bidder associated with bid
	 * @param price The bid amount
	 */
	BidImpl(@NotNull final Bidder bidder, @NotNull final Double price) {
		super();
		this.bidder = bidder;
		this.price = price;
	}

	/**
	 * Sets bazaar bid is associated with
	 * 
	 * @param bid The bid to associate with instance
	 */
	void setBazaar(@NotNull final Bazaar bazaar) {
		this.bazaar = bazaar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bid#getBazaar()
	 */
	@Override
	public Bazaar getBazaar() {
		return this.bazaar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bid#getPrice()
	 */
	@Override
	public Double getPrice() {
		return this.price;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.bazaar.Bid#getBidder()
	 */
	@Override
	public Bidder getBidder() {
		return this.bidder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.getIdentifier(), this.bazaar, this.bidder, this.price });
	}

}
