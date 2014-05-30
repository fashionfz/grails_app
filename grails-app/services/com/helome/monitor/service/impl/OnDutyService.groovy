package com.helome.monitor.service.impl

import java.text.SimpleDateFormat

import net.rubyeye.xmemcached.XMemcachedClient

import org.apache.commons.httpclient.ConnectTimeoutException
import org.apache.commons.httpclient.HostConfiguration
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import org.hibernate.Query
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.quartz.Scheduler
import org.quartz.SimpleTrigger
import org.quartz.TriggerKey
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.CollectionUtils

import com.helome.monitor.CheckUrl
import com.helome.monitor.Constant
import com.helome.monitor.Dictionary
import com.helome.monitor.DutyConfig
import com.helome.monitor.DutyMain
import com.helome.monitor.DutyTable
import com.helome.monitor.MonitorMenu
import com.helome.monitor.Role
import com.helome.monitor.User

/**
 * 
 * @ClassName: OnDutyService
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年3月21日 下午4:21:57
 *
 */
class OnDutyService {
	
	@Autowired
	Scheduler scheduler;
	@Autowired
	HttpClient httpClient;
	
	@Autowired
	XMemcachedClient memcachedClient;
	
	
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	SimpleDateFormat all = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");

	def SessionFactory sessionFactory;
	
	int errorNum=3;
	
	def NotifyService notifyService;
	
	/**
	 * 
	  * deleteCheckUrl
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param urlId
	  * @return
	 */
    def deleteCheckUrl(long urlId) {
		def check = CheckUrl.get(urlId);
		check.delete();
    }
	/**
	 * 
	  * saveCheckUrl
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param url
	  * @return
	 */
	def saveCheckUrl(CheckUrl url){
		url.save();
	}
	/**
	 * 
	  * queryDutyMain
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param time
	  * @return
	 */
	def List<DutyMain> queryDutyMain(Date time){
		Session session = sessionFactory.getCurrentSession();
		String hql="from DutyMain m where m.beginDate < ? and m.endDate > ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, time);
		query.setParameter(1, time);
		return query.list();
	}
	/**
	 * 获取已排班的最后结束日期
	 * @return
	 */
	def Date getMaxDutyDate() {
		Session session = sessionFactory.getCurrentSession();
		List list = session.createQuery("select max(t.endDate) from DutyMain t").list();
		Date lastDutyDate = null;
		if (!CollectionUtils.isEmpty(list)) {
			lastDutyDate = list.get(0);
		}
		return lastDutyDate;
	}
	
	/**
	 * 
	  * dutyTabletList
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param id
	  * @return
	 */
	def List<DutyTable> dutyTabletList(int id) {
		def q =DutyTable.where {
			dutyMain.id == id
		}.order("dutyDate").order("sort");
		q.list();
	}
	/**
	 * 
	  * saveConfigRole
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param roleId
	  * @param menuIds
	  * @return
	 */
	def saveConfigRole(String roleId,String menuIds){
		String[] array = menuIds.split(",");
		Role role = Role.get(Long.parseLong(roleId));
		List<MonitorMenu> list = new ArrayList<MonitorMenu>();
		for(String menuId : array){
			if(menuId != null && !"".equals(menuId)){
				list.add(MonitorMenu.get(Long.parseLong(menuId)));
			}
		}
		role.menus =list ;
		role.save();
	}
	
	/**
	 * 
	  * queryByDate
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param time
	  * @return
	 */
	def List<DutyTable> queryByDate(Date time){
		return DutyTable.where {
			dutyDate == time
		}.list();
	}
	
	/**
	 * 
	  * exchange
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param userName
	  * @param dutyId1
	  * @param dutyId2
	  * @return
	 */
	def exchange(String userName,int dutyId1,int dutyId2) {
		
		DutyTable dutyTable1 = DutyTable.get(dutyId1);
		DutyTable dutyTable2 = DutyTable.get(dutyId2);
		def tmpName = dutyTable1.getDutyUser();
		dutyTable1.setDutyUser(dutyTable2.getDutyUser());
		dutyTable1.setExchangeId(dutyId2);
		dutyTable1.save();
		dutyTable2.setDutyUser(tmpName);
		dutyTable2.setExchangeId(dutyId1);
		dutyTable2.save();
		//发送调班通知
		def message = [];
		queryDutyConfig("调班通知"). each{DutyConfig config ->
			message << config.getContext()
		}
		if(message == null || message.size()<1){
			return;
		}
		message = message[0].replace("#user#", userName)
		.replace("#name1#", tmpName)
		.replace("#date1#", df.format(dutyTable1.getDutyDate()))
		.replace("#timerang1#", dutyTable1.getTimeRang())
		.replace("#name2#", dutyTable1.getDutyUser())
		.replace("#date2#", df.format(dutyTable2.getDutyDate()))
		.replace("#timerang2#", dutyTable2.getTimeRang());
		notifyService.notifyMessage(message, Constant.NOTIFY_ON_DUTY);
	}
	
	/**
	 * 
	  * queryDutyConfig
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param name
	  * @return
	 */
	def List<DutyConfig> queryDutyConfig(String name) {
		Session session = sessionFactory.getCurrentSession();
		String hql="from DutyConfig c where c.name = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, name);
		return query.list();
	}
	
	
	/**
	 * 
	  * saveDutyTable
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param beginDate
	  * @param endDate
	  * @param createUser
	  * @param content
	  * @param timeRang
	  * @return
	 */
	def int saveDutyTable(Date beginDate,Date endDate,String createUser,String content,String timeRang) {
		int weekCount = BeteewToWeek(beginDate,endDate);
		String[] rangArray = timeRang.split(",");
		DutyMain dutyMain = new DutyMain();
		dutyMain.setWeekCount(weekCount);
		dutyMain.setRangCount(rangArray.length);
		dutyMain.setBeginDate(beginDate);
		dutyMain.setEndDate(endDate);
		dutyMain.setCreateUser(createUser);
		dutyMain.setCreateTime(new Date());
		dutyMain.setTimeRang(timeRang);
		dutyMain.setName(df.format(beginDate)+"--"+df.format(endDate)+"  班次")
		dutyMain.save();
		//保存子项
		String[] records = content.split("/");
		Calendar cal = Calendar.getInstance();
		for(String record : records) {
			String[] columns = record.split(",");
			DutyTable table = new DutyTable();
			table.setDutyMain(dutyMain);
			table.setDutyDate(df.parse(columns[0]));
			cal.setTime(df.parse(columns[0]));
			table.setWeek(cal.get(Calendar.DAY_OF_WEEK)-1);
			table.setTimeRang(columns[1]);
			table.setDutyUser(columns[2]);
			table.setOtherUser(columns[3]);
			table.setSort(Integer.parseInt(columns[4]));
			table.setExchangeId(0);
			table.save();
		 }
		return dutyMain.getId();
	}
	/**
	 * 
	  * clearConfig
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @return
	 */
	def clearConfig(){
		Session session = sessionFactory.getCurrentSession();
		session.createQuery("delete from DutyConfig").executeUpdate();
	}
	/**
	 * 
	  * BeteewToWeek
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param begin
	  * @param end
	  * @return
	 */
	def BeteewToWeek(Date begin,Date end){
		int result=1;
		Calendar before = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long from = begin.getTime();
		long to = end.getTime();
		before.setTime(begin);
		int week = before.get(Calendar.DAY_OF_WEEK)-1;
		if(week ==0){
			week=7;
		}
		int beteew = (int) ((to - from) / (1000 * 60 * 60 * 24));
		for(int i=7;i<beteew+week;i++){
			if(i%7 == 0)
			{
				result++;
			}
		}
		return result;
	}
	
	/**
	 * 
	  * getDutyMainById
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param pid
	  * @return
	 */
	def getDutyMainById(long pid){
		return DutyMain.get(pid);
	}
	/**
	 * 
	  * saveDutyConfig
	  * TODO(这里描述这个方法适用条件 – 可选)
	  *
	  * @param params
	  * @return
	 */
	def saveDutyConfig(Map params){
		clearConfig();
		DutyConfig config1 = new DutyConfig();
		config1.setName(params.notifyName1);
		config1.setDictionary(Dictionary.get(params.long('notifyWay1')));
		config1.setStatus(params.int('notify1'));
		config1.setContext(params.content1);
		config1.save();
		
		DutyConfig config2 = new DutyConfig();
		config2.setName(params.notifyName2);
		config2.setDictionary(Dictionary.get(params.long('notifyWay2')));
		config2.setStatus(params.int('notify2'));
		config2.setContext(params.content2);
		config2.save();
		
		DutyConfig config3 = new DutyConfig();
		config3.setName(params.notifyName3);
		config3.setDictionary(Dictionary.get(params.long('notifyWay3')));
		config3.setStatus(params.int('notify3'));
		config3.setContext(params.content3);
		config3.save();
		
		DutyConfig config4 = new DutyConfig();
		config4.setName(params.notifyName4);
		config4.setDictionary(Dictionary.get(params.long('notifyWay4')));
		config4.setStatus(params.int('notify4'));
		config4.setContext(params.content4);
		config4.save();
	}
	
	
	
	
	def onDutyJob(){
		Date result = memcachedClient.get(Constant.EXCHANGE_ON_DUTY_TIME);
		if(result == null){
			result = new Date();
			memcachedClient.add(Constant.EXCHANGE_ON_DUTY_TIME, 0, result)
		}
		//先发送消息，后找时间
		String[] sendContext = memcachedClient.get(Constant.EXCHANGE_ON_DUTY_CONTEXT);
		if(sendContext!=null&&sendContext[0]!=null&&!"".equals(sendContext[0])){
			def message=""
			def status = 0
			queryDutyConfig("换班通知"). each{DutyConfig config ->
				message = config.getContext()
				status = config.getStatus()
				if(status==1&&!"".equals(message)){
					def msg = message.replaceAll("#name#", sendContext[0])
					.replace("#phone#", sendContext[2]==null?"":sendContext[2]);
					notifyService.notifyMessage(msg, Constant.NOTIFY_ON_DUTY)
				}
			}
		}
		//找时间
		queryDutyTime();
		modifyTrriger()
	}
	
	def modifyTrriger(){
		try{
			TriggerKey key = new TriggerKey("dutyTrigger");
			SimpleTrigger trigger = scheduler.getTrigger(key);
			trigger.startTime = memcachedClient.get(Constant.EXCHANGE_ON_DUTY_TIME);
			scheduler.rescheduleJob(key, trigger);
			log.info("换班定时任务调度时间更新--->"+df2.format(trigger.startTime));
		}catch(Exception e){
		
		}
		
	}
	/**
	 *
	  * queryDutyTime(查询当日下个值班时段和值班人名称)
	  *
	  * @Title: queryDutyTime
	  * @Description: TODO
	  * @return
	  * @throws
	 */
	def queryDutyTime(){
		Date now = new Date();
		String tmp = simple.format(now);
		getAllMsag(simple.parse(tmp));
	}
	/**
	 *
	  * getAllMsag(查询当日下个值班时段和值班人名称)
	  *
	  * @Title: getAllMsag
	  * @Description: TODO
	  * @param query 当前系统日期
	  * @return
	  * @throws
	 */
	def getAllMsag(Date time){
		Date result= memcachedClient.get(Constant.EXCHANGE_ON_DUTY_TIME);
		String[] sendContext = new String[3];
		List<DutyTable> tables = DutyTable.where {
			dutyDate == time
		}.list();
		Date small = null;
		//对时间已经排序
		for(DutyTable table : tables) {
			Date tmp = df2.parse(simple.format(time)+" "+table.getTimeRang().substring(0, table.getTimeRang().lastIndexOf("-")));
			if(tmp.after(result)){
				small = tmp;
				sendContext[0]=table.getDutyUser();
				sendContext[1]=table.getTimeRang();
				User.list().each {User user ->
					if(user.realname.equals(table.getDutyUser())){
						sendContext[2] = user.phone;
						return;
					}
				}
				break;
			}
		}
		//最小定时时间，如果没找到将返回null
		result = small;
		//如果没找到时间，返回明天凌晨时间，明天定日任务启动查找明天的换班情况
		if(result == null){
			Calendar  cal = Calendar.getInstance();
			cal.setTime(time);
			cal.add(Calendar.DATE,1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			result = cal.getTime();
			//没找到时间，发送内容赋值空
			sendContext[0]=null;
		}
		memcachedClient.set(Constant.EXCHANGE_ON_DUTY_TIME, 0, result)
		memcachedClient.set(Constant.EXCHANGE_ON_DUTY_CONTEXT, 0, sendContext)
	}
	
	def queryCheckUrl(){
		List<Dictionary> dlist = Dictionary.where {
			codeType == 'check_url'
		}.list();
		if(dlist){
			errorNum = dlist.get(0).value;
		}
		List<CheckUrl> ulist = CheckUrl.list();
		for(CheckUrl url : ulist){
			check(url);
		}
	}
	
	
	public void check(CheckUrl url){
		
		String localIp = InetAddress.getLocalHost().getHostAddress().toString();
		
		HostConfiguration config = new HostConfiguration ();
		config.setHost(url.getUrl());
		httpClient.setHostConfiguration(config)
		int code=400;
		GetMethod method
		
		int checkNum = 0;
		if(memcachedClient.get(Constant.MONITOR_CHECK_URL+url.getUrl())){
			checkNum = memcachedClient.get(Constant.MONITOR_CHECK_URL+url.getUrl());
		}
		try{
			method=new GetMethod(url.getUrl());
			code=httpClient.executeMethod(method);
			if(code == 200){
				checkNum=0;
			}else{
				checkNum++;
				if(checkNum >= errorNum){
					log.info("状态码==========="+url.getUrl()+"----"+code);
					String msg = all.format(new Date())+":"+url.getUrl()+"  状态码："+code+" --消息来自"+localIp;
					notifyService.notifyMessage(msg, Constant.NOTIFY_ON_DUTY)
					checkNum=0;
				}
			}
			
		}catch(ConnectTimeoutException e){
			  checkNum++;
			if(checkNum>=errorNum){
				log.info(url.getUrl()+"----"+e.getMessage());
				String msg = all.format(new Date())+":"+url.getUrl()+" 连接超时异常，未建立网络连接  --消息来自"+localIp
				notifyService.notifyMessage(msg, Constant.NOTIFY_ON_DUTY)
				checkNum=0;
			}
		}catch(SocketTimeoutException e){
			  checkNum++;
			if(checkNum>=errorNum){
				log.info(url.getUrl()+"----"+e.getMessage());
				String msg = all.format(new Date())+":"+url.getUrl()+" socket超时异常，未获得http响应  --消息来自"+localIp
				notifyService.notifyMessage(msg, Constant.NOTIFY_ON_DUTY)
				checkNum=0;
			}
		}catch(ConnectException e){
			  checkNum++;
			if(checkNum>=errorNum){
				log.info(url.getUrl()+"----"+e.getMessage());
				String msg = all.format(new Date())+":"+url.getUrl()+" 端口未开放  --消息来自"+localIp
				notifyService.notifyMessage(msg, Constant.NOTIFY_ON_DUTY)
				checkNum=0;
			}
		}catch(UnknownHostException e){
			  checkNum++;
			if(checkNum>=errorNum){
				log.info(url.getUrl()+"----"+e.getMessage());
				String msg = all.format(new Date())+":"+url.getUrl()+" 找不到主机  --消息来自"+localIp
				notifyService.notifyMessage(msg, Constant.NOTIFY_ON_DUTY)
				checkNum=0;
			}
		}catch(Exception e){
			e.printStackTrace();
			 checkNum++;
			if(checkNum>=errorNum){
				log.info(url.getUrl()+"----"+e.getMessage());
				String msg = all.format(new Date())+":"+url.getUrl()+" 未知异常  --消息来自"+localIp
				notifyService.notifyMessage(msg, Constant.NOTIFY_ON_DUTY)
				checkNum=0;
			}
		}finally{
			try{
				method.releaseConnection();
			}catch(Exception e){
			
			}
		}
		memcachedClient.set(Constant.MONITOR_CHECK_URL+url.getUrl(),0,checkNum);
	}
}
