package com.helome.monitor.indicator;

public class Mysql_lock extends CommonParentBaseData{
	
	private static final long serialVersionUID = -4922871345615680844L;
	private String dbt;
	private String counts;
	
	public Mysql_lock() {
	}
	
	public String getDbt() {
		return dbt;
	}
	public void setDbt(String dbt) {
		this.dbt = dbt;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	
}
