package com.helome.monitor.jobs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.helome.monitor.service.impl.OnDutyService
/**
 * 
 * @ClassName: DynamicJob
 * @Description: 动态定时任务，完成值班管理换班通知信息功能
 * @author bin.deng@helome.com
 * @date 2014年3月21日 下午2:23:27
 *
 */
@Component
class DynamicJob {
	/**
	 * @Field notifyService 通知服务
	 */
	/**
	 * @Field onDutyService 值班管理服务
	 */
	@Autowired
	OnDutyService onDutyService;
	
	
	/**
	 * @Field sendContext 定时服务发送的内容,需要放到分布式缓存中
	 */

	
	/**运行一次，服务启动后运行*/
    static triggers = {
	  simple name:'dutyTrigger', startDelay:3000, repeatInterval: 3000, repeatCount: 0
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
		onDutyService.onDutyJob();
    }
	

}
