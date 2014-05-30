import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import grails.plugin.quartz2.InvokeMethodJob;

import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.Trigger

// Quartz Block

//没天定时9点提醒待命的值班人员
//grails.plugin.quartz2.jobSetup.notifyTodayDuty = { quartzScheduler,ctx ->
//	def props = new JobDataMap([targetObject:ctx.notifyUserJob,targetMethod:'notifyTodayDuty']);
//	JobDetail jobDetail = newJob(InvokeMethodJob.class).withIdentity("notifyTodayDuty Job","monitor").usingJobData(props).build()
//
//	Trigger trigger = newTrigger().withIdentity("notifyTodayDuty Trigger").withSchedule(dailyAtHourAndMinute(16, 3)).startNow().build()
//	quartzScheduler.scheduleJob(jobDetail,trigger)
//}
////没天定时12点提醒次日的值班人员
//grails.plugin.quartz2.jobSetup.notifyTomorrowDuty = { quartzScheduler,ctx ->
//	def props = new JobDataMap([targetObject:ctx.notifyUserJob,targetMethod:'notifyTomorrowDuty']);
//	JobDetail jobDetail = newJob(InvokeMethodJob.class).withIdentity("notifyTomorrowDuty Job","monitor").usingJobData(props).build()
//
//	Trigger trigger = newTrigger().withIdentity("notifyTomorrowDuty Trigger").withSchedule(dailyAtHourAndMinute(16, 8)).startNow().build()
//	quartzScheduler.scheduleJob(jobDetail,trigger)
//}
grails.plugin.quartz2.jobSetup.socketCheckTask = { quartzScheduler,ctx ->
	def props = new JobDataMap([targetObject:ctx.socketChecker,targetMethod:'doCheck']);
	JobDetail jobDetail = newJob(InvokeMethodJob.class).withIdentity("socketChecker Job","monitor").usingJobData(props).build()
	
	Trigger trigger = newTrigger().withIdentity("socketChecker Trigger").withSchedule(repeatSecondlyForever(10)).startNow().build()
	quartzScheduler.scheduleJob(jobDetail,trigger)
}