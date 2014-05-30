/**
 * Project Name:monitor-webapp
 * @Title: CheckUrlStatusJob.groovy
 * @Package com.helome.monitor.jobs
 * @Description: TODO
 * Copyright: Copyright (c) 2014 All Rights Reserved. 
 * Company:helome.com
 * 
 * @author bin.deng@helome.com
 * @date 2014年3月31日 下午4:20:13
 * @version V1.0
 */
package com.helome.monitor.jobs

import java.text.SimpleDateFormat

import org.apache.commons.httpclient.HostConfiguration
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import org.quartz.Scheduler
import org.quartz.SimpleTrigger
import org.quartz.TriggerKey
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.helome.monitor.CheckUrl
import com.helome.monitor.Constant
import com.helome.monitor.Dictionary
import com.helome.monitor.service.impl.NotifyService
import com.helome.monitor.service.impl.OnDutyService

/**
 * @ClassName: CheckUrlStatusJob
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年3月31日 下午4:20:13
 *
 */
@Component
class CheckUrlStatusJob {

	@Autowired
	NotifyService notifyService
	
	@Autowired
	OnDutyService onDutyService
	@Autowired
	Scheduler scheduler;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	

	static triggers = {
		simple name:'checkTrigger', startDelay:5000, repeatInterval: 60000, repeatCount: -1
	  }
	  /**
	   *
		* execute(定时任务运行入口)
		*
		* @Title: execute
		* @Description: TODO
		* @return
		* @throws
	   */
	  def execute() {
		  onDutyService.queryCheckUrl();
	  }
	  /**
	   * 
	    * modifyJob
	    *
	    * @Title: modifyJob
	    * @Description: 修改调度时间
	    * @param time
	    * @return
	    * @throws
	   */
	  def modifyJob(long time){
	        try {
				TriggerKey key = new TriggerKey("checkTrigger");
	            SimpleTrigger trigger = (SimpleTrigger)scheduler.getTrigger(key)
                trigger.repeatInterval=time;
                scheduler.rescheduleJob(key,trigger);
				log.info("URL检查间隔时间更新为 --"+time+"ms")
	        } catch (Exception e) {
				log.error(e.getMessage());
	        }
	  }
}
