<%@ page import="com.helome.monitor.WarningStrategy" %>
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
        <div>
            <p class="line" style="margin-top:0;"></p>
            <div id="container">
                <g:hasErrors bean="${warningStrategyInstance}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${warningStrategyInstance}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>
                <g:form action="warnStrategySave" controller="strategy" class="form1" method="post">
                    <g:render template="../warningStrategy/form"/>
                    <p class="submit">
                    	<g:submitButton name="submit" class="button" value="保存"/>
                    </p>
                </g:form>
            </div>

        </div>
		<script type="text/javascript">
            $(function(){
               $(".button").click(function(e){
                   //获取选择的用户
  //                 var users = [];
  //                 var ut = $("#ut");
  //                 var usersChecked = $("#ut").tree('getChecked');
 //                  $.each(usersChecked, function(i,r) {
                       //检查是否是叶子节点
 //                      if (ut.tree('isLeaf', r.target)) {
//                           users.push(r.id);
 //                      }
 //                  });
 //                  $("#users").val(users);
                   //获取选择的告警条件
                   var conditions = {};
                   var st = $("#st");
                   var stNodes = $("#st").tree('getChecked');
                   $.each(stNodes, function(i,r) {
                       //检查是否是叶子节点
                       if (st.tree('isLeaf', r.target)) {
                           var serverId = r.attributes.serverId;
                           if(serverId != undefined){
	                           if(!conditions[serverId]) conditions[serverId] = [];
	                           conditions[serverId].push(r.id);
                           }
                       }
                   });
                   $("#serverConditions").val(JSON.stringify(conditions));
                   $('form:first').submit();
               });
            });
        </script>
	</body>
</html>
