package com.helome.monitor

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.helome.monitor.form.TreeNode

import org.springframework.dao.DataIntegrityViolationException

/**
 * 监控策略设置
 */
class MonitorStrategyController {

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
       
    }

    def save() {
		params.each {
			//处理server
			//用正则表达式取出server的id
			Pattern pattern = Pattern.compile("^server\\[(\\d+)\\]\\[\\]")
			Matcher matcher = pattern.matcher(it.key)
			if(matcher.find()) {
				def server = ServerInfo.get(matcher.group(1) as long)
				server.monitoringIndicators.clear()
				it.value.each { value ->
					def indicator = Indicator.get(value)
					server.monitoringIndicators<<indicator
				}
			}
			//处理app
			//处理其他
		}
		
		render(contentType: "application/json") { success = true }
    }

	/**
	 * 获取树状结构的数据
	 * @return
	 */
	def tree() {
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
                def serviceNode = new TreeNode(id: service.id, text: service.name, state: 'open', attributes: [type: 'server', id: server.id])
                appIndicator.children << serviceNode

                //将所有已被勾选的应用指标合并到一起
                def appMonitoringIndicators = []
                service.monitoringIndicators.each {
                    appMonitoringIndicators << it.id
                }
                //应用指标节点
                service.indicators.each { indicator ->
                    def appIndNode = new TreeNode(id: indicator.id, text: indicator.name, state: 'open')
                    appIndNode.checked = appMonitoringIndicators.contains(indicator.id) as boolean
                    serviceNode.children << appIndNode
                }

            }

			/*//将所有应用指标合并到一个集合
			def appIndicators = []
			server.services.each{
				appIndicators += it
			}

			//将所有已被勾选的应用指标合并到一起
			def appMonitoringIndicators = []
			server.services.each{
				appMonitoringIndicators += it
			}

			//将已勾选的应用指标放入map
			Map<Long, Object> appMonitoringIndicatorMap = new HashMap<Long, Object>()
			appMonitoringIndicators.each {
				appMonitoringIndicatorMap.put(it.id, it)
			}

			//应用指标
			appIndicators.each { indicator ->
				def indicatorNode = new TreeNode(id: indicator.id, text: indicator.name, state: 'open', attributes: ['serverId': server.id])
				indicatorNode.checked = appMonitoringIndicatorMap.containsKey(indicator.id) as boolean
				appIndicator.children<<indicatorNode
			}*/
			
			result<<serverNode
		}

		render(contentType: "application/json") { result }
	}
}
