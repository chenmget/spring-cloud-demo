package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.CloseOrderApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CloseOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyGetReq;

/**
 * 关闭订单
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月25日
 */
public interface CloseOrderOpenService {

    /**
     * 申请关闭订单
     *
     * @param req
     * @return
     */
    ResultVO applyCloseOrder(CloseOrderReq req);

    /**
     * 查询关闭订单申请信息
     *
     * @param req 请求入参
     * @return
     */
    ResultVO<CloseOrderApplyResp> queryCloseOrderApply(OrderApplyGetReq req);

    /**
     * 买家同意关闭订单
     * @param req
     * @return
     */
    ResultVO agreeClose(CloseOrderReq req);

    /**
     * 买家驳回关闭订单
     * @param req
     * @return
     */
    ResultVO disagreeClose(CloseOrderReq req);

}
