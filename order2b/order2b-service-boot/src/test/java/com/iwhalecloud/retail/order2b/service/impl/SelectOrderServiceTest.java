package com.iwhalecloud.retail.order2b.service.impl;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectOrderReq;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import com.iwhalecloud.retail.order2b.service.SelectAfterSaleOpenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SelectOrderServiceTest extends TestBase {

    @Autowired
    private OrderSelectOpenService orderSelectService;

    @Test
    public void selectOrderTest(){
        AdvanceOrderReq selectOrderReq=new AdvanceOrderReq();
//        selectOrderReq.setOrderId("20190116173541220867919");
//        selectOrderReq.setStatus("2,4");
//        selectOrderReq.setUserId("11");
        selectOrderReq.setUserCode("4301811022885");
        selectOrderReq.setUserId("1077839559879852033");
        orderSelectService.queryAdvanceOrderList(selectOrderReq);
    }

    @Test
    public void selectOrderItemDetailTest(){
        SelectOrderReq selectOrderReq=new SelectOrderReq();
        selectOrderReq.setOrderId("20190104104733495176239");
//        selectOrderReq.setStatus("2,4");
        orderSelectService.selectOrderItemDetail(selectOrderReq);
    }

    @Test
    public void selectDetail(){
        SelectOrderReq req=new SelectOrderReq();
//        req.setUserCode("4301211021582");
//        req.setOrderId("20190116153838472221832");
//        req.setUserId("201406175741000822");
//        req.setOrderId("20190223105509588238282");
        SourceFromContext.setSourceFrom("YHJ");
        OrderRequest orderRequest=new OrderRequest();
        orderRequest.setLanId("731");
        Order2bContext.setDBLanId(orderRequest);
        orderSelectService.managerOrderList(req);
    }

    @Autowired
    private SelectAfterSaleOpenService selectAfterSaleOpenService;

    @Test
    public void selectSale(){
        SelectAfterSalesReq selectAfterModel=new SelectAfterSalesReq();
        selectAfterModel.setOrderApplyId("20190213103953855132393");
//        selectAfterModel.setUserCode("10010");
//        selectAfterModel.setServiceType("2,4");
       ResultVO iPage= selectAfterSaleOpenService.detail(selectAfterModel);
       System.out.println(iPage);
    }

    @Test
    public void exportAdvance(){
        AdvanceOrderReq req=new AdvanceOrderReq();
        req.setUserExportType("3");
        req.setOrderCatList(Lists.newArrayList("0"));
        req.setUserCode("4301811022885");
        req.setUserId("1077839559879852033");
        req.setSourceFrom("YHJ");
        SourceFromContext.setSourceFrom("YHJ");
        orderSelectService.orderExport(req);
    }



}
