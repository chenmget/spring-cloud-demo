package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResNbrResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.THReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.THSendGoodsRequest;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;

public interface AfterSalesTHService {

    /**
     * 校验
     */
    CommonResultResp check(OrderApplyReq req);

    /**
     * 保存数据
     */
     CommonResultResp builderOrderApply(OrderApplyReq req, OrderApply orderApply);

    /**
     * 买家退货
     */
    CommonResultResp returnGoods(THSendGoodsRequest request);

    /**
     * 卖家收货
     */
    CommonResultResp receiveGoods(THReceiveGoodsReq req);

    /**
     *  查询退货要收货串码
     */
    CommonResultResp<AfterSaleResNbrResp> selectReceiveResNbr(SelectAfterSalesReq req);


    SendGoodsItemDTO selectResnbrStock(OrderItemDetailModel orderItemDetailModel);


}
