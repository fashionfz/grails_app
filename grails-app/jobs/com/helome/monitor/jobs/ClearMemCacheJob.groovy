package com.helome.monitor.jobs

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helome.monitor.service.impl.MonitorDataService;

/**
 * 每天晚上00点00分清空MemCached里的监控数据 
 * @author dt
 */
@Component
class ClearMemCacheJob {
	
	@Autowired
	MonitorDataService monitorDataService
	
	static triggers = {
		cron name:'clearMemCacheTrigger', startDelay:10000, cronExpression: '00 00 00 * * ?'
	}
	
	def execute(){
		log.info "Clean Memcached Monitor Data !"
		monitorDataService.clearBaseData();
	}
}
