package com.helome.monitor.rabbitMQ.recieve

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
 * 接收MQ的消息,并处理
 * @author dt
 */
class ReciveMessageHandler {
	
	private ThreadPoolTaskExecutor messageHandleExecutor;
	
	private List<AbstractRecivedMessageMetadata> messageMetadata
	
	public void handleMessage(List<Map<String,Object>> items){
		messageMetadata?.each {
			it.messages = items
			messageHandleExecutor.execute(it)
		}
	}
	
	public void setMessageHandleExecutor(ThreadPoolTaskExecutor messageHandleExecutor){
		this.messageHandleExecutor = messageHandleExecutor
	}
	
	public void setMessageMetadata(List<AbstractRecivedMessageMetadata> messageMetadata){
		this.messageMetadata = messageMetadata
	}
}
