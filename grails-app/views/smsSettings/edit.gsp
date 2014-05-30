<%@ page import="com.helome.monitor.SmsSettings" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'screen.css')}" type="text/css">
		<g:set var="entityName" value="短信通知设置" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
        <script type="text/javascript">
            $(document).ready(function(){
               $('.button').click(function(){
                  $("form:first").submit();
               });
            });
        </script>
	</head>
	<body>
        <p class="line" style="margin-top:0;"></p>
        <div id="container">
            <g:hasErrors bean="${smsSettingsInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${smsSettingsInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
            <g:form class="form4" method="post" action="smsSettingSave">
                <g:hiddenField name="id" value="${smsSettingsInstance?.id}" />
                <g:hiddenField name="version" value="${smsSettingsInstance?.version}" />
                <g:render template="form"/>
                <p class="submit">
                    <shiro:hasRole name="ROLE_ADMIN">
                        <button class="button">保存</button>
                    </shiro:hasRole>
                    %{--<g:actionSubmit class="button" action="save" value="保存"/>--}%
                </p>
            </g:form>
        </div>


		%{--<div id="edit-smsSettings" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${smsSettingsInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${smsSettingsInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${smsSettingsInstance?.id}" />
				<g:hiddenField name="version" value="${smsSettingsInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="btn-primary" action="save" value="保存" />
				</fieldset>
			</g:form>
		</div>--}%
	</body>
</html>
