package com.helome.monitor.indicator;

public class Mysql_check extends CommonParentBaseData {
	
	private static final long serialVersionUID = -1127094536496939216L;
	private String status;
	private String cpupct;
	private String mem;
	private String threadcount;
	private String abortedclient;
	private String abortedconnects;
	private String uptime;
	private String qps;
	private String slowsql;
	private String slowsqlatime;
	private String slowsqlttime;
	
	public Mysql_check() {
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCpupct() {
		return cpupct;
	}
	public void setCpupct(String cpupct) {
		this.cpupct = cpupct;
	}
	public String getMem() {
		return mem;
	}
	public void setMem(String mem) {
		this.mem = mem;
	}
	public String getThreadcount() {
		return threadcount;
	}
	public void setThreadcount(String threadcount) {
		this.threadcount = threadcount;
	}
	public String getAbortedclient() {
		return abortedclient;
	}
	public void setAbortedclient(String abortedclient) {
		this.abortedclient = abortedclient;
	}
	public String getAbortedconnects() {
		return abortedconnects;
	}
	public void setAbortedconnects(String abortedconnects) {
		this.abortedconnects = abortedconnects;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public String getQps() {
		return qps;
	}
	public void setQps(String qps) {
		this.qps = qps;
	}
	public String getSlowsql() {
		return slowsql;
	}
	public void setSlowsql(String slowsql) {
		this.slowsql = slowsql;
	}
	public String getSlowsqlatime() {
		return slowsqlatime;
	}
	public void setSlowsqlatime(String slowsqlatime) {
		this.slowsqlatime = slowsqlatime;
	}
	public String getSlowsqlttime() {
		return slowsqlttime;
	}
	public void setSlowsqlttime(String slowsqlttime) {
		this.slowsqlttime = slowsqlttime;
	}
	
}
