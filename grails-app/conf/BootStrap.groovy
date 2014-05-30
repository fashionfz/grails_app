import grails.util.Environment
import net.rubyeye.xmemcached.XMemcachedClient

import org.apache.shiro.realm.Realm
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.web.context.support.WebApplicationContextUtils

import com.helome.monitor.Constant
import com.helome.monitor.EmailSettings
import com.helome.monitor.MonitorMenu
import com.helome.monitor.Role

class BootStrap {
	
	def ctx
	
	def grailsApplication
	
    def init = { servletContext ->
		
		ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext)
		
		DefaultWebSecurityManager dwsm = ctx.getBean(DefaultWebSecurityManager.class)
		dwsm.setRealm(ctx.getBean(Realm.class))
		
//		def scheduler = ctx.getBean(Scheduler.class)
//		def param = InetAddress.getLocalHost().getHostAddress().toString();
//		new Thread(new QuartzZookeeper(grailsApplication.config.zookeeper.connectString, scheduler, param), "Quartz Zookeeper").start();
		
		switch(Environment.current){
			case Environment.PRODUCTION:
				// 预先清理已存数据库的监测数据
//				ctx.monitorDataService.clearBaseData()
				break
		}
		
		// 清空Memcache里的监控数据
//		ctx.monitorDataService.clearBaseData()
		
//		ctx.getBean(SimpleMessageListenerContainer.class).start()
		
		// 服务器连通检查阻塞队列监听
//		ctx.checkerLauncher.startCheck()
		queryAllMenu();
		setEmailConfig();
    }
	
	

	def queryAllMenu(){
		log.info("系统启动，初始化权限验证角色功能关系信息！");
		Map map = new HashMap();
		
		XMemcachedClient memcachedClient = ctx.getBean("memcachedClient");
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
	
	
	def setEmailConfig(){
		XMemcachedClient memcachedClient = ctx.getBean("memcachedClient");
		Map config = new HashMap();
		EmailSettings.list().each {EmailSettings dic ->
			config.put("host", dic.smtpServer);
			config.put("port", dic.smtpPort);
			config.put("username", dic.username);
			config.put("password", dic.password);
			config.put("props", dic.props);
			config.put("disabled", dic.enabled);
		}
		memcachedClient.set(Constant.MONITOR_EMAIL_CONFIG, 0, config);
	}
}
