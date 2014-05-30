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

import java.text.SimpleDateFormat

import org.hibernate.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.helome.monitor.Constant
import com.helome.monitor.DutyConfig
import com.helome.monitor.DutyTable
import com.helome.monitor.User
import com.helome.monitor.service.impl.NotifyService
import com.helome.monitor.service.impl.OnDutyService

/**
 * @ClassName: NotifyTodayDuty
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年4月4日 下午4:00:36
 *
 */
@Component
class NotifyTodayDutyJob {

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	NotifyService notifyService;
	@Autowired
	OnDutyService onDutyService;
	@Autowired
	def SessionFactory sessionFactory;
		
	static triggers = {
		cron name:'todayDutyTrigger', cronExpression:"0 0 9 * * ?"
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
		  Date now = new Date();
		  String simple = df.format(now);
		  Date query = df.parse(simple);
		  List<DutyConfig> list = onDutyService.queryDutyConfig("值班通知");
		  for(DutyConfig config : list) {
			  if(1 == config.getStatus()) {
				  getAllMsag(query,config.getContext());
			  }else{
				  log.info("值班通知功能已被关闭，消息不能被发送！");
			  }
			  return;
		  }
		  log.info("未查询到“值班通知”配置信息");
	  }
	  
	  /**
	   *
		* getAllMsag
		*
		* @Title: getAllMsag
		* @Description: 当天待命通知，查询当天排班信息，查询所有值班人员联系信息。
		* @param query 当前系统日期
		* @param message 要发送的消息模板
		* @param way 发送消息的方式1、短信，2、邮件，3、短信+邮件
		* @return
		* @throws
	   */
	  def getAllMsag(Date query,String message){
		  def q = DutyTable.where {
			  dutyDate == query;
		  }
		  List<String[]> tabs = new ArrayList<String[]>();
		  //检查重复数据
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
			  log.info("今日值班无人员安排，没有消息需要发送！");
			  return;
		  }
		  String showCtx ="";
		  for(User user : User.list()){
			  for(String[] array : tabs){
				  if(user.realname.equals(array[0])){
					  showCtx += array[1]+" "+user.realname+","
				  }
			  }
		  }
		  if(!"".equals(showCtx)) {
			  message = message.replace("#content#", showCtx.substring(0, showCtx.length()-1));
			  notifyService.notifyMessage(message, Constant.NOTIFY_ON_DUTY)
		  }
	  }
}
