package com.iwhalecloud.retail.order.ropservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;

import java.util.List;

public interface BuilderOrderManagerService {

    /**
     * 本地创建订单
     */
    CommonResultResp builderOrderLocal(BuilderOrderRequest request);

    /**
     * 订单生成，初始化状态
     */
    CommonResultResp orderInitInfo(List<OrderUpdateAttrEntity> orderInitList,BuilderOrderRequest request);

    /**
     * 订单失败，订单回滚到异常订单
     */
    void orderException(List<String> order,BuilderOrderRequest request);

    /**
     * 下单成功之后，更新库存
     */
    CommonResultResp orderSuccess(List<String> order,BuilderOrderRequest request);


}
