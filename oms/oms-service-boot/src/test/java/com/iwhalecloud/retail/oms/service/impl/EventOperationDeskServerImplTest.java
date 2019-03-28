package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.OmsServiceApplication;
import com.iwhalecloud.retail.oms.consts.GoodsCountRankConstants;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.response.content.ContentIdLIstResp;
import com.iwhalecloud.retail.oms.service.EventOperationDeskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = OmsServiceApplication.class)
@RunWith(SpringRunner.class)
public class EventOperationDeskServerImplTest {
    @Autowired
    private EventOperationDeskService eventOperationDeskService;



    @Test
    public void getTodayEventCountByPartnerId() {

        int result = eventOperationDeskService.getTodayEventCountByPartnerId("", GoodsCountRankConstants.GOODS_CART_EVENT);
        System.out.println("结果：" + result);
    }
}

