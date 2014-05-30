package com.helome.monitor

/**
 * 
 * @ClassName: ServerIndexColumu
 * @Description: server指标要显示的字段
 * @author bin.deng@helome.com
 * @date 2014年3月28日 上午9:49:32
 *
 */
class ServerIndexColumu implements Comparable{

	long id;
	String fieldName;
	String labelName;
	String fieldType;
	int srot;
	int status;
	Indicator indicator;
    static constraints = {
    }

	/*
	 * <p>Title: compareTo</p>
	 * <p>Description: </p>
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return sort-o.sort;
	}}
