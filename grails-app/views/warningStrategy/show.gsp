
<%@ page import="com.helome.monitor.WarningStrategy" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'warningStrategy.label', default: 'WarningStrategy')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-warningStrategy" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-warningStrategy" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list warningStrategy">
			
				<g:if test="${warningStrategyInstance?.disabled}">
				<li class="fieldcontain">
					<span id="disabled-label" class="property-label"><g:message code="warningStrategy.disabled.label" default="Disabled" /></span>
					
						<span class="property-value" aria-labelledby="disabled-label"><g:fieldValue bean="${warningStrategyInstance}" field="disabled"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${warningStrategyInstance?.enabled}">
				<li class="fieldcontain">
					<span id="enabled-label" class="property-label"><g:message code="warningStrategy.enabled.label" default="Enabled" /></span>
					
						<span class="property-value" aria-labelledby="enabled-label"><g:fieldValue bean="${warningStrategyInstance}" field="enabled"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${warningStrategyInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="warningStrategy.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${warningStrategyInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${warningStrategyInstance?.serverWarnConditions}">
				<li class="fieldcontain">
					<span id="serverWarnConditions-label" class="property-label"><g:message code="warningStrategy.serverWarnConditions.label" default="Server Warn Conditions" /></span>
					
						<g:each in="${warningStrategyInstance.serverWarnConditions}" var="s">
						<span class="property-value" aria-labelledby="serverWarnConditions-label"><g:link controller="serverInfoWarnCondition" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${warningStrategyInstance?.users}">
				<li class="fieldcontain">
					<span id="users-label" class="property-label"><g:message code="warningStrategy.users.label" default="Users" /></span>
					
						<g:each in="${warningStrategyInstance.users}" var="u">
						<span class="property-value" aria-labelledby="users-label"><g:link controller="user" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${warningStrategyInstance?.id}" />
					<g:link class="edit" action="edit" id="${warningStrategyInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
