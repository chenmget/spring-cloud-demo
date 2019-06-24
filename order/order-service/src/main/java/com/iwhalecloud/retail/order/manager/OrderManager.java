package com.iwhalecloud.retail.order.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order.dto.model.OrderItemModel;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.entity.Order;
import com.iwhalecloud.retail.order.mapper.OrderItemMapper;
import com.iwhalecloud.retail.order.mapper.OrderMapper;
import com.iwhalecloud.retail.order.model.OrderEntity;
import com.iwhalecloud.retail.order.model.OrderItemEntity;
import com.iwhalecloud.retail.order.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Component
public class OrderManager {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Value("${fdfs.show.url}")
    private String showUrl;


    public Order getOrder(String id) {
        return orderMapper.selectById(id);
    }

    public int addOrder(Order order) {
        return orderMapper.insert(order);
    }


    public int saveOrder(OrderEntity dto) {
        return orderMapper.saveOrder(dto);
    }

    public int saveOrderItem(List<OrderItemEntity> dto) {
        return orderItemMapper.saveOrderItem(dto);
    }

    public OrderModel selectOrder(String orderId) {
        return orderMapper.getOrderById(orderId);
    }

    public List<OrderModel> selectOrderByIds(List<String> orders) {
        return orderMapper.selectOrderListById(orders);
    }

    public List<OrderItemModel> selectOrderItemsList(List<String> orders) {
        List<OrderItemModel> list=orderItemMapper.selectOrderItemByOrderIds(orders);
        return list;
    }

    public List<OrderItemModel> selectOrderItemsList(String orders) {
        List<OrderItemModel> list=orderItemMapper.selectOrderItemByOrderId(orders);
        for (OrderItemModel model:list){
            if(!StringUtils.isEmpty(model.getImage())){
                model.setImage(Utils.attacheUrlPrefix(showUrl,model.getImage()));
            }
        }
        return list;
    }

    public IPage<OrderModel> selectMemberOrderList(SelectOrderRequest requestDTO){
        Page<OrderModel> page=new Page<>();
        page.setSize(requestDTO.getPageSize());
        page.setCurrent(requestDTO.getPageNo());
        IPage<OrderModel> pageList= orderMapper.selectMemberOrderList(page,requestDTO);
        return pageList;
    }
    public IPage<OrderModel> selectManagerOrderList(SelectOrderRequest requestDTO){
        Page<OrderModel> page=new Page<>();
        page.setSize(requestDTO.getPageSize());
        page.setCurrent(requestDTO.getPageNo());
        IPage<OrderModel> pageList= orderMapper.selectManagerOrderList(page,requestDTO);
        return pageList;
    }
    
    /**
     * 订单表写入揽装录入ID
     * @param orderId
     * @param recommendIds
     * @return
     */
    public Integer updateRecommdId(String orderId, String recommendIds){
    	return orderMapper.updateRecommdId(orderId, recommendIds);
    }

}
