package com.helome.monitor

/**
 * 系统角色
 */
class Role {
    /**
     * 英文名
     */
    String name
    /**
     * 中文名
     */
    String realname

    /**
     * 多对多：含有多个用户，多个权限
     */
    static hasMany = [users: User, permissions: String,menus: MonitorMenu]
    /**
     * 多对多关系中，User主导关系的维护
     */
    static belongsTo = User
	

    static constraints = {
        name(nullable: false, blank: false, unique: true)
        realname(nullable: false, blank: false, unique: true)
    }
}
