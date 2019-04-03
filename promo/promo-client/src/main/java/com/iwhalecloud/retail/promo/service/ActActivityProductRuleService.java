package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActReBateProductReq;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductReq;
import com.iwhalecloud.retail.promo.dto.req.ReBateActivityListReq;
import com.iwhalecloud.retail.promo.dto.resp.ActActivityProductRuleServiceResp;
import com.iwhalecloud.retail.promo.dto.resp.ReBateActivityListResp;

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
     * 查询返利活动列表
     * @param req
     * @return
     */
    ResultVO<Page<ReBateActivityListResp>> listReBateActivity(ReBateActivityListReq req);

}