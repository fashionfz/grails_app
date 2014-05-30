package com.helome.monitor

/**
 * 每个策略中服务器对应的告警条件
 */
class ServerInfoWarnCondition implements java.io.Serializable{

    /**
     * 服务器
     */
    ServerInfo server
	/**
	 * 策略
	 */
	WarningStrategy warningStrategy
    /**
     * 服务器对应的告警条件
     */
    static hasMany = [conditions: IndicatorWarnCondition]
    /**
     * 创建更新删除都是由告警策略控制
     */
    static belongsTo = [warningStrategy: WarningStrategy]

    static constraints = {
    }
}
