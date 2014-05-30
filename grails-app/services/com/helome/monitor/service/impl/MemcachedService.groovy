package com.helome.monitor.service.impl

import net.rubyeye.xmemcached.XMemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;

class MemcachedService {
	
	@Autowired
	XMemcachedClient memcachedClient
	
	def saveMonitorData(String servername,String indicator,Collection messages){
		Map indicatorName_memcache = null
		synchronized (servername) {
			indicatorName_memcache = queryMonitorDataByServerName(servername)
			if(!indicatorName_memcache){
				indicatorName_memcache = new HashMap();
			}
		}
		if(messages){
			def indicators = indicatorName_memcache.get(indicator)
			if(!indicators){
				indicators = new LinkedList();
				indicatorName_memcache.put(indicator, indicators);
			}
			indicators.addAll messages
		}
		save(servername,indicatorName_memcache)
	}
	
	def queryMonitorDataByServerName(servername){
		findWithKey(servername)
	}
	
	def save(key,val){
		save(key,0,val)
	}
	
	def save(key,exp,val){
		memcachedClient.set(key, exp, val)
	}
	
	def findWithKey(key){
		memcachedClient.get(key)
	}
	
	def deleteWithKey(key){
		memcachedClient.delete(key)
	}
	
}
