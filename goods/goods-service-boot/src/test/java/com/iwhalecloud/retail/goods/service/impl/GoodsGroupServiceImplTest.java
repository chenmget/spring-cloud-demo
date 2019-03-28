package com.iwhalecloud.retail.goods.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupAddReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupUpdateReq;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsGroupRelService;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsGroupService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsServiceApplication.class)
public class GoodsGroupServiceImplTest {
    @Autowired
    private GoodsGroupService goodsGroupService;
    @Autowired
    private GoodsGroupRelService goodsGroupRelService;

    @Test
    public void addGoodsGroup(){
        GoodsGroupAddReq req = new GoodsGroupAddReq();
        List<String> strList = Lists.newArrayList();
        req.setGroupName("智慧套餐3333");
        req.setGroupDesc("测试");
        req.setGroupCode("MM");
        req.setSourceFrom("qqq");
        req.setGoodsIds(strList);
        int num = goodsGroupService.insertGoodsGroup(req);
        Assert.assertTrue(num>0);
    }

    @Test
    public void deleteGoodsGroup(){
        String groupId = "1060368775300636673";
        int num = goodsGroupService.deleteGoodsGroup(groupId);
        Assert.assertTrue(num>0);
    }

    @Test
    public void updateGoodsGroup(){
        GoodsGroupUpdateReq req = new GoodsGroupUpdateReq();
        List<String> strList = Lists.newArrayList("234","235");
        req.setGroupName("IG-win");
        req.setGroupDesc("测试一下");
        req.setGroupCode("MM");
        req.setSourceFrom("thanks");
        req.setGoodsIds(strList);
        req.setGroupId("1060716875823185922");
        int num = goodsGroupService.updateGoodsGroup(req);
        Assert.assertTrue(num>0);
    }
    @Test
    public void listGoodsGroupByGroupId(){
        String groupId = "1060722298643038209";
        GoodsGroupDTO goodsGroupDTO =goodsGroupService.listGoodsGroupByGroupId(groupId);
        System.out.println(goodsGroupDTO.hashCode());
        Assert.assertTrue(goodsGroupDTO.getGroupId().isEmpty());
    }
    @Test
    public void listGoodsGroup(){
        GoodGroupQueryReq req = new GoodGroupQueryReq();
        Page<GoodsGroupDTO> page = goodsGroupService.listGoodsGroup(req);
        req.setGroupName("张三");
    }
}
