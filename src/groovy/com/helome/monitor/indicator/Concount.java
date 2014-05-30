package com.helome.monitor.indicator;

public class Concount extends CommonParentBaseData {

	private static final long serialVersionUID = 8881192618323130778L;
	
	private String concount;
	private String foreignip;
	private String contype;
	
	public Concount() {
	}
	public String getConcount() {
		return concount;
	}
	public void setConcount(String concount) {
		this.concount = concount;
	}
	public String getForeignip() {
		return foreignip;
	}
	public void setForeignip(String foreignip) {
		this.foreignip = foreignip;
	}
	public String getContype() {
		return contype;
	}
	public void setContype(String contype) {
		this.contype = contype;
	}
}
