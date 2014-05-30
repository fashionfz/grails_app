/**
 * Project Name:monitor-webapp
 * @Title: AccountEnableException.groovy
 * @Package com.helome.monitor.exception
 * @Description: TODO
 * Copyright: Copyright (c) 2014 All Rights Reserved. 
 * Company:helome.com
 * 
 * @author bin.deng@helome.com
 * @date 2014年4月8日 下午1:48:09
 * @version V1.0
 */
package com.helome.monitor.exception

import org.aspectj.weaver.ResolvedType.SuperClassWalker;

/**
 * @ClassName: AccountEnableException
 * @Description: TODO
 * @author bin.deng@helome.com
 * @date 2014年4月8日 下午1:48:09
 *
 */
class AccountEnableException extends Exception{

	String message;
	
	public AccountEnableException(String message){
		super();
		this.message = message;
	}
}
