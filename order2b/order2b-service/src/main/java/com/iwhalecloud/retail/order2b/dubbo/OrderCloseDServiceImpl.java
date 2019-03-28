package com.iwhalecloud.retail.order2b.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.CloseOrderApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CloseOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyGetReq;
import com.iwhalecloud.retail.order2b.service.CloseOrderOpenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 关闭订单
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月25日
 */
public class OrderCloseDServiceImpl implements CloseOrderOpenService {

    @Autowired
    private CloseOrderOpenService closeOrderOpenService;
    @Override
    public ResultVO applyCloseOrder(CloseOrderReq req) {
        return closeOrderOpenService.applyCloseOrder(req);
    }

    @Override
    public ResultVO<CloseOrderApplyResp> queryCloseOrderApply(OrderApplyGetReq req) {
        return closeOrderOpenService.queryCloseOrderApply(req);
    }

    @Override
    public ResultVO agreeClose(CloseOrderReq req) {
        return closeOrderOpenService.agreeClose(req);
    }

    @Override
    public ResultVO disagreeClose(CloseOrderReq req) {
        return closeOrderOpenService.disagreeClose(req);
    }
}
