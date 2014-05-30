<%@ page import="com.helome.monitor.ServerInfo" %>
<%@ page import="com.helome.monitor.User" %>
<%@ page import="com.helome.monitor.Dictionary" %>
<%@ page import="com.helome.monitor.Indicator" %>

<fieldset>
    <legend></legend>
    <p>
        <label for="name">服务器名称:*</label>
        <g:textField name="name" maxlength="50" required="" value="${serverInfoInstance?.name}"/>
    </p>
    <p>
        <label for="login">登录账号:*</label>
        <g:textField name="login" required="" value="${serverInfoInstance?.login}"/>
    </p>
    <p>
        <label for="computerRoom">所在机房:*</label>
        <g:textField name="computerRoom" value="${serverInfoInstance?.computerRoom}"/>
    </p>
    <p>
        <label for="user.id">管理员:*</label>
        <g:select name="user.id" from="${User.list()}" optionValue="username" optionKey="id" value="${serverInfoInstance?.user?.id}"/>
    </p>
    <p>
        <label for="allowedSSH">允许SSH登录:*</label>
        <g:select name="allowedSSH" from="${[[val:'1',name:'允许'],[val:'0',name:'禁止']]}" optionValue="name" optionKey="val" value="${serverInfoInstance?.allowedSSH}"/>
    </p>
</fieldset>
<fieldset>
    <p>
        <label for="os">操作系统:*</label>
        <g:textField name="os" required="" value="${serverInfoInstance?.os}"/>
    </p>
    <p>
        <label for="password">登录密码:*</label>
        <g:passwordField name="password" value="${serverInfoInstance?.password}"/>
    </p>
    <p>
        <label for="monitorType">监控方式:</label>
        <g:textField name="monitorType" value="${serverInfoInstance?.monitorType}"/>
    </p>
    <p>
        <label for="enabled">启用:*</label>
        <g:select optionKey="value" optionValue="name"  name="enabled" from="${Dictionary.where {codeType == 'bool'}.list()}" value="${serverInfoInstance?.enabled}"/>
    </p>
</fieldset>
<p class="line" style="margin-top:0;"></p>
<div style="padding: 0 25px 25px;">
<table id="dg" title="服务器IP地址" style="width: 550px;height: 250px;">
</table>
</div>

<div id="tb" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
    <g:if test="${serverInfoInstance.id != null}">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">完成修改</a>
    </g:if>
    <g:if test="${serverInfoInstance.id == null}">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accomplish()">完成添加</a>
    </g:if>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a>
    %{--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">GetChanges</a>--}%
</div>

<p class="line" style="margin-top:0;"></p>
<div style="padding: 0 25px 25px;">
    <table id="dgs" title="服务器应用" style="width: 550px;height: 250px;">
    </table>
</div>

<div id="tbs" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appends()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeits()">删除</a>
    <g:if test="${serverInfoInstance.id != null}">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accepts()">完成修改</a>
    </g:if>
    <g:if test="${serverInfoInstance.id == null}">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accomplishs()">完成添加</a>
    </g:if>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejects()">撤销</a>
    %{--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChangess()">GetChanges</a>--}%
</div>

<p class="line" style="margin-top:0;"></p>
<fieldset>
    支持的指标:<br><br>
    <g:set var="indicators" value="${Indicator.where {type == 1}.list()}"/>
    <g:each in="${indicators}" status="i" var="indicator">
        <g:checkBox class="checkbox" id="check${i}" name="indicatorChecks" hiddenValue="${indicator.id}" value="${ServerInfo.hasIndicator(serverInfoInstance?.id, indicator.id)}"></g:checkBox><label for="check${i}">${indicator.name}</label><br>
    </g:each>
</fieldset>
<g:hiddenField name="indicatorIds" id="indicatorIds" value="${indicatorIds}"/>
<g:hiddenField name="ifInserted" id="ifInserted" />
<g:hiddenField name="serviceInserted" id="serviceInserted" />
<script type="text/javascript">
	$(function(){
		$.extend($.fn.datagrid.defaults.editors,{
	        combobox: {
	            init: function(container, options){
	                var combo = $('<input type="text">').appendTo(container);
	                combo.combobox(options || {});
	                return combo;
	            },
	            destroy: function(target){
	                $(target).combobox('destroy');
	            },
	            getValue: function(target){
	                var opts = $(target).combobox('options');
	                if (opts.multiple){
	                    return $(target).combobox('getValues').join(opts.separator);
	                } else {
	                    return $(target).combobox('getValue');
	                }
	            },
	            setValue: function(target, value){
	                var opts = $(target).combobox('options');
	                if (opts.multiple){
	                    if (value == '' || value == undefined){
	                        $(target).combobox('clear');
	                    } else {
	                        $(target).combobox('setValues', value.split(opts.separator));
	                    }
	                } else {
	                    $(target).combobox('setValue', value);
	                }
	            },
	            resize: function(target, width){
	                $(target).combobox('resize', width)
	            }
	        }
	    });
	});
    
    //获取应用类型
    function getAppDictionarys() {
        var result;
        $Q.ajax({
            url: '${request.contextPath}/dictionary/listByCodeType?codeType=' + 'servicetype',
            dataType: 'json',
            async: false,
            success: function(data){
                result = data;
            }
        });

        return result;
    }
    //获取应用指标
    function getAppIndicators() {
        var result;
        $Q.ajax({
            url: 'serviceIndicators',
            dataType: 'json',
            async: false,
            success: function(data){
                result = data;
            }
        });

        return result;
    }

    $(document).ready(function(){
        //支持的指标保存到Hashtable
        var ids = new Hashtable();
        var indicatorIdStr = "${indicatorIds}";
        if (indicatorIdStr && indicatorIdStr != '') {
            var indicatorIds = indicatorIdStr.split(",");
            $.each(indicatorIds, function(inx,r){
                ids.put($.trim(r)+"", $.trim(r)+"");
            });
        }
        //变更支持的指标
        $('.checkbox').change(function(){
            var self = $(this);
            var val = $(this).attr('hiddenValue');
            if (self.attr('checked')) {
                ids.put(val,val);
            } else {
                ids.remove(val);
            }
            console.log(ids.values());
            //if (!ids.isEmpty()) {
                $('#indicatorIds').val(ids.values());
            //}

        });

        var datas = [{"val":"1", name:"内网IP"},{"val":"2", name:"外网IP"},{"val":"3", name:"IDRAC卡IP"}];
        $('#dg').datagrid({
            fitColumns: true,
            nowrap: false,
            rownumbers: true,
            autoRowHeight: true,
            iconCls: 'icon-edit',
            singleSelect: true,
            toolbar: '#tb',
            url: 'serverIfs?id=${serverInfoInstance?.id}',
            method: 'get',
            striped: true,
            checkOnSelect: false,
            selectOnCheck: false,
            showFooter: true,
            onClickRow: onClickRow,
            columns: [[
                {field: 'id', hidden: true},
                {field:'type',rowspan:2,width:50,title:'ip类型',editor:{
                    type:'combobox',
                    options:{
                        valueField:'value',
                        textField:'name',
                        url: '${request.contextPath}/dictionary/listByCodeType?codeType=' + 'iptype',
                        required:true,
                        panelHeight: 'auto',
                        separator: ","
                    }
                },formatter: function(value) {
                    var name="";
                    $.each(datas, function(i,v) {
                        if (v.val == value) {
                            name = v.name;
                        }
                    });
                    return name;
                }},
                {field:'ipAddress',rowspan:2,width:50,title:'ip地址',editor:{type:'text', options:{required:true}}}
            ]]
        });
        //服务器应用
        /*var datas1 = [{"val":"1", name:"Nginx"},{"val":"2", name:"Tomcat"},{"val":"3", name:"Mysql"},
            {"val":"4", name:"Play"},{"val":"5", name:"Memcached"},{"val":"7", name:"3rd"}];*/
        var datas1 = getAppDictionarys();
        var appIndicators = getAppIndicators();
        $('#dgs').datagrid({
            fitColumns: true,
            nowrap: false,
            rownumbers: true,
            autoRowHeight: true,
            iconCls: 'icon-edit',
            singleSelect: true,
            toolbar: '#tbs',
            url: 'serverServices?id=${serverInfoInstance?.id}',
            method: 'get',
            striped: true,
            checkOnSelect: false,
            selectOnCheck: false,
            showFooter: true,
            onClickRow: onClickRows,
            columns: [[
                {field:'id', hidden: true},
                {field:'type',rowspan:2,width:50,title:'应用类型',editor:{
                    type:'combobox',
                    options:{
                        valueField:'value',
                        textField:'name',
                        url: '${request.contextPath}/dictionary/listByCodeType?codeType=' + 'servicetype',
                        required:true,
                        panelHeight: 'auto'
                    }
                },formatter: function(value){
                    var name="";
                    $.each(datas1, function(i,r) {
                        if (r.value === value) {
                            name = r.name;
                        }
                    });
                    return name;
                }},
                {field:'name',rowspan:2,width:50,title:'应用名',editor:{type:'text', options:{required:true}}},
                {field:'contentPath',rowspan:2,width:80,title:'安装根目录',editor:{type:'text', options:{required:true}}},
                {field:'ports',rowspan:2,width:80,title:'主端口',editor:{type:'text', options:{required:true}}},
                {field:'indicatorIds',rowspan:2,width:80,title:'应用指标',editor:{
                    type:'combobox',
                    options:{
                        //url:'serviceIndicators',
                        data: appIndicators,
                        method:'get',
                        valueField:'id',
                        textField:'text',
                        multiple:true,
                        panelHeight:'auto'
                    }
                },formatter: function(value) {
                    var ids = value.split(',');
                    var text = '';
                    for (var i = 0; i < ids.length; i++) {
                        $.each(appIndicators,function(idx,r){
                            if (r.id == ids[i]) {
                                text += r.text;
                                if (idx < (appIndicators.length - 1)) {
                                    text += ",";
                                }
                            }
                        });
                    }
                    return text;
                }}
            ]]
        });
    });

    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#dg').datagrid('validateRow', editIndex)){
            $('#dg').datagrid('getEditor', {index:editIndex});

            $('#dg').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickRow(index){
        if (editIndex != index){
            if (endEditing()){
                $('#dg').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#dg').datagrid('selectRow', editIndex);
            }
        }
    }
    function append(){
        if (endEditing()){
            $('#dg').datagrid('appendRow',{
                type:'',
                ipAddress: '',
                id: null
            });
            editIndex = $('#dg').datagrid('getRows').length-1;
            $('#dg').datagrid('selectRow', editIndex)
                    .datagrid('beginEdit', editIndex);
        }
    }
    function removeit(){
        if (editIndex == undefined){return}
        $('#dg').datagrid('cancelEdit', editIndex)
                .datagrid('deleteRow', editIndex);
        editIndex = undefined;
    }
    function accept(){
        if (endEditing()){
            var id = "${serverInfoInstance?.id}";
            //获取变更的行
            var dg = $('#dg');
            var inserted = dg.datagrid('getChanges', "inserted");
            var deleted = dg.datagrid('getChanges', "deleted");
            var updated = dg.datagrid('getChanges', "updated");

            var effectRow = new Object();
            if (inserted.length) {
                effectRow["inserted"] = JSON.stringify(inserted);
            }
            if (deleted.length) {
                effectRow["deleted"] = JSON.stringify(deleted);
            }
            if (updated.length) {
                effectRow["updated"] = JSON.stringify(updated);
            }
            effectRow["id"] = id;
            $.post('saveIfs',effectRow, function(data){
                if (data.success) {
                    $.messager.show({title:"消息提示", msg:"保存成功！"});
                } else {
                    $.messager.show({title:"消息提示", msg:"保存失败！"});
                }
            },'json');
        }
    }
    //完成添加
    function accomplish(){
        if (endEditing()){
            //获取变更的行
            var dg = $('#dg');
            //var rows = dg.datagrid('getChanges','inserted');
            var inserted = dg.datagrid('getChanges', "inserted");

            $('#ifInserted').val(JSON.stringify(inserted));
        }
    }
    function reject(){
        $('#dg').datagrid('rejectChanges');
        editIndex = undefined;
    }
    function getChanges(){
        var rows = $('#dg').datagrid('getChanges');
        alert(rows.length+' rows are changed!');
    }

    var editIndexs = undefined;
    function endEditings(){
        if (editIndexs == undefined){return true}
        if ($('#dgs').datagrid('validateRow', editIndexs)){
            $('#dgs').datagrid('getEditor', {index:editIndexs});

            $('#dgs').datagrid('endEdit', editIndexs);
            editIndexs = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickRows(index){
        if (editIndexs != index){
            if (endEditings()){
                $('#dgs').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                editIndexs = index;
            } else {
                $('#dgs').datagrid('selectRow', editIndexs);
            }
        }
    }
    function appends(){
        if (endEditings()){
            $('#dgs').datagrid('appendRow',{
                type: '',
                name: '',
                contentPath: '',
                ports: '',
                id: null,
                indicatorIds: ''
            });
            editIndexs = $('#dgs').datagrid('getRows').length-1;
            $('#dgs').datagrid('selectRow', editIndexs)
                    .datagrid('beginEdit', editIndexs);
        }
    }
    function removeits(){
        if (editIndexs == undefined){return}
        $('#dgs').datagrid('cancelEdit', editIndexs)
                .datagrid('deleteRow', editIndexs);
        editIndexs = undefined;
    }
    function accepts(){
        if (endEditings()){
            var id = "${serverInfoInstance?.id}";
            //获取变更的行
            var dg = $('#dgs');
            var inserted = dg.datagrid('getChanges', "inserted");
            var deleted = dg.datagrid('getChanges', "deleted");
            var updated = dg.datagrid('getChanges', "updated");
            console.log(updated);
            var effectRow = new Object();
            if (inserted.length) {
                effectRow["inserted"] = JSON.stringify(inserted);
            }
            if (deleted.length) {
                effectRow["deleted"] = JSON.stringify(deleted);
            }
            if (updated.length) {
                effectRow["updated"] = JSON.stringify(updated);
            }
            effectRow["id"] = id;
            $.post('saveServices',effectRow, function(data){
                if (data.success) {
                    $.messager.show({title:"消息提示", msg:"保存成功！"});
                } else {
                    $.messager.show({title:"消息提示", msg:"保存失败！"});
                }

            },'json');
        }

    }
    //完成添加
    function accomplishs(){
        if (endEditings()){
            //获取变更的行
            var dg = $('#dgs');
            //var rows = dg.datagrid('getChanges','inserted');
            var inserted = dg.datagrid('getChanges', "inserted");

            $('#serviceInserted').val(JSON.stringify(inserted));
        }
    }

    function rejects(){
        $('#dgs').datagrid('rejectChanges');
        editIndexs = undefined;
    }
    function getChangess(){
        var rows = $('#dgs').datagrid('getChanges');
        alert(rows.length+' rows are changed!');
    }
</script>
