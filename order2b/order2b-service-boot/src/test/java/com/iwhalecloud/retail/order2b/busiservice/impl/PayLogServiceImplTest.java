package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.order2b.Order2BServiceApplication;
import com.iwhalecloud.retail.order2b.busiservice.BPEPPayLogService;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.AsynNotifyReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = Order2BServiceApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class PayLogServiceImplTest {

    @Autowired
    private BPEPPayLogService bpepPayLogService;

    @Test
    public void checkNotifyData() {
        String json = "{\"BANKCODE\":\"866900\",\"COMMENT1\":\"\",\"COMMENT2\":\"\",\"CUSTCODE\":\"\",\"FEE\":\"0.00\",\"IOUSAMOUNT\":\"0.00\",\"ORDERAMOUNT\":\"0.01\",\"ORDERID\":\"EXT20190619487475\",\"ORDERSTATUS\":\"1\",\"ORDERTYPE\":\"3\",\"ORGLOGINCODE\":\"13308418900\",\"PERENTFLAG\":\"1\",\"PLATCODE\":\"8614050900698985\",\"SIGNSTR\":\"brvni8vLgF7iTASKnDP8OYjJrnPA96K8AeS/L8/bFXCIXYrMnWBpsxE5th1xFeUEX5KIAHIM6UrZW6I+GPGUGcOLAeUlEFG2qLrFg6UgFscwX0NUbeF/QyL/4RBMJNXDRCmhqCteqHsCZcYVYUIbOK8h5e4bzfIpVmAavoywxr4=\",\"TRANSDATE\":\"2019-06-19 11:31:03\",\"TRSSEQ\":\"190619101991414266\"}";
        AsynNotifyReq req = JSONObject.parseObject(json, AsynNotifyReq.class);
        boolean b = bpepPayLogService.checkNotifyData(req);
        log.info("b:{}", b);
    }
}