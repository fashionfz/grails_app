/**
 * Project Name:monitor-webapp
 * @Title: NotifyTomorrowDutyJob.groovy
 * @Package com.helome.monitor.jobs
 * @Description: TODO
 * Copyright: Copyright (c) 2014 All Rights Reserved. 
 * Company:helome.com
 * 
 * @author bin.deng@helome.com
 * @date 2014年4月4日 下午4:03:13
 * @version V1.0
 */
package com.helome.monitor.jobs

import java.text.SimpleDateFormat

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.helome.monitor.DutyConfig
import com.helome.monitor.DutyTable
import com.helome.monitor.User
import com.helome.monitor.service.impl.NotifyService
import com.helome.monitor.service.impl.OnDutyService

/**
 * @ClassName: NotifyTomorrowDutyJob
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年4月4日 下午4:03:13
 *
 */
@Component
class NotifyTomorrowDutyJob {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	NotifyService notifyService;
	@Autowired
	OnDutyService onDutyService;
		
	static triggers = {
		cron name:'tomorrowDutyTrigger', cronExpression: "0 0 12 * * ?"
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
		  Calendar cal = Calendar.getInstance();
		  cal.add(Calendar.DATE, 1);
		  String simple = df.format(cal.getTime());
		  Date query = df.parse(simple);
		  List<DutyConfig> list = onDutyService.queryDutyConfig("次日通知");
		  for(DutyConfig config : list) {
			  if(	1 == config.getStatus()) {
				  getOneMsag(query,config.getContext(),config.dictionary?.value);
			  }else{
				  log.info("次日通知功能已被关闭，消息不能被发送！");
			  }
			  return;
		  }
		  log.info("未查询到“次日通知”配置信息");
	  }
	  
	  /**
	   *
		* getOneMsag
		*
		* @Title: getOneMsag
		* @Description: 次日通知信息，查询次日排班信息，和次日值班人员的联系信息。
		* @param query 次日系统日期
		* @param message 要发送的消息模板
		* @param way 消息发送的方式1、短信，2、邮件，3、短信+邮件
		* @return
		* @throws
	   */
	  def getOneMsag(Date query,String message,int way){
		  def q = DutyTable.where {
			  dutyDate == query;
		  }
		  List<String[]> tabs = new ArrayList<String[]>();
		  Set<String> check = new HashSet<String>();
		  for(DutyTable table : q.list()) {
			  if(check.add(table.getDutyUser()+table.getTimeRang())){
				  String[] array = new String[2];
				  array[0]=table.getDutyUser();
				  array[1]=table.getTimeRang();
				  tabs.add(array);
			  }
		  }
		  if(tabs.size()<1){
			  log.info("明日值班无人员安排，没有消息需要发送！");
			  return;
		  }
		  List<User> users = new ArrayList<User>();
		  for(User user : User.list()){
			  for(String[] array : tabs){
				  if(user.realname.equals(array[0])){
					  users.add(user);
					  message = message.replace("#name#", user.realname)
					  .replace("#timeRang#", array[1]);
					  notifyService.notifyUser(message, users, way)
					  users.clear();
				  }
			  }
		  }
  
	  }
}
