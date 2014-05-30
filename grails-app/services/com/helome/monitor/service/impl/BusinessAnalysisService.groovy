package com.helome.monitor.service.impl

import java.text.DecimalFormat

import com.helome.monitor.ReturnRate

class BusinessAnalysisService {

	/**
	 * 
	  * getNextUserReturnRate
	  * 获取次日留存率table数据
	  *
	  * @return
	 */
    def getNextUserReturnRate(String type,String prduct,Date begin,Date end) {
		def p = ReturnRate.where {
			returnStyle == type
			prduct == prduct
			returnDate >= begin
			returnDate <= end
		}.order("returnDate").order("prduct").order("returnType").order("dateType");
		return p.list();
    }
	
	/**
	 * 
	  * getNextRegistCount
	  * 获取留存率曲线数据
	  *
	  * @return
	 */
	def getReturnRateLine(String prduct,String type,Date begin,Date end){
		List<ReturnRate> list;
		def q = ReturnRate.where {
			returnType == type
			prduct == prduct
			returnDate >= begin
			returnDate <= end
			returnStyle == 'day'
		}.order("returnDate").order("prduct").order("returnType").order("dateType");
		list = q.list();
		
		DecimalFormat df = new DecimalFormat("#.00");
		Binding binding = null
		def results = []
		int allCount = 0;
		for(ReturnRate rate : list){
			if(rate.dateType == 0){
				allCount = rate.userCount;
			}else if(allCount > 0){
				binding = new Binding();
				double per = (rate.userCount/allCount)*100;
				binding.obj = rate;
				binding.per =df.format(per);
				results << binding.variables
				allCount=0
			}
		}
		return results;
	}
}
