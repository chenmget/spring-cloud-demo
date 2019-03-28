package com.iwhalecloud.retail.oms.quartz.model;

import java.io.Serializable;

/**
 * @Description
 * @author  zhangJun
 * @date    2016年3月23日
 * @version 1.0
 */
public class JobSchedulerInfo implements Serializable {
	
	/**
	 * 
	 */
	
	private String sched_name;
	private String instance_name;
	private String last_checkin_time_date;
	private String checkin_interval_date;

	public String getSched_name() {
		return sched_name;
	}

	public void setSched_name(String sched_name) {
		this.sched_name = sched_name;
	}

	public String getInstance_name() {
		return instance_name;
	}

	public void setInstance_name(String instance_name) {
		this.instance_name = instance_name;
	}

	public String getLast_checkin_time_date() {
		return last_checkin_time_date;
	}

	public void setLast_checkin_time_date(String last_checkin_time_date) {
		this.last_checkin_time_date = last_checkin_time_date;
	}

	public String getCheckin_interval_date() {
		return checkin_interval_date;
	}

	public void setCheckin_interval_date(String checkin_interval_date) {
		this.checkin_interval_date = checkin_interval_date;
	}

}