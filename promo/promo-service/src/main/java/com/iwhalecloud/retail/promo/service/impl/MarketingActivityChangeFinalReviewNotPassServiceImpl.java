package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.constant.Constant;
import com.iwhalecloud.retail.promo.manager.ActivityChangeManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.MarketingActivityChangeFinalReviewNotPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**营销活动终审不通过业务方法
 * @author lp 2019-05-24 10:00:00
 */
@Slf4j
@Service
public class MarketingActivityChangeFinalReviewNotPassServiceImpl implements MarketingActivityChangeFinalReviewNotPassService {

    @Autowired
    private ActivityChangeManager activityChangeManager;
    @Autowired
    private MarketingActivityManager marketingActivityManager;
    @Autowired
    private Constant constant;
    /**
     * 审核未通过
     * @param params
     * @return
     */
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("MarketingActivityChangeFinalReviewNotPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("MarketingActivityChangeFinalReviewNotPassServiceImpl.run  params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String changeId = params.getParamsValue();
        //执行审核操作，终审不通过
        boolean flag = activityChangeManager.updateActivityChangeNoPassById(changeId,PromoConst.AuditState.AuditState_6.getCode());
        if (flag){
            //2.将营销活动“修改标识”改为0(不在审核修改中)
            marketingActivityManager.updateMarketingActivityToModifying(params.getBusinessId(),PromoConst.ActivityIsModifying.NO.getCode());
            return ResultVO.successMessage(constant.getUpdateSuccess());
        }else{
            return ResultVO.error(constant.getUpdateFaile());
        }
    }
}
