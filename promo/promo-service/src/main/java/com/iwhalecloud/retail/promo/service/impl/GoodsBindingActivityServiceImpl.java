package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsQueryByProductIdsReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsUpdateActTypeByGoodsIdsReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdFileReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsProductRelService;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.manager.ActivityProductManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.GoodsBindingActivityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: wang.jiaxin
 * @date: 2019年03月26日
 * @description: 根据活动更新商品参与活动状态
 **/
@Slf4j
@Service
@Component("goodsBindingActivityService")
public class GoodsBindingActivityServiceImpl implements GoodsBindingActivityService {

    @Autowired
    private MarketingActivityManager marketingActivityManager;

    @Autowired
    private ActivityProductManager activityProductManager;

    @Reference
    private GoodsProductRelService goodsProductRelService;

    @Reference
    private GoodsService goodsService;

    @Override
    public void goodsBingActivity() {
        log.info("开始查询活动，更新商品表是否参与预售/前置补贴活动字段");
        // 查询生效的预售活动
        List<MarketingActivity> advanceSaleActivityList = marketingActivityManager.queryActivityListByStatus(false, PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        if (CollectionUtils.isNotEmpty(advanceSaleActivityList)) {
            List<String> goodsIdList = queryGoodsIdList(advanceSaleActivityList);
            log.info("参与预售活动的商品ID列表 goodsIdList={}", JSON.toJSON(goodsIdList));
            if (CollectionUtils.isNotEmpty(goodsIdList)) {
                // 更新商品表是否参与预售活动字段
                GoodsUpdateActTypeByGoodsIdsReq req = new GoodsUpdateActTypeByGoodsIdsReq();
                req.setGoodsIds(goodsIdList);
                req.setIsAdvanceSale(GoodsConst.IsAdvanceSale.IS_ADVANCE_SALE.getCode());
                log.info("开始更新商品表字段 req={}", JSON.toJSON(req));
                Boolean resultData = goodsService.updateGoodsActTypeByGoodsIdList(req).getResultData();
                log.info("更新商品表字段结果 resp={}", JSON.toJSON(resultData));
            }
        }
        // 查询生效的前置补贴活动
        List<MarketingActivity> subsidyActivityList = marketingActivityManager.queryActivityListByStatus(false, PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode());
        if (CollectionUtils.isNotEmpty(subsidyActivityList)) {
            List<String> goodsIdList = queryGoodsIdList(subsidyActivityList);
            log.info("参与前置补贴活动的商品ID列表 goodsIdList={}", JSON.toJSON(goodsIdList));
            if (CollectionUtils.isNotEmpty(goodsIdList)) {
                // 更新商品表是否参与前置补贴活动字段
                GoodsUpdateActTypeByGoodsIdsReq req = new GoodsUpdateActTypeByGoodsIdsReq();
                req.setGoodsIds(goodsIdList);
                // 前置补贴只更新地包的，加merchantType类型
                req.setMerchantType(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType());
                req.setIsSubsidy(GoodsConst.IsSubsidy.IS_SUBSIDY.getCode());
                log.info("开始更新商品表字段 req={}", JSON.toJSON(req));
                Boolean resultData = goodsService.updateGoodsActTypeByGoodsIdList(req).getResultData();
                log.info("更新商品表字段结果 resp={}", JSON.toJSON(resultData));
            }
        }
    }

    @Override
    public void goodsUnBundlingActivity() {
        // 查询失效的预售活动
        List<MarketingActivity> advanceSaleActivityList = marketingActivityManager.queryActivityListByStatus(true, PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        if (CollectionUtils.isNotEmpty(advanceSaleActivityList)) {
            List<String> goodsIdList = queryGoodsIdList(advanceSaleActivityList);
            log.info("参与预售活动的商品ID列表 goodsIdList={}", JSON.toJSON(goodsIdList));
            if (CollectionUtils.isNotEmpty(goodsIdList)) {
                // 更新商品表是否参与预售活动字段
                GoodsUpdateActTypeByGoodsIdsReq req = new GoodsUpdateActTypeByGoodsIdsReq();
                req.setGoodsIds(goodsIdList);
                req.setIsAdvanceSale(GoodsConst.IsAdvanceSale.IS_NOT_ADVANCE_SALE.getCode());
                log.info("开始更新商品表字段 req={}", JSON.toJSON(req));
                Boolean resultData = goodsService.updateGoodsActTypeByGoodsIdList(req).getResultData();
                log.info("更新商品表字段结果 resp={}", JSON.toJSON(resultData));
            }
        }
        // 查询失效的前置补贴活动
        List<MarketingActivity> subsidyActivityList = marketingActivityManager.queryActivityListByStatus(true, PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode());
        if (CollectionUtils.isNotEmpty(subsidyActivityList)) {
            List<String> goodsIdList = queryGoodsIdList(subsidyActivityList);
            log.info("参与前置补贴活动的商品ID列表 goodsIdList={}", JSON.toJSON(goodsIdList));
            if (CollectionUtils.isNotEmpty(goodsIdList)) {
                // 更新商品表是否参与前置补贴活动字段
                GoodsUpdateActTypeByGoodsIdsReq req = new GoodsUpdateActTypeByGoodsIdsReq();
                req.setGoodsIds(goodsIdList);
                req.setIsSubsidy(GoodsConst.IsSubsidy.IS_NOT_SUBSIDY.getCode());
                log.info("开始更新商品表字段 req={}", JSON.toJSON(req));
                Boolean resultData = goodsService.updateGoodsActTypeByGoodsIdList(req).getResultData();
                log.info("更新商品表字段结果 resp={}", JSON.toJSON(resultData));
            }
        }
    }

    /**
     * 根据活动查询商品ID列表
     *
     * @param activityList 活动列表
     * @return
     */
    private List<String> queryGoodsIdList(List<MarketingActivity> activityList) {
        List<String> goodsIdsList = Lists.newArrayList();
        List<String> actIdList = this.getActIdList(activityList);
        log.info("活动ID列表 actIdList={}", JSON.toJSON(actIdList));
        if (CollectionUtils.isNotEmpty(actIdList)) {
            List<ActivityProduct> activityProductList = activityProductManager.queryActivityProductByCondition(actIdList);
            log.info("活动产品列表 activityProductList={}", JSON.toJSON(activityProductList));
            if (CollectionUtils.isNotEmpty(activityProductList)) {
                List<String> productIdList = this.getProductIdList(activityProductList);
                log.info("产品ID列表 productIdList={}", JSON.toJSON(productIdList));
                if (CollectionUtils.isNotEmpty(productIdList)) {
                    GoodsQueryByProductIdsReq req = new GoodsQueryByProductIdsReq();
                    req.setProductIds(productIdList);
                    goodsIdsList = goodsProductRelService.queryGoodsIdsByProductIds(req).getResultData().getGoodsIds();
                    log.info("产品ID列表 productIdList={}", JSON.toJSON(productIdList));
                }
            }
        }
        return goodsIdsList;
    }

    /**
     * 获取活动ID列表
     *
     * @param activityList 活动列表
     * @return 活动ID列表
     */
    private List<String> getActIdList(List<MarketingActivity> activityList) {
        List<String> actIdList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(activityList)) {
            activityList.forEach(item -> {
                actIdList.add(item.getId());
            });
        }
        return actIdList;
    }

    /**
     * 获取活动产品ID列表
     *
     * @param activityList 活动产品列表
     * @return 活动产品ID列表
     */
    private List<String> getProductIdList(List<ActivityProduct> activityList) {
        List<String> productIdList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(activityList)) {
            activityList.forEach(item -> {
                productIdList.add(item.getProductId());
            });
        }
        return productIdList;
    }

}
