package com.helome.monitor.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 疑似故障机器IP已发告警记录
 * @author hadoop
 *
 */
public class HasSend implements Delayed {
	
	private String ip;
	
	private Date lastSendTime;
	
	public HasSend(String ip) {
		this.ip = ip;
	}

	public int compareTo(Delayed o) {
		long substrac = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
		if(substrac == 0) return 0;
		return substrac > 0 ? 1 : -1;
	}

	public long getDelay(TimeUnit unit) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastSendTime);
		calendar.add(Calendar.MINUTE,5);
		long substract = calendar.getTime().getTime() - Calendar.getInstance().getTime().getTime();
		return unit.convert(substract, TimeUnit.MILLISECONDS);
		 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HasSend other = (HasSend) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		return true;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	@Override
	public String toString() {
		return "HasSend [ip=" + ip + ", lastSendTime=" + lastSendTime + "]";
	}
}
