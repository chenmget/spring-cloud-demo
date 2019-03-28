package com.iwhalecloud.retail.goods2b.service.impl;

import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.ComplexInfoDTO;
import com.iwhalecloud.retail.goods2b.dto.InfoDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ComplexInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class ComplexInfoServiceImplTest {

    @Autowired
    private ComplexInfoService complexInfoService;

    @Test
    public void testAdd(){
        ComplexInfoDTO complexInfoDTO = new ComplexInfoDTO();
        complexInfoDTO.setComplexInfo("111");
        complexInfoDTO.setAGoodsId("123");
        complexInfoDTO.setZGoodsId("234");
        complexInfoService.insertComplexInfo(complexInfoDTO);
    }

    @Test
    public void testBatchAdd(){
        ComplexInfoReq req = new ComplexInfoReq();
        List<InfoDTO> infoDTOList = new ArrayList<>();
        InfoDTO infoDTO = new InfoDTO();
        infoDTO.setZGoodsId("222");
        infoDTO.setComplexInfo("222ceshi");
        InfoDTO infoDTO2 = new InfoDTO();
        infoDTO2.setZGoodsId("333");
        infoDTO2.setComplexInfo("333ceshi");
        infoDTOList.add(infoDTO);
        infoDTOList.add(infoDTO2);
        req.setAGoodsId("111");
        req.setInfoDTOList(infoDTOList);
        complexInfoService.batchAddComplexInfo(req);
    }

    @Test
    public void updateTest(){
        ComplexInfoDTO complexInfoDTO = new ComplexInfoDTO();
        complexInfoDTO.setComplexInfoId("1101019789253713921");
        complexInfoDTO.setComplexInfo("ceshi_update");
        complexInfoService.updateComplexInfo(complexInfoDTO);
    }

    @Test
    public void deleteOne(){
        String complex_info_id = "1101019789253713921";
        ComplexInfoDTO complexInfoDTO = new ComplexInfoDTO();
        complexInfoDTO.setComplexInfo(complex_info_id);
        complexInfoService.deleteOneComplexInfo(complexInfoDTO);
    }

    @Test
    public void deleteGoodsisTest(){
        String goodsId = "111";
        ComplexInfoDTO complexInfoDTO = new ComplexInfoDTO();
        complexInfoDTO.setAGoodsId(goodsId);
        complexInfoService.deleteComplexInfoByGoodsId(complexInfoDTO);
    }

    @Test
    public void qryTestByGoodsId(){
        List<String> goodsIdList = new ArrayList<>();
        goodsIdList.add("111");
        goodsIdList.add("123");
        ComplexInfoQueryReq complexInfoQueryReq = new ComplexInfoQueryReq();
        complexInfoQueryReq.setGoodsIdList(goodsIdList);
        List<ComplexInfoDTO> complexInfoDTOList = complexInfoService.queryComplexInfo(complexInfoQueryReq).getResultData();
    }

}
