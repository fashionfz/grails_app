package com.helome.monitor
/**
 * 告警策略
 */
class WarningStrategy implements java.io.Serializable{
    /**
     * 监控策略名称
     */
    String name

    /**
     * 启用状态
     */
    Integer enabled = 1
    /**
     * 删除状态
     */
    Integer disabled = 0

    /**
     * 多对多
     */
    static hasMany = [serverWarnConditions: ServerInfoWarnCondition/*多个服务器警告策略*/]
	
    static constraints = {
    }
	
	static namedQueries = {
		listAvilableWarningStrategy{
			eq 'disabled',0
		}
	}
}
