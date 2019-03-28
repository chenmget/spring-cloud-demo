package com.iwhalecloud.retail.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.service.dubbo.ProdProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Z
 * @date 2018/11/28
 */

@SpringBootTest(classes = GoodsServiceApplication.class)
@RunWith(SpringRunner.class)
public class ProdProductServiceTest {

    @Resource
    private ProdProductService prodProductService;

    @Test
    public void getProductTest() {
        ProdProductDTO productDTO = prodProductService.getProduct("1067695247957291009");

        System.out.println("productDto=" + JSON.toJSON(productDTO));
        Assert.assertNotNull(productDTO);
    }
}
