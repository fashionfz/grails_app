package com.helome.monitor.vo

/**
 * Created with IntelliJ IDEA.
 * User: helome
 * Date: 14-1-26
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
class WarningItem {
    /**
     * 时间
     */
    String alarmTime

    String indicatorName

    /**
     * 告警条件
     */
    String indicatorWarnConditionName


    /**
     * 被监控对象id
     */
    String serverName

    String serverIp

    String serverGroupName
	
	String expressionValue;
}
