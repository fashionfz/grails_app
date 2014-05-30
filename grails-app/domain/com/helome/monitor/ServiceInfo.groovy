package com.helome.monitor
/**
 * 服务实体，包含服务名，端口，根目录
 */
class ServiceInfo {

    /**
     * 名称
     */
    String  name
    /**
     * 安装根目录
     */
    String  contentPath

    /**
     * 类型:
     * 1--Nginx <br>
     * 2--Tomcat <br>
     * 3--Mysql <br>
     * 4--Play <br>
     * 5--Memcached <br>
     * 7--3rd(第三方软件)
     */
    Integer type
	
	Boolean support

    /**
     * 含有多个端口
     */
    static hasMany = [ports: Integer]
//			,
//            indicators: Indicator/*支持的指标*/,
//            monitoringIndicators: Indicator/*需要监控的指标*/]

    /**
     * 所属服务器
     */
    static belongsTo = [serverInfo:ServerInfo]
	static mapping = {
//		indicators cascade:'save-update'
	}
    static constraints = {
        name(nullable: false, blank: false)
        contentPath(nullable: false, blank: false)
        type(nullable: false)
        ports(nullable: true)
    }
}
