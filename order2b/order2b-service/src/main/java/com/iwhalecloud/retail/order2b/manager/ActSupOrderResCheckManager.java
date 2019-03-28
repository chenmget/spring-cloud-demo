package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.AddActSupRecordReq;
import com.iwhalecloud.retail.order2b.entity.Delivery;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.mapper.DeliveryMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderItemDetailMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.mapper.PromotionMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhou.zc
 * @date 2019年03月22日
 * @Description:前置补贴活动补录记录订单串码校验（非分片查询）
 */
@Component
public class ActSupOrderResCheckManager {

    @Resource
    private DeliveryMapper deliveryMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemDetailMapper orderItemDetailMapper;

    @Resource
    private PromotionMapper promotionMapper;

    /**
     * 校验订单的发货时间
     * @param addActSupRecordReq
     * @return
     */
    public Integer orderShippingTimeCheck(AddActSupRecordReq addActSupRecordReq, Integer batchId){
        QueryWrapper<Delivery> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",addActSupRecordReq.getOrderId());
        queryWrapper.eq("batch_id",batchId);
        queryWrapper.gt("create_time",addActSupRecordReq.getEndTime());
        return deliveryMapper.selectCount(queryWrapper);
    }

    /**
     * 校验订单，串码符合前置补贴补录
     * @param addActSupRecordReq
     * @return
     */
    public OrderItemDetail orderNesCheck(AddActSupRecordReq addActSupRecordReq){
        QueryWrapper<OrderItemDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",addActSupRecordReq.getOrderId());
        queryWrapper.eq("res_nbr",addActSupRecordReq.getResNbr());
        queryWrapper.in("state", Lists.newArrayList(OrderAllStatus.ORDER_STATUS_5.getCode(),OrderAllStatus.ORDER_STATUS_6.getCode()));
        return orderItemDetailMapper.selectOne(queryWrapper);
    }

    /**
     * 校验订单下单时间在前置补贴活动有效期内
     * @param addActSupRecordReq
     * @return
     */
    public Integer orderCreateDateCheck(AddActSupRecordReq addActSupRecordReq){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",addActSupRecordReq.getOrderId());
        queryWrapper.ge("create_time",addActSupRecordReq.getStartTime());
        queryWrapper.le("create_time",addActSupRecordReq.getEndTime());
        return orderMapper.selectCount(queryWrapper);
    }

    /**
     * 校验订单id是否参与该活动
     * @param addActSupRecordReq
     * @return
     */
    public Integer orderActivityCheck(AddActSupRecordReq addActSupRecordReq){
        QueryWrapper<Promotion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",addActSupRecordReq.getOrderId());
        queryWrapper.eq("mkt_act_id",addActSupRecordReq.getMarketingActivityId());
        return promotionMapper.selectCount(queryWrapper);
    }
}
