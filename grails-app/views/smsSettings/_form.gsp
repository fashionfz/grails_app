<%@ page import="com.helome.monitor.SmsSettings" %>
<%@ page import="com.helome.monitor.Dictionary" %>

<fieldset>
    <legend>邮件设置</legend>
    <p>
        <label for="enabled">开启短信通知设置:</label>
        <g:select optionKey="value" optionValue="name" name="enabled" from="${Dictionary.where {codeType == 'bool'}.list()}" value="${smsSettingsInstance?.enabled}"/>
    </p>
    <p>
        <label for="deviceName">设备:</label>
        <g:select optionKey="name" optionValue="name"  name="deviceName" from="${Dictionary.where {codeType == 'model'}.list()}" value="${smsSettingsInstance?.deviceName}"/>
    </p>
    <p>
        <label for="comport">COM端口:</label>
        <g:select optionKey="value" optionValue="name" name="comport" from="${Dictionary.where {codeType == 'comport'}.list()}" value="${smsSettingsInstance?.comport}"/>
    </p>
    <p>
        <label for="comport">每分钟最多发送数目:</label>
        <g:field name="sendPerMin" type="number" value="${smsSettingsInstance.sendPerMin}" required=""/>
    </p>
    <p>
        <label for="smsPostfix">短信后缀:</label>
        <g:textField name="smsPostfix" value="${smsSettingsInstance?.smsPostfix}"/>
    </p>
</fieldset>
%{--
<div class="row edit-properties fieldcontain ${hasErrors(bean: smsSettingsInstance, field: 'enabled', 'error')} required">
    <label class="property-label" for="enabled">
        开启短信通知设置:
        <span class="required-indicator">*</span>
    </label>
    <g:select optionKey="value" optionValue="name" name="enabled" from="${Dictionary.where {codeType == 'bool'}.list()}" value="${smsSettingsInstance?.enabled}"/>
</div>

<div class="row edit-properties fieldcontain ${hasErrors(bean: smsSettingsInstance, field: 'comport', 'error')} ">
    <label class="property-label" for="comport">
        COM端口:

    </label>
    <g:select optionKey="value" optionValue="name"  name="comport" from="${Dictionary.where {codeType == 'comport'}.list()}" value="${smsSettingsInstance?.comport}"/>
</div>

<div class="row edit-properties fieldcontain ${hasErrors(bean: smsSettingsInstance, field: 'deviceName', 'error')} ">
    <label class="property-label" for="deviceName">
        设备:

	</label>
    <g:select optionKey="name" optionValue="name"  name="deviceName" from="${Dictionary.where {codeType == 'model'}.list()}" value="${smsSettingsInstance?.deviceName}"/>

</div>

<div class="row edit-properties fieldcontain ${hasErrors(bean: smsSettingsInstance, field: 'sendPerMin', 'error')} required">
    <label class="property-label" for="sendPerMin">
        每分钟最多发送数目:
        <span class="required-indicator">*</span>
    </label>
    <g:field name="sendPerMin" type="number" value="${smsSettingsInstance.sendPerMin}" required=""/>
</div>

<div class="row edit-properties fieldcontain ${hasErrors(bean: smsSettingsInstance, field: 'smsPostfix', 'error')} ">
	<label class="property-label" for="smsPostfix">
        短信后缀:

    </label>
	<g:textField name="smsPostfix" value="${smsSettingsInstance?.smsPostfix}"/>
</div>--}%

