package com.helome.monitor

/**
 * 监控策略
 */
class MonitorStrategy {

    /**
     * 刷新频率
     */
    Integer freshFrequency
	
    /**
     * 启动标识
     */
    Integer enabled = 1
    /**
     * 逻辑删除标识
     */
    Integer disabled = 0

    static constraints = {
        freshFrequency(nullable: false)
    }
}
