package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.PromotionWithMarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.*;

import java.util.List;

public interface MarketingActivityService{

    ResultVO<MarketingActivityAddResp> addMarketingActivity(MarketingActivityAddReq req);

    ResultVO<MarketingActivityDetailResp> queryMarketingActivity(String id);

    ResultVO<Page<MarketingActivityListResp>> listMarketingActivity(MarketingActivityListReq req);

    ResultVO<Boolean> cancleMarketingActivity(CancelMarketingActivityStatusReq req);

    ResultVO<Boolean> endMarketingActivity(EndMarketingActivityStatusReq req);

    ResultVO<Boolean> startMarketingActivity(StartMarketingActivityStatusReq req);

    /**
     * 查询B2B商品适用活动
     * @param req 商品参数
     * @return 商品适用活动列表
     */
    ResultVO<List<MarketingGoodsActivityQueryResp>> listGoodsMarketingActivitys(MarketingActivityQueryByGoodsReq req);

    /**
     * 查询B2B商品适用减免
     * @param req 商品参数
     * @return 商品适用减免列表
     */
    ResultVO<List<MarketingReliefActivityQueryResp>> listGoodsMarketingReliefActivitys(MarketingActivityQueryByGoodsReq req);

    /**
     * 查询B2B商品适用卡券
     * @param req 商品参数
     * @return 商品适用卡券列表
     */
    ResultVO<List<MarketingCouponActivityQueryResp>> listGoodsMarketingCouponActivitys(MarketingActivityQueryByGoodsReq req);

    /**
     * 营销活动更新
     * @param req
     * @return
     */
    ResultVO<Boolean> updateMarketingActivity(MarketingActivityAddReq req);
    /**
     * 营销活动审核
     * @param userId  invoiceId
     * @return
     */
    ResultVO marketingActivityAuitStartProcess(String mktName,String userId,String userName,String orgName,String sysPostName,String marketId);

    /**
     * 根据优惠ID集合查询 营销活动集合
     * @param mktResIds 优惠ID集合
     * @return
     */
    ResultVO<List<PromotionWithMarketingActivityDTO>> getMarketingActivitiesByMktResIds(List<String> mktResIds);

    /**
     * 根据营销活动ID查询营销活动、优惠券、参与产品详情
     * @param activityId
     * @return
     */
    ResultVO<MarketingActivityInfoResp> queryMarketingActivityInfo(String activityId);

    /**
     * 据营销活动ID查询营销活动信息
     * @param queryMarketingActivityReq
     * @return
     */
    ResultVO<MarketingActivityDTO> queryMarketingActivityById(QueryMarketingActivityReq queryMarketingActivityReq);

    /**
     * 更新预售活动的规则
     * @param marketingActivityAddReq
     * @return
     */
    ResultVO updatePreSaleActivityRule(MarketingActivityAddReq marketingActivityAddReq);

    /**
     * 获取预售活动单个产品信息
     * @param advanceActivityProductInfoReq
     * @return
     */
    ResultVO<AdvanceActivityProductInfoResp> getAdvanceActivityProductInfo(AdvanceActivityProductInfoReq advanceActivityProductInfoReq);


    /**
     * 根据卖家查询营销活动
     * @param marketingActivityQueryBySupplierReq
     * @return 有效的营销活动
     */
    ResultVO<List<MarketingActivityQueryBySupplierResp>> queryMarketingActivityQueryBySupplier(MarketingActivityQueryBySupplierReq marketingActivityQueryBySupplierReq);

    /**
     * 根据活动ID查询活动详情
     * @param activityId
     * @return
     */
    ResultVO<MarketingGoodsActivityQueryResp> getMarketingActivity(String activityId);

    /**
     * 查询营销活动信息关联的优惠信息
     * @param req 商品参数
     * @return
     */
    ResultVO<List<MarketingAndPromotionResp>> listMarketingActivityAndPromotions(MarketingActivityQueryByGoodsReq req);


    /**
     * 查询特定类型失效的活动
     * @param marketingActivityListReq
     * @return
     */
    ResultVO<List<MarketingActivityListResp>> queryInvalidActivity(MarketingActivityListReq marketingActivityListReq);

    /**
     * 前置补贴活动审核后推送活动
     * @param marketingActivityId
     * @return
     */
    ResultVO autoPushActivityCoupon(String marketingActivityId);

    /**
     * 通过产品ID查询某一类型的活动列表
     * @param productId
     * @param activityType
     * @return
     */
    ResultVO<List<MarketingActivityDTO>> queryActivityByProductId(String productId,String activityType);

    /**
     * 营销活动审核
     * @param req
     */
    void auitMarketingActivity(AuitMarketingActivityReq req);
}