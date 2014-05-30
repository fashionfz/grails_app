package com.helome.monitor

import net.rubyeye.xmemcached.XMemcachedClient

import org.springframework.beans.factory.annotation.Autowired

/**
 * 发送短信设置
 */
class EmailSettingsController {

    static allowedMethods = [save: "POST"]
	
	@Autowired
	XMemcachedClient memcachedClient;

    def index() {
        params.id = 1
        redirect(action: "edit", params: params)
    }

    /**
     * 保存
     * @return
     */
    def save() {
        def emailSettingsInstance = EmailSettings.get(params.id)
        if (!emailSettingsInstance) {
            emailSettingsInstance = new EmailSettings(params)
        } else {
            emailSettingsInstance.properties = params
        }
        if (!emailSettingsInstance.save(flush: true)) {
            render(view: "edit", model: [emailSettingsInstance: emailSettingsInstance])
            return
        }
		
		setEmailConfig();

        flash.message = message(code: 'default.created.message', args: ['EmailSettings', emailSettingsInstance.id])
        redirect(action: "edit", id: emailSettingsInstance.id)
    }

    /**
     * 编辑
     * @return
     */
    def edit() {
        def emailSettingsInstance = EmailSettings.get(params.id)
        if (!emailSettingsInstance) {
            emailSettingsInstance = new EmailSettings()
        }

        [emailSettingsInstance: emailSettingsInstance]
    }
	
	
	def setEmailConfig(){
		Map config = new HashMap();
		EmailSettings.list().each {EmailSettings dic ->
			config.put("host", dic.smtpServer);
			config.put("port", dic.smtpPort);
			config.put("username", dic.username);
			config.put("password", dic.password);
			config.put("props", dic.props);
			config.put("disabled", dic.enabled);
		}
		memcachedClient.set(Constant.MONITOR_EMAIL_CONFIG, 0, config);
	}


}
