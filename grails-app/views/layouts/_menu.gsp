<%@ page import="com.helome.monitor.User" %>
<%@ page import="com.helome.monitor.DutyTable" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
 %>

<div class="head">
	<g:img file="logo.png" width="211" height="30" alt="helome" class="head-left" />
	<div class="head-right">
		<shiro:isLoggedIn>
			欢迎您,<label id="username"><shiro:principal /></label>!&nbsp;&nbsp;&nbsp;&nbsp;
			<label style="color:#ff5c36">今天值班人员为:
			<g:each var="duty" in="${DutyTable.where{dutyDate == df.parse(df.format(new Date())) }}">
			   <g:each var="user" in="${User.list()}">
				   <g:if test="${user.realname.equals(duty.dutyUser) }">
				       ${user.realname }TEL:${user.phone }
				   </g:if>
			   </g:each>
			</g:each> 
			</label>&nbsp;&nbsp;&nbsp;&nbsp;
			此次登陆IP为<label>${User.findByUsername(shiro.principal()).loginIp}</label>&nbsp;&nbsp;&nbsp;&nbsp;
			登陆时间：<label><g:formatDate format="yyyy年MM月dd日 HH:mm" date="${User.findByUsername(shiro.principal()).loginTime}"/></label>
			| <g:link controller="auth" action="signOut" class="system_logout">退出</g:link>
		</shiro:isLoggedIn>
		
	</div>
	<div class="draggable" id="changepasswd" style="width: 500px;height:200px; background:#fff;border-radius: 3px;border: 1px solid #45494d;background-color: #f5f5f5;position: relative;top: 200px;left: 300px;z-index: 1002;padding-bottom: 20px; display:none;">
		<span style="width: 500px;height: 30px;background: #45494d;color: #fff;line-height: 30px;position: relative; display:block;">	    
		     <h4>修改密码</h4>
		</span>
		<g:img dir="images" file="close.png" width="20" height="18" id="div-close" style="position: absolute;top: 5px;left: 470px;"/>
			<g:form action="changeUserPasswd" controller="user" method="post" style="width: 200px; margin-left: 0px;">
				<table border="0" style="width:500px;">
					<tr>
						<td style="text-align:right;">新密码：</td>
						<td><g:field type="password" id="passwordExpress2" name="passwordExpress" required="true" onblur="checkInput2()"/></td>
						<td width="250px"><div id="pwdRemark2"></div></td>
					</tr>
					<tr>
						<td style="text-align:right;">确认新密码：</td>
						<td><g:field type="password" id="confirmPasswordExpress2" name="confirmPasswordExpress" required="true" onblur="checkInput2()"/></td>
						<td><div id="repwdRemark2"></div></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align:center;"><g:submitButton name="提交" class="button"/></td>
					</tr>
					
				</table>
			</g:form>
	</div>
	<script type="text/javascript">
		//关闭隐藏弹出框
		$(function(){
			$('#div-close').click(function(){
				$('#changepasswd').hide();
			});
			$('#username').click(function(){
				$('#changepasswd').show();
			});
		});
		function checkPass(s) {

			if (s.length < 8) {

				return 0;
			}
			var ls = 0;

			if (s.match(/([a-z])+/)) {

				ls++;

			}

			if (s.match(/([0-9])+/)) {

				ls++;
			}

			if (s.match(/([A-Z])+/)) {

				ls++;

			}
			if (s.match(/[^a-zA-Z0-9]+/)) {

				ls++;

			}
			return ls

		}

		function checkInput2(){
			var pwd = $("#passwordExpress2").val();
			var repwd = $("#confirmPasswordExpress2").val();
			var i = checkPass(pwd);
			if (checkPass(pwd) < 3) {
				$("#pwdRemark2").html("密码要求长度大于8,字母大写，字母小写，数字，特殊字符中任意三个组合")
				$("#passwordExpress2").val("");
				return
			}else{
				$("#pwdRemark2").html("密码符合要求")
			}
			if(pwd != repwd){
				$("#repwdRemark2").html("两次密码不相同！")
				$("#confirmPasswordExpress2").val("");
				return
			}else{
				$("#repwdRemark2").html("密码符合要求")
			}
		}	
	</script>
</div>