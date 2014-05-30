package com.helome.monitor.service.impl

import java.text.SimpleDateFormat

import javax.annotation.Resource

import net.rubyeye.xmemcached.XMemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;

import com.helome.monitor.Constant
import com.helome.monitor.Alarm
import com.helome.monitor.IndicatorWarnCondition
import com.helome.monitor.ServerInfo
import com.helome.monitor.WarningStrategy
import com.helome.monitor.service.IAlarmService

class DealServerInfoDataService {
	
    @Resource
    IAlarmService alarmService
	
	@Autowired
	XMemcachedClient memcachedClient;

	private static Map lastTimeIfstat = new HashMap<String, Binding>()
	
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
	
    def dealData(List records,String serverName,String clazzName) {
			ServerInfo info = getByName(serverName);
			if("Cpu".equals(clazzName)){
				records.each {Map record ->
					Set cds = findeCondition(df.parse(record.time),info);
					cds?.each {cd->
						if("cpu".equals(cd.indicator.code)){
							def strs = cd.expression?.split(">")
							def condition = Double.valueOf(strs[1].trim())
							//log.info("execute cpu ...")
							def cpuUsedPct = 0D
							if (record.userpct) {
								cpuUsedPct = Double.valueOf(record.userpct)
							}
							//如果超过条件
							if (cpuUsedPct > condition) {
								buildAndSaveAlarm(info,record, cd,cd.message, 1, record.userpct)
							}
						}
					}
				}
			}else if("Mem".equals(clazzName)){
				records.each {Map record ->
					Set cds = findeCondition(df.parse(record.time),info);
					cds?.each {cd->
						if("mem".equals(cd.indicator.code)){
							def strs = cd.expression?.split(">")
							def condition = Double.valueOf(strs[1].trim())
							//log.info("execute mem ...")
							def memUsedPct = 0D
							if (record.usedpct) {
								memUsedPct = Double.valueOf(record.usedpct)
							}
							//如果超过条件
							if (memUsedPct > condition) {
								buildAndSaveAlarm(info, record, cd,cd.message, 1, record.usedpct)
							}
						}
					}
				}
			}else if("Ifstat".equals(clazzName)){
				records.each {Map record ->
				def lastRecivebyte=memcachedClient.get(Constant.LAST_TIME_IFSTAT+info.name+"lastRecivebyte");
				def lastTransmitbyte=memcachedClient.get(Constant.LAST_TIME_IFSTAT+info.name+"lastTransmitbyte");
				
				memcachedClient.set(Constant.LAST_TIME_IFSTAT+info.name+"lastRecivebyte", 0, record.recivebyte)
				memcachedClient.set(Constant.LAST_TIME_IFSTAT+info.name+"lastTransmitbyte", 0, record.transmitbyte)
				
				if (lastRecivebyte&&lastTransmitbyte){
					Set cds = findeCondition(df.parse(record.time),info);
					cds?.each {cd->
						if("ifstat".equals(cd.indicator.code)){
							def strs = cd.expression?.split(">")
							def condition = Double.valueOf(strs[1].trim())
							//log.info("execute ifstat ...")
	
							//log.info("=====>"+record.recivebyte+"&&"+lastRecivebyte+"&&"+lastTransmitbyte)
							def minRec = BigDecimal.valueOf(Long.valueOf(record.recivebyte?:0)).subtract(Long.valueOf(lastRecivebyte)).divide(1024 * 60, 0)
							def minTra = BigDecimal.valueOf(Long.valueOf(record.transmitbyte?:0)).subtract(Long.valueOf(lastTransmitbyte)).divide(1024 * 60, 0)
							
		
							if (minRec.doubleValue() > condition){
								buildAndSaveAlarm(info,record, cd,cd.message, 1, "平均每秒接收流量"+minRec.longValue()+"kbps")
								log.info("kbps rec : ${minRec}, tra : ${minTra}")
								log.info(record.recivebyte+"---"+lastRecivebyte)
							}
							if (minTra.doubleValue() > condition)
								buildAndSaveAlarm(info, record, cd,cd.message, 1, "平均每秒发送流量"+minTra.longValue()+"kbps")
	
						}
					}
				}
			}
		}else if("Disk".equals(clazzName)){
			records.each {Map record ->
				Set cds = findeCondition(df.parse(record.time),info);
				cds?.each {cd->
					if("disk".equals(cd.indicator.code)){
						def strs = cd.expression?.split(">")
						def condition = Double.valueOf(strs[1].trim())
						//log.info("execute ifstat ...")
						def diskUsedpct = 0D
						if (record.usedpct) {
							diskUsedpct = Double.valueOf(record.usedpct)
						}
						//如果超过条件
						if (diskUsedpct > condition) {
							buildAndSaveAlarm(info, record, cd,cd.message, 1, record.usedpct)
						}
					}
				}
			}
		}else if("Jvm".equals(clazzName)){
			
		}else if("Mysql".equals(clazzName)){
		
		}else if("Mysql_lock".equals(clazzName)){
		
		}else if("Concount".equals(clazzName)){
			records.each {Map record ->
				Set cds = findeCondition(df.parse(record.time),info);
				cds?.each {cd->
					if("concount".equals(cd.indicator.code)){
						def strs = cd.expression?.split(">")
						def condition = Double.valueOf(strs[1].trim())
						//log.info("execute ifstat ...")
						def concount = 0D
						if (record.concount) {
							concount = Double.valueOf(record.concount)
						}
						//如果超过条件
						if (concount > condition) {
							buildAndSaveAlarm(info, record, cd,cd.message, 1, record.concount)
						}
					}
				}
			}
		}else{
			log.info("找不到对象名称:"+clazzName);
		}
    }
	
	
	
	def ServerInfo getByName(String name){
		def q = ServerInfo.where {
			name == name
		}
		List list = q.list();
		return list?.get(0)
	}
	
    def buildAndSaveAlarm(ServerInfo info,Map map, IndicatorWarnCondition cd,String message, int type, String value) {
        def alarm = new Alarm()
		
		log.info("alarm time"+map.time)
        alarm.alarmTime = df.parse(map.time)
		log.info("alarm time object"+alarm.alarmTime.toLocaleString())
		alarm.message = message.replace('#[server]', info.name)
		
		def content = formatString(message, [
			server: info.name,
			value: value,
			time: map.time
		])
		
		alarm.message = content;
		
        alarm.observedType = type
        alarm.observedId = info.getId()
        //告警条件
        alarm.indicatorWarnCondition = cd
        alarm.value = value
		synchronized(this){
			alarm.save(flush: true)
			alarmService.sendAlarmMessage(alarm)
		}
    }
	
	
	private String formatString(value, data) {
		String result = value
		
		data.each {
			if (null == it.value) return
			result = result.replace("#[" + it.key + "]", it.value)
		}
		return result
	}

	def Set findeCondition(Date time,ServerInfo server){
		Set conditions = new HashSet();
		log.debug("aspect invoking")
		def now = new Date()
		def tt = now.getTime() - time.getTime()
		if (tt < (2 * 60 * 1000)) { //只发上一分钟的告警信息
			
			
			
//			log.debug("Execting ...")
//			def c = WarningStrategy.createCriteria()
//			def wss = c.list {
//				serverWarnConditions {
//					eq 'server', server
//				}
//			}
//			wss.each { ws ->
//				ws.serverWarnConditions?.each { swc ->
//					if (server.id == swc.server.id) {
//							conditions.addAll(swc.conditions);
//						}
//					}
//				}
			conditions = memcachedClient.get(Constant.MONITOR_WARNING_STRATEGY+server.getName());
		}
		return conditions;
	}
}
