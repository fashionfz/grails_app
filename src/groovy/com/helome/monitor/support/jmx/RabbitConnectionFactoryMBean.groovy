package com.helome.monitor.support.jmx

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.helome.monitor:name=rabbitMQ", description="")
class RabbitConnectionFactoryMBean {

	@Autowired
	private CachingConnectionFactory cachingConnectionFactory;
	
	@ManagedOperation
	@ManagedOperationParameters([
			@ManagedOperationParameter(name="username", description = "MessageQueue UserName"),
			@ManagedOperationParameter(name="passwd",description="MessageQueue Password")])
	public void setUser(String username,String passwd){
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setPassword(passwd);
	}
	
	@ManagedOperation
	@ManagedOperationParameters(@ManagedOperationParameter(name="address",description="MessageQueue Server Addresses"))
	public void setAddress(String address){
		cachingConnectionFactory.setAddresses(address);
	}
	
}
