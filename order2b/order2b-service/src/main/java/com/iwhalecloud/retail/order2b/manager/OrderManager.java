package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.response.FtpOrderDataResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.FtpOrderDataReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsRawReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.mapper.OrderItemDetailMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderItemMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.model.*;
import com.iwhalecloud.retail.order2b.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        Order order = new Order();
        order.setOrderId(orderId);
        return orderMapper.getOrderById(order);
    }

    public OrderItem getOrderItemById(String itemId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(itemId);
        return orderItemMapper.selectOrderItemByItemId(orderItem);
    }

    public List<OrderItemInfoModel> getOrderItemInfoListById(OrderItem orderItem) {
        List<OrderItemInfoModel> list = orderItemMapper.selectOrderItemInfoListById(orderItem);
        if (!CollectionUtils.isEmpty(list)) {
            for (OrderItemInfoModel infoModel : list) {
                OrderItemDetailModel model = new OrderItemDetailModel();
                model.setItemId(infoModel.getItemId());
                model.setState(OrderAllStatus.ORDER_STATUS_5.getCode());
                infoModel.setDetailList(orderItemDetailMapper.selectOrderItemDetail(model));
            }

        }
        return list;
    }


    public List<OrderItem> selectOrderItemsList(String order) {
        OrderItemModel orderItem = new OrderItemModel();
        orderItem.setOrderId(order);
        List<OrderItem> orderItems = orderItemMapper.selectOrderItem(orderItem);
        return orderItems;
    }

    @Value("${fdfs.show.url}")
    private String showUrl;


    /**
     * 查询订单,订单项
     */
    public IPage<OrderInfoModel> selectOrderListByOrder(SelectOrderDetailModel req) {
        Page page = new Page();
        page.setSize(req.getPageSize());
        page.setCurrent(req.getPageNo());
        page.setDesc("create_time");
        IPage<OrderInfoModel> list = orderMapper.selectOrderListByOrder(page, req);
        if (CollectionUtils.isEmpty(list.getRecords())) {
            return list;
        }
        List<String> orderIds = new ArrayList<>(list.getRecords().size());
        for (OrderInfoModel model : list.getRecords()) {
            orderIds.add(model.getOrderId());
        }
        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setOrderIdList(orderIds);
        orderItemModel.setLanIdList(req.getLanIdList());
        List<OrderItem> orderItems = orderItemMapper.selectOrderItem(orderItemModel);
        Map<String, List<OrderItem>> orderItemMaps = orderItems.stream().collect(Collectors.groupingBy(OrderItem::getOrderId));

        for (OrderInfoModel model : list.getRecords()) {
            model.setOrderItems(orderItemMaps.get(model.getOrderId()));
            int receiveNum = 0;
            int deliveryNum = 0;
            for (OrderItem orderItem : model.getOrderItems()) {
                if (orderItem.getReceiveNum() != null) {
                    receiveNum += orderItem.getReceiveNum();
                }
                if (orderItem.getDeliveryNum() != null) {
                    deliveryNum += orderItem.getDeliveryNum();
                }
                if (!org.springframework.util.StringUtils.isEmpty(orderItem.getImage())) {
                    orderItem.setImage(Utils.attacheUrlPrefix(showUrl, orderItem.getImage()));
                }
            }
            model.setReceiveNum(receiveNum);
            model.setDeliveryNum(deliveryNum);
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
        Double doub = orderMapper.getOrderAmountByCondition(req);
        if (doub == null) {
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


    public List<String> selectOrderIdByresNbr(OrderItemDetailModel orderItemDetail) {
        return orderItemDetailMapper.selectOrderIdByresNbr(orderItemDetail);
    }

    public int updateResNbr(OrderItemDetailModel list) {
        return orderItemDetailMapper.updateResNbr(list);
    }

    public List<String> selectResNbrListByIds(List<String> ids) {
        OrderItemDetailModel model = new OrderItemDetailModel();
        model.setDetailList(ids);
        return orderItemDetailMapper.selectResNbrListByIds(model);
    }

    public List<OrderItemDetail> selectOrderItemDetail(OrderItemDetailModel detail) {
        return orderItemDetailMapper.selectOrderItemDetail(detail);
    }

    //查询订单项
    public List<OrderItem> selectOrderItem(OrderItemModel item) {
        return orderItemMapper.selectOrderItem(item);
    }

    public Page<FtpOrderDataResp> queryFtpOrderDataRespList(FtpOrderDataReq req){
        Page<FtpOrderDataResp> page = new Page<FtpOrderDataResp>(req.getPageNo(), req.getPageSize());
        page.setSearchCount(false);
        return orderMapper.queryFtpOrderDataRespList(page,req);
    }
    public String getFstTransDate(){
        return orderMapper.getFstTransDate();
    }
    public int queryFtpOrderDataRespListCount(FtpOrderDataReq req){
        return orderMapper.queryFtpOrderDataRespListCount(req);
    }
    /**
     * 根据orderId查询未全部发货订单
     * @param orderIds
     * @return
     */
    public List<OrderInfoModel> selectNotDeliveryOrderByIds(List<String> orderIds) {
        return orderMapper.selectNotDeliveryOrderByIds(orderIds);

    }

}
