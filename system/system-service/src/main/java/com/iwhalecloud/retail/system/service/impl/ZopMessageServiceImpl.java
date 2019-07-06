package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.ZopMessageConst;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.RandomLogGetResp;
import com.iwhalecloud.retail.system.dto.request.SmsVerificationtemplate;
import com.iwhalecloud.retail.system.model.ZopMsgModel;
import com.iwhalecloud.retail.system.service.RandomLogService;
import com.iwhalecloud.retail.system.service.ZopMessageService;
import com.iwhalecloud.retail.system.utils.ZopMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


import java.util.*;

@Slf4j
@Service
public class ZopMessageServiceImpl implements ZopMessageService {

    @Autowired
    private ZopMsgUtil zopMsgUtil;

    @Autowired
    private RandomLogService randomLogService;

    @Override
    public ResultVO sendRegistVerifyCode(VerifyCodeGetReq getReq) {
        try {
            checkReq(getReq);
            //产生六位随机验证码
            String verifyCode = generateCode();
            //调用能开发送验证码
           if(!sendSmsVerificationCode(getReq.getPhoneNo(),getReq.getLandId(),verifyCode,getReq.getOperatType())){
               return ResultVO.error("zop msg remote fail");
           }
            //插入数据库
            insertVerifyCode(getReq,verifyCode);
        }catch (Exception e){
            log.info(e.getMessage());
            return ResultVO.error();
        }
        return ResultVO.success();
    }

    private void checkReq(BaseZopMsgReq getReq) {
        if(null == getReq.getPhoneNo() || getReq.getPhoneNo().equals("")){
            throw new RuntimeException("phoneNo must not null");
        }
        if(null == getReq.getLandId() || getReq.getLandId().equals("")){
            throw new RuntimeException("lanId must not null");
        }
    }


    public ResultVO checkVerifyCode(String phoneNo,String verifyCode){
        Assert.notNull(phoneNo,"电话不能空");
        Assert.notNull(verifyCode,"验证码不能为空");
        RandomLogGetReq req = new RandomLogGetReq();
        req.setReceviNo(phoneNo);
        //selectLogIdByRandomCode 写死查询的是失效大于当前时间所以会有查询DATA为null
        ResultVO<RandomLogGetResp> resultVO = randomLogService.selectLogIdByRandomCode(req);
        RandomLogGetResp resp = resultVO.getResultData();
        if(resp == null)return ResultVO.error(ZopMessageConst.VERIFYCODE_ERR);

        if(verifyCode.equals(resp.getRandomCode())){
            RandomLogUpdateReq randomLogUpdateReq = new RandomLogUpdateReq();
            randomLogUpdateReq.setLogId(resp.getLogId());
            randomLogUpdateReq.setValidStatus(1);
            randomLogUpdateReq.setValidTime(new Date());
            randomLogService.updateByPrimaryKey(randomLogUpdateReq);
            return ResultVO.success();
        }
        return  ResultVO.error(ZopMessageConst.VERIFYCODE_ERR) ;
    }

    @Override
    public ResultVO noticeMsg(NoticeMsgReq msgReq) {
      try {
          List<BaseZopMsgReq> zopMsgReqs = msgReq.getBaseZopMsgReqs();
          List  zopModeList = new ArrayList();
          for(BaseZopMsgReq req : zopMsgReqs){
              checkReq(req);
              ZopMsgModel model = new ZopMsgModel();
              //模板ID要改
              model.setBusinessId(msgReq.getBusId());
              model.setLatnId(req.getLandId());
              model.setToTel(req.getPhoneNo());
              model.setSentContent("");
              zopModeList.add(model);
          }
          if(zopMsgUtil.SendMsgs(zopModeList,msgReq.getTemplateList())){
              return ResultVO.success();
          }
      }catch (Exception e){
          log.info(e.getMessage());
          return ResultVO.error();
      }
        return ResultVO.error();
    }


    private boolean sendSmsVerificationCode(String phoneNo,String landId,String verifyCode,int opType) {
        ZopMsgModel model = new ZopMsgModel();
        SmsVerificationtemplate template = new SmsVerificationtemplate();
        template.setSmsVerificationCode(verifyCode);
        if(ZopMessageConst.VerifyCode.LOGIN_CODE.getType() == opType){
            model.setBusinessId(ZopMessageConst.VerifyCode.LOGIN_CODE.getBsid());
        }
        if(ZopMessageConst.VerifyCode.REGIST_CODE.getType() == opType){
            model.setBusinessId(ZopMessageConst.VerifyCode.REGIST_CODE.getBsid());
        }
        if(ZopMessageConst.VerifyCode.RESET_PASSWD_CODE.getType() == opType){
            model.setBusinessId(ZopMessageConst.VerifyCode.RESET_PASSWD_CODE.getBsid());
        }
        if(StringUtils.isBlank(model.getBusinessId()))return false;
        model.setLatnId(landId);
        model.setToTel(phoneNo);
        model.setSentContent("");
        return zopMsgUtil.SendMsg(model,template);
    }

    private void insertVerifyCode(VerifyCodeGetReq getReq,String code) {
        RandomLogAddReq addReq = new RandomLogAddReq();
        addReq.setBusiType(getReq.getOperatType());
        addReq.setRandomCode(code);
        addReq.setValidStatus(0);
        addReq.setSendType(ZopMessageConst.SendMsgType.SEND_MESSAGE.getType());
        addReq.setReceviNo(getReq.getPhoneNo());
        addReq.setValidStatus(ZopMessageConst.UNVERIFIED);
        addReq.setCreateDate(new Date());
        Calendar calendar = Calendar.getInstance();
        Date createDate = calendar.getTime();
        addReq.setCreateDate(createDate);
        addReq.setSendTime(createDate);
        calendar.add(Calendar.SECOND,90);
        Date effDate = calendar.getTime();
        addReq.setEffDate(effDate);
        randomLogService.insertSelective(addReq);
    }

    //生成验证码
    private String generateCode() {
        return  String.valueOf(new Random().nextInt(899999) + 100000);
    }

}
