package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.dto.model.order.AdvanceOrderDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.mapper.AdvanceOrderMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderItemMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.model.AdvanceOrderInfoModel;
import com.iwhalecloud.retail.order2b.model.OrderItemModel;
import com.iwhalecloud.retail.order2b.model.SelectOrderDetailModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class AdvanceOrderManager {

    @Resource
    private AdvanceOrderMapper advanceOrderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderMapper orderMapper;

    public int insertInto(AdvanceOrder advanceOrder){
       return advanceOrderMapper.insert(advanceOrder);
    }

    /**
     * 查询预售订单列表
     * @param req
     * @return
     */
    public IPage<AdvanceOrderInfoModel> queryAdvanceOrderList(SelectOrderDetailModel req) {
        Page page = new Page();
        page.setSize(req.getPageSize());
        page.setCurrent(req.getPageNo());
        page.setDesc("create_time");
        IPage<AdvanceOrderInfoModel> list = orderMapper.queryAdvanceOrderList(page, req);
        if (!CollectionUtils.isEmpty(list.getRecords())) {
            for (AdvanceOrderInfoModel model : list.getRecords()) {
                OrderItemModel orderItem = new OrderItemModel();
                orderItem.setOrderId(model.getOrderId());
                orderItem.setLanIdList(req.getLanIdList());
                model.setOrderItems(orderItemMapper.selectOrderItem(orderItem));
                model.setAdvanceOrder(getAdvanceOrderByOrderId(model.getOrderId()));
            }
        }

        return list;
    }

    /**
     * 按条件查询预售订单信息
     * @param req
     * @return
     */
    public List<AdvanceOrder> queryAdvanceOrderByCondition(SelectOrderDetailModel req) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(req.getOrderList())) {
            queryWrapper.in("ORDER_ID",req.getOrderList());
        }
        if (!Objects.isNull(req.getAdvancePayTimeStart())) {
            queryWrapper.ge("ADVANCE_PAY_TIME", req.getAdvancePayTimeStart());
        }
        if (!Objects.isNull(req.getAdvancePayTimeEnd())) {
            queryWrapper.le("ADVANCE_PAY_TIME", req.getAdvancePayTimeEnd());
        }
        if (!Objects.isNull(req.getRestPayTimeStart())) {
            queryWrapper.ge("REST_PAY_TIME", req.getRestPayTimeStart());
        }
        if (!Objects.isNull(req.getRestPayTimeEnd())) {
            queryWrapper.le("REST_PAY_TIME", req.getRestPayTimeEnd());
        }
        String lanId = Order2bContext.getDubboRequest().getLanId();
        if (!StringUtils.isEmpty(lanId)) {
            queryWrapper.eq("LAN_ID", lanId);
        }
        List<AdvanceOrder> advanceOrders = advanceOrderMapper.selectList(queryWrapper);
        return advanceOrders;
    }

    /**
     * 查询超时未支付的订单
     * @return
     */
    public List<AdvanceOrder> queryOverTimeAdvancePayOrder() {

        List<AdvanceOrder> advanceOrders = advanceOrderMapper.queryOverTimePayOrder();

        return advanceOrders;
    }

    /**
     * 根据订单id查询预售订单
     * @param orderId
     * @return
     */
    public AdvanceOrder getAdvanceOrderByOrderId(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", orderId);
        String lanId = Order2bContext.getDubboRequest().getLanId();
        if (!StringUtils.isEmpty(lanId)) {
            queryWrapper.eq("LAN_ID", lanId);
        }
        return advanceOrderMapper.selectOne(queryWrapper);
    }

    /**
     * 更新预售订单信息
     * @param advanceOrderDTO
     * @return
     */
    public int updateAdvanceOrderAttr(AdvanceOrderDTO advanceOrderDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ORDER_ID", advanceOrderDTO.getOrderId());
        String lanId = Order2bContext.getDubboRequest().getLanId();
        if (!StringUtils.isEmpty(lanId)) {
            queryWrapper.eq("LAN_ID", lanId);
        }
        AdvanceOrder advanceOrder = new AdvanceOrder();
        BeanUtils.copyProperties(advanceOrderDTO,advanceOrder);
        return advanceOrderMapper.update(advanceOrder,queryWrapper);
    }
}
