package com.helome.monitor.service.impl

import net.sf.ehcache.Ehcache

import org.hibernate.SQLQuery
import org.hibernate.Session
import org.hibernate.transform.AliasToBeanResultTransformer
import org.hibernate.type.StringType
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache

import com.helome.monitor.Indicator
import com.helome.monitor.ServerIf
import com.helome.monitor.ServerInfo
import com.helome.monitor.ServiceInfo;
import com.helome.monitor.bean.HostBean

class ServerInfoService {
	static transactional = true
	
	@Value("#{grailsCacheManager.getCache(T(com.helome.monitor.Constant).CACHE_UNREACHABLE_NAME)}")
	private Cache unReachableHost
	
	def sessionFactory
	
	def saveServerIp(def parameters){
		def serverIf = null;
		if(parameters.id){
			serverIf = ServerIf.get(parameters.id)
		}else{
			serverIf = new ServerIf();
			serverIf.serverInfo = ServerInfo.get(parameters.serverId);
		}
		serverIf.properties = parameters
		serverIf.save()
	}
	
	def deleteServerIp(def parameters){
		def serverIf = ServerIf.get(parameters.id?:-1)
		serverIf?.delete()
		serverIf
	}
	
	def saveServerInfo(def parameters){
		ServerInfo serverInfo = null
		if(parameters.id){
			serverInfo = ServerInfo.get(parameters.id)
		}else{
			serverInfo = new ServerInfo();
		}
		serverInfo.properties = parameters
		
		def indicators = Indicator.findAllByIdInList(parameters.requestIndicators)
		if(indicators){
			serverInfo.indicators = (indicators as Set)
		}else{
			serverInfo.indicators.clear()
		}
		if(serverInfo.allowedSSH == null) serverInfo.allowedSSH = 0
		serverInfo.save(flush:true)
		if(serverInfo.hasErrors()){
			serverInfo.errors.each {
				log.info it
			}
		}
	}
	
	/**
	 * 清空所有服务器对应支持的指标关系数据
	 * @return
	 */
	def clearAllMonitoringIndicatorsRelation(){
		ServiceInfo.list()*.support = false
		ServerInfo.list()?.each {
			clearMonitoringIndicatorsRelation(it)
		}
	}
	
	/**
	 * 清空指定服务器对应支持的指标关系数据
	 * @param serverInfo
	 * @return
	 */
	def clearMonitoringIndicatorsRelation(ServerInfo serverInfo){
		serverInfo.monitoringIndicators.clear();
	}
	
	/**
	 * 列出所有服务器
	 * @return
	 */
    def listServerInfoAvaliable() {
		def avaliableQuery = ServerInfo.where {
			disabled == 0
		}
		avaliableQuery.list()
    }
	
	def listServerInfoExcludeServiceType(List serviceType){
		ServerInfo.createCriteria().list {
			eq("disabled",0)
			services{
				and{
					not {
						'in' ("type",serviceType)
					}
				}
			}
		}
	}
	
	/**
	 * 依据已配的服务类型列出对应的服务器
	 * @param serviceType
	 * @return
	 */
	def listServerInfoWithServiceType(int serviceType){
		ServerInfo.createCriteria().list {
			eq("disabled",0)
			services{
				eq "type",serviceType
				eq "support",true
			}
		}
	}
	
	def listHostBean(){
		Session session = sessionFactory.getCurrentSession();
		SQLQuery squery = session.createSQLQuery("SELECT s.name AS hostname,i.ip_address AS ip,s.connect_port AS port"
					+" FROM server_info s,server_if i"
					+" WHERE s.enabled = 1"
					+" AND s.id = i.server_info_id"
					+" AND i.type=2"
					+" AND s.connect_port != ''")
					.addScalar("hostname", new StringType()).addScalar("ip", new StringType()).addScalar("port", new StringType())
					.setResultTransformer(new AliasToBeanResultTransformer(HostBean.class));
		squery.list()
	}
	
	def listCantConnectServer(){
		Ehcache ehcache = unReachableHost.getNativeCache();
		def elements = ehcache.getAll(ehcache.getKeys()).values();
		elements*.getObjectValue() as List
	}
	
	def evictCantConnectServerFromCache(long serverID){
		def ips = ServerIf.where {
			type == 2
			serverInfo.id == serverID
		}.list()
		if(ips){
			unReachableHost.evict(ips.get(0).ipAddress)
		}
	}
}
