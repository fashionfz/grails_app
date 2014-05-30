package com.helome.monitor.vo

/**
 * ServerInfo VO
 * User: bin.liu
 * Date: 14-1-20
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
class ServerInfoItem {
    Long id
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
    Integer enabled
    String username
    String ip
}
