package com.helome.monitor.service.impl

import com.helome.monitor.ServerGroup
import com.helome.monitor.ServerInfo;
import com.helome.monitor.service.IServerGroupService

/**
 * 服务器组服务接口实现类
 * @author bin.liu
 */
class ServerGroupService implements IServerGroupService {
	static transactional = true
    @Override
    public def findGroupsByHostName(String hostName) {
        def criteria = ServerGroup.createCriteria()
        def results = criteria {
            eq "disabled", 0
            servers {
                eq "name", hostName
            }
        }
        return results
    }
	
	def saveServerGroup(ServerGroup serverGroup,serversId){
		
		deleteServerGroupRelation(serverGroup)
		
		// 查询调用端传入的serverId对应的服务器
		def servers = ServerInfo.findAllByIdInList(serversId)?.each{
			// 为当前服务器组与选定的服务器建立关联
			serverGroup.addToServers(it)
		}
		serverGroup.save(flush:true)
	}
	
	/**
	 * 删除指定服务器组所对应的所有服务器的关系
	 * @param serverGroup
	 * @return
	 */
	def deleteServerGroupRelation(ServerGroup serverGroup){
		// 清空当前服务器组所关联的服务器
		serverGroup.servers?.clear();
		
		// 移除所有与该服务器组有关联的服务器的关系
		if(serverGroup.id){
			ServerInfo.where {
				groups.id == serverGroup.id
			}.list()?.each { ServerInfo serverInfo->
				serverInfo.removeFromGroups(serverGroup)
			}
		}
	}
	
	def deleteServerGroup(ServerGroup serverGroup){
		deleteServerGroupRelation(serverGroup)
		serverGroup.delete();
	}
}
