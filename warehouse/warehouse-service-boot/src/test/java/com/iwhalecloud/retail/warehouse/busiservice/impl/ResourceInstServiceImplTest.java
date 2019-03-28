package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.PageProductReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResourceInstServiceImplTest {

    @Autowired
    private ResourceInstService resourceInstService;

    /**
     * 间接验证constant常量值
     */
    @Test
    public void selectProduct() {
        PageProductReq req = new PageProductReq();
        req.setSourceType(ResourceConst.SOURCE_TYPE.RETAILER.getCode());
        ResultVO resp = resourceInstService.selectProduct(req);
        log.info("ResourceInstServiceImplTest.selectProduct req={} resp={}", JSON.toJSONString(req), JSON.toJSONString(resp));
    }
}