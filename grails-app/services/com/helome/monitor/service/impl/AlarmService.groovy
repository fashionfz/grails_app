package com.helome.monitor.service.impl

import java.text.SimpleDateFormat

import org.hibernate.Query
import org.hibernate.SQLQuery
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.transaction.annotation.Transactional

import com.helome.monitor.Alarm
import com.helome.monitor.Constant
import com.helome.monitor.IndicatorWarnCondition
import com.helome.monitor.ServerGroup
import com.helome.monitor.ServerInfo
import com.helome.monitor.ServerInfoWarnCondition
import com.helome.monitor.service.IAlarmService

class AlarmService implements IAlarmService {
	
	def mailService
	def notifyService
	def grailsApplication
	private static GroovyShell groovyShell = new GroovyShell()
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static transactional = true
	@Override
    @Transactional
	public void save(Alarm alarm) {
		//保存
        try {
            alarm.save(flush: true)
        } catch (Exception e) {
            log.error(e.getMessage());
        }

		/*
		 * 根据设置的告警策略的条件表达式判断是否需要发送告警通知
		 */

		if (alarm.observedType == 1) {	//主机
			sendMessageByServerId(alarm)
		} else if (alarm.observedType == 2) {	//应用
			
		}
	}


    Binding query(Map conditions) {
        Binding binding = new Binding()
        def serverId = conditions.serverId
        def serverGroup = conditions.servergroup
        def serverIds = []
        if (serverGroup) {
            def sg = ServerGroup.get(serverGroup)
            sg?.servers.each { server ->
                if (server.disabled == 0) serverIds << server.id
            }
        }
        def c = Alarm.createCriteria()
        def totalCount = c {
            if (serverId) eq('observedId', serverId as Long)
            if (serverIds) 'observedId' in serverIds
        }.size()
        def c2 = Alarm.createCriteria()
        def results = c2 {
            if (serverId) eq('observedId', serverId as Long)
            if (serverIds) 'in'('observedId', serverIds)
            maxResults(conditions.max as Integer)
            firstResult(conditions.offset as Integer)
        }

        binding.results = results
        binding.totalCount = totalCount
        return binding
    }

    @Override
    void sendAlarmMessage(Alarm alarm) {
        sendMessageByServerId(alarm)
    }

    /**
	 * 发送消息
	 * @param id
	 */
	@Override
	public void sendMessageByServerId(Alarm alarm){
		def data = []
		def list = ServerInfoWarnCondition.where {
			server.id == alarm.observedId
		}.list().each {
			def server = it.server
			for(IndicatorWarnCondition ind : it.conditions){
				if(ind.id == alarm.indicatorWarnCondition.id){
					String value = alarm.value
					String alarmTime = df.format(alarm.alarmTime)
					//格式化通知内容
					def content = formatString(ind.message, [
						server: it.server.name,
						value: value,
						time: alarmTime
					])
					try {
						notifyService.notifyMessage(content, Constant.NOTIFY_CAPABILITY)
					} catch(e) {
						log.error(e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 格式化字符串
	 * @param value 原字符串
	 * @param data 要替换的参数
	 * @return
	 */
	private String formatString(value, data) {
		String result = value
		
		data.each {
			if (null == it.value) return
			result = result.replace("#[" + it.key + "]", it.value)
		}
		return result
	}
	
	/**
	 * 分组查询告警指标次数
	 */
	public List queryAlarmPartion(){
		Session session = sessionFactory.currentSession
		Query query = session.createQuery("select a.indicatorWarnCondition.indicator.name, count(a.indicatorWarnCondition.indicator.name) from Alarm a group by a.indicatorWarnCondition.indicator.name")
		query.list()
	}
	
	/**
	 * 告警次数topN
	 */
	public List queryAlarmTopN(int topN){
		Session session = sessionFactory.currentSession
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR_OF_DAY, 0)
		begin.set(Calendar.MINUTE, 0)
		begin.set(Calendar.SECOND, 0)
		Date beginTime = begin.getTime();
		
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 23)
		end.set(Calendar.MINUTE, 59)
		end.set(Calendar.SECOND, 59)
		Date endTime = end.getTime();
		
		
		SQLQuery sqlQuery = session.createSQLQuery("SELECT count(i. NAME), s.`name` AS sname, i. NAME, w.`name` AS wname FROM"
			+" alarm a LEFT JOIN server_info s ON a.observed_id = s.id"
			+" LEFT JOIN indicator_warn_condition w ON a.indicator_warn_condition_id=w.id"
			+" LEFT JOIN indicator i ON i.id = w.indicator_id where a.alarm_Time >= ? and a.alarm_Time <= ?"
			+" GROUP BY s.`name`,w.`name`, i.`name` ORDER BY COUNT(i. NAME) DESC LIMIT 0,$topN");
		sqlQuery.setParameter(0, begin.getTime());
		sqlQuery.setParameter(1, end.getTime());
		sqlQuery.list();
	}
	/**
	 * 最近告警latestN
	 * @param latestN
	 */
	public List queryAlarmLatestN(int latestN){
		Alarm.listOrderByAlarmTime(max:latestN,offset:0,order:"desc")
	}
	
	public List alarmLatestForController(int latestN){
		List alarms = queryAlarmLatestN(latestN)
		alarms.each {Alarm alarm ->
			if(alarm.observedType == 1){
				ServerInfo serverInfo = ServerInfo.findById(alarm.observedId)
				alarm.observed = serverInfo
			}
		}
	}
	
	
	SessionFactory sessionFactory
}
