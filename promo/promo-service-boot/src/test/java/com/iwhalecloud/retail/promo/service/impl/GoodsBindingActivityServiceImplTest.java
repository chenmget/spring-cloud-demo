package com.iwhalecloud.retail.promo.service.impl;

import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.service.GoodsBindingActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author: wang.jiaxin
 * @date: 2019年03月26日
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class GoodsBindingActivityServiceImplTest {

    @Autowired
    private GoodsBindingActivityService goodsBindingActivityService;

    @Test
    public void goodsBingActivity() {
        goodsBindingActivityService.goodsBingActivity();
    }

    @Test
    public void goodsUnBundlingActivity() {
        goodsBindingActivityService.goodsUnBundlingActivity();
    }

    @Test
    public void goodsBingActivityTest() {
        goodsBindingActivityService.goodsBingActivity();
        goodsBindingActivityService.goodsUnBundlingActivity();
    }
}