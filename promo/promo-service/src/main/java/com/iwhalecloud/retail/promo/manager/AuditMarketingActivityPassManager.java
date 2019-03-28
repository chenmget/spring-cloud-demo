package com.iwhalecloud.retail.promo.manager;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.mapper.MarketingActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lhr 2019-02-23 10:01:30
 */
@Slf4j
@Component
public class AuditMarketingActivityPassManager {

    @Resource
    private MarketingActivityMapper activityMapper;

    /**
     * @param marketingActivityId
     * @return
     */
    public ResultVO updateMarketActPassById(String marketingActivityId){
        log.info("AuditMarketingActivityPassManager.updateMarketActPassById marketingActivityId{}",marketingActivityId);
        if (StringUtils.isNotEmpty(marketingActivityId)){
            MarketingActivity marketingActivity =new MarketingActivity();
            marketingActivity.setId(marketingActivityId);
            marketingActivity.setGmtModified(new Date());
            marketingActivity.setStatus(PromoConst.STATUSCD.STATUS_CD_20.getCode());
            return ResultVO.success(activityMapper.updateById(marketingActivity) > 0);
        }else {
            return ResultVO.error("活动ID为空");
        }
    }
}
