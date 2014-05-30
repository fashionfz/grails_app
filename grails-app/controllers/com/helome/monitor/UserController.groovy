package com.helome.monitor

import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.subject.Subject
import org.springframework.util.CollectionUtils

import com.helome.monitor.vo.UserItem

/**
 * 账户管理控制器
 */
class UserController {

	def userService
	
    def usermanager() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def results = User.where{
            disabled == 0
        }.list(params)

        def count = User.where{
            disabled == 0
        }.list().size()
        //[userInstanceList: results, userInstanceTotal: count]
        //def json = ["total": count, "raws": results]
        def datas = []
        results.each { user ->
            def roles = user.roles
            def roleName = ""
            roles.each { role ->
                roleName = role.realname
            }
            def item = new UserItem(id: user.id, username: user.username,
                    realname: user.realname, validateType: user.validateType,
                    email: user.email, phone: user.phone,
                    roleName: roleName, enabled: user.enabled
             )
            datas << item
        }
        render(view: "list",model:[rows:datas])
    }

    /**
     * 保存
     * @return
     */
    def save() {
		if(params.id){
			def userInstance = User.get(params.long('id'))
			//老密码
			def oldPwd = userInstance.passwordHash.toString()
			userInstance.properties = params
			if (oldPwd != params.passwordHash) {
				userInstance.passwordHash = new Sha256Hash(params.passwordHash).toHex()
				userInstance.confirmPassword = new Sha256Hash(params.confirmPassword).toHex()
			}
			if(!userInstance.save(flush: true)){
				render(view: "edit", model: [userInstance: userInstance])
				return
			}
		}else{
			def userInstance = new User(params)
			userInstance.enabled = 1
			userInstance.disabled = 0
			userInstance.passwordHash = new Sha256Hash(params.passwordHash).toHex()
			userInstance.confirmPassword = new Sha256Hash(params.confirmPassword).toHex()
			if (!userInstance.save(flush: true)) {
				render(view: "edit", model: [userInstance: userInstance])
				return
			}
		}
        redirect(action: "usermanager")
    }

    /**
     * 编辑
     * @return
     */
    def edit() {
		def userInstance = null;
		if(params.id){
			userInstance = User.get(params.id)
		}
        [userInstance: userInstance]
    }

    /**
     * 逻辑删除
     * @return
     */
    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			redirect(action: "usermanager")
            return
        }
		userService.deleteUser(userInstance)
			
		redirect(action: "usermanager")
    }
	
	def emailSetting() {
		def emailSettingsInstance
		if(params.id){
			emailSettingsInstance = EmailSettings.get(params.id)
		}else{
			List list = EmailSettings.list();
			if(!CollectionUtils.isEmpty(list)){
				emailSettingsInstance = list.get(0);
			}
		}
		if (!emailSettingsInstance) {
			emailSettingsInstance = new EmailSettings()
		}
		render(view:'/emailSettings/edit',model:[config: emailSettingsInstance])
	}
	
	def emailSettingSave() {
		def emailSettingsInstance = EmailSettings.get(params.id)
		if (!emailSettingsInstance) {
			emailSettingsInstance = new EmailSettings(params)
		} else {
			emailSettingsInstance.properties = params
		}
		userService.saveEmailConfig(emailSettingsInstance);
		userService.setEmailConfig();
		render(view: "/emailSettings/edit", model: [config: emailSettingsInstance])
	}

	

	
	def smsSetting() {
		params.id = params.id?:0
		def smsSettingsInstance = SmsSettings.get(params.id)
		if (!smsSettingsInstance) {
			smsSettingsInstance = new SmsSettings();
		}
		render(view:'/smsSettings/edit',model:[smsSettingsInstance: smsSettingsInstance])
	}
	
	def smsSettingSave() {
		
		def smsSettingsInstance = SmsSettings.get(params.id)
		if (!smsSettingsInstance) {
			smsSettingsInstance = new SmsSettings(params)
		} else {
			smsSettingsInstance.properties = params
		}
		if (!smsSettingsInstance.save(flush: true)) {
			render(view: "/smsSettings/edit", model: [smsSettingsInstance: smsSettingsInstance])
			return
		}
		flash.message = message(code: 'default.created.message', args: ['SmsSettings', smsSettingsInstance.id])
		redirect(action: "smsSetting", id: smsSettingsInstance.id)
	}
	
	def changeUserPasswd(){
		if(params.passwordExpress&&params.confirmPasswordExpress){
			Subject currentUser = SecurityUtils.getSubject();
			String username = currentUser.getPrincipal();
			User user = User.findByUsername(username);
			user.passwordHash = new Sha256Hash(params.passwordExpress).toHex()
			user.confirmPassword = new Sha256Hash(params.confirmPasswordExpress).toHex()
			log.info username
			if(!user.save(flush: true)){
				redirect controller:'/', action:'error', params: [errors: user.errors]
				return
			}
			redirect controller:'auth', action:'signOut'
		}else{
		redirect controller:'monitor', action:'index'
		}
	}
	
}
