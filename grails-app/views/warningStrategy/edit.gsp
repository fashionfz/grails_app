<%@ page import="com.helome.monitor.WarningStrategy" %>
     <!--  <link rel="stylesheet" href="${resource(dir: 'css', file: 'screen.css')}" type="text/css">-->
        <script type="text/javascript">
            $(function(){
                $(".button").click(function(e){
                    //获取选择的告警条件
                    var conditions = {};
                    var st = $("#st");
                    var warnId = $("#warnId").val();
                    var name = $("#name").val();
                    var enable = $("#enable").val();
                    
                    var stNodes = $("#st").tree('getChecked');
                    $.each(stNodes, function(i,r){
                        //检查是否是叶子节点
                        if (st.tree('isLeaf', r.target)) {
                            var serverId = r.attributes.serverId;
                            if(!conditions[serverId]) conditions[serverId] = [];
                            conditions[serverId].push(r.id);
                        }
                    });
                    var url = "warnStrategySave?id="+warnId+"&name="+name+"&enable="+enable+"&serverConditions="+JSON.stringify(conditions);
                    $.post(url,function(data){
                        
                    });
                    window.location.href="warnStragegyIndex";
                    //$(".form1").parent().hide().children().remove();
                });
            });
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
            <g:hasErrors bean="${warningStrategyInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${warningStrategyInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
            <g:form class="form1" method="post">
                <g:hiddenField name="id" id="warnId" value="${warningStrategyInstance?.id}" />
                <g:hiddenField name="version" value="${warningStrategyInstance?.version}" />
                <g:render template="../warningStrategy/form"/>
                <p class="submit">
                    <shiro:hasRole name="ROLE_ADMIN">
                        <input type="button" class="button" value="保存"/>
                    </shiro:hasRole>
                </p>
            </g:form>
