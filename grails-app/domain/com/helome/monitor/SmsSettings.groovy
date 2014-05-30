package com.helome.monitor

/**
 * 短信设置
 */
class SmsSettings {

    Integer enabled;
    /**
     * 设备名称
     */
    String deviceName;
    /**
     * COM接口
     */
    String comport;
    /**
     * 每分钟发送短信数
     */
    Integer sendPerMin;
    /**
     * 短信后缀
     */
    String smsPostfix;

}