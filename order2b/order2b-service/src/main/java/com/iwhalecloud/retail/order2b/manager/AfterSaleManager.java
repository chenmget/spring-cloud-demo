package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyGetReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsRawReq;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.entity.OrderApplyDetail;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.mapper.OrderApplyDetailMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderApplyMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderItemMapper;
import com.iwhalecloud.retail.order2b.model.AfterSalesModel;
import com.iwhalecloud.retail.order2b.model.CloseOrderApplyModel;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;
import com.iwhalecloud.retail.order2b.model.SelectAfterModel;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AfterSaleManager {


    @Resource
    private OrderApplyMapper orderApplyMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderApplyDetailMapper orderApplyDetailMapper;

    public  OrderApply getLastOrderApplyByOrderId(String orderId){
        OrderApply orderApply=new OrderApply();
        orderApply.setOrderId(orderId);
        return orderApplyMapper.getLastOrderApplyByOrderId(orderApply);
    }
    public int insertInto(OrderApply orderApply){
       return orderApplyMapper.insert(orderApply);
    }

    public OrderApply selectOrderApplyById(String applyId){
        OrderApply orderApply=new OrderApply();
        orderApply.setOrderApplyId(applyId);
        return orderApplyMapper.selectOrderApplyById(orderApply);
    }

    public int updateApplyState(OrderApply apply){
        return orderApplyMapper.updateApplyState(apply);
    }

    public IPage<AfterSalesModel> selectAfterSales(SelectAfterModel afterModel){
        Page page=new Page();
        page.setDesc("create_time");
        page.setSize(afterModel.getPageSize());
        page.setCurrent(afterModel.getPageNo());

        IPage<AfterSalesModel> list= orderApplyMapper.selectAfterSales(page,afterModel);
        if(CollectionUtils.isEmpty(list.getRecords())){
            return list;
        }
        for (AfterSalesModel model:list.getRecords()) {
            OrderItem orderItem=new OrderItem();
            orderItem.setItemId(model.getOrderItemId());
            model.setOrderItems(orderItemMapper.selectOrderItemByItemId(orderItem));
        }
        return list;
    }

    public int insertIntoDetail(List<OrderApplyDetail> list){
        return orderApplyDetailMapper.insertByList(list);
    }

    public int updateResNbrByApplyId(String applyId,String state){
        OrderApplyDetail applyDetail=new OrderApplyDetail();
        applyDetail.setOrderApplyId(applyId);
        applyDetail.setState(state);
        return orderApplyDetailMapper.updateResNbr(applyDetail);
    }

    public List<OrderApplyDetail> selectOrderItem(OrderItemDetailModel model){
        return orderApplyDetailMapper.selectOrderItem(model);
    }

    public int getApplyOrderCountByCondition(OrderStatisticsRawReq req){
        return orderApplyMapper.getApplyOrderCountByCondition(req);
    }

    public int selectAllSubmit(String orderId){
        OrderApply orderApply=new OrderApply();
        orderApply.setOrderId(orderId);
        return orderApplyMapper.selectAllSubmit(orderApply);
    }

    /**
     * 查询关闭订单申请的信息
     *
     * @param req 查询入参
     * @return
     */
    public List<CloseOrderApplyModel> queryCloseOrderApply(OrderApplyGetReq req) {
        return orderApplyMapper.queryCloseOrderApply(req);
    }

    /**
     * 根据条件更新申请单的状态
     *
     * @param orderApply orderApply
     * @return
     */
    public int updateOrderApplyStateByCondition(OrderApply orderApply) {
        return orderApplyMapper.updateOrderApplyStateByCondition(orderApply);
    }

}
