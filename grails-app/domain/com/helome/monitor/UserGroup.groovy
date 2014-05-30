package com.helome.monitor
/**
 * 用户组
 */
class UserGroup {
    /**
     * 组名
     */
    String name
    /**
     * 备注
     */
    String remark
    Integer enabled = 1
    Integer disabled = 0
	
	Integer groupType=0;

    static hasMany = [users:User]

    static constraints = {
        name(nullable: false, blank: false)
        remark(nullable: true)
    }
	static namedQueries = {
		avaliableUserGroup {
			eq 'disabled',0
		}
	}
}
