package com.helome.monitor.support.cluster

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheException;

import com.helome.monitor.service.impl.MemcachedService;

class ShiroMemcachedCache<K, V> implements Cache<K, V> {
	private final String MEMCACHED_SHIRO_CACHE = "shiro-cache:";
	
	private String name;
	private MemcachedService memcachedService;
	
	public ShiroMemcachedCache(String name, MemcachedService memcachedService) {
		this.name = name;
		this.memcachedService = memcachedService;
	}

	@Override
	public void clear() throws CacheException {
		
	}

	public String getName() {
       if (name == null)
            return "";
       return name;
	}
	
	@Override
	public V get(K arg0) throws CacheException {
		def val = memcachedService.findWithKey(getCacheKey(arg0))
		val
	}

	@Override
	public Set<K> keys() {
		return [];
	}

	@Override
	public V put(K arg0, V arg1) throws CacheException {
		memcachedService.save(getCacheKey(arg0), arg1)
		return arg1;
	}

	@Override
	public V remove(K arg0) throws CacheException {
		def val = get(arg0)
		memcachedService.deleteWithKey(getCacheKey(arg0))
		return val;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<V> values() {
		return [];
	}

	private String getCacheKey(Object key){
		return this.MEMCACHED_SHIRO_CACHE + getName() + ":" + key;
	}
	
}
