package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.config.TelDBDefValueConfig;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.entity.ZFlow;
import com.iwhalecloud.retail.order2b.mapper.OrderZFlowMapper;
import com.iwhalecloud.retail.order2b.service.OrderPayOpenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PayServiceTest extends TestBase {

    @Autowired
    private OrderPayOpenService orderPayOpenService;

    @Test
    public void pay() {
        String params = "{\"flowType\":\"C\",\"orderId\":\"20190227154236494435333\",\"payRemark\":\"123\",\"payType\":\"3\",\"paymoney\":999,\"refundImgUrl\":\"group1/M00/00/1D/Ci0vWVx4neSASLTcAAE9O8cwJNQ075.jpg\",\"sourceFrom\":\"YHJ\",\"userId\":\"1077840960118870018\"}";

        PayOrderRequest request= JSON.parseObject(params,PayOrderRequest.class);
        orderPayOpenService.pay(request);
    }

    @Autowired
    private OrderZFlowMapper orderZFlowMapper;

    @Autowired
    private TelDBDefValueConfig telDBDefValueConfig;

    @Test
    public void testFlow(){
        String params="[{\"flowType\":\"B\",\"isExe\":\"1\",\"sort\":1},{\"flowType\":\"C\",\"isExe\":\"0\",\"sort\":3},{\"flowType\":\"H\",\"isExe\":\"0\",\"sort\":6},{\"flowType\":\"J\",\"isExe\":\"0\",\"sort\":7},{\"flowType\":\"PJ\",\"isExe\":\"0\",\"sort\":8}]";
        List<ZFlow> list = JSON.parseArray(params, ZFlow.class);
        for (ZFlow item : list) {
            item.setSourceFrom("CS");
            item.setOrderId("1");
            item.setHandlerId("1");
        }
        telDBDefValueConfig.setTableDefTime(list);
        orderZFlowMapper.insertFlowList(list);
    }
}
