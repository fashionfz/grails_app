package com.helome.monitor.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext ac;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}

	public static Object getBean(String name){
		return ac.getBean(name);
	}
}
