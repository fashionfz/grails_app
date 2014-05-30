package com.helome.monitor.service.impl

import static com.helome.monitor.Constant.IndicatorNameMeta.*

import com.helome.monitor.utils.MapComparator
import org.hibernate.SQLQuery
import org.hibernate.transform.Transformers
import org.hibernate.type.IntegerType
import org.hibernate.type.StringType

/**
 * 数据库监控数据服务
 * @author dt
 *
 */
class DbService {
	
	static transactional = false
	def sessionFactory
	
	def memcachedService
	/**
	 * 获取指定服务器的mysql监控数据
	 * @param hostname
	 * @return
	 */
    def queryDbData(String hostname) {
		def mysql_cpu = []
		def mysql_mem = []
		def mysql_allconn = []
		def mysql_deadconn = []
		
		def categories = []
		
		def chartData = [categories:categories,deadconn:[name:hostname,data:mysql_deadconn],allconn:[name:hostname,data:mysql_allconn],cpu:[name:hostname,data:mysql_cpu],mem:[name:hostname,data:mysql_mem]]
		
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(hostname)
		def mysql_check = indicatorName_map?.get(INDICATOR_NAME_MYSQL_CHECK)
		mysql_check?.each {
			mysql_allconn << (it.threadcount as int)
			mysql_deadconn << (it.abortedclient as int)
			mysql_cpu << (it.cpupct as double)
			mysql_mem << new BigDecimal(it.mem).divideToIntegralValue(BigDecimal.valueOf(1024)) 
			categories << new StringBuffer(it.time.substring(8)).insert(2, ":").toString()
		}
//		def session = sessionFactory.getCurrentSession();
//		SQLQuery sqlQuery = session.createSQLQuery("SELECT m.cpupct,truncate(m.mem/1024,0) mem,m.threadcount, m.abortedclient,DATE_FORMAT(m.time, '%H:%i') time FROM monitor_mysql_check m, server_info b WHERE b.`name` = '${hostname}' AND b.id = m.server_info_id ORDER BY m.time ASC")
//			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//			.addScalar("cpupct",new FloatType()).addScalar("mem",new IntegerType())
//			.addScalar("threadcount",new IntegerType()).addScalar("abortedclient",new IntegerType())
//			.addScalar("time",new StringType());
//		List lists = sqlQuery.list()
//		lists.each {
//			mysql_allconn << it.threadcount
//			mysql_deadconn << it.abortedclient
//			mysql_cpu << it.cpupct
//			mysql_mem << it.mem
//			categories << it.time
//		}
		chartData
    }
	/**
	 * 获取指定服务器的mysql运行状态
	 * @param hostname
	 * @return
	 */
	def lastRuntimeStatus(String hostname){
		def results = [day:0,hour:0,minute:0,runStatus:-1]
		def mysql_checks = []
		
//		def mysql_check = Mysql_check.last('time')
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(hostname)
		mysql_checks = indicatorName_map?.get(INDICATOR_NAME_MYSQL_CHECK)
		def mysql_check = mysql_checks.get(mysql_checks.size() - 1)
		if(mysql_check){
			def seconds = (mysql_check.uptime) as int
			results.day = seconds / (24*60*60)
			results.hour = seconds % (24*60*60) / (60*60)
			results.minute = seconds % (24*60*60) % (60*60) / 60
			results.runStatus = (mysql_check.status) as int
		}
		results
	}
	
	/**
	 * 查询锁表数据,默认只查指定服务器的前五条锁表情况
	 * @param hostname
	 * @return
	 */
	def queryDbLock(String hostname){
		
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(hostname)
		def mysql_lock = indicatorName_map?.get(INDICATOR_NAME_MYSQL_LOCK)
		if(!mysql_lock) return []
		
		Collections.sort(mysql_lock, new MapComparator("counts"));
		int toIndex = 5;
		if(mysql_lock.size() < toIndex){
			toIndex = mysql_lock.size()
		}
		mysql_lock.subList(0, toIndex)
		
//		def session = sessionFactory.getCurrentSession();
//		SQLQuery sqlQuery = session.createSQLQuery("SELECT DATE_FORMAT(m.time, '%H:%i') time,m.dbt,m.counts FROM server_info b,monitor_mysql_lock m WHERE b.`name` = '${hostname}' AND b.id = m.server_info_id ORDER BY cast(m.counts as UNSIGNED INTEGER) DESC LIMIT 0,5")
//			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//			.addScalar("dbt",new StringType()).addScalar("counts",new IntegerType())
//			.addScalar("time",new StringType());
//		List lists = sqlQuery.list()
	}
	
	def queryExpensiveSql(String hostname){
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(hostname)
		def mysql_checks = indicatorName_map?.get(INDICATOR_NAME_MYSQL_CHECK)
		def doubleComparator = new MapComparator("slowsqlatime")
		doubleComparator.clzz = Double.class
		Collections.sort(mysql_checks, doubleComparator);
		int toIndex = 1;
		toIndex = mysql_checks.size() < toIndex ? mysql_checks.size() : toIndex
		def sublist = mysql_checks.subList(0, toIndex);
		return sublist?.get(0)
//		Mysql_check.first(sort:'slowsqlatime',order:'desc')
	}
	
}
