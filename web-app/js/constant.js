var helome = {};
helome.chart = {
	commonAreaOption : {
		xAxis : {
			tickInterval : 60,
			labels : {
				rotation : 330
			}
		},
		chart : {
			backgroundColor : '#F5F5F5',
			plotBackgroundColor : '#f5f5f5',
			type : 'area',
			zoomType : 'x',
			plotBorderWidth: null,
            plotShadow: false
		},
		credits : {
			enabled : false //去掉图标右下角highcharts官方链接
		},
		exporting:{
            enabled:false
        },
		plotOptions : {
			area : {
				fillColor : {
					linearGradient : {
						x1 : 0,
						y1 : 0,
						x2 : 0,
						y2 : 1
					},
					stops : [
						[ 0, Highcharts.getOptions().colors[0] ],
						[ 1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba') ] 
					]
				},
				lineWidth : 1,
				marker : {
					enabled : false
				},
				shadow : false,
				states : {
					hover : {
						lineWidth : 1
					}
				},
				threshold : null
			}
		},
		series : [ {} ]
	}
};