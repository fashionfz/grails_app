<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<r:require module="easyui"/>
</head>
<body>
	<ul id="tt" class="easyui-tree" style="width: 350px;"></ul>
	<br /><br />
    <shiro:hasRole name="ROLE_ADMIN">
    <a id="btn_ok" href="###" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
    </shiro:hasRole>
	<script type="text/javascript" src="${resource(dir:'js', file:'common.js')}"></script>
	<script type="text/javascript">
	$(function(){
		var tree_data = getServers();
		$('#tt').tree({
			data: tree_data,
			checkbox: true,
			lines: true,
			onlyLeafCheck: true
		});
	
		$('#btn_ok').click(f_save);
	});

	//获取服务器
	function getServers(){
		var result;
		$Q.ajax({
			url: 'monitorStrategyTree',
			dataType: 'json',
			async: false,
			success: function(data){
				result = data;
			}
		});
	
		return result;
	}

	//保存
	function f_save(){
		var nodes = $('#tt').tree('getChecked');
	
		var id;
		//类型：server, app……
		var type;
		//要提交到后台的数据
		var data = {
			server: {},
            app:{}
		};
		for(var i in nodes){
			id = nodes[i].attributes.id;
			type = nodes[i].attributes.type;
			if(!data[type][id]) data[type][id] = [];
			data[type][id].push(nodes[i].id);
		}
	
		$Q.ajax({
			url: 'monitorStrategySave',
			data: data,
			dataType: 'json',
			success: function(result){
				if(result.success === true) {
					$Q.info(null, '操作成功');
				} else {
					$Q.error(null, '操作失败');
				}
			}
		});
	}
</script>
</body>
</html>