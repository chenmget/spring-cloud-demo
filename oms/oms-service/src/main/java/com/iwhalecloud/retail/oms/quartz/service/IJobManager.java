//package com.iwhalecloud.retail.oms.quartz.service;
//
//
//import com.iwhalecloud.retail.oms.quartz.model.JobInfo;
//import com.ztesoft.net.framework.database.Page;
//
//
///**
// * @Description 集群定时任务管理
// * @author  zhangJun
// * @date    2016年3月22日
// * @version 1.0
// */
//public interface IJobManager {
//	/**
//	 * 新增
//	 */
//	public String save(JobInfo jobInfo) throws Exception;
//	/**
//	 * 更新
//	 */
//	public String update(JobInfo jobInfo) throws Exception;
//	/**
//	 * 删除
//	 */
//	public String delete(JobInfo jobInfo);
//	/**
//	 * 列表查询
//	 */
//	public Page queryList(JobInfo jobInfo, int pageNo, int pageSize) throws Exception;
//	/**
//	 * 暂停任务
//	 */
//	public String pauseJob(String job_name, String job_group);
//	/**
//	 * 恢复任务
//	 */
//	public String resumeJob(String job_name, String job_group);
//	/**
//	 * 立即执行
//	 */
//	public String triggerJob(String job_name, String job_group);
//
//	/**
//	 * 根据id查找任务
//	 * @param jobInfo
//	 * @return
//	 */
//	public JobInfo findJobInfo(JobInfo jobInfo)throws Exception;
//	/**
//	 * 列表查询
//	 */
//	public Page jobSchedulerList(int pageNo, int pageSize) ;
//}
