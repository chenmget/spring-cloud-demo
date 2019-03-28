package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityAddReq;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.manager.AuditMarketingActivityPassManager;
import com.iwhalecloud.retail.promo.service.AuditMarketingActivityPassService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author lhr 2019-02-22 17:50:30
 */
@Slf4j
@Service
public class AuditMarketingActivityPassServiceImpl implements AuditMarketingActivityPassService{

    @Autowired
    private AuditMarketingActivityPassManager activityPassManager;
    @Autowired
    private MarketingActivityService marketingActivityService;
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("AuditMarketingActivityPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("AuditMarketingActivityPassServiceImpl.run  params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String id = params.getBusinessId();
        MarketingActivityAddReq marketingActivity = new MarketingActivityAddReq();
        marketingActivity.setId(id);
        marketingActivity.setStatus(PromoConst.STATUSCD.STATUS_CD_20.getCode());
        return activityPassManager.updateMarketActPassById(id);
    }
}
