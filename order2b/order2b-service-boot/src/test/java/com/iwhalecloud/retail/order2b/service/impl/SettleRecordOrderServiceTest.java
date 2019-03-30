package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.model.order.SettleRecordOrderDTO;
import com.iwhalecloud.retail.order2b.service.SettleRecordOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */
public class SettleRecordOrderServiceTest extends TestBase {

    @Autowired
    private SettleRecordOrderService settleRecordOrderService;

    @Test
    public void test(){
        List<String> orderIds = new ArrayList<>();
        orderIds.add("201903254010109902");
        orderIds.add("201903256710109930");
        List<SettleRecordOrderDTO> settleRecordOrderDTOs = settleRecordOrderService.getSettleRecordOrder(orderIds);
        System.out.println(settleRecordOrderDTOs);
    }
}
