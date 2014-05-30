package com.helome.monitor

/**
 * 排班主信息
 * @author Administrator
 *
 */
class DutyMain {

	/**
	 * id
	 */
	Integer id;
	/**
	 * 排班的起始日期
	 */
	Date beginDate;
	/**
	 * 排班的结束日期
	 */
	Date endDate;
	/**
	 * 排班创建人
	 */
	String createUser;
	/**
	 * 创建时间
	 */
	Date createTime;
	/**
	 * 班次名称
	 */
	String name;
	/**
	 * 时间段
	 */
	String timeRang;
	
	Integer weekCount;
	
	Integer rangCount;
	
    static constraints = {
    }
}
