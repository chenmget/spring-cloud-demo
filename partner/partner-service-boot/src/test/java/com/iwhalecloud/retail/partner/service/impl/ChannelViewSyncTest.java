package com.iwhalecloud.retail.partner.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.service.ChannelViewSyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class ChannelViewSyncTest {

    @Autowired
    private ChannelViewSyncService channelViewSyncService;

    @Test
    public void sync_business_entity_test() {
        ResultVO result = channelViewSyncService.syncBusinessEntity();
        System.out.print("结果：" + result.toString());
    }

    @Test
    public void sync_merchant_test() {
        ResultVO result = channelViewSyncService.syncMerchant();
        System.out.print("结果：" + result.toString());
    }

    @Test
    public void deal_business_entity_data_test() {
        channelViewSyncService.dealBusinessEntityData();
    }

    @Test
    public void deal_merchant_data_test() {
        channelViewSyncService.dealMerchantData();
    }

    @Test
    public void delete_business_entity_data_test() {
        channelViewSyncService.deleteBusinessEntityTempData();
    }

    @Test
    public void delete_merchant_data_test() {
        channelViewSyncService.deleteMerchantTempData();
    }

}
