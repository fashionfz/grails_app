<%@ page import="com.helome.monitor.ServerInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="服务器信息" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'screen.css')}" type="text/css">
        <r:require module="easyui"/>
	</head>
	<body>
        <div>
            <p class="line" style="margin-top:0;"></p>
            <div id="container">
                <g:hasErrors bean="${serverInfoInstance}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${serverInfoInstance}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form class="form1" method="post">
                    <g:render template="form"/>
                    <p class="submit">
                        <g:actionSubmit class="button" action="save" value="保存"/>
                    </p>
                </g:form>
            </div>
        </div>
		<script type="text/javascript" src="${resource(dir: 'js', file: 'jshashtable.js')}"></script>
	</body>
</html>
