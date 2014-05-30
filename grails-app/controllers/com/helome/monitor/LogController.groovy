package com.helome.monitor

import java.text.SimpleDateFormat

import org.springframework.beans.factory.annotation.Autowired

import com.helome.monitor.service.impl.UserOperationLogService
import java.util.Calendar;

/**
 * 日志查询控制器
 *
 */
class LogController {

	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Autowired
	UserOperationLogService userOperationLogService
    /**
     * 日志分析
     * @return
     */
    def index() {

        render (view: "index", model: params)
    }
	
	def userlogIndex(){
		def logTypes = userOperationLogService.queryLogType();
		render(view:'userlog',model:[pageIndex:1,pageCount:10,logType:logTypes]);
	}
	
	def userlog(){
		String pageIndex="1";
		if(params.pageIndex==null){
			params.pageIndex=1;
		}else{
			pageIndex = params.pageIndex;
		}
		String pageCount = params.pageCount==null?10:params.pageCount;
		int start = (Integer.parseInt(pageIndex) - 1) * Integer.parseInt(pageCount);
		
		UserOperationLog userlog = new UserOperationLog(params);
		
		Date startTime=null;
		Date endTime = null;
		
		
		if(!params.startTime){
			Calendar begin = Calendar.getInstance();
			begin.set(Calendar.HOUR_OF_DAY, 0)
			begin.set(Calendar.MINUTE, 0)
			begin.set(Calendar.SECOND, 0)
			params.startTime = df.format(begin.getTime());
		}
		
		
		if(!params.endTime){
			Calendar end = Calendar.getInstance();
			end.set(Calendar.HOUR_OF_DAY, 23)
			end.set(Calendar.MINUTE, 59)
			end.set(Calendar.SECOND, 59)
			params.endTime = df.format(end.getTime());
		}
		
		
		
		if(params.startTime!=null&&!"".equals(params.startTime)){
			startTime = df.parse(params.startTime);
		}
		if(params.endTime!=null&&!"".equals(params.endTime)){
			endTime = df.parse(params.endTime);
		}
		def logTypes = userOperationLogService.queryLogType();
		def count =  userOperationLogService.userLog(userlog,startTime,endTime)
		def list = userOperationLogService.userLog(userlog,startTime,endTime, start, Integer.parseInt(pageCount))
		int pageNum
		if(count%Integer.parseInt(pageCount)==0){
			pageNum= (int)(count/Integer.parseInt(pageCount));
		}else{
			pageNum= (int)(count/Integer.parseInt(pageCount))+1;
		}
		if(pageNum==0){
			pageNum=1;
		}
		render(view:'userlog',model:[allCount:count,logList:list,pageNum:pageNum,logType:logTypes])
	}
}
