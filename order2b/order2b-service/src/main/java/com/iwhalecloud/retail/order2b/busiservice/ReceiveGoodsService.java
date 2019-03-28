package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;

public interface ReceiveGoodsService {

    /**
     * 收货校验
     */
    CommonResultResp receiveGoodsCheck(ReceiveGoodsReq req);

    /**
     * 入库
     */
    CommonResultResp inResource(ReceiveGoodsReq request);

    /**
     * 收货校验
     */
    CommonResultResp receiveGoods(ReceiveGoodsReq req);

    /**
     * 完成
     */
    CommonResultResp receiveGoodsFinish(ReceiveGoodsReq request);

}
