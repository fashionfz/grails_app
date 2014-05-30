package com.helome.monitor

import java.text.DecimalFormat
import java.text.SimpleDateFormat

import com.helome.monitor.service.impl.BusinessAnalysisService
import com.helome.monitor.service.impl.IpAdrressService
import com.helome.monitor.utils.ReturnStyle

class BusinessAnalysisController {

	BusinessAnalysisService businessAnalysisService;
	
	IpAdrressService ipAdrressService;
	
	
    def visitRangList() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar begin = null;
		if(params.begin){
			begin = Calendar.getInstance();
			begin.setTime(df.parse(params.begin))
		}
		Calendar end = null;
		if(params.end){
			end = Calendar.getInstance();
			end.setTime(df.parse(params.end))
		}
		Map map = new TreeMap();
		List<TimeSlot> list = null;
		
		if(!params.prduct){
			params.prduct = "hi"
		}
		
		if(begin!=null&&end!=null){
			list = TimeSlot.where {
				prduct == params.prduct
				visitDate >= begin.getTime()
				visitDate <= end.getTime()
			}.list();
		}else{
			begin = Calendar.getInstance();
			begin.add(Calendar.DATE, -1);
			begin.set(Calendar.HOUR_OF_DAY, 0)
			begin.set(Calendar.MINUTE, 0)
			begin.set(Calendar.SECOND, 0)
			
			
			end = Calendar.getInstance();
			end.add(Calendar.DATE, -1);
			end.set(Calendar.HOUR_OF_DAY, 23)
			end.set(Calendar.MINUTE, 59)
			end.set(Calendar.SECOND, 59)
			
			
			params.begin = df.format(begin.getTime())
			params.end = df.format(end.getTime())
			list = TimeSlot.where {
			prduct == params.prduct
			visitDate >= begin.getTime()
			visitDate <= end.getTime()
		}.list();
		}
		int sum = 0;
		list.each{
			if(map.containsKey(it.timeRang)){
				int value = map.get(it.timeRang) + it.visitCount;
				map.put(it.timeRang, value);
			}else{
			map.put(it.timeRang, it.visitCount);
			}
			sum = sum+ it.visitCount;
		}
		
		
		List<TimeSlot> res = new ArrayList<TimeSlot>();
		Set set = map.keySet();  //获得map的Iterator
		for(String key : set){
			TimeSlot slot = new TimeSlot();
			slot.setTimeRang(key)
			slot.setVisitCount(map.get(key));
			res.add(slot);
		}
		
		
		Binding binding = null
		def results = []
		res.each {
			binding = new Binding();
			double per = (it.visitCount/sum)*100;
			binding.obj = it;
			binding.per =per;
			results << binding.variables
			
		}
		render(view:'visitRangList',model:[rows:results])
	}
	
	
	def loginArea(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar begin = null;
		if(params.begin){
			begin = Calendar.getInstance();
			begin.setTime(df.parse(params.begin))
		}
		Calendar end = null;
		if(params.end){
			end = Calendar.getInstance();
			end.setTime(df.parse(params.end))
		}
		Map map = new TreeMap();
		List<LoginArea> list = null;
		
		if(!params.prduct){
			params.prduct = "hi"
		}
		
		if(begin!=null&&end!=null){
			list = LoginArea.where {
				prduct == params.prduct
				visitDate >= begin.getTime()
				visitDate <= end.getTime()
			}.list();
		}else{
			begin = Calendar.getInstance();
			begin.add(Calendar.DATE, -1);
			begin.set(Calendar.HOUR_OF_DAY, 0)
			begin.set(Calendar.MINUTE, 0)
			begin.set(Calendar.SECOND, 0)
			
			end = Calendar.getInstance();
			end.add(Calendar.DATE, -1);
			end.set(Calendar.HOUR_OF_DAY, 23)
			end.set(Calendar.MINUTE, 59)
			end.set(Calendar.SECOND, 59)
			
			params.begin = df.format(begin.getTime())
			params.end = df.format(end.getTime())
			list = LoginArea.where {
			prduct == params.prduct
				visitDate >= begin.getTime()
				visitDate <= end.getTime()
			}.list();
		}
		int sum = 0;
		list.each{
			if(it.region){
				if(map.containsKey(it.region)){
					int value = map.get(it.region) + it.visitCount;
					map.put(it.region, value);
				}else{
				map.put(it.region, it.visitCount);
				}
				sum = sum+ it.visitCount;
			}
		}
		
		
		List<LoginArea> res = new ArrayList<LoginArea>();
		Set set = map.keySet();  //获得map的Iterator
		for(String key : set){
			LoginArea slot = new LoginArea();
			slot.setIp(key)
			slot.setVisitCount(map.get(key));
			res.add(slot);
		}
		
		
		Binding binding = null
		def results = []
		res.each {
			binding = new Binding();
			double per = (it.visitCount/sum)*100;
			binding.obj = it;
			binding.per =per;
			results << binding.variables
			
		}
		render(view:'loginArea',model:[rows:results])
	}
	
	def returnRate(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar begin = Calendar.getInstance();
		if(params.begin){
			begin.setTime(df.parse(params.begin))
		}else{
			begin.add(Calendar.DATE, -8);
		}
		begin.set(Calendar.HOUR_OF_DAY, 0)
		begin.set(Calendar.MINUTE, 0)
		begin.set(Calendar.SECOND, 0)
		Calendar end = Calendar.getInstance();
		if(params.end){
			end.setTime(df.parse(params.end))
		}else{
			end.add(Calendar.DATE, -2);
		}
		end.set(Calendar.HOUR_OF_DAY, 23)
		end.set(Calendar.MINUTE, 59)
		end.set(Calendar.SECOND, 59)
		
		if(!params.type){
			params.type='1'
		}
		if(!params.prduct){
			params.prduct='hi'
		}
			
		params.begin = df.format(begin.getTime())
		params.end = df.format(end.getTime())

		
		def results=businessAnalysisService.getReturnRateLine(params.prduct,params.type,begin.getTime(),end.getTime())
		DecimalFormat ddf = new DecimalFormat("#0.00");
		
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		Map map = new HashMap();
		Map regist = new HashMap();
		int allCount =0;
		
		String type =ReturnStyle.getName(params.type)
		
		businessAnalysisService.getNextUserReturnRate(type,params.prduct,begin.getTime(),end.getTime()).each {
			if(it.dateType == 0){
				allCount = it.userCount;
			}else if(allCount > 0){
				double per = (it.userCount/allCount)*100;
				map.put(simple.format(it.returnDate)+type+it.returnType, ddf.format(per))
				regist.put(simple.format(it.returnDate), allCount)
				allCount =0
			}
		}
		
		
		render(view:'returnRate',model:[begin:begin.getTime(),end:end.getTime(),rows:results,map:map,regist:regist])
	}
}
