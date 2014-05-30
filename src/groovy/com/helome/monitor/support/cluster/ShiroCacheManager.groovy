package com.helome.monitor.support.cluster

import com.helome.monitor.service.impl.MemcachedService

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.SoftHashMap;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("shiroCacheManager")
class ShiroCacheManager extends AbstractCacheManager {
	
	@Autowired
	private MemcachedService memcachedService
	
//	@Override
//	public void destroy() throws Exception {
//		//TODO:
//	}
//
//	@Override
//	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
//		if (!StringUtils.hasText(name)) {
//			throw new IllegalArgumentException("Cache name cannot be null or empty.");
//		}
//		
//		return cache;
//	}

	@Override
	protected Cache createCache(String name) throws CacheException {
		Cache cache = new ShiroMemcachedCache(name, memcachedService);
		return cache;
	}

}
