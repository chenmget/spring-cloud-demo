package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.constant.Constant;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.entity.MarketingActivityModify;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.AuditMarketingActivityModifyPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lhr 2019-04-02 20:30:30
 */
@Slf4j
@Service
public class AuditMarketingActivityModifyPassServiceImpl implements AuditMarketingActivityModifyPassService {
    @Autowired
    private MarketingActivityManager marketingActivityManager;
    @Autowired
    private Constant constant;
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("AuditMarketingActivityModifyPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("AuditMarketingActivityModifyPassServiceImpl.run  params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String id = params.getBusinessId();
        log.info("AuditMarketingActivityModifyPassServiceImpl.run id={}", id);
        List<MarketingActivityModify> modifies = marketingActivityManager.queryMarketingActivityModifySize(id);
        if (!CollectionUtils.isEmpty(modifies)){
            MarketingActivityModify marketingActivityModify = modifies.get(modifies.size()-1);
            MarketingActivity marketingActivity = marketingActivityManager.getMarketingActivityById(marketingActivityModify.getMarketingActivityId());
            BeanUtils.copyProperties(marketingActivityModify, marketingActivity);
            marketingActivity.setId(marketingActivityModify.getMarketingActivityId());
            int num = marketingActivityManager.addMarketingActivity(marketingActivity);
            if (num > 0){
                return ResultVO.successMessage(constant.getUpdateSuccess());
            }
        }
        return ResultVO.error(constant.getUpdateFaile());
    }
}
