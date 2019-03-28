//package com.iwhalecloud.retail.oms.quartz.action;
//
//import com.iwhalecloud.retail.oms.quartz.consts.Const;
//import com.iwhalecloud.retail.oms.quartz.model.JobInfo;
//import com.iwhalecloud.retail.oms.quartz.service.IJobManager;
//import com.ztesoft.net.framework.database.Page;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//
///**
// * @Description
// * @author  zhangJun
// * update   (gongS)
// * @date    2016年3月22日
// * @version 1.0
// */
//@Component
//public class JobAction extends WWAction {
//	@Autowired
//	private IJobManager jobManager;
//
//	/**
//	 * 查询列表
//	 * @return
//	 */
//	public Page queryList(JobInfo jobInfo){
//		try {
//			this.webpage =  this.jobManager.queryList(jobInfo, this.getPage(),  this.getPageSize());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return webpage;
//	}
//	/**
//	 * 查询实例列表
//	 * @return
//	 */
//	public Page jobSchedulerList(JobInfo jobInfo){
//
//		this.webpage =  this.jobManager.jobSchedulerList(this.getPage(),  this.getPageSize());
//		return webpage;
//	}
//
//
//	//跳转编辑页面
//	public JobInfo edit(JobInfo jobInfo){
//		try {
//			jobInfo = this.jobManager.findJobInfo(jobInfo);
//			this.analysisCronExpression(jobInfo);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return jobInfo;
//	}
//
//	public String saveAdd(JobInfo jobInfo){
//		try{
//			if(!StringUtils.isEmpty(jobInfo.getCronSyntax())){
//				String cron=jobInfo.getCronSyntax();
//				cron=cron.replace("×", "*");
//				jobInfo.setCronSyntax(cron);
//			}
//			jobInfo.setCron_expression(this.generateCronExpression(jobInfo));//根据页面的选择设置cron表达式
//			//jobInfo.setCron_expression("0 0 0-18 * * ?");
//			String result=this.jobManager.save(jobInfo);
//			if(!StringUtils.isEmpty(result)){
//				this.json="{result:0,message:'"+result+"'}";
//			}else{
//				this.json="{result:1}";
//			}
//		}catch(Exception e){
//			this.logger.error(e.getMessage(), e);
//			this.json="{result:0,message:'"+e.getMessage()+"'}";
//		}
//		return this.JSON_MESSAGE;
//	}
//	//保存更新
//	public String saveEdit(JobInfo jobInfo){
//		try{
//			//this.jobInfo = this.jobManager.findJobInfo(this.jobInfo);
//			if(!StringUtils.isEmpty(jobInfo.getCronSyntax())){
//				String cron=jobInfo.getCronSyntax();
//				cron=cron.replace("×", "*");
//				jobInfo.setCronSyntax(cron);
//			}
//			jobInfo.setCron_expression(this.generateCronExpression(jobInfo));
//			String result=this.jobManager.update(jobInfo);
//			if(!StringUtils.isEmpty(result)){
//				this.json="{result:0,message:'"+result+"'}";
//			}else{
//				this.json = "{result:1,message:'更新成功！'}";
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			this.logger.error(e.getMessage(), e);
//			this.json="{result:0,message:'"+e.getMessage()+"'}";
//		}
//		return this.JSON_MESSAGE;
//	}
//	//暂停定时任务
//	public String pauseJob(JobInfo jobInfo){
//		try {
//			jobInfo = this.jobManager.findJobInfo(jobInfo);
//			this.jobManager.pauseJob(jobInfo.getJob_name(), jobInfo.getJob_group());
//			this.json = "{result:1,message:'暂停定时任务成功！'}";
//		} catch (Exception e) {
//			this.logger.error(e.getMessage(), e);
//			this.json = "{result:0,message:'"+e.getMessage()+"'}";
//		}
//		return this.JSON_MESSAGE;
//	}
//	//恢复定时任务
//	public String resumeJob(JobInfo jobInfo){
//		try {
//			jobInfo = this.jobManager.findJobInfo(jobInfo);
//			this.jobManager.resumeJob(jobInfo.getJob_name(), jobInfo.getJob_group());
//			this.json = "{result:1,message:'恢复定时任务成功！'}";
//		} catch (Exception e) {
//			this.logger.error(e.getMessage(), e);
//			this.json = "{result:0,message:'"+e.getMessage()+"'}";
//		}
//		return this.JSON_MESSAGE;
//	}
//	//立即执行定时任务
//	public String triggerJob(JobInfo jobInfo){
//		try {
//			jobInfo = this.jobManager.findJobInfo(jobInfo);
//			this.jobManager.triggerJob(jobInfo.getJob_name(), jobInfo.getJob_group());
//			this.json = "{result:1,message:'定时任务执行成功！'}";
//		} catch (Exception e) {
//			this.logger.error(e.getMessage(), e);
//			this.json = "{result:0,message:'"+e.getMessage()+"'}";
//		}
//		return this.JSON_MESSAGE;
//	}
//	//删除定时任务
//	public String delete(JobInfo jobInfo){
//		try {
//			jobInfo = this.jobManager.findJobInfo(jobInfo);
//			this.jobManager.delete(jobInfo);
//			this.json = "{result:1,message:'删除定时任务成功！'}";
//		} catch (Exception e) {
//			this.logger.error(e.getMessage(), e);
//			this.json = "{result:0,message:'"+e.getMessage()+"'}";
//		}
//		return this.JSON_MESSAGE;
//	}
//
//	/**
//	 * 根据前台参数，产生Cron表达式
//	 * @param jobInfo
//	 * @return
//	 */
//	private String generateCronExpression(JobInfo jobInfo){
//		if (jobInfo == null) {
//			return "";
//		}
//
//		if (Const.SCHEDULE_TYPE_INTERVAL.equals(jobInfo.getType())) {  //按分钟循环
//
//			return "0 0/"+jobInfo.getIntervalMins()+" * * * ?";
//
//		} else if (Const.SCHEDULE_TYPE_HOURS.equals(jobInfo.getType())) { //小时
//
//			return "0 "+jobInfo.getMinutes()+" * * * ?";
//
//		} else if (Const.SCHEDULE_TYPE_DAYS.equals(jobInfo.getType())) { //每天
//
//			return "0 "+jobInfo.getMinutes()+" "+ jobInfo.getHours() +" * * ?";
//
//		} else if (Const.SCHEDULE_TYPE_WEEKS.equals(jobInfo.getType())) {//每周
//
//			return "0 "+jobInfo.getMinutes()+" "+jobInfo.getHours()+" ? * "+jobInfo.getDayOfweek();
//
//		} else if (Const.SCHEDULE_TYPE_MONTHS.equals(jobInfo.getType())) {//每月
//
//			return "0 "+jobInfo.getMinutes()+" "+jobInfo.getHours()+" "+jobInfo.getDayOfMonth()+" * ?";
//
//		} else if (Const.SCHEDULE_TYPE_YEARS.equals(jobInfo.getType())) {//每年
//
//			return "0 " + jobInfo.getMinutes() + " " + jobInfo.getHours()+" "+jobInfo.getDayOfMonth()+" "+jobInfo.getMonths()+" ?";
//
//		} else if (Const.SCHEDULE_TYPE_CRON.equals(jobInfo.getType())) {//自定义
//
//			return jobInfo.getCronSyntax();
//		}
//
// 		return "";
//	}
//	/**
//	 * cron 表达式变成相应的时间
//	 * @param jobInfo
//	 */
//	private void analysisCronExpression(JobInfo jobInfo){
//		if (jobInfo == null || StringUtils.isEmpty(jobInfo.getCron_expression())) {
//			return;
//		}
//		if (Const.SCHEDULE_TYPE_INTERVAL.equals(jobInfo.getType())) {  //按分钟循环
//			//cron 表达式   ("0 0/3 * * * ?")
//			String[] temString = jobInfo.getCron_expression().split(" ");
//			jobInfo.setIntervalMins(temString[1].substring(2));
//		} else if (Const.SCHEDULE_TYPE_HOURS.equals(jobInfo.getType())) { //小时
//			//cron 表达式   ("0 3 * * * ?")
//			String[] temString = jobInfo.getCron_expression().split(" ");
//			jobInfo.setMinutes(temString[1]);
//		} else if (Const.SCHEDULE_TYPE_DAYS.equals(jobInfo.getType())) { //每天
//			//cron 表达式   ("0 3 4 * * ?")
//			String[] temString = jobInfo.getCron_expression().split(" ");
//			jobInfo.setMinutes(temString[1]);
//			jobInfo.setHours(temString[2]);
//		} else if (Const.SCHEDULE_TYPE_WEEKS.equals(jobInfo.getType())) {//每周
//			//cron 表达式   ("0 3 4 ? * WEB")
//			String[] temString = jobInfo.getCron_expression().split(" ");
//			jobInfo.setMinutes(temString[1]);
//			jobInfo.setHours(temString[2]);
//			jobInfo.setDayOfweek(temString[temString.length-1]);
//		} else if (Const.SCHEDULE_TYPE_MONTHS.equals(jobInfo.getType())) {//每月
//			//cron 表达式   ("0 3 4 5 * ?")
//			String[] temString = jobInfo.getCron_expression().split(" ");
//			jobInfo.setMinutes(temString[1]);
//			jobInfo.setHours(temString[2]);
//			jobInfo.setDayOfMonth(temString[3]);
//		} else if (Const.SCHEDULE_TYPE_YEARS.equals(jobInfo.getType())) {//每年
//			//cron 表达式   ("0 3 4 5 6 ?")
//			String[] temString = jobInfo.getCron_expression().split(" ");
//			jobInfo.setMinutes(temString[1]);
//			jobInfo.setHours(temString[2]);
//			jobInfo.setDayOfMonth(temString[3]);
//			jobInfo.setMonths(temString[4]);
//		}
//
//	}
//}
