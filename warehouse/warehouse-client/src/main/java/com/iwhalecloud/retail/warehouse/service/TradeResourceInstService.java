package com.iwhalecloud.retail.warehouse.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.TradeResourceInstReq;

public interface TradeResourceInstService {



    /**
     * 采购发货 串码出库
     * @param req
     * @return
     */
    ResultVO tradeOutResourceInst(TradeResourceInstReq req);

    /**
     * 采购收货确认 串码入库
     *
     * @param req
     * @return
     */
    ResultVO tradeInResourceInst(TradeResourceInstReq req);

}