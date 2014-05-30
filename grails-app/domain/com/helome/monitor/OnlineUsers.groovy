package com.helome.monitor

/**
 * 保存在线用户数
 */
class OnlineUsers {

    String product
    String device
    Integer amount
    Date time

    static constraints = {
        product nullable: true, blank: true
        device nullable: true, blank: true

    }

    static mapping = {
        version false
    }
}
