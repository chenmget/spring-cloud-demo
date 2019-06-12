package com.iwhalecloud.retail.system.service.impl;

import com.iwhalecloud.retail.system.SystemServiceApplication;
import com.iwhalecloud.retail.system.dto.request.SmsVerificationtemplate;
import com.iwhalecloud.retail.system.dto.request.VerifyCodeGetReq;
import com.iwhalecloud.retail.system.model.ZopMsgModel;
import com.iwhalecloud.retail.system.service.ZopMessageService;
import com.iwhalecloud.retail.system.utils.ZopMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class SendMsgServiceImplTest {

    @Autowired
    ZopMsgUtil util;

    @Autowired
    ZopMessageService zopMessageService;

    @Test
    public void testSendMsg(){
        try {
            ZopMsgModel info1 = new ZopMsgModel();
            SmsVerificationtemplate template = new SmsVerificationtemplate();
            template.setSmsVerificationCode("3351");
            info1.setBusinessId("2691");
            info1.setLatnId("731");
            info1.setToTel("15367976152");
            info1.setSentContent("");
            util.SendMsg(info1,template);
            log.info("====");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMsgs(){
        ZopMsgModel info1 = new ZopMsgModel();
        ZopMsgModel info2 = new ZopMsgModel();
        info1.setBusinessId("2691");
        info1.setLatnId("731");
        info1.setToTel("15367976152");

        info2.setBusinessId("2691");
        info2.setLatnId("731");
        info2.setToTel("15367976152");
        SmsVerificationtemplate template1 = new SmsVerificationtemplate();
        SmsVerificationtemplate template2 = new SmsVerificationtemplate();
        template1.setSmsVerificationCode("3351");
        template2.setSmsVerificationCode("3351");
        List modeList = new ArrayList();
        List temList = new ArrayList();
        modeList.add(info1);
        modeList.add(info2);
        temList.add(template1);
        temList.add(template2);
        util.SendMsgs(modeList,temList);
    }

    @Test
    public void yz(){
        zopMessageService.checkVerifyCode("15367976152","997668");
    }

    @Test
    public void get(){
        VerifyCodeGetReq req = new VerifyCodeGetReq();
        req.setOperatType(1);
        req.setLandId("701");
        req.setPhoneNo("15367976152");
        zopMessageService.sendRegistVerifyCode(req);
        //997668
    }

}
