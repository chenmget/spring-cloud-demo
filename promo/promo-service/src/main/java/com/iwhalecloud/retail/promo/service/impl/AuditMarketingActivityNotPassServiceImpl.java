package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityAddReq;
import com.iwhalecloud.retail.promo.manager.AuditMarketingActivityNotPassManager;
import com.iwhalecloud.retail.promo.service.AuditMarketingActivityNotPassService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lhr 2019-02-22 17:50:30
 */
@Slf4j
@Service
public class AuditMarketingActivityNotPassServiceImpl implements AuditMarketingActivityNotPassService {

    @Autowired
    private AuditMarketingActivityNotPassManager activityNotPassManager;
    @Autowired
    private MarketingActivityService marketingActivityService;
    /**
     * 审核未通过
     * @param params
     * @return
     */
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("AuditMarketingActivityNotPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("AuditMarketingActivityNotPassServiceImpl.run  params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String id = params.getBusinessId();
        MarketingActivityAddReq marketingActivity = new MarketingActivityAddReq();
        marketingActivity.setId(id);
        marketingActivity.setStatus(PromoConst.STATUSCD.STATUS_CD_PLUS_20.getCode());
        return marketingActivityService.addMarketingActivity(marketingActivity);
    }
}
