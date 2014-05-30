package com.helome.monitor.indicator;

public class Cpu extends CommonParentBaseData {

	private static final long serialVersionUID = 9001896130373759400L;
	
	private String userpct;
	private String nicepct;
	private String systempct;
	private String iowait;
	private String steal;
	private String idle;
	
	public Cpu() {}
	
	public String getUserpct() {
		return userpct;
	}
	public void setUserpct(String userpct) {
		this.userpct = userpct;
	}
	public String getNicepct() {
		return nicepct;
	}
	public void setNicepct(String nicepct) {
		this.nicepct = nicepct;
	}
	public String getSystempct() {
		return systempct;
	}
	public void setSystempct(String systempct) {
		this.systempct = systempct;
	}
	public String getIowait() {
		return iowait;
	}
	public void setIowait(String iowait) {
		this.iowait = iowait;
	}
	public String getSteal() {
		return steal;
	}
	public void setSteal(String steal) {
		this.steal = steal;
	}
	public String getIdle() {
		return idle;
	}
	public void setIdle(String idle) {
		this.idle = idle;
	}
}
