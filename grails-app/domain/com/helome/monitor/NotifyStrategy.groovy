package com.helome.monitor

class NotifyStrategy {

	/* 通知方式,对应字典表alarm_type的配置 */
	Integer notifyWay
	
	/* 启用状态 */
	Integer enabled 
	
	/* 用户, notifyCapability 对应字典表的配置 */
	static hasMany = [user:User,notifyCapability:Integer]
	
	static mapping = {
		version false
		autoTimestamp false
    }
}
