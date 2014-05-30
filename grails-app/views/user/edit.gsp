<script>
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

	function checkInput(){
		var pwd = $("#passwordHash").val();
		var repwd = $("#confirmPassword").val();
		if(pwd.length>63){
			return;
		}
		var i = checkPass(pwd);
		if (checkPass(pwd) < 3) {
			$("#pwdRemark").html("密码要求长度大于8,字母大写，字母小写，数字，特殊字符中任意三个组合")
			$("#passwordHash").val("");
			return
		}else{
			$("#pwdRemark").html("密码符合要求")
		}
		if(pwd != repwd){
			$("#repwdRemark").html("两次密码不相同！")
			$("#confirmPassword").val("");
			return
		}else{
			$("#repwdRemark").html("密码符合要求")
		}
	}
</script>
<h4>
	<g:if test="${userInstance?.id}">
		修改账号
	</g:if>
	<g:else>
		新建账号
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18"
	class="dev-close" />
<g:form method="post" controller="user" action="save">
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td><g:hiddenField name="id" value="${userInstance?.id}" />
				${userInstance?.id}</td>
		</tr>
		<tr>
			<td style="text-align: right;">用户账号</td>
			<td><input type="text" name="username"
				value="${userInstance?.username}" /></td>
			<td><span class="new-list">用户账户可用数字和字母,不能用特殊字符.</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">真实姓名</td>
			<td><g:textField name="realname"
					value="${userInstance?.realname}" required="" /></td>
		</tr>
		<tr>
			<td style="text-align: right;">密码</td>
			<td><g:passwordField name="passwordHash" id="passwordHash" onblur="checkInput()"
					value="${userInstance?.passwordHash}" required="" /></td>
			<td><div id="pwdRemark"><span class="new-list"></span></div></td>
		</tr>
		<tr>
			<td style="text-align: right;">确认密码</td>
			<td><g:passwordField name="confirmPassword" onblur="checkInput()"
					value="${userInstance?.passwordHash}" required="" /></td>
			<td><div id="repwdRemark"><span class="new-list"></span></div></td>
		</tr>
		<tr>
			<td style="text-align: right;">角色</td>
			<td><g:select name="roles"
					from="${com.helome.monitor.Role.list()}"
					value="${userInstance?.roles?.id }" multiple="true" required=""
					optionKey="id" optionValue="realname" style="height: 70px;" /></td>
			<td><span class="new-list">系统默认角色为系统管理员,普通管理员,普通用户.</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">验证方式</td>
			<td><g:select optionKey="value" optionValue="name"
					name="validateType"
					from="${com.helome.monitor.Dictionary.where {codeType == 'validateType'}.list()}"
					value="${userInstance?.validateType}" /></td>
			<td><span class="new-list">验证方式默认本地验证,后期考虑RADISU等多种验证方式</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">邮箱地址</td>
			<td><input type="text" name="email"
				value="${userInstance?.email}" placeholder="必填哟~"
				required="required" /></td>
		</tr>
		<tr>
			<td style="text-align: right;">手机号码</td>
			<td><input type="text" name="phone"
				value="${userInstance?.phone}" placeholder="真的要必填哟"
				required="required" /></td>
			<td><span class="new-list">手机号码必须为11位,只能为数字.</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">启用</td>
			<td><g:radio checked="${userInstance?.enabled == 1}"
					name="enabled" value="1" />YES&nbsp;&nbsp;&nbsp; <g:radio
					checked="${userInstance?.enabled == 0}" name="enabled" value="0" />NO
			</td>
		</tr>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" /> 
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>
