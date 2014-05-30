package com.helome.monitor.jobs

import com.helome.monitor.bean.HasSend;
import com.helome.monitor.support.WarnMessageSender;

/**
 * 延迟队列监听
 * @author dt
 */
class DelayQueueCheckerJob {
	
	static triggers = {
		simple name:'delayQueue Trigger', startDelay:20000, repeatInterval: 300000, repeatCount: 0
	}
	
	def execute(){
		log.info "DelayQueue Check Start!"
		Runnable removeFromDelayQueue = new Runnable(){
			public void run() {
				try {
					while(true){
						HasSend hasSend = WarnMessageSender.delayQueue.take();
						log.info hasSend.toString() + "Be Removed From DelayedQueue."
					}
				} catch (InterruptedException e) {
					log.error(e.getMessage());
				}
			}
		};
		new Thread(removeFromDelayQueue,"DelayQueueChecker").start();
	}
}
