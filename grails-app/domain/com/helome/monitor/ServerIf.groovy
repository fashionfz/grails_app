package com.helome.monitor

/**
 * 服务器的IP地址
 */
class ServerIf {

    /**
     * 外网地址
     */
    String ipAddress

    /**
     * 类型
     * 1.内网IP
     * 2.外网IP
     * 3.IDRAC卡IP
     *
     */
    Integer type

    static belongsTo = [serverInfo:ServerInfo]

    static mapping = {
        ipAddress column: "ip_address", comment("ip地址")
        type column: "type", comment("类型: 1.内网IP 2.外网IP 3.IDRAC卡IP")
    }

    static constraints = {
        ipAddress(nullable: false, blank: false)
        type(nullable: false)

    }
}
