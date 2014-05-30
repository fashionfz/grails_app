package com.helome.monitor

import java.text.SimpleDateFormat

import net.rubyeye.xmemcached.XMemcachedClient

import org.apache.commons.lang.StringUtils
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.hibernate.collection.PersistentSet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.CollectionUtils

import com.helome.monitor.jobs.CheckUrlStatusJob
import com.helome.monitor.service.impl.OnDutyService

/**
 * 
 * @ClassName: OnDutyController
 * @Description: 值班管理
 * @author bin.deng@helome.com
 * @date 2014年3月21日 下午3:18:26
 *
 */
class OnDutyController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	OnDutyService onDutyService;
	
	@Autowired
	CheckUrlStatusJob checkUrlStatusJob;
	
	@Autowired
	XMemcachedClient memcachedClient;
	/**
	 * 
	  * deleteCheckUrl()
	  *
	  * @Title: deleteCheckUrl
	  * @Description: TODO
	  * @return
	  * @throws
	 */
	def deleteCheckUrl(){
		onDutyService.deleteCheckUrl(params.long('id'));
		redirect(action:'urlchecklist');
	}
	/**
	 * 
	  * modifyCheckTime(这里用一句话描述这个方法的作用)
	  *
	  * @Title: modifyCheckTime
	  * @Description: TODO
	  * @return
	  * @throws
	 */
	def modifyCheckTime(){
		checkUrlStatusJob.modifyJob(params.long('time'));
	}
	
	def urlchecklist(){

		def result =[]
		CheckUrl.list().each {
			Binding binding = new Binding();
			binding.obj = it;
			int checkNum = 0;
			if(memcachedClient.get(Constant.MONITOR_CHECK_URL+it.url)){
				checkNum = memcachedClient.get(Constant.MONITOR_CHECK_URL+it.url);
			}
			binding.checkNum=checkNum;
			result << binding;
		}
		render(view:'urlchecklist',model:[result:result]);
	}
	/**
	 * 
	  * saveCheckUrl(这里用一句话描述这个方法的作用)
	  *
	  * @Title: saveCheckUrl
	  * @Description: TODO
	  * @return
	  * @throws
	 */
	def saveCheckUrl(){
		String pid = params.id;
		CheckUrl url;
		if (StringUtils.isEmpty(pid)) {
			url = new CheckUrl();
		} else {
			url = CheckUrl.get(Long.parseLong(pid));
		}
		url.setName(params.name);
		url.setUrl(params.url);
		url.setTimeOut(params.long('timeOut'))
		onDutyService.saveCheckUrl(url);
		redirect(action:'urlchecklist');
	}
	/**
	 * 
	  * list
	  *
	  * @Title: list
	  * @Description: 值班管理主页面
	  * @return 返回当前系统日期在内的值班表
	  * @throws
	 */
    def list() { 
		List<DutyMain> list = onDutyService.queryDutyMain(new Date());
		if (CollectionUtils.isEmpty(list)) {
			logger.info("没有包含当前系统日期在内的排班表！");
		} else {
			DutyMain main = list.get(0);
			List<DutyTable> dutys = onDutyService.dutyTabletList(main.getId());
			render(view:'list',model:[dutys:dutys,main:main]);
		}
	}
	/**
	 * 
	  * userView
	  *
	  * @Title: userView
	  * @Description: 用户视图，配置角色所有包含的功能
	  * @return 
	  * @throws
	 */
	def userView() {
		render(view:'userView',model:[roleId:params.roleId]);
	}
	
	/**
	 * 
	  * saveConfigRole
	  *
	  * @Title: saveConfigRole
	  * @Description: 保存角色配置,并更新权限验证的缓存
	  * @return
	  * @throws
	 */
	def saveConfigRole(){
		String roleId = params.roleId;
		if (StringUtils.isEmpty(roleId)) {
			logger.info("角色Id为空，保存失败！");
			render(view:'userView',model:[roleId:params.roleId]);
			return;
		}
		String menuIds = params.menuIds;
		onDutyService.saveConfigRole(roleId,menuIds);
		//更新缓存
		queryAllMenu();
		render(view:'userView',model:[roleId:params.roleId]);
	}
	/**
	 * 
	  * queryAllMenu
	  *
	  * @Title: queryAllMenu
	  * @Description: 更新权限验证缓存
	  * @return
	  * @throws
	 */
	def queryAllMenu(){
		log.info("角色被修改，初始化权限验证角色功能关系信息！");
		Map map = new HashMap();
		def q = MonitorMenu.where {
			id >0;
		}
		for(MonitorMenu menu : q.list()){
			List roleNames = new ArrayList<String>();
			for(Role role : menu.roles){
				roleNames.add(role.name);
			}
			map.put(menu.contollerName+menu.actionName,roleNames);
		}
		memcachedClient.set(Constant.USER_PERMISSION, 0, map)
	}
	/**
	 * 
	  * change
	  *
	  * @Title: change
	  * @Description: 换班保存
	  * @return
	  * @throws
	 */
	def change(){
		def dutyId1 = params.int('dutyTableId1');
		def dutyId2 = params.int('dutyTableId2');
		Subject currentUser = SecurityUtils.getSubject();
		String username = currentUser.getPrincipal();
		onDutyService.exchange(username, dutyId1, dutyId2);
		render(contentType:'application/json'){
			dutyId = params.dutyId
		}
	}
	/**
	 * 
	  * queryMaxDutyDate
	  *
	  * @Title: queryMaxDutyDate
	  * @Description: 异步查询排班最新日期
	  * @return
	  * @throws
	 */
	def queryMaxDutyDate() {
		def lastDutyDate = onDutyService.maxDutyDate
		if(lastDutyDate != null){
			render(contentType:'application/json'){
				data = df.format(lastDutyDate);
				beginDate = params.beginDate
			}
		}
	}
	/**
	 * 
	  * saveDutyTable
	  *
	  * @Title: saveDutyTable
	  * @Description: 保存排班信息
	  * @return
	  * @throws
	 */
	def saveDutyTable() {
		Subject currentUser = SecurityUtils.getSubject();
		String username = currentUser.getPrincipal();
		int saveId = onDutyService.saveDutyTable(df.parse(params.beginDate),
			df.parse(params.endDate),username,params.content,params.timeRang);
		render(contentType:'application/json'){
			dutyId = saveId;
		}
	}
	/**
	 * 
	  * dutyTabletList
	  *
	  * @Title: dutyTabletList
	  * @Description: 根据排班班次查询排班详细信息
	  * @return
	  * @throws
	 */
	def dutyTabletList() {
		def id = params.dutyId;
		if (StringUtils.isEmpty(id)) {
			logger.info("排班班次id为空，查询失败");
			render(view:'list',model:[dutys:dutys,main:main]);
		} else {
			def dutys = onDutyService.dutyTabletList(Integer.parseInt(id));
			def main = onDutyService.getDutyMainById(Integer.parseInt(id));
			render(view:'list',model:[dutys:dutys,main:main]);
		}
	}
	/**
	 * 
	  * saveDutyConfig
	  *
	  * @Title: saveDutyConfig
	  * @Description: 保存值班配置
	  * @return
	  * @throws
	 */
	def saveDutyConfig(){
		onDutyService.saveDutyConfig(params);
		redirect(action:'list');
	}
}
