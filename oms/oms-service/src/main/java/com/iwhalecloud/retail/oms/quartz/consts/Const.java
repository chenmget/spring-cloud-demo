package com.iwhalecloud.retail.oms.quartz.consts;


public class Const {
	 /**定时任务状态*/
   	public static final String TRIGGER_STATE_WAITING = "WAITING";//等待
   	public static final String TRIGGER_STATE_ACQUIRED = "ACQUIRED";//正常执行
   	public static final String TRIGGER_STATE_COMPLETE = "COMPLETE";//完成
   	public static final String TRIGGER_STATE_PAUSED = "PAUSED";//暂停
   	public static final String TRIGGER_STATE_BLOCKED = "BLOCKED";//阻塞
   	public static final String TRIGGER_STATE_PAUSED_BLOCKED = "PAUSED_BLOCKED";//暂停阻塞
   	public static final String TRIGGER_STATE_ERROR = "ERROR";//错误
	public static final String TRIGGER_STATE_DELETED = "DELETED";//删除
	public static final String TRIGGER_STATE_EXECUTING = "EXECUTING";//执行中
   	
   	/**页面类型*/
   	public static final String SCHEDULE_TYPE_INTERVAL = "INTERVAL";//按分钟循环
   	public static final String SCHEDULE_TYPE_HOURS = "HOURS";//小时
   	public static final String SCHEDULE_TYPE_DAYS = "DAYS";//每天
   	public static final String SCHEDULE_TYPE_WEEKS = "WEEKS";//每周
   	public static final String SCHEDULE_TYPE_MONTHS = "MONTHS";//每月
   	public static final String SCHEDULE_TYPE_YEARS = "YEARS";//每年
   	public static final String SCHEDULE_TYPE_CRON = "CRON";//自定义
}
