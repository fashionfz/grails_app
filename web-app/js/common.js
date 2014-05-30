//设置easyui默认属性
$.messager.defaults = {ok: '确定', cancel: '取消'};

var $Q = {};

$Q.window = function(url, opts){
	return top.$Base.window(url, opts);
};

$Q.dialog = function(opts,fn){
	return top.$Base.dialog(opts,fn);
};

$Q.closeCurrentWindow = function(){
	top.$Base.closeCurrentWindow();
}

$Q.closeCurrentDialog = function(){
	top.$Base.closeCurrentDialog();
}

$Q.error = function(title, msg, fn){
	$.messager.alert(title || '错误', msg, 'error', fn);
};

$Q.question = function(title, msg, fn){
	$.messager.alert(title, msg, 'question', fn);
};

$Q.info = function(title, msg, fn){
	$.messager.alert(title || '提示', msg, 'info', fn);
};

$Q.warning = function(title, msg, fn){
	$.messager.alert(title || '警告', msg, 'warning', fn);
};

$Q.confirm = function(title, msg, fn){
	$.messager.confirm(title, msg, fn);
};

$Q.ajax = function(opts){
	var default_opts = {
		type: 'post',
		error: function(){
			alert('error');
		}
	};
	$.extend(default_opts, opts);
	$.ajax(default_opts);
};

$Q.datagrid = function(tag, opts){
	var default_opts = {
		fit: true,
	    pagination: true,
	   	fitColumns: true,
	   	striped: true,
	   	singleSelect: true,
	   	rownumbers: true,
	   	pageSize: 20
	};
	$.extend(default_opts, opts);
	return $(tag).datagrid(default_opts);
};