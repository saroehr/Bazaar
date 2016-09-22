/*
 * EntityManagerFactoryImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 28, 2016 at 8:35:38 PM
 */
package org.apache.bazaar.persistence;

import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.validation.constraints.NotNull;

/**
 * EntityManagerFactoryImpl decorates
 * {@link javax.persistence.EntityManagerFactory}
 */
public final class EntityManagerFactoryImpl implements EntityManagerFactory {

	// declare members

	private static final EntityManagerFactoryImpl INSTANCE = new EntityManagerFactoryImpl(
			Persistence.createEntityManagerFactory(org.apache.bazaar.persistence.config.Configuration.PERSISTENCE_UNIT_NAME));

	private final javax.persistence.EntityManagerFactory entityManagerFactory;

	/**
	 * Constructor for EntityManagerFactoryImpl
	 * @param entityManagerFactory The EntityManagerFactory
	 * instance to be decorated.
	 */
	EntityManagerFactoryImpl(@NotNull final javax.persistence.EntityManagerFactory entityManagerFactory) {
		super();
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * Factory method for obtaining instance.
	 * 
	 * @return Instance of EntityManagerFactoryImpl
	 */
	public static final EntityManagerFactory newInstance() {
		return EntityManagerFactoryImpl.INSTANCE;
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#addNamedEntityGraph(java.lang.String, javax.persistence.EntityGraph)
	 */
	@Override
	public <T> void addNamedEntityGraph(final String name, final EntityGraph<T> graph) {
		this.entityManagerFactory.addNamedEntityGraph(name, graph);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#addNamedQuery(java.lang.String, javax.persistence.Query)
	 */
	@Override
	public void addNamedQuery(final String name, final Query query) {
		this.entityManagerFactory.addNamedQuery(name, query);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#close()
	 */
	@Override
	public void close() {
		this.entityManagerFactory.close();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#createEntityManager()
	 */
	@Override
	public EntityManager createEntityManager() {
		return new EntityManagerImpl(this, this.entityManagerFactory.createEntityManager());
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#createEntityManager(java.util.Map)
	 */
	@Override
	public EntityManager createEntityManager(@SuppressWarnings("rawtypes") final Map map) {
		return new EntityManagerImpl(this, this.entityManagerFactory.createEntityManager(map));
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#createEntityManager(javax.persistence.SynchronizationType)
	 */
	@Override
	public EntityManager createEntityManager(final SynchronizationType synchronizationType) {
		return new EntityManagerImpl(this, this.entityManagerFactory.createEntityManager(synchronizationType));
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#createEntityManager(javax.persistence.SynchronizationType, java.util.Map)
	 */
	@Override
	public EntityManager createEntityManager(final SynchronizationType synchronizationType,
			@SuppressWarnings("rawtypes") final Map map) {
		return new EntityManagerImpl(this, this.entityManagerFactory.createEntityManager(synchronizationType, map));
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#getCache()
	 */
	@Override
	public Cache getCache() {
		return this.entityManagerFactory.getCache();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#getCriteriaBuilder()
	 */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return this.entityManagerFactory.getCriteriaBuilder();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#getMetamodel()
	 */
	@Override
	public Metamodel getMetamodel() {
		return this.entityManagerFactory.getMetamodel();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#getPersistenceUnitUtil()
	 */
	@Override
	public PersistenceUnitUtil getPersistenceUnitUtil() {
		return this.entityManagerFactory.getPersistenceUnitUtil();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return this.entityManagerFactory.getProperties();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.entityManagerFactory.isOpen();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManagerFactory#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(final Class<T> clazz) {
		return this.entityManagerFactory.unwrap(clazz);
	}

}
