import javax.servlet.http.HttpServletRequest

import net.rubyeye.xmemcached.XMemcachedClient

import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import com.helome.monitor.Constant
import com.helome.monitor.OperationMap
import com.helome.monitor.UserOperationLog


class SecurityFilters {
	
	@Autowired
	XMemcachedClient memcachedClient;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	def filters = {
		all(controller:'*', action:'*') {
			before = {
				if(controllerName == null || controllerName == null){
					return true;
				}
				Object map = memcachedClient.get(Constant.USER_PERMISSION);
				
				if(map.containsKey(controllerName+actionName)){
					List<String> roleNames = map.get(controllerName+actionName);
					boolean[] flags = SecurityUtils.getSubject().hasRoles(roleNames);
					for(boolean flag : flags){
						if(flag){
							return true;
						}
					}
					redirect(controller:'auth',action:'error');
					return false;
				}else{
					return true;
				}
	
			}
			after = { Map model ->
				if(controllerName.equals("error")&&actionName.equals("error")){
					errorLog(params,request,session);
				}else{
					request['actionName'] = actionName;
					request['controllerName'] = controllerName;
				}
			}
			afterView = {
				if(!controllerName.equals("error")&&!actionName.equals("error")){
					getOperation(actionName,controllerName,params,request);
				}
			}
		}
		
	}
	def getOperation(String action,String controller,Object params,HttpServletRequest request){
		Subject currentUser = SecurityUtils.getSubject();
		String username = currentUser.getPrincipal();
		List<OperationMap> list = OperationMap.where{
			actionName == action
			controllerName == controller
		}.list();
		for(OperationMap opt : list) {
			UserOperationLog log = new UserOperationLog();
			if("signIn".equals(action)){
				if(username!=null&&!"".equals(username)){
					params.password="***";
				}
				log.setOpt_result(username==null?0:1);
			}else if("save".equals(action)&&"user".equals(controller)){
				params.passwordHash="***";
				params.confirmPassword="***";
				log.setOpt_result(1);
			}else{
				log.setOpt_result(1);
			}
			log.setOptUserName(username==null?params.username:username);
			log.setTargetId(params.targetId==null?"":params.targetId);
			log.setTargetName(getMessage(params));
			log.setOperation(opt);
			log.setTypeName(opt.getLogTypeName());
			log.setClientIp(getIpAddr(request));
			log.setOptTime(new Date());
			log.setRemark(params.remark==null?"":params.remark);
			log?.save(flush:true);
			break;
		}
	}
	
	
	def errorLog(Object params,HttpServletRequest request,GrailsHttpSession session){
		def error = request.getAttribute("exception");
		String action = request['actionName'];
		String controller = request['controllerName'];
		List<OperationMap> list = OperationMap.where{
			actionName == action
			controllerName == controller
		}.list();
		Subject currentUser = SecurityUtils.getSubject();
		String username = currentUser.getPrincipal();
		for(OperationMap opt : list) {
			UserOperationLog log = new UserOperationLog();
			log.setOpt_result(0);
			log.setOptUserName(username==null?"":username);
			log.setTargetId("");
			log.setTargetName(error.toString());
			log.setOperation(opt);
			log.setTypeName("异常日志")
			log.setClientIp(getIpAddr(request));
			log.setOptTime(new Date());
			log.setRemark("");
			log?.save(flush:true);
			break;
		}
	}
	
	def getMessage(Map params){
		if(params){
			String message = params.toString();
			if(message.length()>255){
				message = params.toString().subSequence(0, 252)+"...";
			}
			return message;
		}
		return "";
	}
	
	def getMessage(String value){
		String message=value;
		if(message){
			if(message.length()>255){
				message = value.subSequence(0, 252)+"...";
			}
			return message;
		}
		return "";
	}
	
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
