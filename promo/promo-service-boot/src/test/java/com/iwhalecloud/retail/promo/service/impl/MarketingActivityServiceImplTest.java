package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.*;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.*;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class MarketingActivityServiceImplTest {

    @Reference
    private MarketingActivityService marketingActivityService;

    @Test
    public void addMarketingActivity() {
        MarketingActivityAddReq req = new MarketingActivityAddReq();
        req.setId("");
        req.setCode("YXHD2019031200001");
        req.setName("营销活动名称2019031200001");
        req.setDescription("营销活动描述");
        req.setStartTime(new Date());
        req.setEndTime(new Date());
        req.setActivityType(PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        req.setActivityTypeName("活动类型名称");
        req.setPromotionTypeCode("优惠类型code");
        req.setStatus("1");
        req.setRelatedTaskId("关联任务ID");
        req.setInitiator("10");
        req.setSequence(1);
        req.setActivityUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setPageImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setTopImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setIsRecommend(0);
        req.setPayType("1234");
        req.setPreStartTime(new Date());
        req.setPreEndTime(new Date());
        req.setTailPayStartTime(new Date());
        req.setTailPayEndTime(new Date());
        List<ActivityScopeDTO> activityScopeList = Lists.newArrayList();
        ActivityScopeDTO activityScopeDTO = new ActivityScopeDTO();
        activityScopeDTO.setLanId("地市");
        activityScopeDTO.setCity("市县");
        activityScopeDTO.setSupplierCode("供应商编码");
        activityScopeDTO.setSupplierName("供应商名称");
        activityScopeList.add(activityScopeDTO);
        req.setActivityScopeList(activityScopeList);
        List<ActivityParticipantDTO> activityParticipantList = Lists.newArrayList();
        ActivityParticipantDTO activityParticipantDTO = new ActivityParticipantDTO();
        activityParticipantDTO.setLanId("地市");
        activityParticipantDTO.setCity("市县");
        activityParticipantDTO.setMerchantCode("商家编码");
        activityParticipantDTO.setMerchantName("商家名称");
        activityParticipantDTO.setMerchantType("商家类型");
        activityParticipantDTO.setShopCode("销售点编码");
        activityParticipantDTO.setShopName("销售点名称");
        activityParticipantList.add(activityParticipantDTO);
        req.setActivityParticipantList(activityParticipantList);
        List<ActivityProductDTO> activityProductList = Lists.newArrayList();
        ActivityProductDTO activityProductDTO = new ActivityProductDTO();
        activityProductDTO.setProductBaseId("productBaseId");
        activityProductDTO.setProductId("productId");
        activityProductDTO.setPrice(100L);
        activityProductDTO.setPrePrice(200L);
        activityProductDTO.setNum(100L);
        //activityProductDTO.setPayType("1234");
        activityProductDTO.setSupplierCode("supplierCode");
//        activityProductDTO.setPreStartTime(new Date());
//        activityProductDTO.setPreEndTime(new Date());
//        activityProductDTO.setTailPayStartTime(new Date());
//        activityProductDTO.setTailPayEndTime(new Date());
        activityProductList.add(activityProductDTO);
        req.setActivityProductList(activityProductList);
        ResultVO<MarketingActivityAddResp> resultVO = marketingActivityService.addMarketingActivity(req);
        System.out.println(resultVO.isSuccess());
    }

    /**
     * 直接新增
     */
    @Test
    public void addMarketingActivity1() {
        MarketingActivityAddReq req = new MarketingActivityAddReq();
        req.setId("");
        req.setId("");
        req.setCode("YXHD20190220019");
        req.setName("营销活动名称20190220019");
        req.setDescription("营销活动描述");
        req.setStartTime(new Date());
        req.setEndTime(new Date());
        req.setActivityType(PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        req.setActivityTypeName("活动类型名称");
        req.setPromotionTypeCode("优惠类型code");
        req.setStatus("1");
        req.setRelatedTaskId("关联任务ID");
        req.setInitiator("10");
        req.setSequence(1);
        req.setActivityUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setPageImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setTopImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setIsRecommend(0);
        ResultVO<MarketingActivityAddResp> resultVO = marketingActivityService.addMarketingActivity(req);
        System.out.println(resultVO.isSuccess());
    }

    /**
     * 已经存在，再次就是修改
     */
    @Test
    public void addMarketingActivity2() {
        MarketingActivityAddReq req = new MarketingActivityAddReq();
        req.setId("");
        req.setCode("YXHD20190333333334");
        req.setName("营销活动名称20190333333334");
        req.setDescription("营销活动描述");
        req.setStartTime(new Date());
        req.setEndTime(new Date());
        req.setActivityType(PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        req.setActivityTypeName("活动类型名称");
        req.setPromotionTypeCode("2626");
        req.setCreator("1");
        //req.setStatus("1");
        req.setRelatedTaskId("关联任务ID");
        req.setInitiator("10");
        req.setSequence(1);
        req.setActivityUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setPageImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setTopImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setIsRecommend(0);
        req.setPayType("1234");
        req.setPreStartTime(new Date());
        req.setPreEndTime(new Date());
        req.setTailPayStartTime(new Date());
        req.setTailPayEndTime(new Date());
        req.setUserId("1");
        req.setUserName("超级管理员");
        req.setOrgId("07XX");
        req.setSysPostName("");
//        List<ActivityScopeDTO> activityScopeList = Lists.newArrayList();
//        ActivityScopeDTO activityScopeDTO = new ActivityScopeDTO();
//        activityScopeDTO.setLanId("地市22");
//        activityScopeDTO.setCity("市县22");
//        activityScopeDTO.setSupplierCode("供应商编码32");
//        activityScopeDTO.setSupplierName("供应商名称32");
//        activityScopeList.add(activityScopeDTO);
//        req.setActivityScopeList(activityScopeList);
        List<ActivityParticipantDTO> activityParticipantList = Lists.newArrayList();
        ActivityParticipantDTO activityParticipantDTO = new ActivityParticipantDTO();
        activityParticipantDTO.setLanId("地市22");
        activityParticipantDTO.setCity("市县22");
        activityParticipantDTO.setMerchantCode("商家编码22");
        activityParticipantDTO.setMerchantName("商家名称22");
        activityParticipantDTO.setMerchantType("商家类型22");
        activityParticipantDTO.setShopCode("销售点编码22");
        activityParticipantDTO.setShopName("销售点名称22");
        activityParticipantList.add(activityParticipantDTO);
        req.setActivityParticipantList(activityParticipantList);
        List<ActivityProductDTO> activityProductList = Lists.newArrayList();
        ActivityProductDTO activityProductDTO = new ActivityProductDTO();
        activityProductDTO.setProductBaseId("productBaseId");
        activityProductDTO.setProductId("productId");
        activityProductDTO.setPrice(100L);
        activityProductDTO.setPrePrice(200L);
        activityProductDTO.setNum(100L);
        //activityProductDTO.setPayType("1234");
        activityProductDTO.setSupplierCode("supplierCode");
//        activityProductDTO.setPreStartTime(new Date());
//        activityProductDTO.setPreEndTime(new Date());
//        activityProductDTO.setTailPayStartTime(new Date());
//        activityProductDTO.setTailPayEndTime(new Date());
        activityProductList.add(activityProductDTO);
        req.setActivityProductList(activityProductList);
        List<ActivityRuleDTO> activityRuleList =Lists.newArrayList();
        ActivityRuleDTO activityRuleDTO= new ActivityRuleDTO();
        activityRuleDTO.setRuleId("1");
        activityRuleDTO.setRuleName("规则22");
        activityRuleDTO.setRemark("备注22");
        activityRuleDTO.setSettlementRule("");
        activityRuleDTO.setUseStartTime(new Date());
        activityRuleDTO.setUseEndTime(new Date());
        activityRuleDTO.setDeductionScale("");
        activityRuleDTO.setCalculationRule("");
        activityRuleDTO.setCreator("1");
        activityRuleList.add(activityRuleDTO);
        req.setActivityRuleDTOList(activityRuleList);

        List<PromotionDTO> activityPromotionList =Lists.newArrayList();
        PromotionDTO promotionDTO= new PromotionDTO();
        promotionDTO.setMktResId("1");
        promotionDTO.setMktResName("优惠券22");
        promotionDTO.setPromotionPrice("200022");
        promotionDTO.setPromotionType("10");
        promotionDTO.setPromotionEffectTime(new Date());
        promotionDTO.setPromotionOverdueTime(new Date());
        promotionDTO.setRemark("备注");
        activityPromotionList.add(promotionDTO);
        req.setPromotionDTOList(activityPromotionList);
        ResultVO<MarketingActivityAddResp> resultVO = marketingActivityService.addMarketingActivity(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void queryMarketingActivity() {
        String id = "1102769128925233154";
        ResultVO<MarketingActivityDetailResp> respResultVO = marketingActivityService.queryMarketingActivity(id);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void listMarketingActivity() {
        try {
            MarketingActivityListReq req = new MarketingActivityListReq();
            req.setPageNo(1);
            req.setPageSize(10);
            String time="2019-02-19";
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date dateTime=simpleDateFormat.parse(time);
//            req.setActivityName("名称");
            req.setActivityCode("YXHD20190333333333");
//            req.setActivityStatus("1");
//            req.setActivityType("活动类型名称");
//            req.setStartTimeS("");
//            req.setStartTimeE(simpleDateFormat.format(new Date()));
//            req.setEndTimeS(time);
//            req.setEndTimeE(simpleDateFormat.format(new Date()));
//            req.setActivityInitiator("10");
            ResultVO<Page<MarketingActivityListResp>> pageResultVO = marketingActivityService.listMarketingActivity(req);
            System.out.println(pageResultVO.isSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void endMarketingActivity() {
        String marketingActivityCode = "1097803327306756098";
        EndMarketingActivityStatusReq req = new EndMarketingActivityStatusReq();
        req.setActivityId(marketingActivityCode);
        ResultVO<Boolean> respResultVO = marketingActivityService.endMarketingActivity(req);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void listGoodsMarketingActivitys() {
        MarketingActivityQueryByGoodsReq req = new MarketingActivityQueryByGoodsReq();
        req.setProductId("1103223172458323970");
//        req.setMerchantCode("商家编码");
        req.setSupplierCode("4301811025392");
        req.setActivityType("1002");
        req.setMerchantCode("4301811022885");
        ResultVO<List<MarketingGoodsActivityQueryResp>> respResultVO = marketingActivityService.listGoodsMarketingActivitys(req);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void listGoodsMarketingReliefActivitys() {
        MarketingActivityQueryByGoodsReq req = new MarketingActivityQueryByGoodsReq();
        req.setProductId("1103223172458323970");
//        req.setMerchantCode("商家编码");
        req.setSupplierCode("4301811025392");
        req.setActivityType("1002");
        req.setMerchantCode("4301811022885");
        ResultVO<List<MarketingReliefActivityQueryResp>> respResultVO = marketingActivityService.listGoodsMarketingReliefActivitys(req);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void listGoodsMarketingCouponActivitys() {
        MarketingActivityQueryByGoodsReq req = new MarketingActivityQueryByGoodsReq();
        req.setProductId("1103223172458323970");
//        req.setMerchantCode("商家编码");
        req.setSupplierCode("4301811025392");
        req.setActivityType("1002");
        req.setMerchantCode("4301811022885");
        ResultVO<List<MarketingCouponActivityQueryResp>> respResultVO = marketingActivityService.listGoodsMarketingCouponActivitys(req);
        System.out.println(respResultVO.isSuccess());
    }
    @Test
    public void updateMarketingActivity(){
        MarketingActivityAddReq req =new MarketingActivityAddReq();
        req.setId("10133465");
        req.setCode("YXHD2019040200001");
        req.setName("YXHD2019040200001");
        req.setDescription("营销活动描述");
        req.setStartTime(new Date());
        req.setEndTime(new Date());
        req.setActivityType(PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        req.setActivityTypeName("活动类型名称");
        req.setPromotionTypeCode("优惠类型code");
        req.setStatus("1");
        req.setRelatedTaskId("关联任务ID");
        req.setInitiator("10");
        req.setSequence(1);
        req.setActivityUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setPageImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setTopImgUrl("https://img10.360buyimg.com/n1/s180x180_jfs/t20140/279/2633113298/113707/57d9da77/5b6018c5N6f80495e.jpg");
        req.setIsRecommend(0);
        req.setPayType("1234");
        req.setPreStartTime(new Date());
        req.setPreEndTime(new Date());
        req.setTailPayStartTime(new Date());
        req.setTailPayEndTime(new Date());
        List<ActivityScopeDTO> activityScopeList = Lists.newArrayList();
        ActivityScopeDTO activityScopeDTO = new ActivityScopeDTO();
        activityScopeDTO.setLanId("地市");
        activityScopeDTO.setCity("市县");
        activityScopeDTO.setSupplierCode("供应商编码");
        activityScopeDTO.setSupplierName("供应商名称");
        activityScopeList.add(activityScopeDTO);
        req.setActivityScopeList(activityScopeList);
        List<ActivityParticipantDTO> activityParticipantList = Lists.newArrayList();
        ActivityParticipantDTO activityParticipantDTO = new ActivityParticipantDTO();
        activityParticipantDTO.setLanId("地市");
        activityParticipantDTO.setCity("市县");
        activityParticipantDTO.setMerchantCode("商家编码");
        activityParticipantDTO.setMerchantName("商家名称");
        activityParticipantDTO.setMerchantType("商家类型");
        activityParticipantDTO.setShopCode("销售点编码");
        activityParticipantDTO.setShopName("销售点名称");
        activityParticipantList.add(activityParticipantDTO);
        req.setActivityParticipantList(activityParticipantList);
        List<ActivityProductDTO> activityProductList = Lists.newArrayList();
        ActivityProductDTO activityProductDTO = new ActivityProductDTO();
        activityProductDTO.setProductBaseId("productBaseId");
        activityProductDTO.setProductId("productId");
        activityProductDTO.setPrice(100L);
        activityProductDTO.setPrePrice(200L);
        activityProductDTO.setNum(100L);
        //activityProductDTO.setPayType("1234");
        activityProductDTO.setSupplierCode("supplierCode");
//        activityProductDTO.setPreStartTime(new Date());
//        activityProductDTO.setPreEndTime(new Date());
//        activityProductDTO.setTailPayStartTime(new Date());
//        activityProductDTO.setTailPayEndTime(new Date());
        activityProductList.add(activityProductDTO);
        req.setActivityProductList(activityProductList);
        marketingActivityService.updateMarketingActivity(req);
    }

    @Test
    public void getMarketingActivitiesByMktResIds(){

        ResultVO resultVO = marketingActivityService.getMarketingActivitiesByMktResIds(Lists.newArrayList("1", "2001"));
        System.out.println(JSON.toJSONString(resultVO));

    }


    /**
     * 查询预售活动的单个产品信息
     */
    @Test
    public void getAdvanceActivityProductInfo() {
        String marketingActivityId = "2019030400001";
        String productId = "1101089045995012097";

        AdvanceActivityProductInfoReq advanceActivityProductInfoReq = new AdvanceActivityProductInfoReq();
        advanceActivityProductInfoReq.setMarketingActivityId(marketingActivityId);
        advanceActivityProductInfoReq.setProductId(productId);

        ResultVO<AdvanceActivityProductInfoResp> advanceActivityProductInfoRespResultVO =  marketingActivityService.getAdvanceActivityProductInfo(advanceActivityProductInfoReq);
        System.out.println(JSON.toJSONString(advanceActivityProductInfoRespResultVO));
    }
    /**
     * 查询营销活动信息关联的优惠信息
     *
     */
    @Test
    public void listMarketingActivityAndPromotions(){
        MarketingActivityQueryByGoodsReq req = new MarketingActivityQueryByGoodsReq();
//        req.setProductId("1105011172959784961");
//        req.setMerchantCode("4301811022885");
//        req.setSupplierCode("4301811025392");
//        req.setActivityType("1002");
        req.setProductId("100000861");
        req.setMerchantCode("4301811022885");
        req.setSupplierCode("4301811025392");
        req.setActivityType("1002");
        ResultVO<List<MarketingAndPromotionResp>> respResultVO = marketingActivityService.listMarketingActivityAndPromotions(req);
        System.out.println(respResultVO.isSuccess());
    }

    /**
     * 自动推送前置补贴优惠券
     */
    @Test
    public void autoPushCoupon(){
       System.out.println(marketingActivityService.autoPushActivityCoupon("1105068774406152193"));
    }
    @Test
    public void getMarketingActivityDtoById(){
        System.out.println(marketingActivityService.autoPushActivityCoupon("1105068774406152193"));
    }

}