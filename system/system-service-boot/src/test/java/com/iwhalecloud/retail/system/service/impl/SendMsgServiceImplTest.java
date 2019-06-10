package com.iwhalecloud.retail.system.service.impl;

import com.iwhalecloud.retail.system.SystemServiceApplication;
import com.iwhalecloud.retail.system.model.SmsVerificationtemplate;
import com.iwhalecloud.retail.system.model.ZopMsgModel;
import com.iwhalecloud.retail.system.utils.ZopMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class SendMsgServiceImplTest {

    @Autowired
    ZopMsgUtil util;

    @Test
    public void test(){
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


}
