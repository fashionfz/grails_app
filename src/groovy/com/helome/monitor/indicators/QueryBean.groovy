package com.helome.monitor.indicators

/**
 * 监测数据实体
 * @author dt
 */
class QueryBean implements Serializable {
	
	/**
	 * 条件查询字段: 开始时间
	 */
	Date startDate
	/**
	 * 条件查询字段：结束时间
	 */
	Date endDate
	
	/**
	 * 条件查询字段：服务器组
	 */
	String servergroup
	/**
	 * 入库字段、条件查询字段：主机名
	 */
	String hostname
	/**
	 * 标示字段: 指标名.
	 * 此字段的值范围约定为此类持有的指标引用名
	 */
	String indicatorname
	
	@Override
	public String toString() {
		return "BaseData [time= $time, hostname=$hostname, startDate=$startDate, endData=$endDate]"
	}
}
