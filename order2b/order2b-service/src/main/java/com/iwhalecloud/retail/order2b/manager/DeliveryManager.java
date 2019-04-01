package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.config.TelDBDefValueConfig;
import com.iwhalecloud.retail.order2b.dto.model.order.SettleRecordOrderDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.DeliveryReq;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.AddActSupRecordReq;
import com.iwhalecloud.retail.order2b.entity.Delivery;
import com.iwhalecloud.retail.order2b.mapper.DeliveryMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryManager {

    @Resource
    private DeliveryMapper deliveryMapper;

    public int insertInto(Delivery delivery) {
        return deliveryMapper.insert(delivery);
    }

    public Delivery selectLastByOrderId(String orderId) {
        Delivery delivery=new Delivery();
        delivery.setOrderId(orderId);
        return deliveryMapper.selectLastByOrderId(delivery);
    }

    public Delivery selectLastByOrderApplyId(String orderApplyId) {
        Delivery delivery=new Delivery();
        delivery.setOrderApplyId(orderApplyId);
        return deliveryMapper.selectLastByOrderApplyId(delivery);
    }

    public List<Delivery> selectDeliveryListByOrderId(Delivery orderID) {
        return deliveryMapper.selectDeliveryListByOrderId(orderID);
    }

    public List<Delivery> selectDeliveryListByOrderIdAndBatchId(DeliveryReq deliveryReq) {
        return deliveryMapper.selectDeliveryListByOrderIdAndBatchId(deliveryReq);
    }


    public List<SettleRecordOrderDTO> getSettleRecordOrder(@Param("orderItemIds")List<String> orderItemIds,@Param("lanId")String lanId){
        if(!CollectionUtils.isEmpty(orderItemIds)){
            return deliveryMapper.getSettleRecordOrder(orderItemIds,lanId);
        }
        return new ArrayList<>();
    }
}
