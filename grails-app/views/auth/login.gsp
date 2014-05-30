<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>监控系统</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}" type="text/css">
</head>
<body>
	<div class="main">
		<div class="login-logo">
			<img src="/monitor-webapp/static/images/login-logo.png" width="395" height="38" alt="logo" />
		</div>
		<div class="login-bg">
			<div class="login-center">
				<g:form action="signIn" controller="auth">
					<table>
						<tr>
							<td>
								<div class="tag-container">
									<input name="username" type="text" placeholder="用户名" class="txt-one" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="tag-container">
									<input name="password" type="password" placeholder="密码" class="txt-one" />
								</div>
							</td>
						</tr>
						<g:if test="${message}">
							<tr>
								<td class="list">${message}</td>
							</tr>
						</g:if>
					</table>
					<input type="submit" value="登录" class="btn-default btn-red" />
				</g:form>
			</div>
		</div>
		<div class="footer">Copyright © 2014 Helome. All Rights Reserved.</div>
	</div>
</body>
</html>
