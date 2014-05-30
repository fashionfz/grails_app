package com.helome.monitor.utils

import java.text.SimpleDateFormat
import org.apache.commons.lang.time.FastDateFormat;

class DateStringHelper {
	
	public static Date string2Date(String src,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.parse(src)
	}
	
	public static String date2String(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.format(date);
	}
}
