/*
 * EntityManagerImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 28, 2016 at 8:36:01 PM
 */
package org.apache.bazaar.jpa;

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;

/**
 * EntityManagerImpl decorates {@link javax.persistence.EntityManager}
 */
final class EntityManagerImpl implements org.apache.bazaar.jpa.EntityManager {

	// declare members

	private final EntityManagerFactory entityManagerFactory;
	private final EntityManager entityManager;
	// private final ThreadLocal<org.apache.bazaar.jpa.EntityTransaction>
	// transaction = new ThreadLocal<org.apache.bazaar.jpa.EntityTransaction>();

	// declare constructors

	/**
	 * Constructor for EntityManagerImpl
	 * 
	 * @param entityManagerFactory The EntityManagerFactory instance
	 *        used to create this instance
	 * @param entityManager The EntityManager
	 *        instance to be decorated.
	 */
	EntityManagerImpl(@NotNull final EntityManagerFactory entityManagerFactory,
			@NotNull final EntityManager entityManager) {
		super();
		this.entityManagerFactory = entityManagerFactory;
		this.entityManager = entityManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#clear()
	 */
	@Override
	public void clear() {
		this.entityManager.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#close()
	 */
	@Override
	public void close() {
		// check transaction
		// if ((this.transaction.get() != null) &&
		// !this.transaction.get().isActive()) {
		// this.transaction.set(null);
		this.entityManager.close();
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(final Object object) {
		return this.entityManager.contains(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createEntityGraph(java.lang.Class)
	 */
	@Override
	public <T> EntityGraph<T> createEntityGraph(final Class<T> clazz) {
		return this.entityManager.createEntityGraph(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createEntityGraph(java.lang.String)
	 */
	@Override
	public EntityGraph<?> createEntityGraph(final String graphName) {
		return this.entityManager.createEntityGraph(graphName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String)
	 */
	@Override
	public Query createNamedQuery(final String name) {
		return this.entityManager.createNamedQuery(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> TypedQuery<T> createNamedQuery(final String name, final Class<T> resultClass) {
		return this.entityManager.createNamedQuery(name, resultClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.EntityManager#createNamedStoredProcedureQuery(java.lang
	 * .String)
	 */
	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(final String name) {
		return this.entityManager.createNamedStoredProcedureQuery(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String)
	 */
	@Override
	public Query createNativeQuery(final String sql) {
		return this.entityManager.createNativeQuery(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public Query createNativeQuery(final String name, @SuppressWarnings("rawtypes") final Class resultClass) {
		return this.entityManager.createNativeQuery(name, resultClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Query createNativeQuery(final String sql, final String resultSetMapping) {
		return this.entityManager.createNativeQuery(sql, resultSetMapping);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String)
	 */
	@Override
	public Query createQuery(final String query) {
		return this.entityManager.createQuery(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.EntityManager#createQuery(javax.persistence.criteria.
	 * CriteriaQuery)
	 */
	@Override
	public <T> TypedQuery<T> createQuery(final CriteriaQuery<T> criteriaQuery) {
		return this.entityManager.createQuery(criteriaQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.EntityManager#createQuery(javax.persistence.criteria.
	 * CriteriaUpdate)
	 */
	@Override
	public Query createQuery(@SuppressWarnings("rawtypes") final CriteriaUpdate criteriaUpdate) {
		return this.entityManager.createQuery(criteriaUpdate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.EntityManager#createQuery(javax.persistence.criteria.
	 * CriteriaDelete)
	 */
	@Override
	public Query createQuery(@SuppressWarnings("rawtypes") final CriteriaDelete criteriaDelete) {
		return this.entityManager.createQuery(criteriaDelete);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> TypedQuery<T> createQuery(final String sql, final Class<T> resultClass) {
		return this.entityManager.createQuery(sql, resultClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.EntityManager#createStoredProcedureQuery(java.lang.
	 * String)
	 */
	@Override
	public StoredProcedureQuery createStoredProcedureQuery(final String name) {
		return this.entityManager.createStoredProcedureQuery(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.EntityManager#createStoredProcedureQuery(java.lang.
	 * String, java.lang.Class[])
	 */
	@Override
	public StoredProcedureQuery createStoredProcedureQuery(final String name,
			@SuppressWarnings("rawtypes") final Class... resultClasses) {
		return this.entityManager.createStoredProcedureQuery(name, resultClasses);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.EntityManager#createStoredProcedureQuery(java.lang.
	 * String, java.lang.String[])
	 */
	@Override
	public StoredProcedureQuery createStoredProcedureQuery(final String name, final String... resultSetMappings) {
		return this.entityManager.createStoredProcedureQuery(name, resultSetMappings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#detach(java.lang.Object)
	 */
	@Override
	public void detach(final Object object) {
		this.entityManager.detach(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#find(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T> T find(final Class<T> clazz, final Object object) {
		return this.entityManager.find(clazz, object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#find(java.lang.Class,
	 * java.lang.Object, java.util.Map)
	 */
	@Override
	public <T> T find(final Class<T> clazz, final Object object, final Map<String, Object> map) {
		return this.entityManager.find(clazz, object, map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#find(java.lang.Class,
	 * java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockModeType) {
		return this.entityManager.find(entityClass, primaryKey, lockModeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#find(java.lang.Class,
	 * java.lang.Object, javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockModeType,
			final Map<String, Object> properties) {
		return this.entityManager.find(entityClass, primaryKey, lockModeType, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#f@lush()
	 */
	@Override
	public void flush() {
		this.entityManager.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getCriteriaBuilder()
	 */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return this.entityManager.getCriteriaBuilder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getDelegate()
	 */
	@Override
	public Object getDelegate() {
		return this.entityManager.getDelegate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getEntityGraph(java.lang.String)
	 */
	@Override
	public EntityGraph<?> getEntityGraph(final String name) {
		return this.entityManager.getEntityGraph(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getEntityGraphs(java.lang.Class)
	 */
	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(final Class<T> entityClass) {
		return this.entityManager.getEntityGraphs(entityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getEntityManagerFactory()
	 */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManagerFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getFlushMode()
	 */
	@Override
	public FlushModeType getFlushMode() {
		return this.entityManager.getFlushMode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getLockMode(java.lang.Object)
	 */
	@Override
	public LockModeType getLockMode(final Object entity) {
		return this.entityManager.getLockMode(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getMetamodel()
	 */
	@Override
	public Metamodel getMetamodel() {
		return this.entityManager.getMetamodel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return this.entityManager.getProperties();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getReference(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
		return this.entityManager.getReference(entityClass, primaryKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getTransaction()
	 */
	@Override
	public EntityTransaction getTransaction() {
		final org.apache.bazaar.jpa.EntityTransaction entityTransaction;
		// check thread local
		// if (this.transaction.get() != null) {
		// entityTransaction = this.transaction.get();
		// }
		// else {
		try {
			if (org.apache.bazaar.jpa.config.Configuration.TransactionType.LOCAL
					.equals(AbstractEntityTransaction.TRANSACTION_TYPE)) {
				entityTransaction = org.apache.bazaar.jpa.AbstractEntityTransaction
						.newInstance(this.entityManager.getTransaction());
			}
			else if (org.apache.bazaar.jpa.config.Configuration.TransactionType.MANAGED
					.equals(AbstractEntityTransaction.TRANSACTION_TYPE)) {
				final InitialContext initialContext = new InitialContext();
				final UserTransaction userTransaction = (UserTransaction)initialContext
						.lookup(AbstractEntityTransaction.MANAGED_TRANSACTION_NAME);
				entityTransaction = org.apache.bazaar.jpa.AbstractEntityTransaction.newInstance(userTransaction);
			}
			else {
				throw new EntityTransactionException(new IllegalStateException());
			}
			((AbstractEntityTransaction)entityTransaction).setEntityManager(this);
			// this.transaction.set(entityTransaction);
		}
		catch (final NamingException exception) {
			throw new EntityTransactionException(exception);
		}
		// }
		return entityTransaction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#isJoinedToTransaction()
	 */
	@Override
	public boolean isJoinedToTransaction() {
		return this.entityManager.isJoinedToTransaction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.entityManager.isOpen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#joinTransaction()
	 */
	@Override
	public void joinTransaction() {
		this.entityManager.joinTransaction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#lock(java.lang.Object,
	 * javax.persistence.LockModeType)
	 */
	@Override
	public void lock(final Object entity, final LockModeType lockModeType) {
		this.entityManager.lock(entity, lockModeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#lock(java.lang.Object,
	 * javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public void lock(final Object entity, final LockModeType lockModeType, final Map<String, Object> properties) {
		this.entityManager.lock(entity, lockModeType, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#merge(java.lang.Object)
	 */
	@Override
	public <T> T merge(final T type) {
		return this.entityManager.merge(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#persist(java.lang.Object)
	 */
	@Override
	public void persist(final Object object) {
		this.entityManager.persist(object);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(final Object object) {
		this.entityManager.refresh(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object,
	 * java.util.Map)
	 */
	@Override
	public void refresh(final Object object, final Map<String, Object> map) {
		this.entityManager.refresh(object, map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object,
	 * javax.persistence.LockModeType)
	 */
	@Override
	public void refresh(final Object entity, final LockModeType lockModeType) {
		this.entityManager.refresh(entity, lockModeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object,
	 * javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public void refresh(final Object entity, final LockModeType lockModeType, final Map<String, Object> properties) {
		this.entityManager.refresh(entity, lockModeType, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#remove(java.lang.Object)
	 */
	@Override
	public void remove(final Object object) {
		this.entityManager.remove(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#setFlushMode(javax.persistence.
	 * FlushModeType)
	 */
	@Override
	public void setFlushMode(final FlushModeType flushModeType) {
		this.entityManager.setFlushMode(flushModeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#setProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setProperty(final String propertyName, final Object value) {
		this.entityManager.setProperty(propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(final Class<T> clazz) {
		return this.entityManager.unwrap(clazz);
	}

}
