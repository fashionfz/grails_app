package com.helome.monitor

import java.util.Date;

class LoginArea {

	long id;
	String prduct;
	String ip;
	int visitCount;
	Date visitDate;
	
	String country;
	String region;
	String city;
	String isp;
	String area;
	String county;
	Integer status;
	
	static mapping = {
		version false
	}
}
