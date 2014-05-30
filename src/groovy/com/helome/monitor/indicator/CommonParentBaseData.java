package com.helome.monitor.indicator;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommonParentBaseData implements Serializable {
	
	private Long id;
	private String server_name;
	private String time;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

}
