package com.iwhalecloud.retail.order2b.busiservice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.model.*;

import java.util.List;

public interface SelectOrderService {


    /**
     * 订单查询
     */
    IPage<OrderInfoModel> selectOrderListByOrder(SelectOrderDetailModel req);

    /**
     * 串码
     */
    List<OrderItemInfoModel> selectOrderItemInfoListById(OrderItem orderiD);

    /**
     * 申请单查询
     */
    IPage<AfterSalesModel> selectAfterSale(SelectAfterModel req);

    /**
     * 回滚组装数据
     */
    List<BuilderOrderModel> builderOrderInfoRollback(String orderId,CreateOrderLogModel logModel);

    /**
     * 预售订单查询
     * @param req
     * @return
     */
    IPage<AdvanceOrderInfoModel> queryadvanceOrderList(SelectOrderDetailModel req);

    /**
     * 根据用户查询订单状态
     */
    List<String> getOrderStatusByUser(String userType,String orderType);

}
