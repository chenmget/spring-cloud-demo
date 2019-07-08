package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelBatchAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelDetailListReq;
import com.iwhalecloud.retail.goods2b.manager.TagRelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagRelService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Z
 * @date 2018/11/28
 */

@SpringBootTest(classes = Goods2BServiceApplication.class)
@RunWith(SpringRunner.class)
public class TagRelServiceTest {

    @Resource
    private TagRelService tagRelService;

    @Autowired
    private TagRelManager tagRelManager;

    @Test
    public void batchAddTagRelProductId() {
        TagRelBatchAddReq req = new TagRelBatchAddReq();
        req.setProductId("201903142030001");
        req.setTagList(Lists.newArrayList("123"));
        ResultVO<Boolean> resultVO = tagRelService.batchAddTagRelProductId(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
        Assert.assertNotNull(resultVO);
    }

    @Test
    public void listTagRelDetail( ) {
        TagRelDetailListReq req = new TagRelDetailListReq();
        req.setProductId("11010389");
        List resultVO = tagRelManager.listTagRelDetail(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
    }

}
