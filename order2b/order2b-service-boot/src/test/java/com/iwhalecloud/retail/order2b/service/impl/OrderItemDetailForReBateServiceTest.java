package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.response.ReBateOrderInDetailResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.ReBateOrderInDetailReq;
import com.iwhalecloud.retail.order2b.service.OrderItemDetailForReBateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author lhr 2019-04-02 10:08:30
 */
@Slf4j
public class OrderItemDetailForReBateServiceTest extends TestBase {

    @Reference
    private OrderItemDetailForReBateService orderItemDetailForReBateService;

    @Test
    public void orderItemDetailForReBateServiceTest(){
        ReBateOrderInDetailReq reBateOrderInDetailReq = new ReBateOrderInDetailReq();
        reBateOrderInDetailReq.setOrderId("201903223810003591");
        reBateOrderInDetailReq.setItemId("201903223810003592");
        ResultVO<Page<ReBateOrderInDetailResp>> resultVO= orderItemDetailForReBateService.queryOrderItemDetailDtoByOrderId(reBateOrderInDetailReq);
        log.info("OrderItemDetailForReBateServiceTest.orderItemDetailForReBateServiceTest resultVO{}",resultVO);
    }

}
