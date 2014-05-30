<!DOCTYPE html>
<html>
	<head>
		<title>错误页面</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<g:if test="${errors}">
			<g:each var="error" in="${errors}">
				<li>${error }</li>
			</g:each>
		</g:if>
	</body>
</html>
