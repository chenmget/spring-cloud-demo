package com.iwhalecloud.retail.promo.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActReBateProductReq;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.dto.resp.ActActivityProductRuleServiceResp;

import java.util.List;

public interface ActActivityProductRuleService{
    /**
     * 删除活动规则信息
     * @param activityProductReq
     * @return
     */
    ResultVO deleteReBateProductRuleActivity(ActivityProductReq activityProductReq);

    /**
     * 查询返利规则产品信息
     * @param marketingActivityId
     * @return
     */
    ResultVO<ActActivityProductRuleServiceResp> queryActActivityProductRuleServiceResp(String marketingActivityId,String prodId);

    /**
     * 添加返利活动产品
     * @param actReBateProductReq
     * @return
     */
    ResultVO addReBateProduct(ActReBateProductReq actReBateProductReq);

    /**
     * 保存更改后的产品
     * @param activityProductReq
     * @return
     */
    ResultVO saveActProduct(ActivityProductReq activityProductReq);

    ResultVO deleteReBateProducts(ActivityProductReq activityProductReq);

    /**
     * 修改返利活动信息
     * @param req
     * @return
     */
    //ResultVO<Boolean> updateReBateActivityInfo(MarketingActivityAddReq req);

    /**
     * 终止返利
     * @param req
     * @return
     */
    ResultVO<Boolean> stopReBateProductActivity(ActivityProductReq req);
}