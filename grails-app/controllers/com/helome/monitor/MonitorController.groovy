package com.helome.monitor

import static com.helome.monitor.Constant.IndicatorNameMeta.*
import grails.converters.JSON

import java.text.SimpleDateFormat

import org.apache.commons.lang.time.DateUtils

import com.helome.monitor.service.IAlarmService
import com.helome.monitor.service.IMonitorDataService
import com.helome.monitor.service.IOnlineUsersService
import com.helome.monitor.service.IServerGroupService
import com.helome.monitor.service.impl.CapabilityService
import com.helome.monitor.service.impl.WebCountService
import com.helome.monitor.utils.DateStringHelper
import com.helome.monitor.vo.WarningItem

/**
 * 监控界面控制器类，报错监控界面数据读取，告警信息查询，监控信息查询和监控主机状态查询
 */
class MonitorController {

	IMonitorDataService monitorDataService
    IAlarmService alarmService
    IServerGroupService serverGroupService
	IOnlineUsersService onlineUsersService
	
	WebCountService webCountService

	CapabilityService capabilityService;
	def applyService
	def serverInfoService
	def dbService
	
    /**
     * 查询监控界面包含的信息，并传到前台生成图片
     */
    def index() {
		def alarmPartion = alarmService.queryAlarmPartion() as JSON
		def alarmTopN = alarmService.queryAlarmTopN(10)
		def alarmLatestN = alarmService.alarmLatestForController(10)
		def rx = monitorDataService.queryAllServerReciveKbps() as JSON
		def connN = monitorDataService.queryConTopN(10)
		render(view:'index',model:[alarmTop:alarmTopN,alarmLatest:alarmLatestN,alarmPartion:alarmPartion,rx:rx,connN:connN])
    }

    /**
     * 查询告警信息
     * @return
     */
    def warnings() {
		params.max = params.max?:10
		params.offset = params.offset?:0
		def res = alarmService.query(params)
		def datas = []

		res.results?.each { alarm ->
			def item = new WarningItem(alarmTime: DateStringHelper.date2String(alarm.alarmTime,'yyyy-MM-dd HH:mm'))
			def server = ServerInfo.get(alarm.observedId)
			item.serverName = server.name
			def serverIp = ""
			server.ifs?.each { ip ->
				if (ip.type == 2)
					serverIp = ip.ipAddress
			}
			item.serverIp = serverIp
			def groupNames = ""
			if (!params.serverGroup) {
				def groups = serverGroupService.findGroupsByHostName(server.name)
				groups.eachWithIndex { def entry, int i ->
					groupNames += entry.name
					if (i < (groups.size() - 1))
						groupNames += ","
				}
			} else {
				groupNames = ServerGroup.get(params.serverGroup)?.name
			}
			item.serverGroupName = groupNames
			item.indicatorName = alarm.indicatorWarnCondition.indicator.name
			String expressionValue = alarm.indicatorWarnCondition.expression
			item.expressionValue = expressionValue.replace("#[value]", alarm.value)
			item.indicatorWarnConditionName = alarm.indicatorWarnCondition.name
			datas << item
		}
		[total:res.totalCount,rows:datas]
    }

    /**
     * 监控信息查询
     * @return
     */
    def messages() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar startDate = Calendar.getInstance();
		if(params.startDate){
			startDate.setTime(df.parse(params.startDate))
		}else{
			startDate.set(Calendar.HOUR_OF_DAY, 0)
			startDate.set(Calendar.MINUTE, 0)
		}
		Calendar endDate = Calendar.getInstance();
		if(params.endDate){
			endDate.setTime(df.parse(params.endDate))
		}else{
			endDate.set(Calendar.HOUR_OF_DAY, 23)
			endDate.set(Calendar.MINUTE, 59)
		}
		
		params.startDate = df.format(startDate.getTime())
		params.endDate = df.format(endDate.getTime())
		
		
		
		
		params.max = params.max?:10;
		params.offset = params.offset?:1;
		def index = Indicator.get(params.indicatorname?:1);
		
		def result = capabilityService.queryMonitorMessages(params,index,startDate.getTime(),endDate.getTime())
		
		def columu = ServerIndexColumu.where{
			indicator == index
			status == 1
		}.list(['sort':'srot','order':'asc']);
		[rows:result,columu:columu]
    }

    /**
     * 查询本地服务器的状态
     * @return
     */
    def serverStatus() {
        render(view:"server_status", model: params)
    }

    /**
     * 获取cpu监控数据
     */
    def graphicdata() {
		
		def serverName = params.serverName
		
		def results = [:]
		
		ServerInfo serverInfo = ServerInfo.findByName(serverName)
		Set monitoringIndicators = serverInfo.monitoringIndicators
		
		Indicator.where{type==1}.list()?.each{Indicator indicator ->
			if(monitoringIndicators.contains(indicator)){
				switch(Constant.IndicatorNameMeta.TABLENAME_PREFIX+indicator.code){
					case INDICATOR_NAME_CPU:
						results << monitorDataService.queryCpuUsedPct(serverInfo)
					break;
					case INDICATOR_NAME_MEM:
						results << monitorDataService.queryMemUsedPct(serverInfo)
					break;
					case INDICATOR_NAME_DISK:
						results << monitorDataService.queryDiskUsedPct(serverInfo)
					break;
					case INDICATOR_NAME_IFSTAT:
						results << monitorDataService.queryIfstatAvgPerSecond(serverInfo)
					break;
					case INDICATOR_NAME_CONCOUNT:
						results << monitorDataService.queryTcpSumCount(serverName,params.contype)
					break;
				}
			}
		}
		render(contentType: "application/json") {
			results 
		}
    }

    def business() {
        def onlineUsers = onlineUsersService.queryLatest()
        def sum = onlineUsersService.findOnlineUserCount()
        def highestLevel = onlineUsersService.queryHighestLevel()


        [onlineUserCount : sum, onlineUsers : onlineUsers, highestLevel : highestLevel]
    }

    def activeUsersLine() {
        def sdf = new SimpleDateFormat("MM-dd HH:mm")
        def monthTime = generateCurrentMonth();
        def users = ActiveUsers.where { (product == params.product) && (time >= monthTime) }.list([sort: "time", order: "asc"])
        def categories = []
        def datas = []
        users.each { activeUser ->
            categories << sdf.format(activeUser.time)
            datas << activeUser.amount
        }

        render(contentType: "application/json") {
            xAxies = categories
            name = params.product
            data = datas
        }
    }

    private def generateCurrentMonth() {
        def now = new Date()
        now = DateUtils.setDays(now, 1)
        now = DateUtils.setHours(now, 0)
        now = DateUtils.setMinutes(now, 0)
        now = DateUtils.setSeconds(now, 0)
        now = DateUtils.setMilliseconds(now, 0)
        return now;
    }

    def registerUsersLine() {
        def sdf = new SimpleDateFormat("MM-dd HH:mm")
        def monthTime = generateCurrentMonth();
        def users= RegisterUsers.where { product == params.product && (time >= monthTime) }.list([sort: "time", order: "asc"])
        def categories = []
        def datas = []
        users.each { registerUser ->
            categories << sdf.format(registerUser.time)
            datas << registerUser.amount
        }

        render(contentType: "application/json") {
            xAxies = categories
            name = params.product
            data = datas
        }
    }

    def performance() {
		def servers = ServerInfo.where {
			monitoringIndicators{
				type==1
			}
		}.list()
        render (view: "performance",model:[servers:servers])
    }

    def application() {
		def servers = ServerInfo.where{
			services{
				id != null
			}
		}.list()
        render (view: "application",model:[servers:servers]);
    }
	def applicationData(){
		int indicatorType = params.int('indicatorType')
		def chartData = null;
		switch(indicatorType){
			case 2:
			chartData = applyService.queryHostJVMApplicationData(params.servername,params.appPort)
			break;
		}
		def chartJsonData = [:]
		if(chartData){
			chartJsonData = chartData as JSON
		}
		render(contentType:"application/json"){
			chartData=chartData
		}
	}
	def db(){
		// 3 代表Mysql
		def servers = serverInfoService.listServerInfoWithServiceType(3)
		if(!params.hostname){
			if(servers.size() > 0){
				params.hostname = servers.get(0).name
			}
		}
		def chartData = dbService.queryDbData(params.hostname)
		def runtimeStatus = dbService.lastRuntimeStatus(params.hostname);
		def lockData = dbService.queryDbLock(params.hostname)
		def expensive = dbService.queryExpensiveSql(params.hostname)
		def chartJsonData = chartData as JSON
		[servers:servers,runtimeState:runtimeStatus,lockData:lockData,expensive:expensive,chart:chartJsonData]
	}
	
	def connectivy(){
		def cantConnectServer = serverInfoService.listCantConnectServer()
		def hosts = serverInfoService.listHostBean();
		[cantConnect:cantConnectServer,servers:hosts]
	}
	def connectivyData(){
		def cantConnectServer = serverInfoService.listCantConnectServer()
		render(contentType:"application/json"){
			cantConnectServer
		}
	}
	
	
	def web(){
		
		if(!params.prduct){
			params.prduct='hi'
			params.remark='网站IP：8.8.8.8   http://www.51chat.com'
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		
		long begin = cal.getTime().getTime();
		cal.add(Calendar.DATE, 1);
		long end = cal.getTime().getTime();
		
		List today = webCountService.queryWebCount(begin, end,params.prduct)
	
		end = begin;
		cal.add(Calendar.DATE, -1);
		begin = cal.getTime().getTime();
	
		List zuotian = webCountService.queryWebCount(begin, end,params.prduct);
		
		List rang = MonitorCount.where {
			countType == 1
			prduct == params.prduct
		}.list();
		String rangstr = "[";
		String datastr = "[";
		boolean flag=false;
		for(int i=0;i<24;i++){
			flag=true;
			rangstr+="\""+i+"\",";
			for(MonitorCount m : rang){
				if(i == m.hour){
					datastr+=m.num+",";
					flag=false;
				}
			}
			if(flag){
				datastr+="0,";
			}
		}
		rangstr = rangstr.substring(0,rangstr.length()-1)+"]";
		datastr = datastr.substring(0,datastr.length()-1)+"]";
		render (view: "web",model:[today:today,zuotian:zuotian,rang:rangstr,datas:datastr]);
	}
	
	
	def queryCountData(){
		
		if(!params.prduct){
			params.prduct ='hi';
		}
		List<Long[]> result = new ArrayList<Long[]>();
		
		List list = MonitorCount.where{
			prduct == params.prduct
			countType == params.type
		}.list(['sort':'visteTime','order':'asc']);
		for(MonitorCount mc : list) {
			Long[] data = new Long[2];
			data[0] = mc.visteTime
			data[1] = mc.num
			result.add(data);
		}
		render(contentType:"application/json"){
			result
		}
	}
}
