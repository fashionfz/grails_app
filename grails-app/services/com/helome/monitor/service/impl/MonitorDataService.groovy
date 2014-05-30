package com.helome.monitor.service.impl

import static com.helome.monitor.Constant.IndicatorNameMeta.*

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateFormatUtils
import org.hibernate.Query
import org.hibernate.Session
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional

import com.helome.monitor.ServerInfo
import com.helome.monitor.indicators.QueryBean
import com.helome.monitor.service.IMonitorDataService
import com.helome.monitor.service.IServerGroupService
import com.helome.monitor.utils.MapComparator
/**
 * 监测数据服务,提供数据查询
 * @author dt
 */
class MonitorDataService implements IMonitorDataService {
	
	static transactional = false	def sessionFactory
	IServerGroupService serverGroupService
	def memcachedService
	
	def hbaseService
	/**
	 * 存入检测数据
	 * @param ServerInfo
	 * @return
	 */
	@Transactional
	def merge2db(Object object){
		object.save()
		if(object.hasErrors()){
			object.errors.each{
				log.error it
			}
		}
	}
	/**
	 * 查询.一次查询,将查出符合条件的所有指标的值
	 */
	public Binding queryCollectionFromDB(QueryBean query,String entityName,Map metaParam){
		Session hsession = sessionFactory.getCurrentSession();
		String hql="select a from "+entityName+" a";
		String hql2="select count(id) from "+entityName+" a";
		List list = new ArrayList();
		if(query.servergroup){
			hql +=",ServerGroup g where a.serverInfo in elements(g.servers) and g.id=?"
			hql2+=",ServerGroup g where a.serverInfo in elements(g.servers) and g.id=?"
			list.add(Long.parseLong(query.servergroup));
		}else{
			hql += " where 1=1"
			hql2 += " where 1=1"
		}
		if(query.startDate){
			hql+=" and a.time > ?";
			hql2+=" and a.time > ?";
			list.add(query.startDate)
		}
		if(query.endDate){
			hql+=" and a.time < ?";
			hql2+=" and a.time < ?";
			list.add(qeury.endDate)
		}
		if(query.hostname){
			hql+=" and a.serverInfo.name = ?";
			hql2+=" and a.serverInfo.name = ?";
			list.add(query.hostname)
		}
		hql +=" order by a.time desc"
		Query q = hsession.createQuery(hql);
		Query q2 = hsession.createQuery(hql2);
		for(int i=0;i<list.size();i++){
			q.setParameter(i, list.get(i))
			q2.setParameter(i, list.get(i))
		}
		q.setFirstResult(metaParam.int('offset'));
		q.setMaxResults(metaParam.int('max'));
		def results = q.list()
		Binding binding = new Binding()
		binding.results = results
		
		def count = q2.list().get(0);
		binding.totalCount = count
		binding
	}
	
	@Override
	public void clearBaseData() {
		ServerInfo.list().each {
			memcachedService.deleteWithKey(it.name)
		}
	}
	
	/**
	 * 查询监控信息,并调整数据,用于返回给控制层
	 */
	@Override
	public Binding queryMonitorMessages(QueryBean query,String entityName, Map metaParam) {
		// 查询所有监控数据
		def rst = queryCollectionFromDB(query,entityName, metaParam);
		Binding binding = null
		def results = []
		rst.results.each {
			binding = new Binding()
			binding.group = serverGroupService.findGroupsByHostName(it.serverInfo.name)
			binding.object = it
			results << binding.variables
		}
		Binding resultBinding = new Binding()
		resultBinding.results = results
		resultBinding.totalCount = rst.totalCount
		resultBinding
	}
	
	/**
	 * 接收流量趋势图
	 * @return
	 */
	public Map queryAllServerReciveKbps(){
		def finalresults = [:]
		def categories = new TreeSet();
		def series = []    // [${results},...]
		def ifresult = null
		
		// 收集时间点
//		categories = ServerInfo.executeQuery("select distinct DATE_FORMAT(b.time,'%H:%i') from Ifstat b order by b.time")
//		if(!CollectionUtils.isEmpty(categories)){
//			categories.remove(0);
//		}
		
		// 找到需要监控ifstat的服务器们
		def serverInfos = ServerInfo.where{
			monitoringIndicators {
				code == "ifstat"
			}
		}.list()
		
		serverInfos.each {ServerInfo serverInfo ->
			ifresult = queryIfstatAvgPerSecond(serverInfo)
			series << ifresult.rx_data
			categories.addAll ifresult.ifstat_xAxies
		}
		finalresults.series = series
		finalresults.categories = categories
		finalresults
	}
	
	private List cutList(List lists, int topN){
		Collections.sort(lists, new MapComparator("concount"));
		int toIndex = lists.size() > topN ? topN : lists.size()
		return lists.subList(0, toIndex)
	}
	
	public Map queryConTopN(int topN){
		String startRow = DateFormatUtils.format(new Date(), "yyyyMMdd")
		def tcp_list = hbaseService.queryConcountCondition(startRow, 'TCP');
		def udp_list = hbaseService.queryConcountCondition(startRow, "UDP");
		
		def result = ['con_tcp':cutList(tcp_list, topN), 'con_udp':cutList(udp_list, topN)]
		result
		
//		Session hsession = sessionFactory.getCurrentSession();
//		Query hquery = hsession.createQuery('from Concount order by cast(concount as integer) desc');
//		hquery.setFirstResult(0);
//		hquery.setMaxResults(topN < 1 ? 10 : topN);
//		hquery.list()
	}
	/**
	 * 
	  * queryTcpSumCount
	  *2014-03-27 11:17
	  *修改base_data为server_info by dengbin
	  *
	  * @Title: queryTcpSumCount
	  * @Description: 
	  * @param hostname
	  * @param conType
	  * @return
	  * @throws
	 */
	def queryTcpSumCount(String hostname,String conType){
		def concount_categories = []
		def concount_datas = []
		def results = [concount_xAxies:concount_categories,concount_data:[name:hostname,data:concount_datas]]
		
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(hostname)
		def concount = indicatorName_map?.get(INDICATOR_NAME_CONCOUNT)
		
		def preTime = null
		int preCount = 0
		concount?.each {
			if(StringUtils.equals(preTime, it.time)){
				preCount += (it.concount as int)
			}else if(StringUtils.isNotBlank(preTime)){
				int totalCount = preCount
				concount_categories << new StringBuffer(preTime.substring(8)).insert(2, ":").toString()
				concount_datas << totalCount
				
				if(it.concount != null) preCount = (it.concount as int)
				
			}
			preTime = it.time
		}
//		Session hsession = sessionFactory.getCurrentSession();
//		SQLQuery sqlQuery = hsession.createSQLQuery("SELECT DATE_FORMAT(c.time,'%H:%i') as time, sum(c.concount) as concount"
//			+" FROM monitor_concount c, server_info b, server_info_monitoring_indicators r"
//			+" WHERE b.id=r.server_info_id AND r.indicator_id = 6 AND b.id = c.server_info_id AND b.name = '$hostname' AND c.contype = '$conType'"
//			+" GROUP BY c.time ORDER BY c.time asc")
//			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//			.addScalar("time",new StringType())
//			.addScalar("concount",new IntegerType())
//		sqlQuery.list()?.each{
//			concount_categories << it.time
//			concount_datas << it.concount
//		}
		results
	}
	
	def queryCpuUsedPct(ServerInfo serverInfo){
		def cpu_categories = []
		def cpu_datas = []
		def results = [cpu_xAxies:cpu_categories,cpu_data:[name:serverInfo.name,data:cpu_datas]]
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(serverInfo.name)
		def cpus = indicatorName_map?.get(INDICATOR_NAME_CPU)
//		def cpus = serverInfo.cpu
		cpus?.each{
			cpu_categories << new StringBuffer(it.time.substring(8)).insert(2, ":").toString()
			cpu_datas << BigDecimal.valueOf(100 - (Float.valueOf(it.idle?:0))).setScale(2, BigDecimal.ROUND_HALF_UP)
		}
		results
	}
	
	def queryMemUsedPct(ServerInfo serverInfo){
		def mem_categories = []
		def mem_datas = []
		def results = [mem_xAxies:mem_categories,mem_data:[name:serverInfo.name,data:mem_datas]]
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(serverInfo.name)
		def mems = indicatorName_map?.get(INDICATOR_NAME_MEM)
//		def mems = serverInfo.mem
		mems?.each {
			mem_categories << new StringBuffer(it.time.substring(8)).insert(2, ":").toString()
			mem_datas << BigDecimal.valueOf(Float.valueOf(it.usedpct?:0)).setScale(2, BigDecimal.ROUND_HALF_UP)
		}
		results
	}
	
	def queryDiskUsedPct(ServerInfo serverInfo){
		def disk_categories = []
		def disk_datas = []
		def results = [disk_xAxies:disk_categories,disk_data:[name:serverInfo.name,data:disk_datas]]
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(serverInfo.name)
		def disks = indicatorName_map?.get(INDICATOR_NAME_DISK)
//		def disks = serverInfo.disk
		disks?.each{
			disk_categories << new StringBuffer(it.time.substring(8)).insert(2, ":").toString()
			disk_datas << BigDecimal.valueOf(Float.valueOf(it.usedpct?:0)).setScale(2, BigDecimal.ROUND_HALF_UP)
		}
		results
	}
	
	def queryIfstatAvgPerSecond(ServerInfo serverInfo){
		def ifstat_categories = []
		def tx_datas = []
		def rx_datas = []
		def results = [ifstat_xAxies:ifstat_categories,
					   tx_data:[name:serverInfo.name,data:tx_datas],
					   rx_data:[name:serverInfo.name,data:rx_datas]]
		Map indicatorName_map = memcachedService.queryMonitorDataByServerName(serverInfo.name)
		def ifstats = indicatorName_map?.get(INDICATOR_NAME_IFSTAT)
//		def ifstats = serverInfo.ifstat
		def preObj = null
		ifstats?.each {
			if(preObj){
				ifstat_categories << new StringBuffer(it.time.substring(8)).insert(2, ":").toString()
				
				def result = BigDecimal.valueOf(Long.valueOf(it.transmitbyte?:0)).subtract(Long.valueOf(preObj.transmitbyte))
				tx_datas << result.divide(new BigDecimal(1024 * 60), 0, BigDecimal.ROUND_UP).doubleValue()
				
				result = BigDecimal.valueOf(Long.valueOf(it.recivebyte?:0)).subtract(Long.valueOf(preObj.recivebyte))
				rx_datas << result.divide(new BigDecimal(1024 * 60), 0, BigDecimal.ROUND_UP).doubleValue()
			}
			preObj = it
		}
		results
	}
}
