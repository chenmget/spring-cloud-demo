package com.iwhalecloud.retail.rights.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.AutoPushCouponReq;

/**
 * 优惠券发放任务
 */
public interface MktResCouponTaskService {

    /**
     * 新增优惠券发放任务
     * @param autoPushCouponReq
     * @return
     */
     void addMktResCouponTask(AutoPushCouponReq autoPushCouponReq);

}