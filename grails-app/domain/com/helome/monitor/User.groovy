package com.helome.monitor

/**
 * 账户信息
 */
class User {
    /**
     * 用户名
     */
    String username
    /**
     * 加密后的密码(加密算法sha256)
     */
    String passwordHash
    /**
     * 真实姓名
     */
    String realname
    /**
     * 验证类型：LOCAL，RADIUS,LDAP
     */
    String validateType
    /**
     * 邮箱地址
     */
    String email
    /**
     * 启用
     */
    Integer enabled
    /**
     * 手机
     */
    String phone
    /**
     * 确认密码(加密算法sha256)
     */
    String confirmPassword
    /**
     * 逻辑删除标记
     */
    Integer disabled
    /**
     * 登录IP
     */
    String loginIp
    /**
     * 登录时间
     */
    Date loginTime

    static hasMany = [roles: Role]
	
    static mapping = {
        roles lazy: false
    }

    static transients = ['confirmPassword']

    static constraints = {
        username nullable: false, blank: false, unique: true
        realname blank: false, nullable: false, unique: true
        email email: true, blank: false, nullable: false
        passwordHash blank: false, nullable: false
        phone nullable: true
        loginIp nullable: true
        loginTime nullable: true
        confirmPassword bindable: true,validator: { value, obj, errors  ->
            if( obj.properties['passwordHash'] && value != obj.properties['passwordHash'] ) return errors.reject('not.confirm', '两次输入的密码不一致！')
        }
    }
}
