$Base = {};

$Base.stack = {
	values: {},
	put: function(key, value){
		if(!this.values[key]) this.values[key] = [];
		this.values[key].push(value);
	},
	remove: function(key){
		if(this.values[key] && this.values[key].length > 0) 
			this.values[key].pop();
	},
	get: function(key){
		if(!this.values[key] && this.values[key].length == 0) return null;
		var index = this.values[key].length - 1;
		return this.values[key][index];
	}
};

//新开一个window
$Base.window = function(url, opts) {
	//url += new Date().getTime();
	var default_opts = {
		width: 600,
		height: 400,
		modal: true,
		resizable: false,
		minimizable: false,
		maximizable: false,
		collapsible: false,
		inline: true,
		onClose: function(){
			if(opts && opts.beforeClose) opts.beforeClose();
			win.window('destroy');
		},
		onDestroy: function(){
			$Base.stack.remove('window');
		}
	};
	$.extend(default_opts, opts);
	var html = $('<div><iframe src="'+url+'" frameborder="no" width="100%" height="' + (default_opts.height - 38) + '"/></div>');
	$(top.document.body).append(html);
	var win = html.window(default_opts);
	$Base.stack.put('window',win);
	return win;
};

//新开一个dialog
$Base.dialog = function(opts,fn) {
	var html = $('<div></div>');
	$(top.document.body).append(html);
	/*$.ajax({
		url: opts.href,
		dataType: 'html',
		async: false,
		success:function(result){
			opts.content = result;
			opts.href = '';
		}
	});*/
	var default_opts = {
		width: 600,
		height: 400,
		modal: true,
		resizable: false,
		minimizable: false,
		maximizable: false,
		collapsible: false,
		inline: true,
		buttons: [{
			text: '确定',
			handler: function(){
				if(fn.ok){
					fn.ok(function(){win.dialog('close');});
				}else{
					win.dialog('close');
				}
			}
		}],
		onClose: function(){
			if(opts && opts.beforeClose) opts.beforeClose();
			win.dialog('destroy');
		},
		onDestroy: function(){
			$Base.stack.remove('dialog');
		}
	};
	$.extend(default_opts, opts);
	var win = html.dialog(default_opts);
	$Base.stack.put('dialog',win);
	return win;
};

//关闭当前window
$Base.closeCurrentWindow = function() {
	var win = $Base.stack.get('window');
	if(win) win.window('close');
};

//关闭当前dialog
$Base.closeCurrentDialog = function() {
	var win = $Base.stack.get('dialog');
	if(win) win.dialog('close');
};
$(document).ready(function(){
    // Table Row Class
    $('.alarm-table tr:even').addClass('tr_odd'); //偶数行
    
    // Button ToggleClass
    var classFlag = false;
	$('.btn-default').mousedown(function(){
		$(this).removeClass('btn-blue');
		classFlag = false;
	}).mouseup(function(){
		$(this).addClass('btn-blue');
		classFlag = true;
	}).mouseleave(function(){
		if(!classFlag) $(this).addClass('btn-blue');
	});
});