package com.helome.monitor.indicator;

public class Ifstat extends CommonParentBaseData {

	private static final long serialVersionUID = -4493386836386726279L;
	private String recivebyte;
	private String transmitbyte;
	public Ifstat() {
	}
	public String getRecivebyte() {
		return recivebyte;
	}
	public void setRecivebyte(String recivebyte) {
		this.recivebyte = recivebyte;
	}
	public String getTransmitbyte() {
		return transmitbyte;
	}
	public void setTransmitbyte(String transmitbyte) {
		this.transmitbyte = transmitbyte;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

