package com.helome.monitor

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import com.helome.monitor.form.TreeNode
import com.helome.monitor.service.impl.WarnStrategyService
import com.helome.monitor.vo.WarningStrategyItem

/**
 * 告警监控策略控制器
 */
class WarningStrategyController {

	
	WarnStrategyService warnStrategyService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST", enable: "POST"]

    def index() {
        render(view: "list")
    }
    /**
     * 返回列表需要的json数据
     * @return
     */
    def list() {
        def start = (params.int('page') - 1) * params.int('rows')
        params.max = params.rows
        params.offset = start
        def count = WarningStrategy.where {disabled == 0}.list().size()
        def list = WarningStrategy.where {disabled == 0}.list(params)
        def results = []
        list.each { ws ->
            def item = new WarningStrategyItem(id: ws.id, name: ws.name, enabled: ws.enabled)
            def usernames = ""
            ws.users.eachWithIndex { user, i ->
                usernames += user.username
                if (i < (ws.users.size() - 1)) usernames += ","
            }
            item.usernames = usernames
            def serverNames = ""
            ws.serverWarnConditions.each { sc ->
                serverNames += sc.server.name + " : "
                sc.server.ifs.each { ip ->
                    if (ip.type == 2) serverNames += ip.ipAddress + "<br>"
                }
            }
            item.serverNames = serverNames
            results << item
        }
        //[warningStrategyInstanceList: WarningStrategy.list(params), warningStrategyInstanceTotal: WarningStrategy.count()]
        render(contentType: "application/json") {
            total = count
            rows = results
        }
    }
    /**
     * 新建跳转
     * @return
     */
    def create() {
        [warningStrategyInstance: new WarningStrategy(params)]
    }
    /**
     * 新建保存
     * @return
     */
    def save() {
        def warningStrategyInstance = new WarningStrategy(params)
        //保存用户
        if (params.userIds) {
            params.userIds.split(',').each {
                warningStrategyInstance.addToUsers(User.get(it as long))
            }
        }
        //保存服务器指标告警条件
        if (params.serverConditions) {
            //管理的服务器告警条件清空,并删除
            def serverConditionsMap = JSON.parse(params.serverConditions)
            serverConditionsMap.each { key,val ->
                //新建服务器告警条件
                def sc = new ServerInfoWarnCondition(server: ServerInfo.get(key as long))
                //服务器清空管理的告警条件
                val.each {
                    sc.addToConditions(IndicatorWarnCondition.get(it as long))
                }
                warningStrategyInstance.addToServerWarnConditions(sc)
            }
        }

        if (!warningStrategyInstance.save(flush: true)) {
            render(view: "create", model: [warningStrategyInstance: warningStrategyInstance])
            return
        }
		warnStrategyService.initWarnStrategyMemcache();

        flash.message = message(code: 'default.created.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), warningStrategyInstance.id])
        redirect(aciton: "index")
    }

    def show(Long id) {
        def warningStrategyInstance = WarningStrategy.get(id)
        if (!warningStrategyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            redirect(action: "index")
            return
        }

        [warningStrategyInstance: warningStrategyInstance]
    }

    def edit(Long id) {
        def warningStrategyInstance = WarningStrategy.get(id)
        if (!warningStrategyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            redirect(action: "index")
            return
        }

        [warningStrategyInstance: warningStrategyInstance]
    }
    /**
     * 更新保存
     * @param id
     * @param version
     * @return
     */
    def update(Long id, Long version) {
        def warningStrategyInstance = WarningStrategy.get(id)
        if (!warningStrategyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (warningStrategyInstance.version > version) {
                warningStrategyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'warningStrategy.label', default: 'WarningStrategy')] as Object[],
                        "Another user has updated this WarningStrategy while you were editing")
                render(view: "edit", model: [warningStrategyInstance: warningStrategyInstance])
                return
            }
        }

        warningStrategyInstance.properties = params
        //保存用户
        if (params.userIds) {
            warningStrategyInstance.users.clear()
            params.userIds.split(',').each {
                warningStrategyInstance.addToUsers(User.get(it as long))
            }
        }
        if (params.serverConditions) {
            //管理的服务器告警条件清空,并删除
            //warningStrategyInstance.serverWarnConditions?.removeAll(warningStrategyInstance.serverWarnConditions);
            def serverConditionsMap = JSON.parse(params.serverConditions)
            serverConditionsMap.each { key,val ->
                //新建服务器告警条件
                //def sc = new ServerInfoWarnCondition(server: ServerInfo.get(key as long))
                def sc = ServerInfoWarnCondition.findByServer(ServerInfo.get(key as long))
                if (sc) {
                    new ServerInfoWarnCondition(server: ServerInfo.get(key as long))
                } else {
                    sc.conditions.clear()
                }
                //服务器清空管理的告警条件
                val.each {
                    sc.addToConditions(IndicatorWarnCondition.get(it as long))
                }
                warningStrategyInstance.addToServerWarnConditions(sc)
            }
        }

        if (!warningStrategyInstance.save(flush: true)) {
            render(view: "edit", model: [warningStrategyInstance: warningStrategyInstance])
            return
        }
		warnStrategyService.initWarnStrategyMemcache();
        flash.message = message(code: 'default.updated.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), warningStrategyInstance.id])
        redirect(action: "index")
//        render (contentType: "application/json") {
//            success  =  true
//        }
    }
    /**
     * 删除
     * @param id
     * @return
     */
    def delete(Long id) {
        def warningStrategyInstance = WarningStrategy.get(id)
        if (!warningStrategyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            render(contentType: "application/json"){
                success = false
            }
            return
        }

        try {
            warningStrategyInstance.disabled == 1
            warningStrategyInstance.save(flush: true)
			warnStrategyService.initWarnStrategyMemcache();
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            render(contentType: "application/json"){
                success = true
            }
			
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            render(contentType: "application/json"){
                success = true
            }
        }
    }
    /**
     * 启用/停用
     * @param id
     */
    def enable(Long id) {
        def warningStrategyInstance = WarningStrategy.get(id)
        try {
            if (warningStrategyInstance.enabled == 1) {
                warningStrategyInstance.enabled = 0
            } else {
                warningStrategyInstance.enabled = 1
            }
            warningStrategyInstance.save(flush: true)
			warnStrategyService.initWarnStrategyMemcache();
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            render(contentType: "application/json"){
                success = true
            }
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            render(contentType: "application/json"){
                success = true
            }
        }
    }

    /**
     * 生成用户树
     * @return
     */
    def usersTree(Long id) {
        def list = UserGroup.where {disabled == 0}.list()
        def warningStrategy = WarningStrategy.get(id)
        def users = warningStrategy?.users
        def result = []
        def usersMap = new HashMap<Long, User>()
        users?.each {
            usersMap.put(it.id, it)
        }
        list.each { group ->
            def groupNode = new TreeNode(id: group.id, text: group.name, state: 'open')
            group.users?.each { user ->
                if (user.disabled == 0) {
                    def userNode = new TreeNode(id: user.id, text: user.realname, state: 'open')
                    userNode.checked = usersMap.containsKey(user.id)
                    groupNode.children << userNode
                }
            }
            result << groupNode
        }

        render(contentType: "application/json") { result }
    }
    /**
     * 生成服务器监控指标树
     * @param id
     */
    def serversTree(Long id) {
        def warningStrategy = WarningStrategy.get(id)
        def servers = ServerInfo.where {disabled == 0}.list()
        def result = []
        def checkedMap = new HashMap<Long, HashSet<Long>>();
        warningStrategy?.serverWarnConditions?.each { sc ->
            def conditionIds = new HashSet<Long>();
            sc.conditions?.each { condition ->
                if (condition.disabled == 0) {
                    conditionIds.add(condition.id)
                }
            }
            checkedMap.put(sc.server.id, conditionIds)
        }
        servers?.each { server ->
            def serverNode = new TreeNode(id: server.id, text: server.name, state: 'open')
            //指标类型
            def appIndicator = new TreeNode(id: 1, text: '性能指标')
            def performanceIndicator = new TreeNode(id: 2, text: '应用指标')

            serverNode.children<<appIndicator
            serverNode.children<<performanceIndicator
            //服务器监控的应用指标
            server?.monitoringIndicators?.each { ind ->
                if (ind.disabled == 0) {
                    def indicatorNode = new TreeNode(id: ind.id, text: ind.name, state: 'open')
                    appIndicator.children << indicatorNode
                    //监控指标对应的告警条件
                    def indicatorId = ind.id
                    def conditions = IndicatorWarnCondition.where {
                        indicator.id == indicatorId
                        disabled == 0
                    }.list();
                    conditions?.each { condition ->
                        def conditionNode = new TreeNode(id: condition.id, text: condition.name, state: 'open', attributes: ['serverId': server.id])
                        if (checkedMap.containsKey(server.id) && checkedMap.get(server.id).contains(condition.id)) {
                            conditionNode.checked = true
                        } else {
                            conditionNode.checked = false
                        }

                        indicatorNode.children << conditionNode
                    }
                }

            }

            result << serverNode

        }
        render(contentType: "application/json") { result }
    }
}
