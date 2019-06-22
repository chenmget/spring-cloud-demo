package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseAddReq;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseUpReq;
import com.iwhalecloud.retail.promo.dto.req.HistoryPurchaseQueryExistReq;
import com.iwhalecloud.retail.promo.service.HistoryPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class HistoryPurchaseServiceTest {

    @Reference
    private HistoryPurchaseService historyPurchaseService;

    @Test
    public void queryHistoryPurchaseQueryIsExist() {
        HistoryPurchaseQueryExistReq req = new HistoryPurchaseQueryExistReq();
        req.setOrderId("12345678");
        req.setProductId("23784632843");
        ResultVO respResultVO = historyPurchaseService.queryHistoryPurchaseQueryIsExist(req);
        log.info(JSON.toJSONString(respResultVO.getResultData()));
    }
    @Test
    public void add() {
        List<ActHistoryPurChaseAddReq> list =new ArrayList<>();
        ActHistoryPurChaseAddReq req = new ActHistoryPurChaseAddReq();
        req.setMarketingActivityId("201903151703");
        req.setMerchantCode("201903151703111");
        req.setOrderId("201903151703111");
        req.setProductId("23784632843");
        req.setCreator("1");
        req.setActivityType("12312");
        req.setSupplierCode("123123123122");
        list.add(req);
        ActHistoryPurChaseAddReq req2 = new ActHistoryPurChaseAddReq();
        req2.setMarketingActivityId("201903151705");
        req2.setMerchantCode("222222");
        req2.setOrderId("1234567834234");
        req2.setProductId("2378463284333");
        req2.setCreator("1");
        req2.setActivityType("12312");
        req2.setSupplierCode("1231231231223333");
        list.add(req2);
        ResultVO respResultVO = historyPurchaseService.addActHistroyPurchase(list);
        log.info(JSON.toJSONString(respResultVO.getResultData()));
    }
    @Test
    public void update(){
        String orderId= "1234567834234";
        ActHistoryPurChaseUpReq actHistroyPurChaseUpReq = new ActHistoryPurChaseUpReq();
        actHistroyPurChaseUpReq.setOrderId(orderId);
        historyPurchaseService.updateHistroyPurchase(actHistroyPurChaseUpReq);
    }
}
