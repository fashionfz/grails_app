package com.helome.monitor


import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import com.helome.monitor.form.TreeNode
import com.helome.monitor.vo.ComboboxItem
import com.helome.monitor.vo.ServiceInfoItem

/**
 * 服务器控制类
 */
class ServerInfoController {
	def serverInfoService
	
	/* 服务器应用设置相关Action */
	def listServerApp(){
		def serverInfo = ServerInfo.get(params.id)
		[serverInfoInstance:serverInfo]
	}
	def serverAppEdit(){
		[serverAppInstance:ServiceInfo.get(params.id?:-1)]
	}
	def deleteServerApp(){
		ServiceInfo serviceInfo = ServiceInfo.get(params.id);
		serviceInfo.delete()
		redirect action:'listServerApp',params:[id:params.serverId]
	}
	def saveServerApp(){
		ServiceInfo serviceInfo = null
		if(params.id){
			serviceInfo = ServiceInfo.get(params.id)
		}else{
			serviceInfo	= new ServiceInfo()
		}
		serviceInfo.support = false
		serviceInfo.properties = params
		def serverInfo = ServerInfo.get(params.serverId)
		def port = params.port?.split(",");
		def ports = port.collect{
			it.toInteger()
		}
		serviceInfo.ports = ports
		serviceInfo.serverInfo = serverInfo
//		def indicators = Indicator.findAllByIdInList(params.indicator?.collect {it.toLong()})
//		serviceInfo.indicators = indicators
		serviceInfo.save()
		if(serviceInfo.hasErrors()){
			serviceInfo.errors.each {
				log.info(it);
			}
		}
		redirect action:"listServerApp",params:[id: params.serverId]
	}
	
	/* 服务器IP设置相关Action */
    def listIPAddress() {
		def serverInfo = ServerInfo.get(params.id)
        [serverInfoInstance:serverInfo]
    }
	def ipAddressEdit(){
		[ipaddressInstance:ServerIf.get(params.id?:-1)]
	}
	def ipAddressDelete(){
		ServerIf serverIf = serverInfoService.deleteServerIp(params)
		if(serverIf?.type == 2){
			log.info serverInfoService.evictCantConnectServerFromCache(serverIf.id)
			log.info message(code:'log.info.cache.delete.custom',args:['Delete Server Gate IP','[serverID: '+params.id+']'])
		}
		
		redirect action:"listIPAddress",params:[id:params.serverId]
	}
	def saveIpAddress(){
		ServerIf serverIf = serverInfoService.saveServerIp(params)
		if(params.id){
			log.info serverInfoService.evictCantConnectServerFromCache(serverIf.id)
			log.info message(code:'log.info.cache.delete.custom',args:['Update Server Gate Ip','[serverID: '+params.id+']'])
		}
		redirect action:"listIPAddress",params:[id:params.serverId]
	}
	
	/* 服务器基本信息设置相关Action */
    def serverlist() {
        params.max = params.max?:10
        params.offset = params.offset?:0
		def results = ServerInfo.listAvilableServer.list(params)
		render(view:'list',model:[rows:results,total:results.totalCount]);
    }

    /**
     * 保存
     * @return
     */
    def save() {
		if(params.indicators?.id){
			params.requestIndicators = request.getParameterValues("indicators.id").collect {
				it.toLong()
			}
		}
		serverInfoService.saveServerInfo(params)
		if(params.id){
			log.info serverInfoService.evictCantConnectServerFromCache(params.long('id'))
			log.info message(code:'log.info.cache.delete.custom',args:['Update Server','[serverID: '+params.id+']'])
		}
		redirect action:'serverlist'
    }

	
	
	def checkServerInfoName(){
		int num = serverInfoService.checkServerInfoName(params.name);
		render(contentType:'application/json'){
			result = num
		}
	}
	
    /**
     * 编辑
     * @param id
     * @return
     */
    def edit() {
		def serverInfoInstance = null;
		if(params.id){
			serverInfoInstance = ServerInfo.get(params.id)
		}
		[serverInfoInstance:serverInfoInstance]
    }

    /**
     * 更新
     * @param id
     * @param version
     * @return
     */
    def update(Long id) {
        def serverInfoInstance = ServerInfo.get(id)

        if (!serverInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: ['服务器信息', id])
            redirect(action: "index")
            return
        }

        //数据库里面的密码
        //def oldPassword = serverInfoInstance.password
        serverInfoInstance.properties = params
        /*if (!oldPassword.equals(params.password)) {//密码是否变更
            //RSA加密
            def keyPair = RSAKeyPair.list().first();
            serverInfoInstance.password = new String(RSACoder.encryptByPrivateKey(params.password.getBytes(), keyPair.privateKey))
        }*/
        if (params.indicatorIds) {
            serverInfoInstance.indicators?.clear();
            params.indicatorIds.split(",").each {
                serverInfoInstance.addToIndicators(Indicator.get(Long.valueOf(it.toString().trim())))
            }
        } else {
            serverInfoInstance.indicators?.clear();
        }
        if (!serverInfoInstance.save(flush: true)) {
            render(view: "edit", model: [serverInfoInstance: serverInfoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: ['服务器信息', serverInfoInstance.id])
        redirect(action: "index")
    }
    /**
     * 删除
     * @param id
     * @return
     */
    def delete(Long id) {
        def serverInfoInstance = ServerInfo.get(id)
		serverInfoService.evictCantConnectServerFromCache(serverInfoInstance.id)
		serverInfoInstance.delete(flush:true);
		log.info message(code:'log.info.cache.delete.custom',args:['Delete Server','[serverID:'+serverInfoInstance.id+']'])
		redirect action:'serverlist'
    }
    /**
     * 启用
     * @param id
     * @return
     */
    def enable(Long id) {
        def serverInfoInstance = ServerInfo.get(id)
        if (!serverInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: ['服务器信息', id])
            render(contentType: "application/json") {
                success = false
            }
            return
        }
        //如果操作为启用则enabled设为1,如果操作为停用enabled设为0
        if (serverInfoInstance.enabled == 1) {
            serverInfoInstance.enabled = 0
			serverInfoService.evictCantConnectServerFromCache(serverInfoInstance.id)
        } else {
            serverInfoInstance.enabled = 1
        }

        try {
            serverInfoInstance.save(flush: true)
            flash.message = message(code: 'default.deleted.message', args: ['服务器信息', id])
            render(contentType: "application/json") {
                success = true
            }
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: ['服务器信息', id])
            render(contentType: "application/json") {
                success = false
            }
        }

    }

    /**
     * 查询服务器所有ip
     */
    def serverIfs(Long id) {
        def serverInfoInstance = ServerInfo.get(id)
        int count = 0
        def list = []
        if (serverInfoInstance) {
            count = serverInfoInstance.ifs.size();
            list = serverInfoInstance.ifs;
        }
        render(contentType: "application/json") {
            total = count
            rows = list
        }
    }
    /**
     * 查询服务器所有应用
     * @param id
     * @return
     */
    def serverServices(Long id) {
        def serverInfoInstance = ServerInfo.get(id)
        int count = 0
        def results = []
        if (serverInfoInstance) {
            count = serverInfoInstance.services.size()
            serverInfoInstance.services?.each { service ->
                def item = new ServiceInfoItem()
                bindData(item, service.properties)
                item.id = service.id
                def indicatorIds = ""
                service.indicators?.eachWithIndex { indicator, i ->
                    indicatorIds += indicator.id
                    if (i < (service.indicators.size() - 1)) {
                        indicatorIds += ","
                    }
                }

                item.indicatorIds = indicatorIds
                results << item
            }
        }

        render(contentType: "application/json") {
            total = count
            rows = results
        }
    }
    /**
     * 保存ip地址
     */
    def saveIfs() {
        def serverInfoInstance = ServerInfo.get(params.id);
        if (!serverInfoInstance) {
            render(contentType: "application/json") {
                success = false
            }
            return
        }
        //新添加的
        if (params.inserted) {
            def inserted = JSON.parse(params.inserted)
            inserted.each {
                serverInfoInstance.addToIfs(new ServerIf(it))
            }
        }
        //更新的
        if (params.updated) {
			serverInfoService.evictCantConnectServerFromCache(serverInfoInstance.id)
            def updated = JSON.parse(params.updated)
            updated.each {
                def serverIf = ServerIf.get(it.id)
                serverIf.properties = it
                serverInfoInstance.addToIfs(serverIf)
            }
        }
        //删除的
        if (params.deleted) {
			serverInfoService.evictCantConnectServerFromCache(serverInfoInstance.id)
            def deleted = JSON.parse(params.deleted)
            deleted.each {
                def serverIf = ServerIf.get(it.id);
                serverInfoInstance.ifs.remove(serverIf)
            }
        }
        render(contentType: "application/json") {
            success = true
        }
    }

    /**
     * 保存应用服务
     */
    def saveServices() {
        def serverInfoInstance = ServerInfo.get(params.id);
        if (!serverInfoInstance) {
            render(contentType: "application/json") {
                success = false
            }
            return
        }
        //新添加的
        if (params.inserted) {
            def inserted = JSON.parse(params.inserted)
            inserted.each {
                serverInfoInstance.addToServices(new ServiceInfo(it))
            }
        }
        //更新的
        if (params.updated) {
			serverInfoService.evictCantConnectServerFromCache(serverInfoInstance.id)
            def updated = JSON.parse(params.updated)
            updated.each {
                def serviceInfo = ServiceInfo.get(it.id)
                serviceInfo.ports.clear()
                serviceInfo.properties = it
                serviceInfo.indicators.clear()
                it.indicatorIds?.split(",").each { id ->
                    serviceInfo.addToIndicators(Indicator.get(id as Long))
                }
                serverInfoInstance.addToServices(serviceInfo)
            }
        }
        //删除的
        if (params.deleted) {
			serverInfoService.evictCantConnectServerFromCache(serverInfoInstance.id)
            def deleted = JSON.parse(params.deleted)
            deleted.each {
                def serviceInfo = ServiceInfo.get(it.id)
                serverInfoInstance.services.remove(serviceInfo)
            }
        }
        render(contentType: "application/json") {
            success = true
        }
    }
    /**
     * 查询应用对应的指标
     * @param id
     */
    def serviceIndicators() {
        def results = []
        Indicator.where {type == 2 || type == 3}.list().each {
            def item = new ComboboxItem(id: it.id, text: it.name)
            results << item
        }

        render results as JSON
    }

    /**
     * 生成服务器树
     */
    def serverTreeData() {
        def servers = ServerInfo.where {disabled == 0}.list()
        def datas = []
        def rootNode = new TreeNode(id: 0, text: "服务器列表", state: 'open')
        datas << rootNode
        servers.each { server ->
            def treeNode = new TreeNode(id: server.id, text: server.name, state: 'open')
            rootNode.children << treeNode
        }

        render datas as JSON
    }
}
