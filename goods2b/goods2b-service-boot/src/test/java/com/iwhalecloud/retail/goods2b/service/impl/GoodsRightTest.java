package com.iwhalecloud.retail.goods2b.service.impl;

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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = Goods2BServiceApplication.class)
@RunWith(SpringRunner.class)
public class GoodsRightTest {
    @Autowired
    private GoodsRightService goodsRightService;

    @Test
    public void addGoodsRight(){
        GoodsRightAddReq req = new GoodsRightAddReq();
        req.setGoodsId("181110147400574472");
        RightDTO rightDTO = new RightDTO();
        rightDTO.setRightsId("2002");
        rightDTO.setRightsName("满400减50");
        RightDTO rightDTO1 = new RightDTO();
        rightDTO1.setRightsId("2003");
        rightDTO1.setRightsName("50元终端通用券");
        List<RightDTO> list = new ArrayList<>();
        list.add(rightDTO);
        list.add(rightDTO1);
        req.setRightsList(list);
         goodsRightService.insertGoodsRight(req);
//        System.out.print("结果行数：" + num);
    }

    @Test
    public void updateGoodsRight(){
        GoodsRightAddReq req = new GoodsRightAddReq();
        req.setGoodsId("181110147400574472");
        RightDTO rightDTO = new RightDTO();
        rightDTO.setRightsId("2002");
        rightDTO.setRightsName("满400减50");
//        RightDTO rightDTO1 = new RightDTO();
//        rightDTO1.setRightsId(2001L);
//        rightDTO1.setRightsName("50元终端通用券");
        List<RightDTO> list = new ArrayList<>();
        list.add(rightDTO);
//        list.add(rightDTO1);
        req.setRightsList(list);
//          goodsRightService.updateGoodsRight(req);
//        System.out.print("结果行数：" + num);
    }

    @Test
    public void deleteGoodsRight(){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsId("181110147400574472");
          goodsRightService.deleteGoodsRight(goodsRightDTO);
//        System.out.print("结果行数：" + num);
    }

    @Test
    public void listByGoodsId(){
        GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
        goodsRightDTO.setGoodsId("181110147400574472");
        List list = goodsRightService.listByGoodsId(goodsRightDTO);
        System.out.print("结果行数：" + list.toString());
    }
}
