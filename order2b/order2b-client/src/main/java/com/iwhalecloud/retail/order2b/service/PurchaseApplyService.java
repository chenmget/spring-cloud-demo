package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyDeliveryReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReceivingReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;

public interface PurchaseApplyService {

    /**
     * 采购单发货
     *
     * @param req
     * @return
     */
    ResultVO delivery(PurApplyDeliveryReq req);

    /**
     * 采购单确认收货
     *
     * @param req
     * @return
     */
    ResultVO receiving(PurApplyReceivingReq req);

    /**
     * 修改采购申请单状态
     *
     * @param req
     * @return
     */
    ResultVO updateNoticeStatus(PurApplyReq req);
}
