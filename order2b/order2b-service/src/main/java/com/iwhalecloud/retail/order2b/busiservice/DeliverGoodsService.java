package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;
import com.iwhalecloud.retail.warehouse.dto.request.DeliveryValidResourceInstReq;

public interface DeliverGoodsService {


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

    /**
     * 串码有效性
     */
    ResultVO resNbrValidity(DeliveryValidResourceInstReq req);

    /**
     * 串码有效性并把校验结果赋值到请求参数
     */
    ResultVO valieNbr(SendGoodsRequest request);

}
