package com.iwhalecloud.retail.promo.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.mapper.MarketingActivityMapper;
import com.iwhalecloud.retail.promo.service.AuditMarketingActivityPassService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lhr 2019-02-22 20:09:30
 */
@Component
public class AuditMarketingActivityNotPassManager {

    @Resource
    private MarketingActivityMapper activityMapper;

    /**
     * @param marketingActivityId
     * @return
     */
    public ResultVO updateMarketActNotPassById(String marketingActivityId){
        if (StringUtils.isNotEmpty(marketingActivityId)){
            MarketingActivity marketingActivity =new MarketingActivity();
            marketingActivity.setId(marketingActivityId);
            marketingActivity.setGmtModified(new Date());
            marketingActivity.setStatus(PromoConst.STATUSCD.STATUS_CD_PLUS_20.getCode());
            return ResultVO.success(activityMapper.updateById(marketingActivity) > 0);
        }else {
            return ResultVO.error("活动ID为空");
        }
    }
}
