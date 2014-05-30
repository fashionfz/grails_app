package com.helome.monitor.indicator;

public class Mem extends CommonParentBaseData {
	
	private static final long serialVersionUID = -9217008377259170211L;
	
	private String used;
	private String free;
	private String usedpct;
	
	public Mem() {
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getUsedpct() {
		return usedpct;
	}
	public void setUsedpct(String usedpct) {
		this.usedpct = usedpct;
	}
}
