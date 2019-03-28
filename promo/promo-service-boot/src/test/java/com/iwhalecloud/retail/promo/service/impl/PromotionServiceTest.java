package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityQueryByGoodsReq;
import com.iwhalecloud.retail.promo.dto.req.OrderPromotionListReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityDetailResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingAndPromotionResp;
import com.iwhalecloud.retail.promo.dto.resp.OrderPromotionListResp;
import com.iwhalecloud.retail.promo.dto.resp.UserDTO;
import com.iwhalecloud.retail.promo.service.ActivityGoodService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.promo.service.PromotionService;
import com.iwhalecloud.retail.rights.dto.request.InputRightsRequestDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveRightsRequestDTO;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class PromotionServiceTest {

//    @Autowired
//    private PromotionService promotionService;

    @Reference
    private CouponInstService couponInstService;
    @Reference
    private MarketingActivityService marketingActivityService;
    
    @Reference
    private ActivityGoodService activityGoodService;
    
    @Test
    public void inputRights() throws ParseException {
        InputRightsRequestDTO req = new InputRightsRequestDTO();
        req.setGmtCreate("11111");
        req.setInventoryNum(1);
        req.setRightsId("2003");
        ResultVO respResultVO = couponInstService.inputRights(req);
        log.info(JSON.toJSONString(respResultVO));
    }

    @Test
    public void saveRights() {
        SaveRightsRequestDTO req = new SaveRightsRequestDTO();
        req.setCustNum("22222");
        req.setMktResId("2003");
        req.setSupplyNum(1L);
        ResultVO respResultVO = couponInstService.saveRights(req);
        log.info(JSON.toJSONString(respResultVO));
    }

//    @Test
//    public void orderPromotionList() {
//        OrderPromotionListReq req = new OrderPromotionListReq();
//        req.setUserId("2222");
//        ResultVO<OrderPromotionListResp> respResultVO = promotionService.orderPromotionList(req);
//        log.info(JSON.toJSONString(respResultVO));
//    }
    @Test
    public void listMarketingActivityAndPromotions(){
        MarketingActivityQueryByGoodsReq req = new MarketingActivityQueryByGoodsReq();
        req.setProductId("1101089045995012097");
        req.setMerchantCode("4301811022885");
        req.setSupplierCode("4301811025392");
        ResultVO<List<MarketingAndPromotionResp>> respResultVO = marketingActivityService.listMarketingActivityAndPromotions(req);
        System.out.println(respResultVO.isSuccess());
    }
    @Test
    public void listMarketingActivityByMerchant(){
        MarketingActivityByMerchantListReq marketingActivityByMerchantListReq = new MarketingActivityByMerchantListReq();
        marketingActivityByMerchantListReq.setPageNo(1);
        marketingActivityByMerchantListReq.setPageSize(10);
//        UserDTO userDTO = new UserDTO();
//        userDTO.setRelCode("1111111");
//        userDTO.setLanId("11111");
//        userDTO.setRegionId("1111111");
//        marketingActivityByMerchantListReq.setUserInfo(userDTO);
        marketingActivityByMerchantListReq.setMerchantId("111111111");
        activityGoodService.listMarketingActivityByMerchant(marketingActivityByMerchantListReq);
    }
}
