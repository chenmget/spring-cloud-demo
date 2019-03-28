//package com.iwhalecloud.retail.oms.quartz.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.iwhalecloud.retail.oms.mapper.QJobTaskManagerMapper;
//import com.iwhalecloud.retail.oms.quartz.service.IJobTaskManager;
//import com.iwhalecloud.retail.oms.quartz.utils.DBTUtil;
//import com.ztesoft.net.mall.core.model.JobRunLog;
//import com.ztesoft.net.mall.core.model.JobTask;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class JobTaskManager implements IJobTaskManager {
//
//    @Autowired
//    private QJobTaskManagerMapper jobTaskManagerMapper;
//
//    @Override
//    public List<JobTask> queryJobTask(String className, String method) {
////		String sql = "select t.* from ES_JOB_TASK t where t.class_name=? and t.run_method=? and t.status='00A' and "+ DBTUtil.current()+" between t.start_time and t.end_time";
////		List<JobTask> jobList = this.baseDaoSupport.queryForList(sql, JobTask.class, className,method);
//        Map<String, Object> paramsMap = new HashMap<>();
//        paramsMap.put("className", className);
//        paramsMap.put("runMethod", method);
//        paramsMap.put("curTime", DBTUtil.current());
//        List<Map> mapList = jobTaskManagerMapper.queryJobTask1(paramsMap);
//
//        //按method_name查询不到，则按class搜索
//        if (CollectionUtils.isEmpty(mapList)) {
////			sql = "select t.* from ES_JOB_TASK t where t.class_name=? and t.status='00A' and "+ DBTUtil.current()+" between t.start_time and t.end_time";
////			jobList = this.baseDaoSupport.queryForList(sql, JobTask.class, className);
//            mapList = jobTaskManagerMapper.queryJobTask2(paramsMap);
//        }
//        List<JobTask> jobList = JSON.parseArray(JSON.toJSONString(mapList), JobTask.class);
//        return jobList;
//    }
//
//    @Override
//    public void updateJobTask(String status, String job_id) {
////        String sql = "update ES_JOB_TASK t set t.status=? where job_id=?";
////        this.baseDaoSupport.execute(sql, status, job_id);
//        Map<String,Object> map=new HashMap<>();
//        map.put("status",status);
//        map.put("jobId",job_id);
//        jobTaskManagerMapper.updateJobTask(map);
//    }
//
//    @Override
//    public void insertJobRunLog(JobRunLog log) {
////        this.baseDaoSupport.insert("ES_JOB_RUN_LOG", log);
////        jobTaskManagerMapper.insertJobRunLog();
//    }
//
//    @Override
//    public List<JobTask> listJobTask() {
//        //修改:查询所有定时任务, 支持定时任务可更改缓存.启动/暂停定时任务
////		String sql = "select t.* from ES_JOB_TASK t where t.status='00A' and "+DBTUtil.current()+" between t.start_time and t.end_time and source_from is not null";
////        String sql = "select t.* from ES_JOB_TASK t where " + DBTUtil.current() + " between t.start_time and t.end_time and source_from is not null";
////        List<JobTask> jobList = this.baseDaoSupport.queryForList(sql, JobTask.class);
//        Map<String,Object> map=new HashMap<>();
//        map.put("curTime",map);
//        List<Map> mapList= jobTaskManagerMapper.listJobTask(map);
//        List<JobTask> jobList = JSON.parseArray(JSON.toJSONString(mapList), JobTask.class);
//        return jobList;
//    }
//
//}
