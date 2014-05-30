package com.helome.monitor.service.impl

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpException
import org.apache.commons.httpclient.HttpMethod
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import static com.helome.monitor.Constant.*

import com.helome.monitor.NotifyStrategy;
import com.helome.monitor.User
import com.helome.monitor.UserGroup

/**
 * 提醒服务,目前包括短信提醒和邮件提醒.<br>
 * @author dt
 */
class NotifyService {
	
	static transactional = true
	
	def grailsApplication
	
	def mailService
	
	@Autowired
	private HttpClient httpClient
	
	@Autowired
	private DefaultHttpMethodRetryHandler defaultHttpMethodRetryHandler
	/**
	  * notifyMessage
	  * 根据功能模块类型对用户发送消息<br>
	  * 目前email没有返回结果.SMS调用返回结果详看notifyBySMS方法
	  * @param notifyMsg 发送的信息
	  * @param type 功能模块类型
	  * @return map [sms:s_resultCode,email:e_resultCode]
	  */
	@Transactional
	def notifyMessage(String notifyMsg,Integer type){
		
		// 返回给调用端的结果集,
		def notify_result_code = [sms:0,email:0] 
	    
		def phoneNotifyNumbers = new HashSet();
		def emailNotifyAddress = new HashSet();
			
		// 查询符合条件的通知策略
		def notifyStrategys = NotifyStrategy.findAllByEnabled(1)
		
		if(notifyStrategys){
			notifyStrategys.each { NotifyStrategy notifyStrategy ->
				if(notifyStrategy.notifyCapability.contains(type)){
					notifyStrategy.user?.each {User user ->
						switch(notifyStrategy.notifyWay){
							case NOTIFY_EMAIL: // 邮件
								emailNotifyAddress << user.email
							break;
							case NOTIFY_SMS: // 短信
								phoneNotifyNumbers << user.phone
							break;
							case NOTIFY_SMS_AND_EMAIL: // 邮件 + 短信
								phoneNotifyNumbers << user.phone
								emailNotifyAddress << user.email
							break;
						}
					}
				}
			}
	     // 发送短信和邮件.
		 notifyByEmail(notifyMsg, emailNotifyAddress as String[])
		 int smsCode = notifyBySMS(notifyMsg, phoneNotifyNumbers.join(";"))
		 notify_result_code.sms = smsCode
		} else {
			log.info "Has No Matched Type [$type] NotifyStrategy Found"
		}
		notify_result_code
	}
	
	/**
	 * 发送短信通知<br>
	 * 如果短信功能是关闭的,在执行逻辑上默认是发送成功的,返回发送成功状态码200<br>
	 * 返回状态：-1: 接收者号码为空<br>
	 * 			-2: Http异常<br>
	 * 			-3：Http编码异常<br>
	 * 			-4：IO异常<br>
	 * 			200：发送成功<br>
	 * @param notifyMsg 通知内容
	 * @param smsRecievers 接收者手机号, 多个号码请用;隔开
	 * @return	Http发送状态
	 */
	private int notifyBySMS(String notifyMsg,String smsRecievers){
		int status = -1;
		def enabled = grailsApplication.config.grails.sms.enable
		if(enabled){
			if(!smsRecievers) return status;
			String smsRequestURI = buildSmsRequestURI(notifyMsg, smsRecievers)
			HttpMethod method = null
			try {
				method = new GetMethod(smsRequestURI);
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, defaultHttpMethodRetryHandler)
				status = httpClient.executeMethod(method)
			} catch (HttpException e) {
				status = -2;
				log.error(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				status = -3;
				log.error(e.getMessage());
			} catch (IOException e) {
				status = -4;
				log.error(e.getMessage());
			}finally{
				method.releaseConnection();
			}
			log.info("Send SMS Statu: "+status);
		}else{
			status = 200;
			log.warn("sms.enable Config is False");
		}
		status
	}
	
	private String buildSmsRequestURI(String notifyMsg,String destNumber){
		StringBuilder builder = new StringBuilder();
		def host = grailsApplication.config.grails.sms.host
		def userid = grailsApplication.config.grails.sms.userId
		def password = grailsApplication.config.grails.sms.password
		def bizType = grailsApplication.config.grails.sms.bizType
		builder.append(host);
		builder.append("?UserId=" + userid);
		builder.append("&Password=" + password);
		builder.append("&MsgContent=" + URLEncoder.encode(notifyMsg, "utf-8"));
		builder.append("&DestNumber=" + destNumber); //propCfg.getString("sms.destNumber")
		builder.append("&SendTime=");
		builder.append("&SubNumber=");
		builder.append("&BatchSendID=");
		builder.append("&BizType=" + bizType);
		builder.append("&WapURL=");
		builder.toString()
		
	}
	
	/**
	 * Email提醒服务
	 * @param notifyMsg 通知内容
	 * @param recivers 接收者号码
	 * @return 
	 */
	private void notifyByEmail(String notifyMsg,String... recivers){
		if(!recivers){
			log.info("Email User List is Empty.");
			return;
		}
		log.info("Send Email: [recivers:$recivers,Msg:$notifyMsg]");
		try{
			mailService.sendMail {
				from grailsApplication.config.grails.mail.username
				to recivers
				subject "告警信息!"
				body notifyMsg
			}
		}catch(Exception e){
			log.info("发送邮件失败！"+recivers)
		}
	}
	
	/**
	 * 
	  * notifyUser
	  * 根据给定的用户发送信息
	  *
	  * @param notifyMsg 发送的信息
	  * @param users 接收消息的用户
	  * @param type 发送方式
	 */
	@Transactional
	public void notifyUser(String notifyMsg,Collection<User> users,int type){
		def enabled = grailsApplication.config.grails.sms.enable
		Set<String> emailRecievers = new HashSet<String>();
		Set<String> phones = new HashSet<String>();
		switch(type){
			case NOTIFY_EMAIL:
				for (User user : users) {
					if(user.getEmail()) {
						emailRecievers.add(user.getEmail());
					}
				}
				break;
			case NOTIFY_SMS:
				if (enabled) {
					for(User user : users) {
						if (user.getPhone()) {
							phones.add(user.getPhone());
						}
					}
				}
				break;
			case NOTIFY_SMS_AND_EMAIL:
				for(User user : users) {
					if (user.getEmail()) {
						emailRecievers.add(user.getEmail());
					}
					if (user.getPhone()) {
						phones.add(user.getPhone());
					}
				}
				break;
			default:
				break;
		}
		if(phones.size()>0)
			notifyBySMS(notifyMsg,phones.join(";"));
		if(emailRecievers.size()>0)
			notifyByEmail(notifyMsg,(String[])emailRecievers.toArray());
	}
}
