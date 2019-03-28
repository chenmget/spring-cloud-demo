package com.iwhalecloud.retail.order.service;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.order.ApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.SelectApplyOrderRequestDTO;
import com.iwhalecloud.retail.order.dto.resquest.order.UpdateApplyOrderRequestDTO;

public interface AfterSaleOrderOpenService {

    /**
     * 添加退货、换货申请单
     */
    CommonResultResp addOrderApply(ApplyOrderRequestDTO request);

    /**
     * 查询售后单
     */
    CommonResultResp selectApplyOrder(SelectApplyOrderRequestDTO request);



    /**
     *  退货处理
     */
    CommonResultResp updateTHApplyOrder(UpdateApplyOrderRequestDTO request);

    /**
     *  换货处理
     */
    CommonResultResp updateHHApplyOrder(UpdateApplyOrderRequestDTO request);
}
