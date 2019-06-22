package com.iwhalecloud.retail.promo.filter.activity;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.ActivityScope;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.filter.activity.model.ActivityAuthModel;
import com.iwhalecloud.retail.promo.manager.ActivityScopeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 卖方活动校验
 *
 * @author z
 * @date 2019/3/4
 */
@Service
@Slf4j
public class SellerActivityFilter extends AbsActivityFilter {

    @Resource
    private ActivityScopeManager activityScopeManager;

    @Override
    public boolean doFilter(ActivityAuthModel activityAuthModel) {

        MarketingActivity marketingActivity = activityAuthModel.getMarketingActivity();
        MerchantDTO merchantDTO = activityAuthModel.getMerchantSeller();
        log.info("SellerActivityFilter authing,marketingActivity={},merchantDTO={}"
                , JSON.toJSONString(marketingActivity),JSON.toJSONString(merchantDTO));

        if (marketingActivity == null || merchantDTO == null) {
            log.error("SellerActivityFilter auth unpass,marketingActivity={},merchantDTO={}"
                    , JSON.toJSONString(marketingActivity),JSON.toJSONString(merchantDTO));
            return false;
        }
        //卖家参与对象类型
        final String activityId = marketingActivity.getId();
        final String activityScopeType = marketingActivity.getActivityScopeType();
        final String lanId = merchantDTO.getLanId();
        final String cityId = merchantDTO.getCity();

        if(PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_10.getCode().equals(activityScopeType)){
            //根据本地网校验
            ActivityScope activityScopeLan = activityScopeManager.queryActivityScopeByLandId(activityId, lanId);
            log.info("SellerActivityFilter lan !!authing!!,activityId={},lanId={}", activityId, lanId);
            if(activityScopeLan != null){
                log.info("SellerActivityFilter lan auth pass,activityId={},lanId={}", activityId, lanId);
                return true;
            } else {
                log.warn("SellerActivityFilter !!lan unpass!!,activityId={},lanId={}", activityId, lanId);
            }

            //根据区县校验
            ActivityScope activityScopeCity = activityScopeManager.queryActivityScopeByCityId(activityId,cityId);
            log.info("SellerActivityFilter cityId !!authing!!,activityId={},cityId={}", activityId, cityId);
            if(activityScopeCity != null){
                log.info("SellerActivityFilter city auth pass,activityId={},lanId={}", activityId, lanId);
                return true;
            } else {
                log.warn("SellerActivityFilter !!city auth unpass!!,activityId={},lanId={}", activityId, lanId);
            }

        } else if(PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_20.getCode().equals(activityScopeType)){
            //过滤卖家编码
            ActivityScope activityScopeSuplier = activityScopeManager.getActivityScopeBySupplierCode(marketingActivity.getId(),merchantDTO.getMerchantId());
            if (activityScopeSuplier != null) {
                log.info("SellerActivityFilter supplierCode auth pass,activityId={},supplierId={}", activityId, merchantDTO.getMerchantId());
                return true;
            } else {
                log.warn("SellerActivityFilter !!supplierCode auth unpass!!,activityId={},supplierId={}", activityId, merchantDTO.getMerchantId());
            }
        }
        return false;
    }

}
