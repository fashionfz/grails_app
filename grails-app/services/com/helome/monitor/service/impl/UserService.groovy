package com.helome.monitor.service.impl

import net.rubyeye.xmemcached.XMemcachedClient

import org.springframework.beans.factory.annotation.Autowired

import com.helome.monitor.Constant
import com.helome.monitor.EmailSettings
import com.helome.monitor.User

class UserService {
	static transactional = true
	
	def userGroupService
	def notifyPlotService
	
	@Autowired
	XMemcachedClient memcachedClient;
	
	def deleteUserRelation(User user){
		userGroupService.deleteGroupRelationWithUser(user)
		notifyPlotService.deleteNotifyStrategyRelationWithUser(user)
	}
	
    def deleteUser(User user) {
		deleteUserRelation(user)
		user.delete()
    }
	
	
	def saveEmailConfig(EmailSettings config){
		config.save();
	}
	
	
	def setEmailConfig(){
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
