/**
 * Project Name:monitor-webapp
 * @Title: DealServerData.groovy
 * @Package com.helome.monitor.store
 * @Description: TODO
 * Copyright: Copyright (c) 2014 All Rights Reserved. 
 * Company:helome.com
 * 
 * @author bin.deng@helome.com
 * @date 2014年4月22日 下午2:43:35
 * @version V1.0
 */
package com.helome.monitor.store

import javax.annotation.Resource

import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ClassUtils

import com.helome.monitor.rabbitMQ.recieve.AbstractRecivedMessageMetadata
import com.helome.monitor.service.impl.DealServerInfoDataService

/**
 * @ClassName: DealServerData
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年4月22日 下午2:43:35
 *
 */
@Component("dealServerData")
class DealServerData extends AbstractRecivedMessageMetadata{

	@Autowired
	DealServerInfoDataService dealServerInfoDataService;
	/*
	 * <p>Title: run</p>
	 * <p>Description: </p>
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		def clazzName = ClassUtils.getShortName(getElementsType())
		dealServerInfoDataService.dealData(this.getMessages(),this.getServerName(),clazzName)
	}
}
