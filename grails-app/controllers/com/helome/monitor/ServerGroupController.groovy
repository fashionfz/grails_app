package com.helome.monitor

import com.helome.monitor.vo.CheckedItem
import com.helome.monitor.vo.ServerGroupItem

import org.springframework.dao.DataIntegrityViolationException

/**
 * 服务器组控制器类
 */
class ServerGroupController {

	def serverGroupService
	
    def serverGroupList() {
        params.max = params.max?:10;
		params.offset = params.offset?:0;
		def results = ServerGroup.avaliableServerGroup.list(params)
		render (view: "list",model:[rows:results,totalCount:results?.totalCount])
    }

    /**
     * 保存
     * @return
     */
    def save() {
        ServerGroup serverGroup = null
		params.serverId = request.getParameterValues("servers").collect {
			it.toLong()
		}
		if(params.id){
			serverGroup = ServerGroup.get(params.id)
		}else{
			serverGroup = new ServerGroup()
		}
		serverGroup.properties = params
		serverGroupService.saveServerGroup(serverGroup,params.serverId)
		redirect(action:'serverGroupList')
    }

    /**
     * 编辑
     * @param id
     * @return
     */
    def edit(Long id) {
		def serverGroupInstance = null;
		if(params.id){
			serverGroupInstance = ServerGroup.get(params.id)
		}
		[serverGroupInstance:serverGroupInstance]
    }


    /**
     * 逻辑删除
     * @param id
     * @return
     */
    def delete(Long id) {
        def serverGroupInstance = ServerGroup.get(id)
		serverGroupService.deleteServerGroup(serverGroupInstance)
		redirect action:"serverGroupList"
    }
	
    /**
     * 获取返回选择框的服务器列表
     */
    def listCheckedServers() {
        def count = ServerInfo.where {disabled == 0}.list().size();
        def servers = ServerInfo.where {disabled == 0}.list(params);
        def list = [];
        if (params.id) {
            def serverGroupInstance = ServerGroup.get(params.id)
            for (def server : servers) {
                def tmp = new CheckedItem();
                tmp.checked = false
                tmp.id = server.id
                serverGroupInstance?.servers?.each {
                    if (it.id == server.id) {
                        tmp.checked = true
                    }
                }
                tmp.name = server.name
                list.add(tmp)
            }
        }
        render (contentType: "application/json") {
            total = count
            rows = list
        }
    }
}
