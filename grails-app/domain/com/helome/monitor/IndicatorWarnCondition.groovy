package com.helome.monitor
/**
 * 监控指标告警条件
 */
class IndicatorWarnCondition implements java.io.Serializable{
    /**
     * 名称
     */
    String name
    /**
     * 监控指标告警条件
     */
    String expression
    /**
     * 紧急执行脚本路径
     */
    String remark
    /**
     * 告警方式：1、邮件；2、短信
     */
    Integer type
    /**
     * 启动标识
     */
    Integer enabled = 1
    /**
     * 逻辑删除标识
     */
    Integer disabled = 0
	
	/**
	 * 指标
	 */
//	Integer indicator
	
	/**
	 * 消息模板
	 */
	String message

    static belongsTo = [indicator: Indicator]
    /**
     * 指标包含多种发送方式
    static hasMany = [notifyModes: IndicatorWarnNotifyMode]
     */
    static constraints = {
        name(nullable: false, blank: false)
        expression(nullable: false, blank: false)
        remark(nullable: true)
        message(nullable: true)
    }
	static namedQueries = {
		listAvaliableIndicatorWarnCondition{
			eq 'disabled',0
		}
	}
}
