/*
 * CacheImpl.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 19, 2016 at 11:26:28 PM
 */
package org.apache.bazaar.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.validation.constraints.NotNull;

/**
 * CacheImpl implements Cache to provide
 * a concrete type implementing the Decorator
 * design patter.
 */
final class CacheImpl<K, V> extends Cache<K, V> {

	// declare members

	private final javax.cache.Cache<K, V> cache;

	/**
	 * Constructor for CacheImpl
	 * 
	 * @param cache The javax.cache.Cache instance
	 *        to be decorated
	 */
	CacheImpl(@NotNull final javax.cache.Cache<K, V> cache) {
		super();
		this.cache = cache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#clear()
	 */
	@Override
	public void clear() {
		this.cache.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#close()
	 */
	@Override
	public void close() {
		this.cache.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(final K key) {
		return this.cache.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.cache.Cache#deregisterCacheEntryListener(javax.cache.configuration.
	 * CacheEntryListenerConfiguration)
	 */
	@Override
	public void deregisterCacheEntryListener(final CacheEntryListenerConfiguration<K, V> listener) {
		this.deregisterCacheEntryListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#get(java.lang.Object)
	 */
	@Override
	public V get(final K key) {
		return this.cache.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#getAll(java.util.Set)
	 */
	@Override
	public Map<K, V> getAll(final Set<? extends K> keys) {
		return this.cache.getAll(keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#getAndPut(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V getAndPut(final K key, final V value) {
		return this.cache.getAndPut(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#getAndRemove(java.lang.Object)
	 */
	@Override
	public V getAndRemove(final K key) {
		return this.cache.getAndRemove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#getAndReplace(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V getAndReplace(final K key, final V value) {
		return this.cache.getAndReplace(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#getCacheManager()
	 */
	@Override
	public CacheManager getCacheManager() {
		return this.cache.getCacheManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#getConfiguration(java.lang.Class)
	 */
	@Override
	public <C extends Configuration<K, V>> C getConfiguration(final Class<C> clazz) {
		return this.cache.getConfiguration(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#getName()
	 */
	@Override
	public String getName() {
		return this.cache.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#invoke(java.lang.Object,
	 * javax.cache.processor.EntryProcessor, java.lang.Object[])
	 */
	@Override
	public <T> T invoke(final K key, final EntryProcessor<K, V, T> processor, final Object... objects)
			throws EntryProcessorException {
		return this.cache.invoke(key, processor, objects);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#invokeAll(java.util.Set,
	 * javax.cache.processor.EntryProcessor, java.lang.Object[])
	 */
	@Override
	public <T> Map<K, EntryProcessorResult<T>> invokeAll(final Set<? extends K> keys,
			final EntryProcessor<K, V, T> processor, final Object... objects) {
		return this.cache.invokeAll(keys, processor, objects);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return this.cache.isClosed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#iterator()
	 */
	@Override
	public Iterator<javax.cache.Cache.Entry<K, V>> iterator() {
		return this.cache.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#loadAll(java.util.Set, boolean,
	 * javax.cache.integration.CompletionListener)
	 */
	@Override
	public void loadAll(final Set<? extends K> values, final boolean replace, final CompletionListener listener) {
		this.cache.loadAll(values, replace, listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(final K key, final V value) {
		this.cache.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#putAll(java.util.Map)
	 */
	@Override
	public void putAll(final Map<? extends K, ? extends V> map) {
		this.cache.putAll(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean putIfAbsent(final K key, final V value) {
		return this.cache.putIfAbsent(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.cache.Cache#registerCacheEntryListener(javax.cache.configuration.
	 * CacheEntryListenerConfiguration)
	 */
	@Override
	public void registerCacheEntryListener(final CacheEntryListenerConfiguration<K, V> listener) {
		this.cache.registerCacheEntryListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#remove(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean remove(final K key, final V value) {
		return this.cache.remove(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(final K key) {
		return this.cache.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#removeAll()
	 */
	@Override
	public void removeAll() {
		this.cache.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#removeAll(java.util.Set)
	 */
	@Override
	public void removeAll(final Set<? extends K> values) {
		this.cache.removeAll(values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#replace(java.lang.Object, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public boolean replace(final K key, final V oldValue, final V newValue) {
		return this.cache.replace(key, oldValue, newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#replace(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean replace(final K key, final V value) {
		return this.cache.replace(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.cache.Cache#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(final Class<T> type) {
		return this.cache.unwrap(type);
	}

}
