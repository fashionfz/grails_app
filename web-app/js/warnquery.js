$(function(){
	$('#query-btn').on('click',function(){
		var paramObj = $(document.forms[0]).serializeJson();
		var selectedIndicatorname = paramObj.indicatorname;
		var reloadedColumnArray = [];
		var reload = false;
		if($('#dg').datagrid('options').columns[0].length < initColumnArray.length && !selectedIndicatorname){
			$.extend(reloadedColumnArray,initColumnArray);
			reload = true;
		}else if(selectedIndicatorname){
			$.extend(reloadedColumnArray,columnArray);
			reloadedColumnArray.push(indicatorNames[selectedIndicatorname]);
			reload = true;
		}
		if(reload){
			$('#dg').datagrid({columns:[reloadedColumnArray]}).datagrid('loadData',function(data){});
		}else{
			$('#dg').datagrid('load',paramObj);
		}
		
	});
	$('#reset-btn').on('click',function(){
		$(document.forms[0]).form('clear');
	});
	
	// datagrid数据表格ID
	 var datagridId = 'dg';
	 var $dg = $('#'+datagridId);
	 // 第一次加载时自动变化大小
	 $dg.resizeDataGrid(327, 230, 0, 0);
	 // 当窗口大小发生变化时，调整DataGrid的大小
	 $(window).resize(function() {
		 $dg.resizeDataGrid(327, 230, 0, 0);
	 });
});

(function($){
    $.fn.extend({
    	/**
    	 * 修改DataGrid对象的默认大小，以适应页面宽度。
    	 * 
    	 * @param heightMargin
    	 *            高度对页内边距的距离。
    	 * @param widthMargin
    	 *            宽度对页内边距的距离。
    	 * @param minHeight
    	 *            最小高度。
    	 * @param minWidth
    	 *            最小宽度。
    	 * 
    	 */
    	resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {
    		var height = $(document.body).height() - heightMargin;
    		var width = $(document.body).width() - widthMargin;
    		height = height < minHeight ? minHeight : height;
    		width = width < minWidth ? minWidth : width;
    		$(this).datagrid('resize', {
    			height : height,
    			width : width
    		});
    	},
    	serializeJson:function(){
    		var serializeObj={};  
            var array=this.serializeArray();  
            var str=this.serialize();  
            $(array).each(function(){  
                if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push(this.value);  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
                    }  
                }else{  
                    serializeObj[this.name]=this.value;   
                }  
            });  
            return serializeObj; 
    	}
    });
})(jQuery); 