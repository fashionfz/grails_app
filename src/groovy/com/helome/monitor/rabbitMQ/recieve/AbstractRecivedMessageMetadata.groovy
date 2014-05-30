package com.helome.monitor.rabbitMQ.recieve

public abstract class AbstractRecivedMessageMetadata implements Runnable {
	
	List messages
	
	public AbstractRecivedMessageMetadata(){
	} 
	
	/**
	 * 获取集合类的元素类型
	 * @return 类包路径全名
	 */
	public String getElementsType(){
		messages.get(0)?.get('class').substring(6)
	}
	
	public List getMessages(){
		return this.messages
	}
	
	public String getServerName(){
		messages.get(0)?.server_name
	}
	
	public void setMessages(List messages){
		this.messages = messages
	}
	
}
