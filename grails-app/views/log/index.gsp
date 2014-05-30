<%@ page import="com.helome.monitor.Dictionary" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>日志分析</title>
</head>
<body>
    <div class="content" role="main">
        <g:form action="index">
            <div class="row edit-properties fieldcontain">
                <label for="target">
                    按条件分析

                </label>
                <g:select name="target" from="${Dictionary.where {codeType == 'target'}}" optionValue="name" optionKey="id" value="${target}"/>
                <g:select name="time" from="${[[id:1, text:'1小时内'], [id:2, text:'24小时内'],
                        [id:3, text:'1周内'],[id:4, text:'1月内'],[id:5, text:'1年内']]}" optionKey="id" optionValue="text" value="${time}"/>

                <g:submitButton name="query1" value="查询"/>
            </div>
        </g:form>

        <div class="u104">
            <g:img uri="/images/u104_original.png"/>
        </div>
    </div>
</body>
</html>