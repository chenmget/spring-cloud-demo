//package com.iwhalecloud.retail.oms.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.iwhalecloud.retail.oms.OmsServiceApplication;
//import com.iwhalecloud.retail.oms.quartz.action.JobAction;
//import com.iwhalecloud.retail.oms.quartz.model.JobInfo;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OmsServiceApplication.class)
//public class QuarzTimerServiceTest {
//
//    @Autowired
//    private JobAction jobAction;
//
//
//
//    @Test
//    public void quartzSave(){
//
//
//
//        JobInfo jobInfo= new JobInfo();
//        jobInfo.setJob_info_name("定时任务测试1");
//        jobInfo.setJob_class_name("com.iwhalecloud.retail.oms.quartz.timer.TestJob1");
//        jobInfo.setDescription("定时任务");
//        jobInfo.setType("INTERVAL");
//        jobInfo.setIntervalMins("1");
//        Object o ="";
////       jobInfo.setJob_name("");
////       jobInfo.setJob_group("DEFAULT");
////       jobInfo.setJob_info_id("20181112151704057003");
//
//
//        o=  jobAction.saveAdd(jobInfo);
////      o=   jobAction.triggerJob(jobInfo);
////      o=   jobAction.pauseJob(jobInfo);
////        jobAction.jobSchedulerList(jobInfo);
////        jobAction.edit(jobInfo);
////        jobAction.queryList(jobInfo);
////      o=     jobAction.delete(jobInfo);
//        System.out.println(JSON.toJSONString(o));
//    }
//}
