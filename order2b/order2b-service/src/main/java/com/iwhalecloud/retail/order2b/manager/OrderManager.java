package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.AddActSupRecordReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsRawReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.mapper.OrderItemDetailMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderItemMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


@Component
public class OrderManager {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderItemDetailMapper orderItemDetailMapper;


    public int saveOrder(Order dto) {
        return orderMapper.insert(dto);
    }

    public int saveOrderItem(List<OrderItem> dto) {
        return orderItemMapper.saveOrderItem(dto);
    }

    public Order getOrderById(String orderId) {
        Order order=new Order();
        order.setOrderId(orderId);
        return orderMapper.getOrderById(order);
    }

    public OrderItem getOrderItemById(String itemId) {
        OrderItem orderItem=new OrderItem();
        orderItem.setItemId(itemId);
        return orderItemMapper.selectOrderItemByItemId(orderItem);
    }

    public List<OrderItemInfoModel> getOrderItemInfoListById(OrderItem orderItem) {
        List<OrderItemInfoModel> list=orderItemMapper.selectOrderItemInfoListById(orderItem);
        if(!CollectionUtils.isEmpty(list)){
            for (OrderItemInfoModel infoModel:list){
                OrderItemDetailModel model=new OrderItemDetailModel();
                model.setItemId(infoModel.getItemId());
                model.setState(OrderAllStatus.ORDER_STATUS_5.getCode());
                infoModel.setDetailList(orderItemDetailMapper.selectOrderItemDetail(model));
            }

        }
        return list;
    }




    public List<OrderItem> selectOrderItemsList(String order) {
        OrderItem orderItem=new OrderItem();
        orderItem.setOrderId(order);
        List<OrderItem> orderItems = orderItemMapper.selectOrderItem(orderItem);
        return orderItems;
    }

    /**
     * 查询订单（管理员）
     */
    public IPage<OrderInfoModel> selectOrderListByOrder(SelectOrderDetailModel req) {
        Page page = new Page();
        page.setSize(req.getPageSize());
        page.setCurrent(req.getPageNo());
        page.setDesc("create_time");
        IPage<OrderInfoModel> list = orderMapper.selectOrderListByOrder(page, req);
        if(CollectionUtils.isEmpty(list.getRecords())){
            return list;
        }
        for (OrderInfoModel model:list.getRecords()){
            OrderItem orderItem=new OrderItem();
            orderItem.setOrderId(model.getOrderId());
            model.setOrderItems(orderItemMapper.selectOrderItem(orderItem));
        }
        return list;
    }

    /**
     * 更新订单属性
     */
    public int updateOrderAttr(OrderUpdateAttrModel orderUpdateAttrModel) {
        return orderMapper.updateOrderAttr(orderUpdateAttrModel);
    }

    public int getOrderCountByCondition(OrderStatisticsRawReq req) {
        return orderMapper.getOrderCountByCondition(req);
    }

    public double getOrderAmountByCondition(OrderStatisticsRawReq req) {
        Double doub=orderMapper.getOrderAmountByCondition(req);
        if(doub == null){
            return 0.0;
        }
        return doub;
    }

    /**
     * 更新订单项属性
     */
    public int updateOrderItemByItemId(OrderItem item) {
        return orderItemMapper.updateOrderItemByItemId(item);
    }


    public int insertOrderItemDetailByList(List<OrderItemDetail> list) {
        return orderItemDetailMapper.insertByList(list);
    }


    public List<String> selectOrderIdByresNbr(String resNbr) {
        OrderItemDetail orderItemDetail=new OrderItemDetail();
        orderItemDetail.setResNbr(resNbr);
        return orderItemMapper.selectOrderIdByresNbr(orderItemDetail);
    }

    public int updateResNbr(OrderItemDetailModel list){
        return  orderItemDetailMapper.updateResNbr(list);
    }

    public List<String> selectResNbrListByIds(List<String> ids){
        OrderItemDetailModel model=new OrderItemDetailModel();
        model.setDetailList(ids);
       return orderItemDetailMapper.selectResNbrListByIds(model);
    }

    public List<OrderItemDetail> selectOrderItemDetail(OrderItemDetailModel detail){
        return orderItemDetailMapper.selectOrderItemDetail(detail);
    }

    //查询订单项
    public List<OrderItem> selectOrderItem(OrderItem item){
        return orderItemMapper.selectOrderItem(item);
    }

}
