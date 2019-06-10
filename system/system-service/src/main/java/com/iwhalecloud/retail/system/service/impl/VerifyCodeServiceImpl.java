package com.iwhalecloud.retail.system.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SysUserMessageConst;
import com.iwhalecloud.retail.system.dto.request.RandomLogAddReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogGetReq;
import com.iwhalecloud.retail.system.dto.request.VerifyCodeGetReq;
import com.iwhalecloud.retail.system.dto.response.RandomLogGetResp;

import com.iwhalecloud.retail.system.model.SmsVerificationtemplate;
import com.iwhalecloud.retail.system.model.ZopMsgModel;
import com.iwhalecloud.retail.system.service.RandomLogService;
import com.iwhalecloud.retail.system.service.VerifyCodeService;
import com.iwhalecloud.retail.system.utils.ZopMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

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
            sendSmsVerificationCode(getReq.getPhoneNo(),getReq.getLandId(),verifyCode);
            //插入数据库
            insertVerifyCode(getReq,verifyCode);
        }catch (Exception e){
            return ResultVO.error();
        }
        return ResultVO.success();
    }

    private void checkReq(VerifyCodeGetReq getReq) {
        if(null == getReq.getPhoneNo() || getReq.getPhoneNo().equals("")){
            throw new RuntimeException("phoneNo must not null");
        }
        if(null == getReq.getLandId() || getReq.getLandId().equals("")){
            throw new RuntimeException("lanId must not null");
        }
    }


    public ResultVO checkVerifyCode(String phoneNo,String verifyCode){
        RandomLogGetReq req = new RandomLogGetReq();
        req.setReceviNo(phoneNo);
        ResultVO<RandomLogGetResp> resultVO = randomLogService.selectLogIdByRandomCode(req);
        RandomLogGetResp resp = resultVO.getResultData();
        if(verifyCode.equals(resp.getRandomCode())){
            return ResultVO.success();
        }
        return  ResultVO.error() ;
    }

    @Override
    public ResultVO noticeMsg() {
        return ResultVO.success();
    }


    private boolean sendSmsVerificationCode(String phoneNo,String landId,String verifyCode) {
        ZopMsgModel model = new ZopMsgModel();
        SmsVerificationtemplate template = new SmsVerificationtemplate();
        template.setSmsVerificationCode(verifyCode);
        model.setBusinessId(SysUserMessageConst.SMS_VERIFY_BSID);
        model.setLatnId(landId);
        model.setToTel(phoneNo);
        model.setSentContent("");
        return zopMsgUtil.SendMsg(model,template);
    }

    private void insertVerifyCode(VerifyCodeGetReq getReq,String code) {
        RandomLogAddReq addReq = new RandomLogAddReq();
        addReq.setBusiType(getReq.getOperatType());
        addReq.setRandomCode(code);
        addReq.setSendType(SysUserMessageConst.SendMsgType.SEND_MESSAGE.getType());
        addReq.setReceviNo(getReq.getPhoneNo());
        addReq.setValidStatus(SysUserMessageConst.UNVERIFIED);
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
