package com.helome.monitor.store

import static com.helome.monitor.Constant.IndicatorNameMeta.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ClassUtils

import com.helome.monitor.rabbitMQ.recieve.AbstractRecivedMessageMetadata
import com.helome.monitor.service.impl.MemcachedService

@Component("memcacheStore")
class MemcacheStore extends AbstractRecivedMessageMetadata {
	
	@Autowired
	MemcachedService memcachedService
	
	@Override
	public void run() {
		def servername = getServerName()
		def indicatorName = ClassUtils.getShortName(getElementsType()).toLowerCase()
		def messages = getMessages()
		
		memcachedService.saveMonitorData(servername,indicatorName,messages)
		
	}

}
