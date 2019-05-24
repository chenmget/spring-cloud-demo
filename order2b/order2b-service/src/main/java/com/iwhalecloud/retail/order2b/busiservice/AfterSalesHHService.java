package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResNbrResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.model.HHReceiveGoodsModel;

public interface AfterSalesHHService {

    /**
     * 换货发货校验
     */
    CommonResultResp sellerCheckDeliverGoods(SendGoodsRequest request);

    /**
     * 商家发货
     */
    CommonResultResp sellerDeliverGoods(SendGoodsRequest request);



    /**
     * 买家换货完成（确认收货）
     */
    CommonResultResp userReceiveGoods(ReceiveGoodsReq request);

    /**
     * 换货完成
     */
    CommonResultResp hhFinish(ReceiveGoodsReq req);

    /**
     * 收货中，是否有需要换货的串码
     */
    HHReceiveGoodsModel hhResNbrNumList(ReceiveGoodsReq req);

    /**
     *  查询退货要收货串码
     */
    CommonResultResp<AfterSaleResNbrResp> selectReceiveResNbr(SelectAfterSalesReq req);


}
