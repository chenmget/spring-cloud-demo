package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.constant.Constant;
import com.iwhalecloud.retail.promo.entity.ActivityChange;
import com.iwhalecloud.retail.promo.manager.ActivityChangeManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.MarketingActivityChangeFirstReviewPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**营销活动初审通过业务方法
 * @author lp 2019-05-24 10:00:00
 */
@Slf4j
@Service
public class MarketingActivityChangeFirstReviewPassServiceImpl implements MarketingActivityChangeFirstReviewPassService {

    @Autowired
    private ActivityChangeManager activityChangeManager;
    @Autowired
    private MarketingActivityManager marketingActivityManager;
    @Autowired
    private Constant constant;

    /**
     * 审核通过
     * @param params
     * @return
     */
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("MarketingActivityChangeFirstReviewPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("MarketingActivityChangeFirstReviewPassServiceImpl.run  params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        //1.变更状态为初审通过
        String changeId = params.getParamsValue();
        ActivityChange change = activityChangeManager.queryActivityChangeById(changeId);
        if (change==null){
            return ResultVO.error(constant.getUpdateFaile());
        }
        change.setAuditState(PromoConst.AuditState.AuditState_3.getCode());
        boolean flag = activityChangeManager.updateActivityChange(change);
        if (flag){
            return ResultVO.successMessage(constant.getUpdateSuccess());
        }else{
            return ResultVO.error(constant.getUpdateFaile());
        }
    }
}
