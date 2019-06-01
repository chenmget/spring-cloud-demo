package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.constant.Constant;
import com.iwhalecloud.retail.promo.manager.ActivityChangeManager;
import com.iwhalecloud.retail.promo.service.MarketingActivityChangeFinalReviewPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**营销活动终审通过业务方法
 * @author lp 2019-05-24 10:00:00
 */
@Slf4j
@Service
public class MarketingActivityChangeFinalReviewPassServiceImpl implements MarketingActivityChangeFinalReviewPassService {

    @Autowired
    private ActivityChangeManager activityChangeManager;
    @Autowired
    private Constant constant;

    /**
     * 审核通过
     * @param params
     * @return
     */
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("MarketingActivityChangeFinalReviewPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("MarketingActivityChangeFinalReviewPassServiceImpl.run  params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String changeId = params.getParamsValue();
        //执行审核操作，终审通过
        boolean flag = activityChangeManager.updateActivityChangePassById(changeId);
        if (flag){
            return ResultVO.successMessage(constant.getUpdateSuccess());
        }else{
            return ResultVO.error(constant.getUpdateFaile());
        }
    }
}
