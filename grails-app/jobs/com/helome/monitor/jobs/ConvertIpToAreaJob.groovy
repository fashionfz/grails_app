/**
 * Project Name:monitor-webapp
 * @Title: NotifyTodayDuty.groovy
 * @Package com.helome.monitor.jobs
 * @Description: TODO
 * Copyright: Copyright (c) 2014 All Rights Reserved. 
 * Company:helome.com
 * 
 * @author bin.deng@helome.com
 * @date 2014年4月4日 下午4:00:36
 * @version V1.0
 */
package com.helome.monitor.jobs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.helome.monitor.service.impl.IpAdrressService

/**
 * @ClassName: NotifyTodayDuty
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年4月4日 下午4:00:36
 *
 */
@Component
class ConvertIpToAreaJob {

	@Autowired
	IpAdrressService ipAdrressService;
		
	static triggers = {
		cron name:'convertIpToAreaTrigger', cronExpression:"0 0 2 * * ?"
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
		  ipAdrressService.convert();
	  }
	  
}
