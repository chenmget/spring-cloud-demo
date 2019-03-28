package com.iwhalecloud.retail.order2b.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.*;
import com.iwhalecloud.retail.order2b.dto.response.*;
import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectNbrReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectOrderReq;


public interface OrderSelectOpenService {

    /**
     * 采购订单list
     */
    ResultVO<IPage<OrderSelectResp>> purchaseOrderList(SelectOrderReq req);

    /**
     * 管理员
     */
    ResultVO<IPage<OrderSelectResp>> managerOrderList(SelectOrderReq req);

    /**
     * 销售订单list
     */
    ResultVO<IPage<OrderSelectResp>> salesOrderList(SelectOrderReq req);

    /**
     * 订单详情
     */
    ResultVO<OrderSelectDetailResp> orderDetail(SelectOrderReq req);

    /**
     * 查询串码通订单id
     */
    ResultVO<CommonListResp<OrderItemDetailListDTO>> selectOrderItemDetail(SelectOrderReq req);

    /**
     * 导出订单
     */
    ResultVO<OrderListExportResp> orderExport(AdvanceOrderReq req);

    /**
     * 导出串码
     */
    ResultVO<CommonListResp<OrderItemDetailDTO>> orderItemDetailExport(SelectNbrReq req);

    /**
     * 查询订单by orderId
     */
    ResultVO<OrderDTO> selectOrderById(String orderId);

    /**
     * 预售订单查询
     * @param req
     * @return
     */
    ResultVO<IPage<AdvanceOrderResp>> queryAdvanceOrderList(AdvanceOrderReq req);


    /**
     * 根据串码查询订单项详情
     * @param resNbr
     * @return
     */
    ResultVO<OrderItemDetailDTO> selectOrderItemDetailBySn(String resNbr);

    /**
     * 根据订单项Id查询订单项信息
     * @param itemId
     * @return
     */
    OrderItemDTO getOrderItemById(String itemId);

    /**
     * 根据orderId、batchId查询订单发货记录
     * @param orderId
     * @param batchId
     * @return
     */
    DeliveryDTO selectDeliveryListByOrderIdAndBatchId(String orderId, String batchId);

}
