/*
 * Cache.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 17, 2016 at 8:35:11 PM
 */
package org.apache.bazaar.cache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.validation.constraints.NotNull;

/**
 * Cache declares the methods an implementation
 * must provide
 * 
 * @param <K> The Type of Key
 * @param <V> The Type of Value
 */
public abstract class Cache<K, V> implements javax.cache.Cache<K, V> {

	// declare members

	private static final CacheManager CACHE_MANAGER = Caching.getCachingProvider().getCacheManager();

	// declare methods

	/**
	 * Retrieves cache instance by name. If instance
	 * does not exist, it is created. The configuration
	 * used includes "store by value" set to false
	 * and an expiration policy of {@link AccessedExpiryPolicy}
	 * with @{link Duration} of one hour
	 * 
	 * @param name The name of cache to retrieve
	 * @param key The type of key for cache
	 * @param value The type of value for cache
	 * @return The Cache instance with name
	 * @throws CacheException if the instance
	 *         could not be created
	 */
	public static <K, V> Cache<K, V> newInstance(@NotNull final String name, @NotNull final Class<K> key,
			@NotNull final Class<V> value) throws CacheException {
		final MutableConfiguration<K, V> configuration = new MutableConfiguration<K, V>();
		configuration.setStoreByValue(false);
		configuration.setTypes(key, value);
		configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_HOUR));
		return Cache.newInstance(configuration, name, key, value);
	}

	/**
	 * Retrieves cache instance by name with provided configuration.
	 * If instance does not exist, it is created. If instance by
	 * name already exists, it is returned and configuration
	 * is ignored.
	 * 
	 * @param configuration The configuration to be used
	 *        for instance
	 * @param name The name of cache to retrieve
	 * @param key The type of key for cache
	 * @param value The type of value for cache
	 * @return The Cache instance with name
	 * @throws CacheException if the instance
	 *         could not be created
	 */
	public static synchronized <K, V> Cache<K, V> newInstance(
			@NotNull final javax.cache.configuration.Configuration<K, V> configuration, @NotNull final String name,
			@NotNull final Class<K> key, @NotNull final Class<V> value) {
		javax.cache.Cache<K, V> cache = Cache.CACHE_MANAGER.getCache(name, key, value);
		if (cache == null) {
			cache = Cache.CACHE_MANAGER.createCache(name, configuration);
		}
		return new CacheImpl<K, V>(cache);
	}

}
