$(function() {
	var firstLevelA = $('.dev-per-ul > li > a');
	var secondLevelA = $('.pus-tab > li > a');
	firstLevelA.click(function() {
		var firstLevelUl = $(this).next('ul');
		if (firstLevelUl.is(':visible')) {
			firstLevelUl.hide();
		} else {
			$('ul.pus-tab:visible').hide();
			firstLevelUl.show();
		}
	});
	secondLevelA.click(function(event) {
		event.stopPropagation();
		$('.per-list').removeClass('per-list');
		$(this).parent('li').addClass('per-list');
	});

	var chart_cpu_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
		title : {
			text : 'CPU占用'
		},
		yAxis : {
			title : {
				text : '百分比(%)'
			}
		},
		chart : {
			renderTo : 'cpu_pct'
		}
	});
	var chart_thread_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
		title : {
			text : 'JVM线程数'
		},
		yAxis : {
			title : {
				text : '个数'
			}
		},
		chart : {
			renderTo : 'thread_count'
		}
	});
	var chart_mem_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
		title : {
			text : '内存占用'
		},
		yAxis : {
			title : {
				text : 'MB'
			}
		},
		chart : {
			renderTo : 'mem'
		}
	});
	var chart_yg_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
		title : {
			text : 'Young Generation'
		},
		yAxis : {
			title : {
				text : '百分比(%)'
			}
		},
		chart : {
			renderTo : 'yg'
		}
	});
	var chart_og_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
		title : {
			text : 'Old Generation'
		},
		yAxis : {
			title : {
				text : '百分比(%)'
			}
		},
		chart : {
			renderTo : 'og'
		}
	});
	var chart_pg_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
		title : {
			text : 'Permanent Generation'
		},
		yAxis : {
			title : {
				text : '百分比(%)'
			}
		},
		chart : {
			renderTo : 'pg'
		}
	});
	var chart_clazz_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
		title : {
			text : '类加载数'
		},
		yAxis : {
			title : {
				text : '个数'
			}
		},
		chart : {
			renderTo : 'clzzload'
		}
	});

	var timeId;
	var req_host, req_port, req_indicator;
	var chart_cpu, chart_thread, chart_mem, chart_yg, chart_og, chart_pg, chart_clazz;
	$('.chart_request').click(function() {
		req_host = $(this).attr('data-host');
		req_type = $(this).attr('data-code');
		req_port = $(this).attr('data-port');
		$('#title').text($(this).text() + '应用监控');
		if (timeId) {
			clearInterval(timeId);
		}
		drawChart();

	});

	function drawChart() {
		$.get('applicationData', {
			servername : req_host,
			appPort : req_port,
			indicatorType : req_type
		}, function(data) {
			if (timeId) {
				redraw(data);
			} else {
				initCharts(data);
				timeId = setInterval(drawChart, 1000 * 60 + 10000);
			}
		});
	}

	function redraw(data) {
		chart_cpu.series[0].setData(data.cpu.data, false);
		chart_cpu.xAxis[0].setCategories(data.categories);
		chart_thread.series[0].setData(data.thread.data, false);
		chart_thread.xAxis[0].setCategories(data.categories);
		chart_mem.series[0].setData(data.mem.data, false);
		chart_mem.xAxis[0].setCategories(data.categories);
		chart_yg.series[0].setData(data.yg.data, false);
		chart_yg.xAxis[0].setCategories(data.categories);
		chart_og.series[0].setData(data.og.data, false);
		chart_og.xAxis[0].setCategories(data.categories);
		chart_pg.series[0].setData(data.pg.data, false);
		chart_pg.xAxis[0].setCategories(data.categories);
		chart_clazz.series[0].setData(data.clazz.data, false);
		chart_clazz.xAxis[0].setCategories(data.categories);
	}

	function initCharts(data) {
		chart_cpu_opt.series[0] = data.cpu;
		chart_cpu_opt.xAxis.categories = data.categories;
		chart_thread_opt.series[0] = data.thread;
		chart_thread_opt.xAxis.categories = data.categories;
		chart_mem_opt.series[0] = data.mem;
		chart_mem_opt.xAxis.categories = data.categories;
		chart_yg_opt.series[0] = data.yg;
		chart_yg_opt.xAxis.categories = data.categories;
		chart_og_opt.series[0] = data.og;
		chart_og_opt.xAxis.categories = data.categories;
		chart_pg_opt.series[0] = data.pg;
		chart_pg_opt.xAxis.categories = data.categories;
		chart_clazz_opt.series[0] = data.clazz;
		chart_clazz_opt.xAxis.categories = data.categories;
		chart_cpu = new Highcharts.Chart(chart_cpu_opt);
		chart_thread = new Highcharts.Chart(chart_thread_opt);
		chart_mem = new Highcharts.Chart(chart_mem_opt);
		chart_yg = new Highcharts.Chart(chart_yg_opt);
		chart_og = new Highcharts.Chart(chart_og_opt);
		chart_pg = new Highcharts.Chart(chart_pg_opt);
		chart_clazz = new Highcharts.Chart(chart_clazz_opt);
	}

	firstLevelA.first().trigger('click');
	secondLevelA.first().trigger('click');
});