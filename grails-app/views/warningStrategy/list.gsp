<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="告警策略" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
        <r:require modules="easyui"/>
        <script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
    	<script type="text/javascript">
    		$(window).load(function() {
    			$('._edit_').click(function() {
    				if($('.dev-new').children().length > 0) return;
    				openWindow('warnStrategyEdit', {id : $(this).attr('data-option')});
    			});
    			function openWindow(url, data) {
    				$.get(url, data || {}, function(data) {
    					var _formPage = $(data);
    					var account_form = $('#account_form');
    					account_form.append(_formPage);
    					$('.dev-close').click(function() {
    						$(this).parent().hide().children().remove();
    					});
    					account_form.show();
    				});
    			}
			});

			function openWindow(url, data) {
				$.get(url, data || {}, function(data) {
					var _formPage = $(data);
					var account_form = $('#account_form');
					account_form.append(_formPage);
					$('.dev-close').click(function() {
						$(this).parent().hide().children().remove();
					});
					account_form.show();
				});
			}


			function edit(wid){
				if($('.dev-new').children().length > 0) return;
				openWindow('warnStrategyEdit', {id : wid});
			}
    	</script>
	</head>
	<body>
       <div class="alarm-log">
			<div class="zhanghao-but">
				<g:img dir="images" file="new.png" class="_edit_" />
				<div id="fade" class="black_overlay"></div>
				<div class="dev-new draggable" id="account_form"></div>
			</div>
            <table id="dg" style="height: 450px;" title="告警策略">
            </table>
        </div>
		<script type="text/javascript">
            $(document).ready(function(){
                $("#dg").datagrid({
                    url: 'warnStrategyList',
                    toolbar: "#toolbar",
                    singleSelect: true,
                    fitColumns: true,
                    nowrap: false,
                    rownumbers: true,
                    pagination: true,
                    autoRowHeight: true,
                    pageSize: 10,
                    striped: true,
                    checkOnSelect: false,
                    selectOnCheck: false,
                    showFooter: true,
                    columns: [[
                        {field:'name',title:'策略名称',rowspan:2,width:80,sortable:true},
                        {field:'serverNames',title:'服务器',rowspan:2,width:80,sortable:true},
                        {field:'enabled',title:'启用状态',rowspan:2,width:80,sortable:true, formatter:function(value, row, index) {
                            if (value == 1) {
                                return '启用';
                            } else {
                                return '停用';
                            }
                        }},
                        {field:'opt', title:'操作',width:160, formatter: function(value, row, index){
                            var text = "<a href='javascript:;' onclick='onEnable(" + row.id + ");'>";
                            if (row.enabled == 1) {
                                text += "停用";
                            } else {
                                text += "启用";
                            }
                            text += "</a>";
                            text += "| <a class=\"_edit_\" onclick='edit("+row.id+");' href='javascript:;'>编辑</a>";
                            text += "<shiro:hasRole name='ROLE_ADMIN'> | <a href='javascript:;' onclick='onDelete(" + row.id + ");'>删除</a></shiro:hasRole>";
                            return text;
                        }}
                    ]]
                });

                $('#createGroup').click(function(){
                    $('#winIframe').attr("src", "create");
                    var $win;
                    $win = $('#win').window({
                        title: '告警策略信息',
                        width: 800,
                        height: 500,
                        shadow: true,
                        modal: true,
                        iconCls: 'icon-add',
                        closed: true,
                        top: 150,
                        left: 300,
                        minimizable: false,
                        maximizable: false,
                        collapsible: false,
                        onClose: function() {
                            $('#dg').datagrid('reload');
                        }
                    });
                    $win.window('open');
                });
            });

            function closeWindow() {
                $('#win').window('close');
            }

            //编辑
            var onEdit = function(id) {
                $('#winIframe').attr("src", "edit?id=" + id);
                var $win;
                $win = $('#win').window({
                    title: '告警策略信息',
                    width: 800,
                    height: 500,
                    shadow: true,
                    modal: true,
                    iconCls: 'icon-update',
                    closed: true,
                    top: 150,
                    left: 300,
                    minimizable: false,
                    maximizable: false,
                    collapsible: false,
                    onClose: function() {
                        $('#dg').datagrid('reload');
                    }
                });
                $win.window('open');
            };

            //删除
            var onDelete = function(id) {
                $.messager.confirm('删除确认', '你确定要删除该用户组吗?', function(r){
                    console.log(r);
                    if (r) {
                        $.post("warnStrategyDelete", {id:id}, function(data){
                            if (data.success) {
                                $.messager.show({title:"消息提示", msg:"删除成功！"});
                            } else{
                                $.messager.show({title:"消息提示", msg:"删除失败！"});
                            }
                            $('#dg').datagrid('reload');
                        });
                    }

                });
            };

            var onEnable = function(id) {
                $.post("warnStrategyEnable", {id:id}, function(data){
                    if (data.success) {
                        $.messager.show({title:"消息提示", msg:"成功！"});
                    } else {
                        $.messager.show({title:"消息提示", msg:"失败！"});
                    }
                    $('#dg').datagrid('reload');
                });
            };
        </script>                
	</body>
</html>
