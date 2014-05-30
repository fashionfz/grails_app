/**
 * 查询排班信息
 */
function query(){
	var mainId = $("#mainId").val();
	window.location="dutyTabletList?dutyId="+mainId;
}
/**
 * 高亮显示值班人员名称
 */
function queryByName(){
	var name = $("#queryName").val();
	if(name ==null ||name==''){
		return;
	}
	var patt =  new RegExp(name);
	$("table[name='item']").find("td").each(function (){ 
		$(this).removeClass("td1");  
		var result=patt.exec($(this).text());
		if(result != null && result != ''){
			$(this).addClass("td1");
		}
	});
}
/**
 * 调班中选择排班表
 * @param tab
 */
function selectTab(tab){
	if($(tab).hasClass("td2")){
		$(tab).removeClass("td2");  
	}else{
		var selectNum = checkSelect();
		if(selectNum>1){
			alert("不能选超过两个");
			return;
		}
		$(tab).addClass("td2");
	}
}
/**
 * 调班中检查选中的个数
 * @returns {Number}
 */
function checkSelect(){
	var selectNum =0;
	$("#dutyContent").find("td").each(function (){ 
		if($(this).hasClass("td2")){
			selectNum+=1;
		}
	});
	return selectNum;
}
/**
 * 保存调班记录到数据库
 */
function change(){
	var table1="";
	var table2="";
	$("#dutyContent").find("td").each(function (){ 
		if($(this).hasClass("td2")){
			if(table1 ==""){
				table1=$(this).attr("id");
			}else{
				table2=$(this).attr("id");
			}
		}
	});
	if(table1==""||table2==""){
		return;
	}
	var mainId = $("#queryMainId").val();
	var url = "change?dutyTableId1="+table1+"&dutyTableId2="+table2+"&dutyId="+mainId;
	$.post(url,function(data){
		alert("调班成功！");
		var mainId = data.dutyId;
		window.location="dutyTabletList?dutyId="+mainId;
	});
}	
/**
 * 保存值班配置信息
 */
function saveConfig(){
	document.getElementById("frm").submit();
}
/**
 * 编辑值班人员信息
 * @param id
 */
function editPerson(id){
	var opt ="<a href=\"javascript:void();\" onclick=\"savePerson("+id+")\">保存</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:void();\" onclick=\"delUser("+id+")\">删除</a>"
	var tmp="";
	$("#tr"+id).find("td").each(function(){
		if($(this).attr("name") == "opt"){
			$(this).empty();
			$(this).append(opt);
		}else if($(this).attr("name") == "way"){
			tmp = $(this).text();
			$(this).empty();
			url = "queryNotifyWay?name="+tmp;
			$.post(url,callbackFun(this,tmp));
		}else{
			tmp = $(this).text();
			$(this).empty();
			$(this).append("<input style='width:80px' type='text' value=\""+tmp+"\"/>");
		}
	});
}

/**
 * post回调函数
 * @param td
 * @param name
 * @returns {Function}
 */
function callbackFun(td,name){   
    return function(data){   
		var array = data.data;
		var html="<select>"
		for(var val in array){
			if(name == array[val].notifyname){
				html+="<option selected='true' value=\""+array[val].id+"\">"+array[val].notifyname+"</option>";
			}else{
				html+="<option value=\""+array[val].id+"\">"+array[val].notifyname+"</option>";
			}
		}
		html+="</select>";
		$(td).append(html);
    }   
}  

/**
 * 保存值班人员到数据库
 * @param id
 */
function savePerson(id){
	var opt ="<a href=\"javascript:void();\" onclick=\"editPerson("+id+")\">编辑</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:void();\" onclick=\"delUser("+id+")\">删除</a>"
	var tmp="";
	var data="id="+id;
	$("#tr"+id).find("td").each(function(){
		if($(this).attr("name") == "opt"){
			$(this).empty();
			$(this).append(opt);
		}else if($(this).attr("name") == "way"){
			tmp = $(this).find("select").val();
			var nme = $(this).find("option:selected").text();
			data += "&"+$(this).attr("name")+"="+tmp;
			$(this).empty();
			$(this).append(nme);
		}else{
			tmp = $(this).find("input").val();
			data += "&"+$(this).attr("name")+"="+tmp;
			$(this).empty();
			$(this).append(tmp);
		}
	});
	var url = "savePerson?"+encodeURI(data);
	$.post(url,function(){
		
	});
}

/**
 * 删除值班人员
 * @param id
 */
function delUser(id){
	var url = "deletePerson?id="+id;
	$.post(url,function(data){
		var rs = data.data;
		alert("删除成功！");
		$("#tr"+rs).remove();
	})
}
