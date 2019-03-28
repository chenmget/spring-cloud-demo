package com.iwhalecloud.retail.oms.service.impl;


import com.iwhalecloud.retail.oms.OmsServiceApplication;
import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;
import com.iwhalecloud.retail.oms.service.GoodsEvaluateTotalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsServiceApplication.class)
public class GoodsEvluateServiceImplTest {

    @Autowired
    private GoodsEvaluateTotalService goodsEvaluateTotalService;

    @Test
    public void testAdd() {
        TGoodsEvaluateTotalDTO tGoodsEvaluateTotalDTO = new TGoodsEvaluateTotalDTO();
        tGoodsEvaluateTotalDTO.setGoodsId("201810101015");
        tGoodsEvaluateTotalDTO.setEvaluateText("性价比高呀");
        goodsEvaluateTotalService.addGoodsEvaluate(tGoodsEvaluateTotalDTO);
    }


    @Test
    public void testUpdate() {
        TGoodsEvaluateTotalDTO tGoodsEvaluateTotalDTO = new TGoodsEvaluateTotalDTO();
        tGoodsEvaluateTotalDTO.setEvaluateText("质量好");
        tGoodsEvaluateTotalDTO.setGoodsId("201810101015");
        goodsEvaluateTotalService.modifyGoodsEvaluate(tGoodsEvaluateTotalDTO);
    }

    @Test
    public void testSelect(){
        TGoodsEvaluateTotalDTO tGoodsEvaluateTotalDTO = new TGoodsEvaluateTotalDTO();
        tGoodsEvaluateTotalDTO.setGoodsId("201810101015");
        goodsEvaluateTotalService.selectGoodsEvaluate(tGoodsEvaluateTotalDTO);
    }

}
