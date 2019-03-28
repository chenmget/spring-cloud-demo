package com.iwhalecloud.retail.promo.filter.activity;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.ActivityParticipant;
import com.iwhalecloud.retail.promo.entity.ActivityScope;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.filter.activity.model.ActivityAuthModel;
import com.iwhalecloud.retail.promo.manager.ActivityParticipantManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 买家营销活动校验
 *
 * @author z
 * @date 2019/3/4
 */
@Service
@Slf4j
public class BuyerActivityFilter extends AbsActivityFilter {


    @Resource
    private ActivityParticipantManager activityParticipantManager;

    @Override
    public boolean doFilter(ActivityAuthModel activityAuthModel) {

        MarketingActivity marketingActivity = activityAuthModel.getMarketingActivity();
        MerchantDTO merchantDTO = activityAuthModel.getMerchantBuyer();
        log.info("BuyerActivityFilter authing,marketingActivity={},merchantDTO={}"
                , JSON.toJSONString(marketingActivity),JSON.toJSONString(merchantDTO));

        if (marketingActivity == null || merchantDTO == null) {
            log.error("BuyerActivityFilter param is null !!auth unpass!!,marketingActivity={},merchantDTO={}"
                    , JSON.toJSONString(marketingActivity),JSON.toJSONString(merchantDTO));
            return false;
        }
        //买家参与对象类型
        final String activityId = marketingActivity.getId();
        final String activityScopeType = marketingActivity.getActivityScopeType();
        final String lanId = merchantDTO.getLanId();
        final String cityId = merchantDTO.getCity();
        final String buyerId = merchantDTO.getMerchantId();

        if(PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_10.getCode().equals(activityScopeType)){
            //根据本地网校验
            ActivityParticipant activityParticipantLan = activityParticipantManager.queryActivityParticipantByLandId(marketingActivity.getId(), lanId);
            log.info("BuyerActivityFilter !!lan authing!!,activityId={},lanId={}", activityId, lanId);
            if(activityParticipantLan != null){
                log.info("BuyerActivityFilter lan auth pass,activityId={},lanId={}", activityId, lanId);
                return true;
            } else {
                log.warn("BuyerActivityFilter !!lan unpass!!,activityId={},lanId={}", activityId, lanId);
            }

            //根据区县校验
            ActivityParticipant activityParticipantCity = activityParticipantManager.queryActivityParticipantByCityId(marketingActivity.getId(), cityId);
            log.info("BuyerActivityFilter !!cityId authing!!,activityId={},cityId={}", activityId, cityId);
            if(activityParticipantCity != null){
                log.info("BuyerActivityFilter city auth pass,activityId={},lanId={}", activityId, lanId);
                return true;
            } else {
                log.warn("BuyerActivityFilter !!city auth unpass!!,activityId={},lanId={}", activityId, lanId);
            }

        } else if(PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_20.getCode().equals(activityScopeType)){
            //过滤买家编码
            ActivityParticipant activityParticipantBuyer = activityParticipantManager.queryActivityParticipantByMerchantCode(marketingActivity.getId(), buyerId);
            if (activityParticipantBuyer != null) {
                log.info("BuyerActivityFilter supplierCode auth pass,activityId={},supplierId={}", activityId, buyerId);
                return true;
            } else {
                log.warn("BuyerActivityFilter !!supplierCode auth unpass!!,activityId={},supplierId={}", activityId, buyerId);
            }
        }
        return false;
    }
}
