package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OffLinePayReq;
import com.iwhalecloud.retail.order2b.entity.OrderFlowInit;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.model.SelectAfterModel;
import com.iwhalecloud.retail.order2b.service.BestPayEnterprisePaymentService;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import com.iwhalecloud.retail.order2b.service.SelectAfterSaleOpenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.transform.Result;
import java.util.List;

public class TestOrderSelectService extends TestBase {

    @Autowired
    private OrderSelectOpenService orderSelectOpenService;

    @Autowired
    private SelectAfterSaleOpenService selectAfterSaleOpenService;

    @Test
    public void tttt(){
        SelectOrderReq req= JSON.parseObject("{\"pageNo\":1,\"pageSize\":5,\"paymentType\":\"1\"}",SelectOrderReq.class);
        orderSelectOpenService.managerOrderList(req);
    }

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Test
    public void submitAll(){
        System.out.println(afterSaleManager.selectAllSubmit("12"));
    }

    @Test
    public void tes(){

        SelectAfterModel req=new SelectAfterModel();
        req.setOrderId("20190227170308738844180");
        req.setSourceFrom("YHJ");
        req.setUserExportType("3");
        System.out.println(JSON.toJSONString(req));
        ResultVO result=selectAfterSaleOpenService.orderApplyExport(req);
        System.out.println(JSON.toJSONString(result));

    }


    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Test
    public void orderInit(){
        OrderFlowInit orderFlowInit=new OrderFlowInit();
        orderFlowInit.setBindType("1");
        orderFlowInit.setTypeCode("2");
        orderFlowInit.setPayType("1");
        orderFlowInit.setOrderType("1");
        orderFlowInit.setServiceType("1");
       List list= orderZFlowManager.selectFlowInit(orderFlowInit);
       System.out.println(JSON.toJSONString(list));
    }

}
