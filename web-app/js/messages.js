var indicatorNames = {
			'cpu':{field:'indicator_cpu',title:'CPU(avg)',formatter:function(value,rowData,rowIndex){return (100-parseFloat(rowData.object.cpu.idle)).toFixed(2);}},
			'disk':{field:'indicator_disk',title:'DISK(kb)',formatter:function(value,rowData,rowIndex){return rowData.object.disk.used;}},
			'ifstat':{field:'indicator_ifstat',title:'IFSTAT(bps)',formatter:function(value,rowData,rowIndex){return '[recive:'+rowData.object.ifstat.recivebyte+',transmit:'+rowData.object.ifstat.transmitbyte+']';}},
			'mem':{field:'indicator_mem',title:'MEM(kb)',formatter:function(value,rowData,rowIndex){return rowData.object.mem.used;}}
        }; 
	var columnArray = [
	            {field:'time',title:'时间',sortable:true,align:'center',formatter:function(value,rowData,rowIndex){return rowData.showedTime;}},
      			{field:'group',title:'服务器组'},
   				{field:'hostname',title:'服务器名',formatter:function(value,rowData,rowIndex){return rowData.object.hostname;}},
   				{field:'manager',title:'管理员'}];
	var initColumnArray = [];
	function loadColumn(){
		$.extend(initColumnArray,columnArray);
		for(var index in indicatorNames){
			initColumnArray.push(indicatorNames[index]);
		}
	}
    $(function(){
    	loadColumn();
    	$('#dg').datagrid({
			url:'messagesdata',
			queryParams:{},
			//fitColumns:true,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageNumber:1,
			pageSize:20,
			pageList:[20,50,100],
			columns:[initColumnArray],
			onLoadSuccess:function(data){}
		});
    });