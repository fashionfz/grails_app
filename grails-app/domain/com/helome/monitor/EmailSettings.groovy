package com.helome.monitor

/**
 * 系统邮箱设置
 */
class EmailSettings {

    /**
     * 是否启用
     */
    Integer enabled;
    /**
     * smtp服务地址
     */
	String smtpPort;
    String smtpServer;
    String username;
    String password;
	String props;
    /**
     * 每分钟发送邮件数
     */
    Integer sendPerMin;
    /**
     * 内容编码
     */
    String contentEncoding;
    /**
     * 标题编码
     */
    String titleEncoding;

}
