package com.helome.monitor
/**
 * 服务器组信息
 */
class ServerGroup {
    /**
     * 名称
     */
    String name
    Integer enabled = 1
    Integer disabled = 0

    /**
     * 多对多:含有多个服务器
     */
    static hasMany = [servers: ServerInfo]

	static belongsTo = [ServerInfo]
	
	static mapping = {
		name column: "name", comment("名称")
		enabled column: "enabled", comment("启用标志")
		disabled column: "disabled", comment("逻辑删除标志")
		servers cascade:"save-update"
	}
	
    static constraints = {
        name size: 1..50, nullable: false, blank: false
    }
	
	static namedQueries = {
		avaliableServerGroup {
			eq 'disabled',0
		}
	}
}
