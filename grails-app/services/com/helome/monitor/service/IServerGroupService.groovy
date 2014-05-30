package com.helome.monitor.service

/**
 * 服务器组服务接口
 * User: bin.liu
 * Date: 14-1-22
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public interface IServerGroupService {
    /**
     * 通过服务器名查询包含此服务器的服务器组
     * @param hostName
     * @return
     */
    public def findGroupsByHostName(String hostName)
}