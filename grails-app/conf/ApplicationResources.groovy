modules = {
    application {
		resource url:'js/jquery-1.8.0.min.js',disposition:'head'
		resource url:'js/layout.js'
		resource url:'js/base.js'
		resource url:'css/right.css'
		resource url:'css/head.css'
		resource url:'css/left.css'
		resource url:'css/index.css'
    }
	draggable {
		resource url:'js/draggable.js'
		dependsOn 'jquery_ui'
	}
	jquery_ui{
		resource url:'js/jquery-ui-1.10.4.custom.min.js'
	}
	datepicker {
		resource url:'css/dateinput.css'
		resource url:'js/dateinput.js'
	}
	easyui {
		resource url: 'easyui/jquery.easyui.min.js'
		resource url: 'easyui/locale/easyui-lang-zh_CN.js'
		resource url: 'js/common.js'
		resource url: 'easyui/themes/icon.css'
		resource url: 'easyui/themes/default/easyui.css'
	}
    highchart {
        resource url:'js/highcharts.js'
		resource url:'js/constant.js'
    }
	indexApplyChart {
		resource url:'js/applychart.js'
		dependsOn 'highchart'
	}
}