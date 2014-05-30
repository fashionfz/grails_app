package com.helome.monitor.utils

import java.text.SimpleDateFormat;
import java.util.Date;

class NDateStringHelper {
	private SimpleDateFormat sdf;

	public NDateStringHelper(String pattern) {
		this.sdf = new SimpleDateFormat(pattern);
	}
	
	public Date string2Date(String src){
		sdf.parse(src)
	}
	
	public String date2String(Date date){
		sdf.format(date);
	}
	
	public String replaceDateStringPattern(String source, String destPattern){
		def date = string2Date(source)
	}
}
