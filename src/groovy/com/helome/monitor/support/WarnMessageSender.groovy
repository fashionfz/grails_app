package com.helome.monitor.support;

import java.util.concurrent.DelayQueue

import org.apache.commons.lang.time.DateFormatUtils
import org.apache.log4j.Logger

import com.helome.monitor.bean.HasSend
import com.helome.monitor.bean.HostBean
import com.helome.monitor.service.impl.NotifyService
import com.helome.monitor.utils.ApplicationContextHolder

/**
 * 警告信息发送器
 * @author hadoop
 */
public class WarnMessageSender {
	
	private Logger log = Logger.getLogger(WarnMessageSender.class);
	
	private HostBean hostBean;
	
	public static final DelayQueue<HasSend> delayQueue = new DelayQueue<HasSend>();
	
	public WarnMessageSender(HostBean hostBean) {
		this.hostBean = hostBean;
	}
	
	public void run() {
		
		if(delayQueue.contains(new HasSend(hostBean.getIp()))){
			log.info(hostBean.toString()+ " From the last Time Not yet Already.");
			return;
		}
		NotifyService notifyService = (NotifyService) ApplicationContextHolder.getBean("notifyService");
		String msg = getMessage()
		def resultMap = notifyService.notifyMessage(msg,2);
		if(resultMap?.sms == 200){
			HasSend hasSend = new HasSend(hostBean.getIp());
			hasSend.setLastSendTime(new Date());
			delayQueue.add(hasSend);
		}
	}
	
	/**
	 * 生成告警内容
	 * @return
	 */
	private String getMessage(){
		def grailsApplication = ApplicationContextHolder.getBean("grailsApplication");
		String messageTpl = grailsApplication.config.grails.sms.msgtpl
		String message = messageTpl
				.replace("#ip#", hostBean.getIp())
				.replace("#port#", hostBean.getPort())
				.replace("#time#", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return message;
	}
}
