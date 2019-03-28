package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;

public interface DeliverGoodsService {

    /**
     *
     */
    CommonResultResp<OrderInfoModel> orderCheck(SendGoodsRequest request);

    /**
     * 串码有效性
     */
    CommonResultResp resNbrValidity(SendGoodsRequest request);

    /**
     * 出库
     */
    CommonResultResp nbrOutResource(SendGoodsRequest request);

    /**
     * 发货记录
     */
    CommonResultResp SendGoodsRecord(SendGoodsRequest request, OrderInfoModel orderInfoModel);

    /**
     * 完成
     */
    CommonResultResp sendGoodsFinish(SendGoodsRequest request);



}
