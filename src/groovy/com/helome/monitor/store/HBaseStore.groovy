package com.helome.monitor.store

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ClassUtils

import com.helome.monitor.rabbitMQ.recieve.AbstractRecivedMessageMetadata
import com.helome.monitor.service.impl.HbaseService

@Component("hbaseStore")
class HBaseStore extends AbstractRecivedMessageMetadata {
	
	@Autowired
	HbaseService hbaseService;
	
	@Override
	public void run() {
		def indicatorName = ClassUtils.getShortName(getElementsType()).toLowerCase()
		def messages = getMessages()
		hbaseService.saveMonitorBaseData(indicatorName, messages);
	}
}
