package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.busiservice.BPEPPayLogService;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.manager.PromotionManager;
import com.iwhalecloud.retail.order2b.model.SaveLogModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PayLogServiceImplTest extends TestBase {

    @Autowired
    private BPEPPayLogService bpepPayLogService;

    @Test
    public void saveLog() {
        String params = "{\"operationType\":\"DJZF\",\"orderAmount\":\"1.0\",\"orderId\":\"20190309145723860961400\",\"payId\":\"1104928136771629058\",\"payStatus\":\"0\",\"requestType\":\"1004\"} ";
        SaveLogModel saveLogModel = JSON.parseObject(params,SaveLogModel.class);
        bpepPayLogService.saveLog(saveLogModel);
    }

    @Autowired
    private PromotionManager promotionManager;
    @Test
    public void tss(){
        List<Promotion> list =new ArrayList<>();
        list.add(new Promotion());
        list.add(new Promotion());
        list.add(new Promotion());
        list.add(new Promotion());
        promotionManager.insertPromotionList(list);
    }
}