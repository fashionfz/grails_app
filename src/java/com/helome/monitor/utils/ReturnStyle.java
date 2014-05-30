/**
 * Project Name:monitor-webapp
 * @Title: ReturnStyle.java
 * @Package com.helome.monitor.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2014 All Rights Reserved. 
 * Company:helome.com
 * 
 * @author bin.deng@helome.com
 * @date 2014年4月28日 下午2:12:23
 * @version V1.0
 */
package com.helome.monitor.utils;

/**
 * @ClassName: ReturnStyle
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年4月28日 下午2:12:23
 *
 */
public enum ReturnStyle {
	DAY("day","1"),WEEK("week","7"),MONTH("month","30");
	
	String index;
	String name;
	
	private ReturnStyle(String name,String index){
		this.name = name;
		this.index = index;
	}
	public static String getName(String index) {
		for (ReturnStyle c : ReturnStyle.values()) {
			if (c.getIndex().equals(index)) {
				return c.name;
			}
		}
		return "day";
	}
	
	// get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
