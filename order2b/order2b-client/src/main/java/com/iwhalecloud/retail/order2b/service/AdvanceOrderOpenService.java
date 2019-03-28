package com.iwhalecloud.retail.order2b.service;

public interface AdvanceOrderOpenService {

    /**
     * 取消超时未支付订单
     */
    void cancelOverTimePayOrder();
}
