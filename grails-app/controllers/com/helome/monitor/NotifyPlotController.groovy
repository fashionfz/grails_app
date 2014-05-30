package com.helome.monitor

import com.helome.monitor.service.impl.NotifyPlotService

/**
 * 
 * @ClassName: NotifyController
 * @Description: 通知策略
 * @author bin.deng@helome.com
 * @date 2014年4月10日 下午1:50:39
 *
 */
class NotifyPlotController {

	
	NotifyPlotService notifyPlotService;
	
    def notifylist() {
		def rows = notifyPlotService.notifyPlotGet();
		render(view:'notifylist',model:[rows:rows]);
	}
	
	
	def edit(){
		NotifyStrategy notifyPlot = notifyPlotService.notifyPlotById(params);
		[notifyPlot: notifyPlot]
	}
	
	/**
	 * 
	  * notifyPlotSave
	  * 保存通知策略
	  *
	  * @return
	 */
	def notifyPlotSave(){
		notifyPlotService.notifyPlotSave(params);
		def rows = notifyPlotService.notifyPlotGet();
		render(view:'notifylist',model:[rows:rows]);
	}
	/**
	 * 
	  * notifyPlotDel
	  * 删除通知策略
	  *
	  * @return
	 */
	def notifyPlotDel(){
		notifyPlotService.notifyPlotDel(params);
		def rows = notifyPlotService.notifyPlotGet();
		render(view:'notifylist',model:[rows:rows]);
	}
	/**
	 * 
	  * notifyPlotStatus
	  * 修改通知策略状态
	  *
	  * @return
	 */
	def notifyPlotStatus(){
		notifyPlotService.notifyPlotStatus(params);
	}
}

