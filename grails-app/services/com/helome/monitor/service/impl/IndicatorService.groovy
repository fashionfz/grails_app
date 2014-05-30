package com.helome.monitor.service.impl

import com.helome.monitor.Indicator
import com.helome.monitor.ServerInfo;
import com.helome.monitor.ServiceInfo;

class IndicatorService {
	
	static transactional = true
	
    def saveIndicator(Indicator indicator) {
		indicator.save()
		if(indicator.hasErrors()){
			indicator.errors.each {
				log.info(it)
			}
		}
    }
	
	/**
	 * 清除此指标的关系数据
	 * @param indicator
	 * @return
	 */
	def deleteIndicatorRelation(Indicator indicator){
		indicator.servers?.clear();
		
		ServerInfo.list().each {ServerInfo serverInfo->
			serverInfo.removeFromIndicators(indicator)
			serverInfo.removeFromMonitoringIndicators(indicator)
		}
	}
	
	def deleteIndicator(Indicator indicator){
		deleteIndicatorRelation(indicator)
		indicator.delete()
	}
}
