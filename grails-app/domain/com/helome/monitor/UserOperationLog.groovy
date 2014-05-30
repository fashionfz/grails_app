package com.helome.monitor

class UserOperationLog {
	
	/**日志id*/
	long id;
	/**操作人名称*/
	String optUserName;
	/**操作目标id*/
	String targetId;
	/**操作目标名称*/
	String targetName;
	/**用户ip*/
	String clientIp;
	/**操作结果*/
	int opt_result;
	/**操作时间*/
	Date optTime;
	/**备注*/
	String remark;
	/**操作类型*/
	
	String typeName;
	
	static belongsTo =[operation:OperationMap];
	
    static constraints = {
    }
}
