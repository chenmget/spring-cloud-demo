package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.TypeSelectByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeDetailResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.TypeService;
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

@SpringBootTest(classes = Goods2BServiceApplication.class)
@RunWith(SpringRunner.class)
public class TypeServiceTest {

    @Resource
    private TypeService typeService;

    @Test
    public void getProductTest() {
        TypeSelectByIdReq req = new TypeSelectByIdReq();
        req.setTypeId("201903142030001");
        ResultVO<TypeDetailResp> resultVO = typeService.getDetailType(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
        Assert.assertNotNull(resultVO);
    }

}
