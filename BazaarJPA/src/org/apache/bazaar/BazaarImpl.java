/*
 * BazaarImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 15, 2016 at 2:49:20 PM
 */
package org.apache.bazaar;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.apache.bazaar.logging.Logger;
import org.apache.bazaar.nls.Messages;
import org.apache.bazaar.persistence.EntityManagerFactory;
import org.apache.bazaar.version.AbstractVersionable;
import org.hibernate.envers.Audited;

/**
 * BazaarImpl implements @{link Bazaar} to provide
 * a concrete implementation.
 *
 */
@Entity(name = org.apache.bazaar.persistence.config.Configuration.BAZAAR_ENTITY_NAME)
@Table(name = org.apache.bazaar.persistence.config.Configuration.BAZAAR_TABLE_NAME, schema = org.apache.bazaar.persistence.config.Configuration.DATABASE_SCHEMA_NAME)
// @PrimaryKeyJoinColumn(name = Configuration.IDENTIFIABLE_COLUMN_NAME)
@Cacheable
@Audited
public class BazaarImpl extends AbstractVersionable implements Bazaar {

	// declare members

	private static final String SELECT_ALL_BIDS_QUERY_PARAMETER_NAME = "bazaar";
	private static final String SELECT_ALL_BIDS_QUERY = "SELECT bid FROM Bid bid WHERE bid.bazaar =:"
			+ BazaarImpl.SELECT_ALL_BIDS_QUERY_PARAMETER_NAME;
	private static final String SELECT_BIDS_BY_BIDDER_QUERY_BIDDER_PARAMETER_NAME = "bidder";
	private static final String SELECT_BIDS_BY_BIDDER_QUERY_BAZAAR_PARAMETER_NAME = "bazaar";
	private static final String SELECT_BIDS_BY_BIDDER_QUERY = "SELECT bid FROM Bid bid where bid.bidder = :"
			+ BazaarImpl.SELECT_BIDS_BY_BIDDER_QUERY_BIDDER_PARAMETER_NAME + " AND bid.bazaar = :"
			+ BazaarImpl.SELECT_BIDS_BY_BIDDER_QUERY_BAZAAR_PARAMETER_NAME;
	@Temporal(TemporalType.DATE)
	@Future
	@Column(name = "STARTDATE", nullable = false, updatable = false)
	private Calendar startDate;
	@Temporal(TemporalType.DATE)
	@Future
	@Column(name = "ENDDATE", nullable = false)
	private Calendar endDate;
	@OneToOne(targetEntity = ItemImpl.class, fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = org.apache.bazaar.persistence.config.Configuration.ITEM_TABLE_NAME, nullable = false, updatable = false, referencedColumnName = org.apache.bazaar.persistence.config.Configuration.IDENTIFIABLE_COLUMN_NAME)
	private Item item;
	@Column(name = "RESERVEPRICE", nullable = true, updatable = false)
	private Double reservePrice;
	@OneToMany(targetEntity = BidImpl.class, mappedBy = "bazaar", fetch = FetchType.EAGER, cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private Set<Bid> bids;

	// declare constructors

	/**
	 * Constructor for BazaarImpl
	 */
	protected BazaarImpl() {
		super();
		this.bids = new HashSet<Bid>(50);
	}

	/**
	 * Constructor for BazaarImpl
	 * 
	 * @param item The item to be Bazaared
	 * @param startDate The Bazaar start date
	 * @param endDate The Bazaar end date
	 */
	BazaarImpl(@NotNull final Item item, @NotNull final Calendar startDate, @NotNull final Calendar endDate) {
		this();
		this.item = item;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor for BazaarImpl
	 * 
	 * @param item The item to be Bazaared
	 * @param startDate The Bazaar start date
	 * @param endDate The Bazaar end date
	 * @param reservePrice The reserve price
	 */
	BazaarImpl(@NotNull final Item item, @NotNull final Calendar startDate, @NotNull final Calendar endDate,
			@NotNull final Double reservePrice) {
		this(item, startDate, endDate);
		this.reservePrice = reservePrice;
	}

	// declare methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#newBid(org.apache.bazaar.Bidder,
	 * java.lang.Double)
	 */
	@Override
	public Bid newBid(final Bidder bidder, final Double price) {
		final BidImpl bid = new BidImpl(bidder, price);
		bid.setBazaar(this);
		this.bids.add(bid);
		return bid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getStartDate()
	 */
	@Override
	public @NotNull Calendar getStartDate() {
		return this.startDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getEndDate()
	 */
	@Override
	public @NotNull Calendar getEndDate() {
		return this.endDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getItem()
	 */
	@Override
	public Item getItem() {
		return this.item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#getReservePrice()
	 */
	@Override
	public Double getReservePrice() {
		return this.reservePrice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#findBids(org.apache.bazaar.Bidder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Bid> findBids(final Bidder bidder)
			throws BazaarException {
		final EntityManager manager = EntityManagerFactory.newInstance().createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Query query = manager.createQuery(BazaarImpl.SELECT_BIDS_BY_BIDDER_QUERY);
		query.setParameter(BazaarImpl.SELECT_BIDS_BY_BIDDER_QUERY_BIDDER_PARAMETER_NAME, bidder);
		query.setParameter(BazaarImpl.SELECT_BIDS_BY_BIDDER_QUERY_BAZAAR_PARAMETER_NAME, this);
		final Set<Bid> bids = new HashSet<Bid>(50);
		try {
			transaction.begin();
			// retrieve persisted bids
			bids.addAll(query.getResultList());
			transaction.commit();
			// must check non persisted bids
			for (final Bid bid : this.bids) {
				if (bid.getBidder().equals(bidder)) {
					bids.add(bid);
				}
			}
		}
		finally {
			manager.close();
		}
		return Collections.unmodifiableSet(bids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.Bazaar#findAllBids()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Bid> findAllBids() {
		final EntityManager manager = EntityManagerFactory.newInstance().createEntityManager();
		final EntityTransaction transaction = manager.getTransaction();
		final Query query = manager.createQuery(BazaarImpl.SELECT_ALL_BIDS_QUERY);
		query.setParameter(BazaarImpl.SELECT_ALL_BIDS_QUERY_PARAMETER_NAME, this);
		final Set<Bid> bids = new HashSet<Bid>(50);
		bids.addAll(this.bids);
		try {
			transaction.begin();
			bids.addAll(query.getResultList());
			transaction.commit();
		}
		finally {
			manager.close();
		}
		return Collections.unmodifiableSet(bids);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.bazaar.AbstractPersistable#persist()
	 */
	@Override
	public void persist() throws BazaarException {
		// we override to enforce endDate for instance
		if (System.currentTimeMillis() > this.endDate.getTimeInMillis()) {
			throw new BazaarExpiredException(Messages.newInstance(Locale.getDefault())
					.findMessage(Messages.BAZAAR_HAS_EXPIRED, new Object[] { this.getIdentifier() }));
		}
		else if (this.startDate.after(this.endDate)) {
			throw new BazaarException(
					Messages.newInstance(Locale.getDefault())
					.findMessage(Messages.BAZAAR_ENDDATE_INVALID,
							new Calendar[] { this.endDate, this.startDate }));
		}
		super.persist();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Logger.toString(this, new Object[] { this.getIdentifier(), this.item, this.startDate, this.endDate,
				this.reservePrice });
	}

}
