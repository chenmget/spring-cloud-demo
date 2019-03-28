package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.GoodsRightDTO;
import com.iwhalecloud.retail.goods2b.dto.RightDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsRightAddReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRightService;
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
public class GoodsRightServiceImplTest {
    @Autowired
    private GoodsRightService goodsRightService;

    @Test
    public void testAdd(){
        GoodsRightAddReq req = new GoodsRightAddReq();
        req.setGoodsId("111111");
        List<RightDTO>  rightDTOList = Lists.newArrayList();
        RightDTO rightDTO = new RightDTO();
        rightDTO.setRightsName("测试11");
        rightDTO.setRightsId("1111");
        rightDTOList.add(rightDTO);
        req.setRightsList(rightDTOList);
        goodsRightService.insertGoodsRight(req);
        goodsRightService.batchInsertGoodsRight(req);
    }

    @Test
    public void testUpdate(){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsRightsId("1099945626699100162");
        goodsRightDTO.setGoodsId("123");
        goodsRightDTO.setRightsName("测试13");
        goodsRightDTO.setRightsId("卷1");
        goodsRightDTO.setStatus("1");
        goodsRightService.updateGoodsRight(goodsRightDTO);
    }

    @Test
    public void testDeleteOne(){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsRightsId("123");
        goodsRightService.deleteOneGoodsRight(goodsRightDTO);

    }

    @Test
    public void testDeleteByGoodsId(){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsId("111111");
        goodsRightService.deleteGoodsRight(goodsRightDTO);

    }

    @Test
    public void testListAll(){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsId("1111113");
        List<GoodsRightDTO> goodsRightDTOList = goodsRightService.listByGoodsId(goodsRightDTO);
        System.out.println(goodsRightDTOList);

    }
}
