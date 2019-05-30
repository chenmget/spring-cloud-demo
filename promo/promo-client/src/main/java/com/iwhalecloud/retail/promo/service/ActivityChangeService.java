package com.iwhalecloud.retail.promo.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.resp.ActivityChangeResp;

public interface ActivityChangeService{

    /**
     * 查找营销活动变更信息
     * @param activityId
     * @return
     */
    ResultVO<ActivityChangeResp> queryMarketingActivityChangeInfo(String activityId);

}