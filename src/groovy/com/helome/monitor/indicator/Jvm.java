package com.helome.monitor.indicator;

public class Jvm extends CommonParentBaseData{

	private static final long serialVersionUID = 7143872340432611588L;
	private String status;
	private String cpuPct;
	private String totalMem;
	private String youngPct;
	private String oldPct;
	private String permPct;
	private String activedThreadCount;
	private String loadClassCount;
	private String port;
	
	public Jvm() {
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCpuPct() {
		return cpuPct;
	}
	public void setCpuPct(String cpuPct) {
		this.cpuPct = cpuPct;
	}
	public String getTotalMem() {
		return totalMem;
	}
	public void setTotalMem(String totalMem) {
		this.totalMem = totalMem;
	}
	public String getYoungPct() {
		return youngPct;
	}
	public void setYoungPct(String youngPct) {
		this.youngPct = youngPct;
	}
	public String getOldPct() {
		return oldPct;
	}
	public void setOldPct(String oldPct) {
		this.oldPct = oldPct;
	}
	public String getPermPct() {
		return permPct;
	}
	public void setPermPct(String permPct) {
		this.permPct = permPct;
	}
	public String getActivedThreadCount() {
		return activedThreadCount;
	}
	public void setActivedThreadCount(String activedThreadCount) {
		this.activedThreadCount = activedThreadCount;
	}
	public String getLoadClassCount() {
		return loadClassCount;
	}
	public void setLoadClassCount(String loadClassCount) {
		this.loadClassCount = loadClassCount;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
