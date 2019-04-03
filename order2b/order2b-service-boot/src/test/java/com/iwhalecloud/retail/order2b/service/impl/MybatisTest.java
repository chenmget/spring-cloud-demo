package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import com.iwhalecloud.retail.order2b.dto.model.order.AdvanceOrderDTO;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.ZFlow;
import com.iwhalecloud.retail.order2b.manager.AdvanceOrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.mapper.OrderItemMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderZFlowMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MybatisTest extends TestBase {

    @Autowired
    private OrderZFlowMapper zFlowMapper;

    @Test
    public void insert(){
        List<ZFlow> list=new ArrayList<>();
        for (int i=0;i<1;i++){
            ZFlow zFlow=new ZFlow();
            zFlow.setFlowId(String.valueOf(System.currentTimeMillis()));
            zFlow.setOrderId(String.valueOf(System.currentTimeMillis()));
            list.add(zFlow);
//            zFlowMapper.insert(zFlow);
        }

        zFlowMapper.insertFlowList(list);
    }
    @Autowired
    private OrderManager orderManager;
    @Test
    public void select(){
        SourceFromContext.setSourceFrom("YHJ");
        orderManager.getOrderById("201903129710000440");
    }

    @Autowired
    private AdvanceOrderManager advanceOrderManager;
    @Test
    public void update(){
        AdvanceOrderDTO advanceOrderDTO=new AdvanceOrderDTO();
        advanceOrderDTO.setRestPayCode("1234567889");
        advanceOrderDTO.setOrderId("201903231910005854");
        OrderRequest orderRequest=new OrderRequest();
        orderRequest.setLanId("731");
        orderRequest.setSourceFrom("YHJ");
        Order2bContext.setDBLanId(orderRequest);
        advanceOrderManager.updateAdvanceOrderAttr(advanceOrderDTO);
    }


    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void selectOrdrByGoodsName(){

        OrderItem orderItem=new OrderItem();
        orderItem.setGoodsName("%yy手机%");
        List or=orderItemMapper.selectOrderItem(orderItem);
        System.out.println(JSON.toJSONString(or));
    }
}
