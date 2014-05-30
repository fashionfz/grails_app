<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>账户信息</title>
</head>
<body>
	<div class="alarm-log">
	<g:form method="post" controller="user" action="emailSettingSave">
		<table border="0" class="email-table">
			<tr>
				<td class="email-first">
					<input type="hidden" name="id" value="${config.id }" />
				    <label>
				    <g:if test="${config.enabled==0 }">
				    	<input name="enabled" type="radio" value="0" checked="checked"/>on
				    	&nbsp;&nbsp;
				    	<input name="enabled" type="radio" value="1" />off
				    </g:if>
				    <g:else>
				    	<input name="enabled" type="radio" value="0"/>on
				    	&nbsp;&nbsp;
				    	<input name="enabled" type="radio" value="1" checked="checked"/>off
				    </g:else>
				    </label>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>开启邮箱通知设置</strong></td>
			</tr>
			<tr>
				<td class="email-first">SMTP服务器</td>
				<td>&nbsp;&nbsp;<input type="text" name="smtpServer" value="${config.smtpServer }"/></td>
			</tr>
			<tr>
				<td class="email-first">服务器端口</td>
				<td>&nbsp;&nbsp;<input type="text" name="smtpPort" value="${config.smtpPort }"/></td>
			</tr>			
			<tr>
				<td class="email-first">发送账号</td>
				<td>&nbsp;&nbsp;<input type="text" name="username" value="${config.username }"/></td>
			</tr>
			<tr>
				<td class="email-first">密码</td>
				<td>&nbsp;&nbsp;<input type="text" name="password" value="${config.password }"/></td>
			</tr>
			<tr>
				<td class="email-first">其他属性</td>
				<td>&nbsp;&nbsp;<input type="text" name="props" value="${config.props }"/></td>
			</tr>			
			<tr>
				<td class="email-first">每分钟最多发送数目</td>
				<td>&nbsp;&nbsp;<input type="text" value="${config.sendPerMin }" name="sendPerMin" style="width: 169px;" /></td>
			</tr>
			<tr>
				<td class="email-first">邮件标题编码方式</td>
				<td>&nbsp;&nbsp;<input type="text" value="${config.titleEncoding }" name="titleEncoding" style="width: 169px;" /></td>
			</tr>
			<tr>
				<td class="email-first">邮件内容编码方式</td>
				<td>&nbsp;&nbsp;<input type="text" value="${config.contentEncoding }" name="contentEncoding" style="width: 169px;" /></td>
			</tr>
		</table>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" /> 
		<input type="submit" value="确定" class="button" />
	</div>	
	</g:form>	
	</div>
</body>
</html>
