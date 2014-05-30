package com.helome.monitor

import javax.persistence.Transient;

/**
 * 告警
 * @author helome
 *
 */
class Alarm {
	
	/**
	 * 时间
	 */
	Date alarmTime = new Date()
	
	/**
	 * 消息
	 */
	String message
	
	/**
	 * 级别
	 */
	Integer level
	
	/**
	 * 告警条件
	 */
	IndicatorWarnCondition indicatorWarnCondition
	
	/**
	 * 被监控对象的类型
	 */
	Integer observedType
	
	/**
	 * 被监控对象id
	 */
	Long observedId

    /**
     * 实际值
     */
    String value
	
	/**
	 * 被监控对象
	 */
	@Transient
	Object observed
	
	//@Override
	public Object getObserved() {
		if(null == observedType || null == observedId)
			return observed
		switch (observedType) {
			case 1:
				observed = ServerInfo.get(observedId)
				break
			case 2:
				observed = ServiceInfo.get(observedId)
				break
			default:
				break
		}
		return observed
	}

    static constraints = {
        level nullable: true
    }

//	public void get
}
