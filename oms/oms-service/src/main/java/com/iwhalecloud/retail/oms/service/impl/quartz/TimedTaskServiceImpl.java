//package com.iwhalecloud.retail.oms.service.impl.quartz;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.fastjson.JSON;
//import com.iwhalecloud.retail.oms.OmsCommonConsts;
//import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
//import com.iwhalecloud.retail.oms.dto.resquest.quartz.QuartzJobDTO;
//import com.iwhalecloud.retail.oms.quartz.action.JobAction;
//import com.iwhalecloud.retail.oms.quartz.model.JobInfo;
//import com.iwhalecloud.retail.oms.service.quartz.TimedTaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Service
//public class TimedTaskServiceImpl implements TimedTaskService {
//
//    @Autowired
//    private JobAction jobAction;
//
//
//    @Override
//    public CommonResultResp queryList(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.queryList(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp jobSchedulerList(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.jobSchedulerList(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp edit(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.edit(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp saveAdd(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.saveAdd(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp saveEdit(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.saveEdit(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp pauseJob(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.pauseJob(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp resumeJob(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.resumeJob(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp triggerJob(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.triggerJob(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//
//    @Override
//    public CommonResultResp delete(QuartzJobDTO quartzJobDTO) {
//        CommonResultResp resp=new CommonResultResp();
//
//        try {
//            JobInfo jobInfo = JSON.parseObject(JSON.toJSONString(quartzJobDTO), JobInfo.class);
//            resp.setResultData(jobAction.delete(jobInfo));
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        }catch (Exception e){
//            e.printStackTrace();
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg(e.getMessage());
//        }
//        return resp;
//    }
//}
