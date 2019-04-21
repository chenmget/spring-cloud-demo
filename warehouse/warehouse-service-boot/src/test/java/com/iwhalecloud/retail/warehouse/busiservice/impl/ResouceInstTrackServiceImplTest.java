package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.dto.request.DeliveryResourceInstItem;
import com.iwhalecloud.retail.warehouse.dto.request.DeliveryResourceInstReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResouceInstTrackServiceImplTest {

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Test
    public void qryOrderIdByNbr() {
        String nbr = "201911223555601";
        String orderId = resouceInstTrackService.qryOrderIdByNbr(nbr);
        log.info("ResouceInstTrackServiceImplTest.qryOrderIdByNbr req={} resp={}", nbr, orderId);
    }

    @Test
    public void asynAcceptTrackForSupplier() {
        DeliveryResourceInstReq req = new DeliveryResourceInstReq();
        req.setBuyerMerchantId("4300001063072");
        DeliveryResourceInstItem item = new DeliveryResourceInstItem();
        item.setMktResInstNbrs(Lists.newArrayList("12345671234564"));
        item.setOrderItemId("201904195410000462");
        item.setProductId("10512210");
        List<DeliveryResourceInstItem> deliveryResourceInstItems = Lists.newArrayList(item);
        req.setDeliveryResourceInstItemList(deliveryResourceInstItems);
        req.setOrderId("201904195410861934");
        req.setSellerMerchantId("4301811025392");
        ResultVO resp1 = ResultVO.success();
        resouceInstTrackService.asynAcceptTrackForSupplier(req, resp1);
    }
}