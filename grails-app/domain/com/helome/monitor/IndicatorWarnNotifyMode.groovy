package com.helome.monitor

/**
 * 指标告警方式
 */
class IndicatorWarnNotifyMode {
    /**
     * 告警方式:1--邮件 ，2--短信
     */
    Integer type
    /**
     * 告警内容模板
     */
    String template

    static belongsTo = [IndicatorWarnCondition]

    static constraints = {
        type(nullable: false)
        template(nullable: true)
    }
}
