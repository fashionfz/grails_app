package com.helome.monitor.service.impl

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import com.helome.monitor.NotifyStrategy
import com.helome.monitor.Dictionary
import com.helome.monitor.User

/**
 * 
 * @ClassName: NotifyPlotService
 * @Description: 通知策略
 * @author bin.deng@helome.com
 * @date 2014年4月10日 下午2:01:17
 *
 */
class NotifyPlotService {

	@Autowired
	SessionFactory sessionFactory;
	/**
	 * 
	  * notifyPlotSave
	  * 保存通知策略
	  *
	  * @param params
	  * @return
	 */
    def notifyPlotSave(Map params) {
		if(params.id==null||"".equals(params.id)){
			NotifyStrategy notify = new NotifyStrategy(params);
			notify.save();
		}else{
			NotifyStrategy notify = NotifyStrategy.get(params.long('id'));
			notify.properties=params;
			notify.save();
		}
    }
	/**
	 * 
	  * notifyPlotDel
	  * 删除通知策略
	  *
	  * @param params
	  * @return
	 */
	def notifyPlotDel(Map params){
		if(params.id){
			NotifyStrategy notify = NotifyStrategy.get(params.long('id'));
			notify?.delete();
		}
	}
	/**
	 * 
	  * notifyPlotStatus
	  * 修改通知策略状态
	  *
	  * @param params
	  * @return
	 */
	def notifyPlotStatus(Map params){
		NotifyStrategy notify = NotifyStrategy.get(params.long('id'));
		notify.setEnabled(params.boolean('enabled'));
		notify.save();
	}
	
	def notifyPlotGet(){
		Binding binding = null
		def results = []
		def plots = NotifyStrategy.list();
		plots.each {
			binding = new Binding()
			binding.modules = findNameDictionary("alarm_type",it.notifyWay);
			binding.ways = findDesDictionary("alarm_module",it.notifyCapability);
			binding.object = it
			results << binding.variables
		}
		return results;
	}
	
	
	def findDesDictionary(String codeType,Set set){
		Set result = new HashSet();
		for(Integer value : set){
			result.add(Dictionary.where{
				codeType == codeType
				value == value
			}.list());
		}
		return result;
	}
	
	def findNameDictionary(String codeType,Integer value){
		def q = Dictionary.where{
			codeType == "alarm_type"
			value == value
		}
		return q.list();
	}
	
	
	def notifyPlotById(Map params){
		if(params.id){
			return NotifyStrategy.get(params.long('id'));
		} else {
			return null;
		}
	}
	
	def deleteNotifyStrategyRelationWithUser(User user){
		NotifyStrategy.list()?.each {notiftyStrategy ->
			notiftyStrategy.removeFromUser(user)
		}
	}
}
