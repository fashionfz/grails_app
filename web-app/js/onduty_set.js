var timeId=0;
var timeRangNum =1;
/**
 * 点击增加值班时段，增加输入框
 */
function addTimeRang(){
	var button=""+
	"<tr id=\"tr"+timeId+"\"><td><input type='text' id=\"workrang"+timeId+"\" name=\"workrang\" class=\"input-time\"/>"+
	"<input type='button' class=\"edit-button\" onclick='delInput(\"#tr"+timeId+"\");'/></td></tr>";  
	$("#listTimeRang").append(button);  
	timeId++;
	timeRangNum=timeRangNum+1;
	$("#timeRangNum").val(timeRangNum);
}
/**
 * 删除值班时段输入框以及按钮
 * @param btn
 * @param id
 * @param lab
 */
function delInput(id){
	$(id).remove();
	timeRangNum=timeRangNum-1;
	$("#timeRangNum").val(timeRangNum);
}

/**
 * 覆盖Date方法
 */
Date.prototype.Format = function(fmt)   
{ //author: meizz    
  var o = {   
    "M+" : this.getMonth()+1,                 //月份    
    "d+" : this.getDate(),                    //日    
    "h+" : this.getHours(),                   //小时    
    "m+" : this.getMinutes(),                 //分    
    "s+" : this.getSeconds(),                 //秒    
    "q+" : Math.floor((this.getMonth()+3)/3), //季度    
    "S"  : this.getMilliseconds()             //毫秒    
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}    
/**
 * 计算两个日期相差的天数
 */
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays  
    aDate  =  sDate1.split("-")  
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
    aDate  =  sDate2.split("-")  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
    return  iDays  
}        
 /**
  * 输入值班人员后自动添加文本输入框显示值班人员
  */
function addTextBox(val,name) { 
	if(val == 0){
		var button="<li><input type='text' id=\"user"+val+"\" name=\"user"+val+"\" value=\""+name+"\" disabled='disabled' class='input-name'/><button onclick=\"changeUser("+val+")\" class='but-bg'></button></li>";
		$("#listuser").append(button);  
	}else{
		var button="<li><input type='text' id=\"user"+val+"\" name=\"user"+val+"\" value=\""+name+"\" disabled='disabled' class='input-name'/><button onclick=\"changeUser("+val+")\" class='but-bg but-bg1'></button></li>";
		$("#listuser").append(button);  
	}
}  
/**
 * 上调下调值班人员顺序
 * @param id
 */
function changeUser(id){
	var tmpVal = "";
	if(id == 0){
		tmpVal = $("#user0").val();
		$("#user0").val($("#user1").val());
		$("#user1").val(tmpVal);
	}else{
		tmpVal = $("#user"+id).val();
		$("#user"+id).val($("#user"+(id-1)).val());
		$("#user"+(id-1)).val(tmpVal);
	}
}
/**
 * 输入值班人员后自动添加文本输入框显示值班人员
 */
function writeUser(){
	$("#listuser").empty();
	var str = $('#dutynames').val()
	var names = str.split(',');
	$('#user0').val(names[0]);
	for(var val in names){
			addTextBox(val,names[val]);
	}
}

/**
 * 生成排班表
 */
function createDuty(){
	//清空所有下拉选项
	clearSelect();
	var start =$('#startDate').val();
	var end =$('#endDate').val();
	var special = $("#special").val();
	//验证输入
	if(start == null || start == ""){
		alert("请选择起始日期！");
		return;
	}
	if(end == null || end == ""){
		alert("请选择结束日期");
		return;
	}
	//计算排班的时间差（天数）
	var towDaylang = DateDiff(start,end)+1;
	start = start.replace(/\-/g, "/" ); 
	end = end.replace(/\-/g, "/" ); 
	var stDate = new Date(start);
	var ndDate = new Date(end);
	if(stDate-ndDate>0){
		alert("结束日期不能小于起始日期！");
		return;
	}
	var workrang = $('#workrang').val();
	var rangArray = findTimeRang();
	var sleepArray = findSleepRang();  
	var userArray = findListUser();
	var allLang = (rangArray.length-1)*towDaylang;
	var specialArray = special.split(",");
	//循环每个时间段
	var pi=0;
	var weekIndex=0;
	for(var i=0;i<allLang;i++){
		var tr ="<tr>"+
			       "<td><select id=\"dutyTime"+i+"\"></select></td>"+
					"<td><select id=\"dutyrang"+i+"\"></select></td>"+
				    "<td><select id=\"dutyuser"+i+"\"></select></td>"+
			        "<td><select id=\"otheruser"+i+"\"></select></td>"+
			"</tr>"
		jQuery(tr).appendTo("#dutyTable");
		var tdate = new Date(start);
		tdate.setDate(tdate.getDate()+parseInt(i/(rangArray.length-1)));
		var tmpWeek = tdate.getDay();
		for(var s in specialArray){
			if(specialArray[s] == tdate.Format("yyyy-MM-dd")){
				tmpWeek=0;
			}
		}
		
		
		fillSelect(workrang,start,i,allLang,rangArray,sleepArray,userArray,tmpWeek);
		//周末
		if(tmpWeek == 0 || tmpWeek == 6){
			fillUserSelect(i,pi,userArray);
			weekIndex++;
			if(weekIndex%(rangArray.length-1)==0)
			{
				pi++;
			}
		}else{
			fillUserSelect(i,pi,userArray);
			pi++;
		}
	}
}

function checkSpecial(){
	
}

/**
 * 查找值班时段所有输入框中的内容
 * @returns
 */
function findTimeRang(){
	var rangs = $("#workrang").val()+",";
	$("#listTimeRang").find("input[type=text]").each(function (){ 
		rangs+=$(this).attr("value")+",";
	});
	var rangArray = rangs.split(",");
	return rangArray;
}

/**
 * 查找周末输入框中的内容
 * @returns
 */
function findSleepRang(){
	var rangs = "";
	$("#listSleepRang").find("input[type=text]").each(function (){ 
		rangs+=$(this).attr("value")+",";
	});
	var rangArray = rangs.split(",");
	return rangArray;
}

/**
 * 查找值班人员所有输入框中的内容
 * @returns
 */
function findListUser(){
	var rangs = "";
	$("#listuser").find("input[type=text]").each(function (){ 
		rangs+=$(this).attr("value")+",";
	});
	var rangArray = rangs.split(",");
	return rangArray;
}

/**
 * 填充值班人员下拉
 * @param i
 * @param pi
 * @param userArray
 */
function fillUserSelect(i,pi,userArray){
	for(var val=0;val<userArray.length-1;val++){
		var index = (parseInt(val)+pi)%(userArray.length-1);
		jQuery("<option value='"+userArray[index]+"'>"+userArray[index]+"</option>").appendTo("#dutyuser"+i);
		jQuery("<option value='"+userArray[index]+"'>"+userArray[index]+"</option>").appendTo("#otheruser"+i);
	}
}

/**
 * 填充值班日期和时间段下拉
 * @param workrang
 * @param start
 * @param i
 * @param allLang
 * @param rangArray
 * @param sleepArray
 * @param userArray
 */
function fillSelect(workrang,start,i,allLang,rangArray,sleepArray,userArray,tmpWeek){
	var tdate = new Date(start);
	var index =i%(rangArray.length-1);
	tdate.setDate(tdate.getDate()-1);
	for(var m=0;m<allLang;m++){
		if(m%(rangArray.length-1)==0){
			tdate.setDate(tdate.getDate()+1);
		}
		if(m == i){
			jQuery("<option value='"+tdate.Format("yyyy-MM-dd")+"' selected='true'>"+
					tdate.Format("yyyy-MM-dd")+"</option>").appendTo("#dutyTime"+i);
			
		} else {
			jQuery("<option value='"+tdate.Format("yyyy-MM-dd")+"'>"+
					tdate.Format("yyyy-MM-dd")+"</option>").appendTo("#dutyTime"+i);
		}

	}
	//填充值班时间段
	if(tmpWeek==0||tmpWeek==6){//周末
		for(var val=0;val<sleepArray.length-1;val++){
			jQuery("<option selected='true' value='"+sleepArray[val]+"'>"+sleepArray[val]+"</option>").appendTo("#dutyrang"+i);
			//标红周末节假日
			$("#dutyrang"+i).addClass("sl1");
		}
	}else{//工作日
		for(var val=0;val<rangArray.length-1;val++){
			if(index == val){
				jQuery("<option selected='true' value='"+rangArray[val]+"'>"+rangArray[val]+"</option>").appendTo("#dutyrang"+i);
			}else{
				jQuery("<option value='"+rangArray[val]+"'>"+rangArray[val]+"</option>").appendTo("#dutyrang"+i);
			}
		}
	}
}
/**
 * 清除所有生成的排班信息
 */
function clearSelect(){
	for(var i=0;i<7;i++){
		$("#dutyTime"+i).empty();
		$("#dutyrang"+i).empty();
		$("#dutyuser"+i).empty();
		$("#otheruser"+i).empty();
	}
	$("#dutyTable").empty();
}

/**
 * 保存排班信息到数据库
 */
function saveDutyTable(){
	var beginDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	var timeRang = $("#workrang").val()+",";
	$("#listTimeRang").find("input[type=text]").each(function (){ 
		timeRang+=$(this).attr("value")+",";
	});
	var rangs = timeRang.split(",");
	var i=0;
	var content="";
	while(true){
		var dateId = "#dutyTime"+i;
		var rangId="#dutyrang"+i;
		var userId="#dutyuser"+i;
		var otherId="#otheruser"+i;
		var dutyTime = $(dateId).val();
		var dutyRang = $(rangId).val();
		var dutyUser = $(userId).val();
		var otherUser = $(otherId).val();
		var sort =0;
		try{
			sort = $(rangId).get(0).selectedIndex;
		}catch(e){}
		
		if(dutyTime == null||dutyTime ==''){
			break;
		}
		content+=dutyTime+","+dutyRang+","+dutyUser+","+otherUser+","+sort+"/";
		i++;
	}	
	var url = "saveDutyTable?beginDate="+beginDate+"&endDate="+endDate+"&timeRang="+timeRang;
	$.post(url,{content:content},function(data){
		var mainId = data.dutyId;
		window.location="dutyTabletList?dutyId="+mainId;		
	});
}
/**
 * 检查排班设置的起始日期
 */
function checkBeginDate(){
	var beginDate = $('#startDate').val();
	$.post("queryMaxDutyDate?beginDate="+beginDate,function(data){
		var str = data.data;
		var begin = data.beginDate;
		var base = str.replace(/\-/g, "/" ); 
		var dateStr = begin.replace(/\-/g, "/" );
		var bdate = new Date(base);
		var newDate = new Date(dateStr);
		if(bdate-newDate>=0){
			alert("该日期已排班");
		}
	});
}