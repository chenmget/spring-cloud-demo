package com.iwhalecloud.retail.goods.service.impl;

import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.RightDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodsRightAddReq;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsRightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = GoodsServiceApplication.class)
@RunWith(SpringRunner.class)
public class GoodsRightTest {
    @Autowired
    private GoodsRightService goodsRightService;

    @Test
    public void addGoodsRight(){
        GoodsRightAddReq req = new GoodsRightAddReq();
        req.setGoodsId("181110147400574472");
        RightDTO rightDTO = new RightDTO();
        rightDTO.setRightsId(2002L);
        rightDTO.setRightsName("满400减50");
        RightDTO rightDTO1 = new RightDTO();
        rightDTO1.setRightsId(2001L);
        rightDTO1.setRightsName("50元终端通用券");
        List<RightDTO> list = new ArrayList<>();
        list.add(rightDTO);
        list.add(rightDTO1);
        req.setRightsList(list);
        int num = goodsRightService.insertGoodsRight(req);
        System.out.print("结果行数：" + num);
    }

    @Test
    public void updateGoodsRight(){
        GoodsRightAddReq req = new GoodsRightAddReq();
        req.setGoodsId("181110147400574472");
        RightDTO rightDTO = new RightDTO();
        rightDTO.setRightsId(2002L);
        rightDTO.setRightsName("满400减50");
//        RightDTO rightDTO1 = new RightDTO();
//        rightDTO1.setRightsId(2001L);
//        rightDTO1.setRightsName("50元终端通用券");
        List<RightDTO> list = new ArrayList<>();
        list.add(rightDTO);
//        list.add(rightDTO1);
        req.setRightsList(list);
        int num = goodsRightService.updateGoodsRight(req);
        System.out.print("结果行数：" + num);
    }

    @Test
    public void deleteGoodsRight(){
        int num = goodsRightService.deleteGoodsRight("181110147400574472");
        System.out.print("结果行数：" + num);
    }

    @Test
    public void listByGoodsId(){
        List list = goodsRightService.listByGoodsId("181110147400574472");
        System.out.print("结果行数：" + list.toString());
    }
}
