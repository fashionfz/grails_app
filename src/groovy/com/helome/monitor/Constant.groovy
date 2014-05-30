package com.helome.monitor


class Constant {
	/* {IP:HostBean} */
	public static final String CACHE_UNREACHABLE_NAME = "unreachablehost";
	
	/* {HostBean:Integer} */
	public static final String CACHE_REPEATCOUNT_NAME = "repeatcount";
	
	static class IndicatorNameMeta{
		public static final String TABLENAME_PREFIX = "monitor_";
		public static final String INDICATOR_NAME_CPU = TABLENAME_PREFIX+"cpu";
		public static final String INDICATOR_NAME_MEM = TABLENAME_PREFIX+"mem";
		public static final String INDICATOR_NAME_DISK = TABLENAME_PREFIX+"disk";
		public static final String INDICATOR_NAME_IFSTAT = TABLENAME_PREFIX+"ifstat";
		public static final String INDICATOR_NAME_JVM = TABLENAME_PREFIX+"jvm";
		public static final String INDICATOR_NAME_MYSQL_CHECK = TABLENAME_PREFIX+"mysql_check";
		public static final String INDICATOR_NAME_MYSQL_LOCK = TABLENAME_PREFIX+"mysql_lock";
		public static final String INDICATOR_NAME_CONCOUNT = TABLENAME_PREFIX+"concount";
		
		public static final List INDICATOR_NAMES = [INDICATOR_NAME_CPU, INDICATOR_NAME_MEM, INDICATOR_NAME_DISK,
												INDICATOR_NAME_IFSTAT,INDICATOR_NAME_JVM,INDICATOR_NAME_MYSQL_CHECK,
												INDICATOR_NAME_MYSQL_LOCK,INDICATOR_NAME_CONCOUNT]
	}
	
	public static final String COLUMN_FAMILY = "indicator";
	
	/**邮件发送*/
	public static final int NOTIFY_EMAIL = 1;
	/**短信发送*/
	public static final int NOTIFY_SMS = 2;
	/**邮件加短信发送*/
	public static final int NOTIFY_SMS_AND_EMAIL = 3;
	
	
	/**性能阀值告警*/
	public static final int NOTIFY_CAPABILITY =1;
	/**宕机通知*/
	public static final int NOTIFY_DOWNTIME =2;
	/**网页检查通知*/
	public static final int NOTIFY_CHECK_URL=3;
	/**值班通知*/
	public static final int NOTIFY_ON_DUTY=4
	
	public static final String[] RETURN_RATE_DAY = ["1","2","3","4","5","6","7","14","30"];
	
	public static final String[] RETURN_RATE_WEEK = ["1","2","3","4","5","6","7","8"];
	
	public static final String[] RETURN_RATE_MONTH = ["1","2","3","4","5","6"];
	
	public static final String[] RETURN_RATE_STYLE = ["day","week","month"];
	/**用户权限memcache缓存key*/
	public static final String USER_PERMISSION = "monitor_user_permission";
	/**换班通知下个班次的时间memcache缓存key*/
	public static final String EXCHANGE_ON_DUTY_TIME = "exchange_on_duty_time";
	/**换班通知当前要发送的内容memcache缓存key*/
	public static final String EXCHANGE_ON_DUTY_CONTEXT = "exchange_on_duty_context";
	
	public static final String LAST_TIME_IFSTAT = "lastTimeIfstat";
	
	public static final String MONITOR_WARNING_STRATEGY ="monitor_warning_strategy"
	
	public static final String MONITOR_EMAIL_CONFIG="monitor_eamil_config";
	
	public static final String MONITOR_CHECK_URL = "check_url";
	
}
