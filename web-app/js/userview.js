function saveConfig(){
	var menuIds = "";
	$("input[type=checkbox]:checked").each(function(){
		menuIds = menuIds+$(this).attr("name")+",";
	});
	var url = "saveConfigRole?roleId="+$("#roleId").val()+"&menuIds="+menuIds;
	window.location = url;
	alert("修改成功");
}
function changeRole(){
	window.location = "userView?roleId="+$("#roleId").val();
}
function hideDiv(img,id){
	$(img).remove();
	$("#imgDiv"+id).append("<img src='../images/hide.png' onclick='show(this,"+id+")'></img>");
	$("#div"+id).hide();
}
function show(img,id){
	$(img).remove();
	$("#imgDiv"+id).append("<img src='../images/show.png' onclick='hideDiv(this,"+id+")'></img>");
	$("#div"+id).show();
}
function selectMenu(sel,id,path){
	if($(sel).attr('checked')==undefined){
		$("#div"+id).find(":checkbox").each(function(){
			$(this).attr("checked",false);
		});
	}else{
		$("#div"+id).find(":checkbox").each(function(){
			$(this).attr("checked",true);//打勾   
		});
		var menuArray = path.split("/");
		for(var i=menuArray.length-2;i>0;i--){
			$("#box"+menuArray[i]).attr("checked",true);//打勾   
		}
	}
}