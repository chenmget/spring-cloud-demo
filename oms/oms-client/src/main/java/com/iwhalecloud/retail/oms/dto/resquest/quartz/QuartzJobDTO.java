package com.iwhalecloud.retail.oms.dto.resquest.quartz;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @author  gongs
 * @date    2016年3月23日
 * @version 1.0
 */
@Data
public class QuartzJobDTO implements Serializable {
	
	private String job_info_id;
	private String job_info_name;
	private String start_now;
	private String create_date;
	private String job_name;
	private String job_group;
	
	
	private String job_class_name;
	private String description;
	private String cron_expression;
	private String next_fire_time_date;
	private String prev_fire_time_date;
	private String trigger_state;
	
	private String trigger_state_name;
	private String sched_name;
	private String sched_name_is_my;
	private String is_only_my_sched;
	
	
	private String type;//前台页面类型  INTERVAL按分钟  HOURS小时   DAYS每天   WEEKS每周   MONTHS每月  YEARS每年   CRON自定义
    private String intervalMins;//循环间隔时间（分）
    private String months;//月份
    private String dayOfMonth;//日
    private String dayOfweek;//星期
    private String hours;//小时
    private String minutes;//分钟
    private String cronSyntax;//CRON公式
	
}