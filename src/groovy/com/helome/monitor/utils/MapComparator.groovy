package com.helome.monitor.utils;

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Map比较器,构造器需提供一个用于比较的key.
 * order 默认按倒序来排
 * @author dt
 *
 */
public class MapComparator implements Comparator<Map<String,Object>> {

	String key;
	String order = "desc";
	Class clzz = Integer.class
	
	public MapComparator(String key) {
		this.key = key;
	}

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		def _o1 = 0;
		def _o2 = 0;
		if(Integer.class.equals(clzz)){
			_o1 = NumberUtils.toInt(String.valueOf(o1.get(key)), 0);
			_o2 = NumberUtils.toInt(String.valueOf(o2.get(key)), 0);
		}else if(Double.class.equals(clzz)){
			_o1 = NumberUtils.toDouble(String.valueOf(o1.get(key)), 0.0d);
			_o2 = NumberUtils.toDouble(String.valueOf(o2.get(key)), 0.0d);
		}
		int diff = 0
		if("asc".equalsIgnoreCase(order)){
			diff = _o1 <=> _o2
		}else{
			diff = _o2 <=> _o1
		}
		diff
	}
	
}
