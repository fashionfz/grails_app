package com.helome.monitor

/**
 * 统计每天注册用户
 */
class RegisterUsers {

    String product
    Integer amount
    Date time

    static constraints = {
        product nullable: true, blank: true
    }

    static mapping = {
        version false
    }
}
