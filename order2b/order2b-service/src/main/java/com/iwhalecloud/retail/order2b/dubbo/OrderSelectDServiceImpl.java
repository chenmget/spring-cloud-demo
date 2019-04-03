package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.*;
import com.iwhalecloud.retail.order2b.dto.response.*;
import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectNbrReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectOrderReq;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class OrderSelectDServiceImpl implements OrderSelectOpenService {

    @Autowired
    private OrderSelectOpenService orderSelectService;
    @Override
    public ResultVO<IPage<OrderSelectResp>> purchaseOrderList(SelectOrderReq req) {
        return orderSelectService.purchaseOrderList(req);
    }

    @Override
    public ResultVO<IPage<OrderSelectResp>> managerOrderList(SelectOrderReq req) {
        return orderSelectService.managerOrderList(req);
    }

    @Override
    public ResultVO<IPage<OrderSelectResp>> salesOrderList(SelectOrderReq req) {
        return orderSelectService.salesOrderList(req);
    }

    @Override
    public ResultVO<OrderSelectDetailResp> orderDetail(SelectOrderReq req) {
        return orderSelectService.orderDetail(req);
    }

    @Override
    public ResultVO<CommonListResp<OrderItemDetailListDTO>> selectOrderItemDetail(SelectOrderReq req) {
        return orderSelectService.selectOrderItemDetail(req);
    }

    @Override
    public ResultVO<OrderListExportResp> orderExport(AdvanceOrderReq req) {
        return orderSelectService.orderExport(req);
    }

    @Override
    public ResultVO<CommonListResp<OrderItemDetailDTO>> orderItemDetailExport(SelectNbrReq req) {
        return orderSelectService.orderItemDetailExport(req);
    }

    @Override
    public ResultVO<OrderDTO> selectOrderById(String orderId) {
        return orderSelectService.selectOrderById(orderId);
    }

    @Override
    public ResultVO<IPage<AdvanceOrderResp>> queryAdvanceOrderList(AdvanceOrderReq req) {
        return orderSelectService.queryAdvanceOrderList(req);
    }

    /**
     * 根据串码查询订单项详情
     *
     * @param resNbr
     * @return
     */
    @Override
    public ResultVO<OrderItemDetailDTO> selectOrderItemDetailBySn(String resNbr) {
        return orderSelectService.selectOrderItemDetailBySn(resNbr);
    }

    /**
     * 根据订单项Id查询订单项信息
     *
     * @param itemId
     * @return
     */
    @Override
    public OrderItemDTO getOrderItemById(String itemId) {
        return orderSelectService.getOrderItemById(itemId);
    }

    /**
     * 根据orderId、batchId查询订单发货记录
     *
     * @param orderId
     * @param batchId
     * @return
     */
    @Override
    public DeliveryDTO selectDeliveryListByOrderIdAndBatchId(String orderId, String batchId) {
        return orderSelectService.selectDeliveryListByOrderIdAndBatchId(orderId, batchId);
    }

    /**
     * 根据orderId查询未全部发货订单
     * @param orderIds
     * @return
     */
    @Override
    public List<OrderDTO> selectNotDeliveryOrderByIds(List<String> orderIds) {
        return  orderSelectService.selectNotDeliveryOrderByIds(orderIds);
    }
}
