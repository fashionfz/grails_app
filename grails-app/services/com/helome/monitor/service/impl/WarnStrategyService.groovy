package com.helome.monitor.service.impl

import net.rubyeye.xmemcached.XMemcachedClient

import org.springframework.beans.factory.annotation.Autowired
import com.helome.monitor.Constant
import com.helome.monitor.ServerInfo
import com.helome.monitor.User
import com.helome.monitor.WarningStrategy

class WarnStrategyService {
	
	@Autowired
	XMemcachedClient memcachedClient;
	
	static transactional = true
	
    def deleteWarnStrategyRelationWithUser(User user) {
		WarningStrategy.list().each { warnStrategy ->
			warnStrategy.removeFromUsers(user)
		}
    }
	
	
	def initWarnStrategyMemcache(){
		ServerInfo.list().each {
			Set conditions = new HashSet();
			log.info("重新初始化告警策略缓存")
			def c = WarningStrategy.createCriteria()
			def wss = c.list {
				serverWarnConditions {
					eq 'server', it
				}
			}
			wss.each { ws ->
				ws.serverWarnConditions?.each { swc ->
					if (server.id == swc.server.id) {
							conditions.addAll(swc.conditions);
						}
					}
				}
			memcachedClient.set(Constant.MONITOR_WARNING_STRATEGY+it.getName(), 0, conditions)
		}
	}
}
