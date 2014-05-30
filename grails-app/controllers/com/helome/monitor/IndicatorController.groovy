package com.helome.monitor

import org.springframework.dao.DataIntegrityViolationException

/**
 * 指标维护控制器
 */
class IndicatorController {

	def indicatorService
	
    def indicatorList(Integer max) {
        params.max = params.max?:10
        params.offset = params.offset?:0
		def result = Indicator.listAvilableIndicator.list(params)
		[rows:result,totalCount:result.totalCount]
    }

	def edit() {
		def indicatorInstance = Indicator.get(params.id)
		[indicatorInstance: indicatorInstance]
	}
	
    def save() {
		Indicator indicator = null;
		if(params.id){
			indicator = Indicator.get(params.id)
		}else{
			indicator = new Indicator()
		}
		indicator.properties = params
		indicatorService.saveIndicator(indicator)
		redirect action:'indicatorList'
    }
	def delete(){
		if(params.id){
			Indicator indicator = Indicator.get(params.id)
			indicatorService.deleteIndicator(indicator)
		}
		redirect action:'indicatorList'
	}
	
	
	def comboboxData() {
		def list = Indicator.where {
			disabled == 0
			type == params.type
		}.list(params)
		render(contentType: "application/json") {
			list
		}
	}

}
