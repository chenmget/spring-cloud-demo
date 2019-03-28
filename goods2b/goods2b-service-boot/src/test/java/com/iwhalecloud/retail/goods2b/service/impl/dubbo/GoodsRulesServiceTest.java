package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulePurchaseDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesOperateDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsRuleEditReq;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsRulesExcelResp;
import com.iwhalecloud.retail.goods2b.exception.ProductException;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRulesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z on 2019/1/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes =Goods2BServiceApplication.class)
public class GoodsRulesServiceTest {

    @Resource
    private GoodsRulesService goodsRulesService;

    @Test
    public void checkGoodsProdRule() {
        List<GoodsRulesOperateDTO> conditionList = new ArrayList<GoodsRulesOperateDTO>();

        conditionList.add(getGoodsRulesDTO("1089771072713842689","zhy0128xhbm021",11L,"4300002063684","4"));
        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setGoodsRulesOperateDTOList(conditionList);
        ResultVO<Boolean> rv =  goodsRulesService.checkGoodsProdRule(prodGoodsRuleEditReq);
        System.out.println(JSON.toJSONString(rv));
        Assert.assertTrue(rv.getResultData());
    }

    private GoodsRulesOperateDTO getGoodsRulesDTO(String goodsId,String productId,Long drawNum,String merchantId,String merchantType) {
        GoodsRulesOperateDTO dto = new GoodsRulesOperateDTO();
        dto.setGoodsId(goodsId);
        dto.setProductId(productId);
        dto.setDrawNum(drawNum);
        dto.setMerchantId(merchantId);
        dto.setMerchantType(merchantType);
        return dto;
    }

    @Test
    public void addProdGoodsRuleBatch() throws ProductException {

        List<GoodsRulesDTO> entityList = new ArrayList<>();

        GoodsRulesDTO goodsRulesDTO = new GoodsRulesDTO();
        goodsRulesDTO.setGoodsId("1090492421358944257");
        goodsRulesDTO.setProductCode("T1");
        goodsRulesDTO.setTargetType("2");               //零售商
        goodsRulesDTO.setTargetCode("4300001063072");
        entityList.add(goodsRulesDTO);

        GoodsRulesDTO goodsRulesDTO2 = new GoodsRulesDTO();
        goodsRulesDTO2.setGoodsId("1090492421358944257");
        goodsRulesDTO2.setProductCode("T1");
        goodsRulesDTO2.setTargetType("1");               //经营主体
        goodsRulesDTO2.setTargetCode("C430422012590");
        entityList.add(goodsRulesDTO2);


        GoodsRulesDTO goodsRulesDTO3 = new GoodsRulesDTO();
        goodsRulesDTO3.setGoodsId("1090492421358944257");
        goodsRulesDTO3.setProductCode("11");
        goodsRulesDTO3.setTargetType("2");               //零售商
        goodsRulesDTO3.setTargetCode("4300001063072");
        entityList.add(goodsRulesDTO3);

        GoodsRulesDTO goodsRulesDTO4 = new GoodsRulesDTO();
        goodsRulesDTO4.setGoodsId("1090492421358944257");
        goodsRulesDTO4.setProductCode("11");
        goodsRulesDTO4.setTargetType("1");               //经营主体
        goodsRulesDTO4.setTargetCode("C430422012590");
        entityList.add(goodsRulesDTO4);

        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setGoodsRulesDTOList(entityList);
        ResultVO<GoodsRulesExcelResp> rv = goodsRulesService.addProdGoodsRuleBatch(prodGoodsRuleEditReq);

        System.out.println(JSON.toJSONString(rv));
    }

    @Test
    public void raiseMarketNumDiffer() {
        List<GoodsRulePurchaseDTO> goodsRulePurchaseDTOs = new ArrayList<>();
        //零售商
        GoodsRulePurchaseDTO dto1 = new GoodsRulePurchaseDTO();
        dto1.setProductId("1082246155772592129");
        dto1.setGoodsId("1090855713436770306");
        dto1.setMerchantId("4300002063736");
        dto1.setPurchasedNum(2L);
        goodsRulePurchaseDTOs.add(dto1);

        //经营主体
        GoodsRulePurchaseDTO dto2 = new GoodsRulePurchaseDTO();
        dto2.setProductId("1082246155772592129");
        dto2.setGoodsId("1090855713436770306");
        dto2.setMerchantId("4307251022829");
        dto1.setPurchasedNum(3L);
        goodsRulePurchaseDTOs.add(dto1);

        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setGoodsRulePurchaseDTOs(goodsRulePurchaseDTOs);
        goodsRulesService.raiseMarketNumDiffer(prodGoodsRuleEditReq);
    }

}
