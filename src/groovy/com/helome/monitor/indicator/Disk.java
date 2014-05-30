package com.helome.monitor.indicator;

public class Disk extends CommonParentBaseData {
	private static final long serialVersionUID = 2839464798328896574L;

	private String used;
	private String available;
	private String usedpct;
	
	public Disk() {
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getUsedpct() {
		return usedpct;
	}

	public void setUsedpct(String usedpct) {
		this.usedpct = usedpct;
	}
	
}
