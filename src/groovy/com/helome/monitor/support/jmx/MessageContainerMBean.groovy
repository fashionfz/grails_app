package com.helome.monitor.support.jmx

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.helome.monitor:name=mqContainer", description="")
class MessageContainerMBean {
	
	@Autowired
	private SimpleMessageListenerContainer messageContainer
	
	@ManagedOperation
	public void start(){
		messageContainer.start();
	}
	
	@ManagedOperation
	public void stop(){
		messageContainer.stop();
		messageContainer.isActive()
	}
	
	@ManagedOperation
	public int getActiveConsumerCount(){
		messageContainer.getActiveConsumerCount()
	}
	
	@ManagedOperation
	public boolean isRunning(){
		messageContainer.isRunning()
	}
	
	@ManagedOperation
	public boolean isActive(){
		messageContainer.isActive()
	}
	
	@ManagedOperation
	public boolean isAutoStart(){
		messageContainer.isAutoStartup()
	}
}
