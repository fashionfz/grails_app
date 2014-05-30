<%@ page import="com.helome.monitor.User; org.apache.shiro.SecurityUtils" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title><g:layoutTitle default="监控系统"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<r:require modules="application, draggable"/>
    <g:layoutHead/>
    <r:layoutResources />
</head>
	<body>
		<div id="wrap">
			<g:render template="/layouts/menu"></g:render>
			<div class="index-center">
       			<g:render template="/layouts/left"/>
       			<div id="right-body">
       				<g:render template="/layouts/subnavigation"></g:render>
       				<g:layoutBody/>
       				<div class="footer">Copyright © 2014 Helome.  All Rights Reserved.</div>
       			</div>
       		</div>
		</div>
        <r:layoutResources />
	</body>
</html>