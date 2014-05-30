package com.helome.monitor

import org.springframework.dao.DataIntegrityViolationException

import com.helome.monitor.service.IAlarmService;

class AlarmController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	IAlarmService alarmService

    def index() {
//		def a = ["observedId": 1L, "alarm.indicatorWarnCondition.id": 1L]
		Alarm alarm = new Alarm()
		alarm.observedId = 1L
		alarm.value = new Random().nextInt(200)
		alarm.indicatorWarnCondition = IndicatorWarnCondition.get(4L)
		alarmService.sendMessageByServerId(alarm)
    }
}
