package com.helome.monitor

import com.helome.monitor.vo.CheckedItem
import com.helome.monitor.vo.ServerInfoItem
import com.helome.monitor.vo.UserGroupItem

import org.springframework.dao.DataIntegrityViolationException

/**
 * 用户组维护控制器
 */
class UserGroupController {

    def userGroupIndex() {
		params.max = params.max?:10;
		params.offset = params.offset?:0;
		def results = UserGroup.avaliableUserGroup.list(params);
        render (view: "list",model:[rows:results,totalCount:results?.totalCount])
    }
    /**
     * 创建跳转页面
     * @return
     */
    def create() {
        [userGroupInstance: new UserGroup(params)]
    }
    /**
     * 保存
     * @return
     */
    def save() {
		UserGroup userGroupInstance = null;
		if(params.id){
			userGroupInstance = UserGroup.get(params.long('id'))
		}else{
			userGroupInstance = new UserGroup()
		}
		userGroupInstance.properties = params
		userGroupInstance.save()
		redirect(action:"userGroupIndex");
    }

    def show(Long id) {
        def userGroupInstance = UserGroup.get(id)
        if (!userGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userGroup.label', default: 'UserGroup'), id])
            redirect(action: "list")
            return
        }

        [userGroupInstance: userGroupInstance]
    }
    /**
     * 编辑
     * @param id
     * @return
     */
    def edit(Long id) {
		def userGroupInstance = null;
		if(params.id){
			userGroupInstance = UserGroup.get(params.id);
		}
		[userGroupInstance:userGroupInstance]
    }
    /**
     * 更新
     * @param id
     * @param version
     * @return
     */
    def update(Long id, Long version) {
        def userGroupInstance = UserGroup.get(id)
        if (!userGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userGroup.label', default: 'UserGroup'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userGroupInstance.version > version) {
                userGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'userGroup.label', default: 'UserGroup')] as Object[],
                        "Another user has updated this UserGroup while you were editing")
                render(view: "edit", model: [userGroupInstance: userGroupInstance])
                return
            }
        }

        userGroupInstance.properties = params

        //保存关联的账户
        if (params.userIds) {
            userGroupInstance.users?.clear()
            params.userIds.split(",").each {
                userGroupInstance.addToUsers(User.get(Long.valueOf(it)))
            }
        } else {
            userGroupInstance.users?.clear()
        }

        if (!userGroupInstance.save(flush: true)) {
            render(view: "edit", model: [userGroupInstance: userGroupInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'userGroup.label', default: 'UserGroup'), userGroupInstance.id])
        render (contentType: "application/json") {
            success = true
        }
    }

    def delete(Long id) {
        def userGroupInstance = UserGroup.get(id)
        userGroupInstance.delete(flush: true)
		redirect(action:'userGroupIndex');
    }
    /**
     * 获取带选择框的列表
     * @return
     */
    def checkedUsers() {
        def count = User.where {disabled == 0}.list().size();
        def users = User.where {disabled == 0}.list(params);
        def list = [];
        if (params.id) {
            def userGroup = UserGroup.get(params.id)
            for (def user : users) {
                def tmp = new CheckedItem()
                tmp.id = user.id
                tmp.name = user.realname
                tmp.checked = false
                userGroup?.users?.each {
                    if (it.id == user.id) {
                        tmp.checked = true
                    }
                }
                list.add(tmp)
            }
        }
        render(contentType: "application/json") {
            total = count
            rows = list
        }
    }
	
	def serverInfoIndex() {
		//redirect(action: "list", params: params)
		render (view: "/serverInfo/list")
	}
	
	def serverInfoList(Integer max) {
		def start = (params.int('page') - 1) * params.int('rows')
		params.max = params.rows
		params.offset = start
		def list = ServerInfo.where {
			disabled == 0
		}.list(params)
		def count = ServerInfo.where {
			disabled == 0
		}.list().size()
		def results = []
		//[serverInfoInstanceList: list, serverInfoInstanceTotal: count]
		list.each { server ->
			def item = new ServerInfoItem()
			bindData(item, server.properties)
			item.username = server.user.username
			item.id = server.id
			server.ifs.each { ip ->
				//显示外网IP
				if (ip.type == 2) item.ip = ip.ipAddress
			}
			results << item
		}
		render(contentType: "application/json"){
			total = count
			rows = results
		}
	}
	def serverInfoDelete(Long id) {
		def serverInfoInstance = ServerInfo.get(id)
		if (!serverInfoInstance) {
			flash.message = message(code: 'default.not.found.message', args: ['服务器信息', id])
			render(contentType: "application/json") {
				success = false
			}
			return
		}

		try {
			serverInfoInstance.disabled = 1
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
	
	def serverInfoCreate() {
		render(view:'../serverInfo/create',model: [serverInfoInstance: new ServerInfo(params)])
	}
}
