package com.helome.monitor

import grails.converters.JSON

import com.helome.monitor.form.TreeNode
import com.helome.monitor.vo.WarningStrategyItem

class StrategyController {

	def serverInfoService
	
	def warnStragegyIndex(){
		render(view:'/warningStrategy/list')
	}
	
    def warnStrategyList() {
        def start = (params.int('page') - 1) * params.int('rows')
        params.max = params.rows
        params.offset = start
        def count = WarningStrategy.where {disabled == 0}.list().size()
        def list = WarningStrategy.where {disabled == 0}.list(params)
        def results = []
        list.each { ws ->
            def item = new WarningStrategyItem(id: ws.id, name: ws.name, enabled: ws.enabled)
//            def usernames = ""
//            ws.users.eachWithIndex { user, i ->
//                usernames += user.username
//                if (i < (ws.users.size() - 1)) usernames += ","
//            }
//            item.usernames = usernames
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
	
	def warnStrategyEnable(Long id) {
        def warningStrategyInstance = WarningStrategy.get(id)
        try {
            if (warningStrategyInstance.enabled == 1) {
                warningStrategyInstance.enabled = 0
            } else {
                warningStrategyInstance.enabled = 1
            }
            warningStrategyInstance.save(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
            render(contentType: "application/json"){
                success = true
            }
        } catch(Exception e){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
			render(contentType: "application/json"){
				success = true
			}
		}
    }
	
	def warnStrategyDelete(Long id) {
		def warningStrategyInstance = WarningStrategy.get(id)
		if (!warningStrategyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
			render(contentType: "application/json"){
				success = false
			}
			return
		}
		try {
//			warningStrategyInstance.disabled == 1
//			warningStrategyInstance.save(flush: true)
			warningStrategyInstance.delete(flush:true);
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
			render(contentType: "application/json"){
				success = true
			}
		} catch(Exception e){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
			render(contentType: "application/json"){
				success = true
			}
		}
	}
	
	/**
	 * 新建跳转
	 * @return
	 */
	def createWarnStrategy() {
		render(view:'/warningStrategy/create',model:[warningStrategyInstance: new WarningStrategy(params)])
	}
	
	def warnStrategySave() {
		def warningStrategyInstance;
		if(params.id){
			warningStrategyInstance = WarningStrategy.get(params.long('id'))
			def q = ServerInfoWarnCondition.where {
				warningStrategy == warningStrategyInstance
			}.each {
				it.delete(flush:true);
			}

		}else{
			warningStrategyInstance = new WarningStrategy(params)
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
			render(view: "/warningStrategy/create", model: [warningStrategyInstance: warningStrategyInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), warningStrategyInstance.id])
		redirect(aciton: "warnStragegyIndex",controller:"strategy")
	}
	
	def warnStrategyEdit(Long id) {
		def warningStrategyInstance = WarningStrategy.get(id)
		if (!warningStrategyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
			render(view:'/warningStrategy/edit');
			return
		}
		render(view:'/warningStrategy/edit',model:[warningStrategyInstance: warningStrategyInstance])
	}
	
	def warnStrategyUpdate(Long id, Long version) {
		def warningStrategyInstance = WarningStrategy.get(id)
		if (!warningStrategyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), id])
			redirect(action: "warnStragegyIndex")
			return
		}

		if (version != null) {
			if (warningStrategyInstance.version > version) {
				warningStrategyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'warningStrategy.label', default: 'WarningStrategy')] as Object[],
						"Another user has updated this WarningStrategy while you were editing")
				render(view: "/warningStrategy/edit", model: [warningStrategyInstance: warningStrategyInstance])
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
			render(view: "/warningStrategy/edit", model: [warningStrategyInstance: warningStrategyInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'warningStrategy.label', default: 'WarningStrategy'), warningStrategyInstance.id])
		redirect(action: "warnStragegyIndex")
//        render (contentType: "application/json") {
//            success  =  true
//        }
	}
	
	def monitorStrategyIndex() {
		render(view:'/monitorStrategy/list')
	}
	
	def monitorStrategySave() {
		serverInfoService.clearAllMonitoringIndicatorsRelation()
		params.each {
			strategyService.saveMonitoringStragey(it.key, it.value)
		}
		render(contentType: "application/json") { success = true }
	}
	
	def monitorStrategyTree() {
		def list = ServerInfo.where {
			disabled == 0
		}.list()
		
		def result = []
		list.each { server ->
			def serverNode = new TreeNode(id: server.id, text: server.name, state: 'open')
			
			//指标类型
			def performanceIndicator = new TreeNode(id: 1, text: '性能指标')
			def appIndicator = new TreeNode(id: 2, text: '应用指标')
			
			serverNode.children<<performanceIndicator
			serverNode.children<<appIndicator
			
			//将已勾选的性能指标放入map
			Map<Long, Object> serverMonitoringIndicatorMap = new HashMap<Long, Object>()
			server.monitoringIndicators.each {
				serverMonitoringIndicatorMap.put(it.id, it)
			}
			
			//性能指标
			server.indicators.each { indicator ->
				def indicatorNode = new TreeNode(id: indicator.id, text: indicator.name, state: 'open', attributes: [type: 'server', 'id': server.id])
				indicatorNode.checked = serverMonitoringIndicatorMap.containsKey(indicator.id) as boolean
				performanceIndicator.children.add(indicatorNode)
			}

            //应用节点
            server.services.each { service ->
                def serviceNode = new TreeNode(id: service.id, text: service.name, state: 'open', attributes: [type: 'app', 'id': server.id])
				serviceNode.checked = service.support
                appIndicator.children << serviceNode

                //将所有已被勾选的应用指标合并到一起
//                def appMonitoringIndicators = []
//                service.monitoringIndicators.each {
//                    appMonitoringIndicators << it.id
//                }
//                //应用指标节点
//                service.indicators.each { indicator ->
//                    def appIndNode = new TreeNode(id: indicator.id, text: indicator.name, state: 'open', attributes: [type: 'app', id: service.id])
//                    appIndNode.checked = appMonitoringIndicators.contains(indicator.id) as boolean
//                    serviceNode.children << appIndNode
//                }

            }

//			//将所有应用指标合并到一个集合
//			def appIndicators = []
//			server.services.each{
//				appIndicators += it
//			}
//
//			//将所有已被勾选的应用指标合并到一起
//			def appMonitoringIndicators = []
//			server.services.each{
//				appMonitoringIndicators += it
//			}
//
//			//将已勾选的应用指标放入map
//			Map<Long, Object> appMonitoringIndicatorMap = new HashMap<Long, Object>()
//			appMonitoringIndicators.each {
//				appMonitoringIndicatorMap.put(it.id, it)
//			}
//
//			//应用指标
//			appIndicators.each { indicator ->
//				def indicatorNode = new TreeNode(id: indicator.id, text: indicator.name, state: 'open', attributes: ['serverId': server.id])
//				indicatorNode.checked = appMonitoringIndicatorMap.containsKey(indicator.id) as boolean
//				appIndicator.children<<indicatorNode
//			}
			
			result<<serverNode
		}

		render(contentType: "application/json") { result }
	}
	
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
	
	def connectivyIndex(){
//		def results = strategyService.showNotifyUser()
//		[rows:results]
	}
	def showNotifyUser(){
//		[notifyUserInstance:NotifyUser.get(params.nuid)]
	}
	def saveNotifyUser(){
//		NotifyUser notifyUser = new NotifyUser(params)
//		if(params.nuid){
//			notifyUser.id = params.int('nuid')
//		}
//		notifyUser.save(flush:true)
		redirect action:'connectivyIndex'
	}
	def deleteNotifyUser(){
//		NotifyUser.get(params.nuid)?.delete();
		redirect action:'connectivyIndex'
	}
	def strategyService
}
