package com.helome.monitor

import javax.persistence.Transient



class TimeSlot {

	long id;
	String prduct;
	String timeRang;
	int visitCount;
	Date visitDate;
	
	static mapping = {
		version false
	}
}
