package com.helome.monitor

/**
 * 短信设置
 */
class SmsSettingsController {

    static allowedMethods = [save: "POST"]

    def index() {
        params.id = 1
        redirect(action: "edit", params: params)
    }

    def save() {

        def smsSettingsInstance = SmsSettings.get(params.id)
        if (!smsSettingsInstance) {
            smsSettingsInstance = new SmsSettings(params)
        } else {
            smsSettingsInstance.properties = params
        }
        if (!smsSettingsInstance.save(flush: true)) {
            render(view: "edit", model: [smsSettingsInstance: smsSettingsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: ['SmsSettings', smsSettingsInstance.id])
        redirect(action: "edit", id: smsSettingsInstance.id)
    }


    def edit() {
        def smsSettingsInstance = SmsSettings.get(params.id)
        if (!smsSettingsInstance) {
            smsSettingsInstance = new SmsSettings();
        }

        [smsSettingsInstance: smsSettingsInstance]
    }

}
