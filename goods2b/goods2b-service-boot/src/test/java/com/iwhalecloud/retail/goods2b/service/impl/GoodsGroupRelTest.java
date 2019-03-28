package com.iwhalecloud.retail.goods2b.service.impl;

import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.GoodsGroupRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupRelAddReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsGroupRelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2019/2/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class GoodsGroupRelTest {

    @Autowired
    private GoodsGroupRelService goodsGroupRelService;

    @Test
    public void addTest(){
        GoodsGroupRelAddReq goodsGroupRelAddReq = new GoodsGroupRelAddReq();
        goodsGroupRelAddReq.setGoodsId("112345");
        goodsGroupRelAddReq.setGroupId("123456");
        goodsGroupRelService.insertGoodsGroupRel(goodsGroupRelAddReq);
    }

    @Test
    public void update(){
        GoodsGroupRelDTO goodsGroupRelAddReq = new GoodsGroupRelDTO();
        goodsGroupRelAddReq.setGroupRelId("1101008270126284802");
        goodsGroupRelAddReq.setGoodsId("112345777");
        goodsGroupRelAddReq.setGroupId("123456777");
        goodsGroupRelService.updateGoodsGroupRel(goodsGroupRelAddReq);
    }

    @Test
    public void deleteByGoodsId(){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        String goods_id="112345777";
        goodsGroupRelDTO.setGoodsId(goods_id);
        goodsGroupRelService.deleteGoodsGroupRel(goodsGroupRelDTO);
    }

    @Test
    public void qryByGoodsId(){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        String goods_id= "112345777";
        goodsGroupRelDTO.setGoodsId(goods_id);
        List<GoodsGroupRelDTO> goodsGroupRelDTOs= goodsGroupRelService.queryGoodsGroupRelByGoodsId(goodsGroupRelDTO);
        System.out.println(goodsGroupRelDTOs);
    }

    @Test
    public void qryByGroupId(){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        String groupId= "123456777";
        goodsGroupRelDTO.setGroupId(groupId);
        List<GoodsGroupRelDTO> goodsGroupRelDTOs= goodsGroupRelService.queryGoodsGroupRelByGroupId(goodsGroupRelDTO);
        System.out.println(goodsGroupRelDTOs);
    }

    @Test
    public void deleteByGroupId(){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        String groupId="1059683690553307137";
        goodsGroupRelDTO.setGroupId(groupId);
        goodsGroupRelService.deleteGoodsGroupRelByGroupId(goodsGroupRelDTO);
    }

    @Test
    public void deleteById(){
        GoodsGroupRelDTO goodsGroupRelDTO = new GoodsGroupRelDTO();
        String id="1059684996336680961";
        goodsGroupRelDTO.setGroupRelId(id);
        goodsGroupRelService.deleteOneGoodsGroupRel(goodsGroupRelDTO);
    }
}
