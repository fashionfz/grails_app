package com.helome.monitor

import java.text.SimpleDateFormat;

/**
 * 值班子表
 * @author Administrator
 *
 */
class DutyTable implements Comparable{

	/**
	 * id
	 */
	Integer id;
	/**
	 * 值班日志
	 */
	Date dutyDate;
	/**
	 * 值班时间段
	 */
	String timeRang;
	/**
	 * 当前值班人员
	 */
	String dutyUser;
	/**
	 * 同班人员
	 */
	String otherUser;
	/**
	 * 换班的id
	 */
	Integer exchangeId;
	
	
	Integer week;
	
	Integer sort;
	
	static belongsTo = [dutyMain:DutyMain]
	
    static constraints = {
    }


	@Override
	public int compareTo(Object o) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DutyTable tmp = (DutyTable)o;
		Date time1= df.parse(df.format(tmp.getDutyDate())+" "+tmp.getTimeRang().substring(0, tmp.getTimeRang().lastIndexOf("-")));
		Date time2= df.parse(df.format(dutyDate)+" "+timeRang.substring(0, timeRang.lastIndexOf("-")));
		return time1.getTime()-time2.getTime();
	}}
