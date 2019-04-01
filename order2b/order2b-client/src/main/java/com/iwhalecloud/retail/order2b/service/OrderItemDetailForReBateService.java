package com.iwhalecloud.retail.order2b.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.response.ReBateOrderInDetailResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.ReBateOrderInDetailReq;

import java.util.List;

/**
 * @author lhr 2019-03-30 15:32:30
 */
public interface OrderItemDetailForReBateService {

    /**
     * 返利活动订单收入明细
     * @param req
     * @return
     */
    ResultVO<Page<ReBateOrderInDetailResp>> queryOrderItemDetailDtoByOrderId(ReBateOrderInDetailReq req);

}
