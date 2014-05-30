package com.helome.monitor.service.impl

import java.util.regex.Matcher
import java.util.regex.Pattern

import com.helome.monitor.Indicator
import com.helome.monitor.ServerInfo
import com.helome.monitor.ServiceInfo

class StrategyService {
	
	static transactional = true
	def sessionFactory
	Pattern pattern = Pattern.compile("^server\\[(\\d+)\\]\\[\\]")
	Pattern patternApp = Pattern.compile("^app\\[(\\d+)\\]\\[\\]")
	
	def saveMonitoringStragey(key,_value){
		Matcher matcher = pattern.matcher(key)
		Matcher matcherApp = patternApp.matcher(key)
		if(matcher.find()){
			serverMonitoringStragey(_value, matcher.group(1) as long)
		}
		
		if(matcherApp.find()){
			appMonitoringStrage(_value)
		}
	}
	
	def serverMonitoringStragey(_value,long serverid){
		def server = ServerInfo.get(serverid)
		def indicatorIds = _value?.collect{
			it.toLong()
		}
		def indicators = Indicator.findAllByIdInList(indicatorIds)
		server.monitoringIndicators = indicators
		server.save()
	}
	
	def appMonitoringStrage(_value){
		def serviceIds = _value?.collect{
			it.toLong()
		}
		def serviceInfos = ServiceInfo.findAllByIdInList(serviceIds)
		serviceInfos.each {ServiceInfo serviceInfo ->
			serviceInfo.support = true
			serviceInfo.save()
		}
	}
}
