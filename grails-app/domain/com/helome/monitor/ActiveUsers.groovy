package com.helome.monitor

/**
 * 统计活跃用户数
 */
class ActiveUsers {
    /**
     * 产品
     */
    String product
    /**
     * 活跃用户数
     */
    Integer amount
    /**
     * 时间
     */
    Date time

    static constraints = {
        product nullable: true, blank: true
    }

    static mapping = {
        version false
    }
}
