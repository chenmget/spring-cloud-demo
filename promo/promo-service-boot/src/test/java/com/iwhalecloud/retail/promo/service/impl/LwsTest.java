package com.iwhalecloud.retail.promo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.service.GoodsBindingActivityService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 李伟松
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class LwsTest {
	
	@Reference
	GoodsBindingActivityService goodsBindingActivityService;
	
	@Test
	public void test() {
		goodsBindingActivityService.goodsBingActivity();
	}

}
