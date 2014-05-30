package com.helome.monitor.service.impl

import static com.helome.monitor.Constant.IndicatorNameMeta.*

import org.apache.commons.lang.StringUtils

class ApplyService {
	
	static transactional = false
	def monitorDataService
	def memcachedService
	def sessionFactory
	/**
	 * 查询服务器相关的应用数据
	 * @param hostname 主机名
	 * @param port 端口号
	 * @return
	 */
	def queryHostJVMApplicationData(String hostname,String port){
		
		def chartData = [:]
		
		def data_cpu = []
		def data_thread = []
		def data_mem = []
		def data_yg = []
		def data_og = []
		def data_pg = []
		def data_class = []
		
		def categories = []
		
		chartData.cpu = [name:hostname,data:data_cpu]
		chartData.thread = [name:hostname,data:data_thread]
		chartData.mem = [name:hostname,data:data_mem]
		chartData.yg = [name:hostname,data:data_yg]
		chartData.og = [name:hostname,data:data_og]
		chartData.pg = [name:hostname,data:data_pg]
		chartData.clazz = [name:hostname,data:data_class]
		chartData.categories = categories
		
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(hostname)
		def jvms = indicatorName_map?.get(INDICATOR_NAME_JVM)
		jvms?.each{
			if(StringUtils.equals(it.port, port)){
				data_cpu << (it.cpuPct as float)
				data_thread << (it.activedThreadCount as int)
				data_mem << (it.totalMem as int)
				data_yg << (it.youngPct as float)
				data_og << (it.oldPct as float)
				data_pg << (it.permPct as float)
				data_class << (it.loadClassCount as int)
				categories << new StringBuffer(it.time.substring(8)).insert(2, ":").toString()
			}
		}
		
//		def session = sessionFactory.getCurrentSession();
//		SQLQuery sqlQuery = session.createSQLQuery("SELECT j.actived_thread_count,j.cpu_pct,j.load_class_count,truncate(j.old_pct,2) old_pct,truncate(j.perm_pct,2) perm_pct,truncate(j.young_pct,2) young_pct,j.`port`,j.`status`,truncate(j.total_mem/(1024 * 1024),0) total_mem,DATE_FORMAT(j.time,'%H:%i') time FROM monitor_jvm j,server_info b where j.server_info_id = b.id and b.name='${hostname}' and j.`port`=$port ORDER BY j.time asc");
//		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//				.addScalar("cpu_pct", new FloatType()).addScalar("actived_thread_count", new IntegerType())
//				.addScalar("total_mem", new IntegerType()).addScalar("young_pct", new FloatType())
//				.addScalar("old_pct", new FloatType()).addScalar("perm_pct", new FloatType())
//				.addScalar("load_class_count", new IntegerType()).addScalar("time",new StringType())
//		List lists = sqlQuery.list();
//		lists.each{
//			data_cpu << it.cpu_pct
//			data_thread << it.actived_thread_count
//			data_mem << it.total_mem
//			data_yg << it.young_pct
//			data_og << it.old_pct
//			data_pg << it.perm_pct
//			data_class << it.load_class_count
//			categories << it.time
//		}
		chartData
	}
}
