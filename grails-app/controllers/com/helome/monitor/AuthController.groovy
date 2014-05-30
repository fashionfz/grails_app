package com.helome.monitor

import java.text.MessageFormat
import javax.servlet.http.HttpServletRequest

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils

import com.helome.monitor.exception.AccountEnableException

/**
 * 用户登录，退出控制器类
 */
class AuthController {
    def shiroSecurityManager

    static allowedMethods = [signIn: "POST"]

    def index = { redirect(action: "login", params: params) }

    def login = {
        return [username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri]
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
		
	
    def signIn = {
        def authToken = new UsernamePasswordToken(params.username, params.password as String)
        if (params.rememberMe) {
            authToken.rememberMe = true
        }
        def targetUri = params.targetUri ?: "/monitor/index"
        SavedRequest savedRequest = WebUtils.getSavedRequest(request)
        if (savedRequest) {
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        }
		def m = [username: params.username]
        try {
            def user = User.findByUsername(params.username)
			//判断账户是否启用
			if(user&&user.enabled == 0){
				throw new AccountEnableException("ACCOUNT_UNENABLE");
			}
			SecurityUtils.subject.login(authToken)
            user.loginTime = new Date()
            user.loginIp = getIpAddr(request)
            user.confirmPassword = user.passwordHash
            user.save(flush: true)
            log.info "Redirecting to '${targetUri}'."
            redirect(uri: targetUri)
        }
		catch (UnknownAccountException ex) {
			log.info "account not found for user '${params.username}'."
			def message = MessageFormat.format(message(code: 'login.failed'),params.username);
			if (params.rememberMe) {
				m["rememberMe"] = true
			}
			if (params.targetUri) {
				m["targetUri"] = params.targetUri
			}
			params.remark = "账号不存在";
			render(view: "login", model:[message:message])
		}
        catch (AuthenticationException ex) {
            log.info "Authentication failure for user '${params.username}'."
            def message = MessageFormat.format(message(code: 'login.failed'),params.username);
            if (params.rememberMe) {
                m["rememberMe"] = true
            }
            if (params.targetUri) {
                m["targetUri"] = params.targetUri
            }
			params.remark = "密码错误";
            render(view: "login", model:[message:message])
        }catch(AccountEnableException ex){
			log.info "account unenable for user '${params.username}'."
			def message = MessageFormat.format(message(code: 'account.unenable'),params.username);
			if (params.rememberMe) {
				m["rememberMe"] = true
			}
			if (params.targetUri) {
				m["targetUri"] = params.targetUri
			}
			params.remark = flash.message;
			render(view: "login", model:[message:message])
        }
    }

    def signOut = {
        // Log the user out of the application.
		params.username = SecurityUtils.subject.getPrincipal();
        SecurityUtils.subject?.logout()
        webRequest.getCurrentRequest().session = null

        // For now, redirect back to the home page.
        redirect(uri: "/")
    }

    def unauthorized = {
        render "You do not have permission to access this page."
    }
	
	def error(){
		
	}
}
