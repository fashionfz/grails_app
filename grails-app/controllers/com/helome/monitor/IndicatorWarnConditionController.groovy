package com.helome.monitor

import com.helome.monitor.service.impl.WarnStrategyService;

class IndicatorWarnConditionController {

	WarnStrategyService warnStrategyService
	
	
    def listIndicatorWarnCondition() {
		params.max = params.max?:10
		params.offset = params.offset?:0
		def results = IndicatorWarnCondition.listAvaliableIndicatorWarnCondition.list(params)
		render(view:'list',model:[rows:results,total:results.totalCount])
    }

	def edit() {
		def indicatorWarnConditionInstance = null
		if(params.id){
			indicatorWarnConditionInstance = IndicatorWarnCondition.get(params.id)
		}
		[indicatorWarnConditionInstance: indicatorWarnConditionInstance]
	}
	
    def save() {
		IndicatorWarnCondition indicatorWarnCondition = null
		if(params.id){
			indicatorWarnCondition = IndicatorWarnCondition.get(params.id)
		}else{
			indicatorWarnCondition = new IndicatorWarnCondition()
		}
		indicatorWarnCondition.properties = params
		
		indicatorWarnCondition.indicator = Indicator.get(params.indicatorId)
		
		indicatorWarnCondition.save()
		if(indicatorWarnCondition.hasErrors()){
			indicatorWarnCondition.errors.each {
				log.info it
			}
		}
		warnStrategyService.initWarnStrategyMemcache();
		redirect action:'listIndicatorWarnCondition'
    }

    def delete() {
		if(params.id){
			def indicatorWarnConditionInstance = IndicatorWarnCondition.get(params.id)
			indicatorWarnConditionInstance.delete();
		}
		warnStrategyService.initWarnStrategyMemcache();
		redirect action:'listIndicatorWarnCondition'
    }
}
