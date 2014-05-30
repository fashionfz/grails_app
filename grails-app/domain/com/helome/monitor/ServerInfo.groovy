package com.helome.monitor

import java.io.Serializable;

/**
 * 服务器信息
 */
class ServerInfo implements Serializable {
    /**
     * 编码
     */
    String code
    /**
     * 名称
     */
    String name
    /**
     * 操作系统
     */
    String os
    /**
     * 监控类型
     */
    String monitorType
    /**
     * 机房
     */
    String computerRoom
    /**
     * 主机登录账户
     */
    String login
    /**
     * 主机登录密码
     */
    String password
    Integer enabled = 1
    Integer disabled = 0
    /**
     * 是否允许ssh远程登录
     */
    Integer allowedSSH
	
	/**
	 * 连通测试端口,或后期可作为远程登陆端口
	 */
	Integer connectPort
	
    /**
     * 多对多关系中，关系由服务器组(ServerGroup)维护
     */
    //static belongsTo = [ServerGroup]
    /**
     * 多对多:属于多个服务器组(ServerGroup）,拥有多个IP
     */
	
    static hasMany = [groups: ServerGroup/*服务器组*/,
            ifs: ServerIf/*ip地址*/,
            services: ServiceInfo/*安装的应用*/,
            indicators: Indicator/* 支持的指标*/,
            monitoringIndicators: Indicator/*需要监控的指标*/
    ]
	static mapping = {
        version(false)
		groups cascade:"save-update"
	}
    static constraints = {
        code nullable: true
        name size: 1..50, nullable: false, blank: false
        os nullable: false, blank: false
        login nullable: false, blank: false
        password nullable: false, blank: false
        allowedSSH(nullable: false)
		connectPort nullable:true
    }

    static boolean hasIndicator(Long serverId, Long indicatorId) {
        def instance = ServerInfo.get(serverId)
        if (!instance) {
            return false
        }
        def result = false
        instance.indicators.each {
            if (it.id == indicatorId) {
                result = true
                return result
            }
        }
        return result
    }
	
	static namedQueries = {
		listAvilableServer{
			eq 'disabled',0
		}
	}
}
