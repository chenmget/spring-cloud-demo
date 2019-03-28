//package com.iwhalecloud.retail.oms.quartz.model;
//
//import org.quartz.JobDataMap;
//
//import java.io.Serializable;
//
///**
// * @Description
// * @author  zhangJun
// * @date    2016年3月23日
// * @version 1.0
// */
//public class JobInfo implements Serializable {
//
//	private String job_info_id;
//	private String job_info_name;
//	private String start_now;
//	private String create_date;
//	private String job_name;
//	private String job_group;
//
//
//	private String job_class_name;
//	private String description;
//	private String cron_expression;
//	private String next_fire_time_date;
//	private String prev_fire_time_date;
//	private String trigger_state;
//
//	private String trigger_state_name;
//	private String sched_name;
//	private String sched_name_is_my;
//	private String is_only_my_sched;
//
//
//	private String type;//前台页面类型  INTERVAL按分钟  HOURS小时   DAYS每天   WEEKS每周   MONTHS每月  YEARS每年   CRON自定义
//    private String intervalMins;//循环间隔时间（分）
//    private String months;//月份
//    private String dayOfMonth;//日
//    private String dayOfweek;//星期
//    private String hours;//小时
//    private String minutes;//分钟
//    private String cronSyntax;//CRON公式
//
//	private JobDataMap jobDataMap;
//
//	public String getJob_info_id(){
//		return job_info_id;
//	}
//
//	public void setJob_info_id(String job_info_id){
//		this.job_info_id = job_info_id;
//	}
//
//	public String getJob_info_name(){
//		return job_info_name;
//	}
//
//	public void setJob_info_name(String job_info_name){
//		this.job_info_name = job_info_name;
//	}
//
//	public String getStart_now(){
//		return start_now;
//	}
//
//	public void setStart_now(String start_now){
//		this.start_now = start_now;
//	}
//
//	public String getCreate_date(){
//		return create_date;
//	}
//
//	public void setCreate_date(String create_date){
//		this.create_date = create_date;
//	}
//
//	public String getJob_name(){
//		return job_name;
//	}
//
//	public void setJob_name(String job_name){
//		this.job_name = job_name;
//	}
//
//	public String getJob_group(){
//		return job_group;
//	}
//
//	public void setJob_group(String job_group){
//		this.job_group = job_group;
//	}
//	@NotDbField
//	public String getJob_class_name(){
//		return job_class_name;
//	}
//
//	public void setJob_class_name(String job_class_name){
//		this.job_class_name = job_class_name;
//	}
//
//	@NotDbField
//	public String getDescription(){
//		return description;
//	}
//
//	public void setDescription(String description){
//		this.description = description;
//	}
//	@NotDbField
//	public String getCron_expression(){
//		return cron_expression;
//	}
//
//	public void setCron_expression(String cron_expression){
//		this.cron_expression = cron_expression;
//	}
//	@NotDbField
//	public String getNext_fire_time_date() {
//		return next_fire_time_date;
//	}
//
//	public void setNext_fire_time_date(String next_fire_time_date) {
//		this.next_fire_time_date = next_fire_time_date;
//	}
//	@NotDbField
//	public String getPrev_fire_time_date() {
//		return prev_fire_time_date;
//	}
//
//	public void setPrev_fire_time_date(String prev_fire_time_date) {
//		this.prev_fire_time_date = prev_fire_time_date;
//	}
//
//	@NotDbField
//	public String getTrigger_state(){
//		return trigger_state;
//	}
//
//	public void setTrigger_state(String trigger_state){
//		this.trigger_state = trigger_state;
//	}
//	@NotDbField
//	public JobDataMap getJobDataMap(){
//		return jobDataMap;
//	}
//
//	public void setJobDataMap(JobDataMap jobDataMap){
//		this.jobDataMap = jobDataMap;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	@NotDbField
//	public String getIntervalMins() {
//		return intervalMins;
//	}
//
//	public void setIntervalMins(String intervalMins) {
//		this.intervalMins = intervalMins;
//	}
//
//	@NotDbField
//	public String getMonths() {
//		return months;
//	}
//
//	public void setMonths(String months) {
//		this.months = months;
//	}
//
//	@NotDbField
//	public String getDayOfMonth() {
//		return dayOfMonth;
//	}
//
//	public void setDayOfMonth(String dayOfMonth) {
//		this.dayOfMonth = dayOfMonth;
//	}
//
//	@NotDbField
//	public String getDayOfweek() {
//		return dayOfweek;
//	}
//
//	public void setDayOfweek(String dayOfweek) {
//		this.dayOfweek = dayOfweek;
//	}
//
//	@NotDbField
//	public String getHours() {
//		return hours;
//	}
//
//	public void setHours(String hours) {
//		this.hours = hours;
//	}
//
//	@NotDbField
//	public String getMinutes() {
//		return minutes;
//	}
//
//	public void setMinutes(String minutes) {
//		this.minutes = minutes;
//	}
//
//	@NotDbField
//	public String getCronSyntax() {
//		return cronSyntax;
//	}
//
//	public void setCronSyntax(String cronSyntax) {
//		this.cronSyntax = cronSyntax;
//	}
//	@NotDbField
//	public String getTrigger_state_name() {
//		return trigger_state_name;
//	}
//
//	public void setTrigger_state_name(String trigger_state_name) {
//		this.trigger_state_name = trigger_state_name;
//	}
//	@NotDbField
//	public String getSched_name() {
//		return sched_name;
//	}
//
//	public void setSched_name(String sched_name) {
//		this.sched_name = sched_name;
//	}
//	@NotDbField
//	public String getIs_only_my_sched() {
//		return is_only_my_sched;
//	}
//
//	public void setIs_only_my_sched(String is_only_my_sched) {
//		this.is_only_my_sched = is_only_my_sched;
//	}
//	@NotDbField
//	public String getSched_name_is_my() {
//		return sched_name_is_my;
//	}
//
//	public void setSched_name_is_my(String sched_name_is_my) {
//		this.sched_name_is_my = sched_name_is_my;
//	}
//
//}