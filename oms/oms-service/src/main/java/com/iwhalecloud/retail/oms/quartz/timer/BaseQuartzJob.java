//package com.iwhalecloud.retail.oms.quartz.timer;
//
//import com.iwhalecloud.retail.oms.quartz.service.IJobTaskManager;
//import org.quartz.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@DisallowConcurrentExecution //禁止定时任务并行执行
//public abstract class BaseQuartzJob implements Job {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    //定义抽象方法，供子类实现
//    public abstract void action(JobExecutionContext context);
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        try {
//            //后续可以加入写日志api,需要完善
////            Scheduler scheduler = SpringContextHolder.getBean("quartzJobFactory");
////            String id = scheduler.getSchedulerInstanceId();
////            String jobName = context.getJobDetail().getKey().getName();
////            logger.error("定时任务执行开始：" + jobName);
////            IJobTaskManager jobTaskManager = SpringContextHolder.getBean("jobTaskManager");
////            JobRunLog log = new JobRunLog(jobName, id, "", this.getClass().getName(), "action");
////            log.setRun_time("sysdate");
////            jobTaskManager.insertJobRunLog(log);
//            //
//            long start = System.currentTimeMillis();
//            this.action(context);
//            long end = System.currentTimeMillis();
////            logger.error(jobName + "定时任务执行花费时间：" + (end - start));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
