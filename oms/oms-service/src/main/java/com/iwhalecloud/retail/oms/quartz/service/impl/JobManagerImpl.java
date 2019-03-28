//package com.iwhalecloud.retail.oms.quartz.service.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.quartz.CronScheduleBuilder;
//import org.quartz.CronTrigger;
//import org.quartz.Job;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.JobKey;
//import org.quartz.Scheduler;
//import org.quartz.TriggerBuilder;
//import org.quartz.TriggerKey;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.iwhalecloud.retail.oms.mapper.QJobManagerMapper;
//import com.iwhalecloud.retail.oms.quartz.consts.Const;
//import com.iwhalecloud.retail.oms.quartz.model.JobInfo;
//import com.iwhalecloud.retail.oms.quartz.service.IJobManager;
//import com.iwhalecloud.retail.oms.quartz.utils.DateUtil;
//import com.iwhalecloud.retail.oms.quartz.utils.IReflectionUtil;
//import com.iwhalecloud.retail.oms.quartz.utils.SequenceTools;
//import com.ztesoft.net.framework.database.Page;
//
//
///**
// * @author zhangJun
// * @version 1.0
// * @Description
// * @date 2016年3月23日
// */
//@Component
//public class JobManagerImpl implements IJobManager {
//
//    @Autowired
//    private Scheduler scheduler;
//
//    @Resource
//    private QJobManagerMapper qJobManagerMapper;
//
//
//    @Override
//    public Page queryList(JobInfo jobInfo, int pageNo, int pageSize) throws Exception {
////        StringBuffer sql = new StringBuffer(SF.baseSql("QUERY_JOB_LIST"));
//        Map<String, String> where = new HashMap<String, String>();
//        where.put("sched_name", scheduler.getSchedulerName());
//        if (!StringUtils.isEmpty(jobInfo.getJob_info_name())) {
////            sql.append(" and a.job_info_name like :job_info_name");
//            where.put("job_info_name", "%" + jobInfo.getJob_info_name() + "%");
//        }
//        if (!StringUtils.isEmpty(jobInfo.getJob_class_name())) {
////            sql.append(" and b.job_class_name like :job_class_name");
//            where.put("job_class_name", "%" + jobInfo.getJob_class_name() + "%");
//
//        }
//        if (!StringUtils.isEmpty(jobInfo.getTrigger_state())) {
////            sql.append(" and c.trigger_state = :trigger_state");
//            where.put("trigger_state", jobInfo.getTrigger_state());
//        }
//        if (!StringUtils.isEmpty(jobInfo.getIs_only_my_sched()) && jobInfo.getIs_only_my_sched().equals("yes")) {
//
////            sql.append(" and b.sched_name = :sched_name");
//            where.put("sched_name", scheduler.getSchedulerName());
//        }
////        sql.append(" order by b.job_class_name ");
////        Page page = daoSupport.queryForPageByMap(sql.toString(), pageNo, pageSize, JobInfo.class, where);
//        Page page = new Page();
//        List list = qJobManagerMapper.queryList(where);
//        page.setResult(list);
//        return page;
//    }
//
//    @Override
//    public String save(JobInfo jobInfo) throws Exception {
//        String result = "";
//        result = this.validateJobInfo(jobInfo);
//        if (!StringUtils.isEmpty(result)) {
//            return result;
//        }
//        result = this.validateSingle(jobInfo, scheduler.getSchedulerName());
//        if (!StringUtils.isEmpty(result)) {
//            return result;
//        }
//
//
//        Class<Job> clazz = (Class<Job>) Class.forName(jobInfo.getJob_class_name());
//        jobInfo.setJob_info_id(SequenceTools.getDateRandom(DateUtil.DATE_FORMAT_7, 1));
//        jobInfo.setJob_name(jobInfo.getJob_info_id() + "_" + clazz.getSimpleName());
//        jobInfo.setJob_group(Scheduler.DEFAULT_GROUP);
//        jobInfo.setCreate_date(DateUtil.getTime2());
//
//        JobBuilder jobBuilder = JobBuilder.newJob(clazz);
//        jobBuilder.withIdentity(jobInfo.getJob_name(), jobInfo.getJob_group()).withDescription(jobInfo.getDescription());
//        if (jobInfo.getJobDataMap() != null) {//传参数用
//            jobBuilder.usingJobData(jobInfo.getJobDataMap());
//        }
//        JobDetail jobDetail = jobBuilder.build();
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCron_expression());
//        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobInfo.getJob_name(), jobInfo.getJob_group()).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing());
//        CronTrigger trigger = triggerBuilder.build();
//        scheduler.scheduleJob(jobDetail, trigger);//此方法会自动写qrtz_*的表
//
//        //暂停任务
//        if (Const.TRIGGER_STATE_PAUSED.equals(jobInfo.getTrigger_state())) {
//            scheduler.pauseJob(jobDetail.getKey());
//        }
//
//        Map map=IReflectionUtil.po2Map(jobInfo);
//        qJobManagerMapper.save(map);
////        daoSupport.insert("es_qrtz_job_info", jobInfo);
//
//        return result;
//
//
//    }
//
//
//    @Override
//    public String update(JobInfo jobInfo) throws Exception {
//        String result = "";
//        //校验输入格式是否正确
//        result = this.validateJobInfo(jobInfo);
//        if (!StringUtils.isEmpty(result)) {
//            throw new Exception(result);
//        }
//
//        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJob_name(), jobInfo.getJob_group());
//        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//
//        if (trigger == null) {
//            return result = "任务触发器不存在,请确认该定时任务是否属于该应用所配置的集群";
//        } else {
//            if (!jobInfo.getCron_expression().equals(trigger.getCronExpression())) {
//                //修改了时间表达式，重新调用任务
//                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCron_expression());
//                TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobInfo.getJob_name(), jobInfo.getJob_group()).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing());
//                trigger = triggerBuilder.build();
//                scheduler.rescheduleJob(triggerKey, trigger);
//
//                //rescheduleJob后会重新调度任务，如果原来的任务状态是暂停，则暂停任务
//                if (Const.TRIGGER_STATE_PAUSED.equals(jobInfo.getTrigger_state())) {
//                    JobKey jobKey = JobKey.jobKey(jobInfo.getJob_name(), jobInfo.getJob_group());
//                    scheduler.pauseJob(jobKey);
//                }
//                //查询任务状态
//                String trigger_state = this.queryTriggerState(jobInfo.getJob_name(), jobInfo.getJob_group());
//                //回填触发器信息
//                jobInfo.setNext_fire_time_date(DateUtil.formatDate(trigger.getNextFireTime(), DateUtil.DATE_FORMAT_2));
//                jobInfo.setPrev_fire_time_date(DateUtil.formatDate(trigger.getPreviousFireTime(), DateUtil.DATE_FORMAT_2));
//                jobInfo.setTrigger_state(trigger_state);
//            }
////            String sql = SF.baseSql("UPDATE_QRTZ_JOB_INFO");
////            daoSupport.execute(sql, jobInfo.getJob_info_name(), jobInfo.getStart_now(), jobInfo.getType(), jobInfo.getJob_info_id());
//            Map map = JSON.parseObject(JSON.toJSONString(jobInfo), Map.class);
//            qJobManagerMapper.update(map);
//        }
//        return result;
//
//
//    }
//
//
//    @Override
//    public String delete(JobInfo jobInfo) {
//        String result = "";
//        if (StringUtils.isEmpty(jobInfo.getJob_info_id())) {
//            result = "任务信息id不能为空";
//            return result;
//        }
//        if (StringUtils.isEmpty(jobInfo.getJob_name())) {
//            result = "job_name不能为空";
//            return result;
//        }
//        if (StringUtils.isEmpty(jobInfo.getJob_group())) {
//            result = "job_group不能为空";
//            return result;
//        }
//        try {
////            String sql = "delete from es_qrtz_job_info where job_info_id = ?";
////            daoSupport.execute(sql, jobInfo.getJob_info_id());
//            Map<String, Object> map = new HashMap<>();
//            map.put("job_info_id", jobInfo.getJob_info_id());
//            qJobManagerMapper.delete(map);
//            JobKey jobKey = JobKey.jobKey(jobInfo.getJob_name(), jobInfo.getJob_group());
//            scheduler.deleteJob(jobKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = "删除任务失败：" + e.getMessage();
//        }
//        return result;
//    }
//
//
//    /**
//     * 校验定时任务的唯一性：实例名+class
//     *
//     * @param jobInfo
//     * @return
//     */
//    private String validateSingle(JobInfo jobInfo, String schedulerName) {
//        String messages = "";
////        String sql = "select a.*  from es_qrtz_job_details a where a.sched_name='" + schedulerName + "' and a.job_class_name='" + jobInfo.getJob_class_name() + "' and  a.source_from is null ";
////        List list = daoSupport.queryForList(sql);
//        Map<String, Object> map = new HashMap<>();
//        map.put("schedulerName", schedulerName);
//        map.put("job_class_name", jobInfo.getJob_class_name());
//        List list = qJobManagerMapper.validateSingle(map);
//        if (list != null && list.size() > 0) {
//            messages = "该集群名称下已存在该java类的定时任务";
//        }
//        return messages;
//
//    }
//
//    /**
//     * 校验任务信息
//     *
//     * @param jobInfo
//     * @return
//     */
//    private String validateJobInfo(JobInfo jobInfo) {
//        String messages = "";
//        if (jobInfo == null) {
//            messages = "任务对象不能为空、";
//        } else {
//            if (StringUtils.isEmpty(jobInfo.getJob_info_name())) {
//                messages = messages + "任务名称不能为空、";
//            }
//
//            if (StringUtils.isEmpty(jobInfo.getJob_class_name())) {
//                messages = messages + "任务执行类不能为空、";
//            } else {
//                try {
//                    Class<?> clazz = Class.forName(jobInfo.getJob_class_name());
//                    if (!Job.class.isAssignableFrom(clazz)) {
//                        messages = messages + "任务执行类必须实现org.quartz.Job接口、";
//                    }
//                } catch (ClassNotFoundException e) {
//                    messages = messages + "任务执行类不存在、";
//                }
//            }
//
//            if (StringUtils.isEmpty(jobInfo.getCron_expression())) {
//                messages = messages + "时间表达式不能为空、";
//            } else {
//                try {
//                    CronScheduleBuilder.cronSchedule(jobInfo.getCron_expression());
//                } catch (Exception e) {
//                    messages = messages + "时间表达式格式错误(请按照quartz时间表达式规范配置)";
//                }
//            }
//        }
//
//        return messages;
//    }
//
//
//    @Override
//    public String pauseJob(String job_name, String job_group) {
//        String result = "";
//        if (StringUtils.isEmpty(job_name)) {
//            result = "job_name不能为空";
//            return result;
//        }
//        if (StringUtils.isEmpty(job_group)) {
//            result = "job_group不能为空";
//            return result;
//        }
//        try {
//            JobKey jobKey = JobKey.jobKey(job_name, job_group);
//            scheduler.pauseJob(jobKey);
//
//            String trigger_state = this.queryTriggerState(job_name, job_group);//需要返回
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = "暂停任务失败：" + e.getMessage();
//        }
//
//        return result;
//    }
//
//
//    @Override
//    public String resumeJob(String job_name, String job_group) {
//
//        String result = "";
//        if (StringUtils.isEmpty(job_name)) {
//            result = "job_name不能为空";
//            return result;
//        }
//        if (StringUtils.isEmpty(job_group)) {
//            result = "job_group不能为空";
//            return result;
//        }
//        try {
//            JobKey jobKey = JobKey.jobKey(job_name, job_group);
//            scheduler.resumeJob(jobKey);
//
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("job_name", job_name);
//            params.put("job_group", job_group);
//            String trigger_state = this.queryTriggerState(job_name, job_group);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = "恢复任务失败：" + e.getMessage();
//        }
//
//        return result;
//
//    }
//
//
//    @Override
//    public String triggerJob(String job_name, String job_group) {
//        String result = "";
//        if (StringUtils.isEmpty(job_name)) {
//            result = "job_name不能为空";
//            return result;
//        }
//        if (StringUtils.isEmpty(job_group)) {
//            result = "job_group不能为空";
//            return result;
//        }
//        try {
//            JobKey jobKey = JobKey.jobKey(job_name, job_group);
//            scheduler.triggerJob(jobKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = "执行任务失败：" + e.getMessage();
//        }
//        return result;
//
//    }
//
//    /**
//     * 查询任务状态
//     */
//    private String queryTriggerState(String job_name, String job_group) {
////        String sql = SF.baseSql("QUERY_TRIGGER_STATE");
////        String trigger_state = daoSupport.queryForString(sql, job_name, job_group);
//        Map<String, Object> map = new HashMap<>();
//        map.put("job_name", job_name);
//        map.put("job_group", job_group);
//        String trigger_state = qJobManagerMapper.queryTriggerState(map);
//        return trigger_state;
//    }
//
//    @Override
//    public JobInfo findJobInfo(JobInfo jobInfo) throws Exception {
////        StringBuffer sql = new StringBuffer(SF.baseSql("QUERY_JOB_LIST"));
////        sql.append(" and a.job_info_id = '" + jobInfo.getJob_info_id() + "'");
////        where.put("sched_name", scheduler.getSchedulerName());
////        JobInfo result = this.daoSupport.queryForObjectByMap(sql.toString(), JobInfo.class, where);
//        Map<String, String> where = new HashMap<String, String>();
//        where.put("job_info_id", jobInfo.getJob_info_id());
//        where.put("sched_name", scheduler.getSchedulerName());
//        List<Map> list = qJobManagerMapper.queryList(where);
//        if (CollectionUtils.isEmpty(list)) {
//            return null;
//        }
//        JobInfo result = new JobInfo();
//        BeanUtils.copyProperties(list.get(0), result);
//        return result;
//    }
//
//
//    @Override
//    public Page jobSchedulerList(int pageNo, int pageSize) {
////        StringBuffer sql = new StringBuffer(SF.baseSql("QUERY_JOB_INSTANCE_LIST"));
////        IDaoSupport daoSupport2 = SpringContextHolder.getBean("daoSupport");
////        Page page = daoSupport2.queryForPage(sql.toString(), pageNo, pageSize, JobSchedulerInfo.class);
//        Map<String, Object> map = new HashMap<>();
//        List list = qJobManagerMapper.jobSchedulerList(map);
//        Page page = new Page();
//        page.setResult(list);
//        return page;
//
//    }
//
//}
