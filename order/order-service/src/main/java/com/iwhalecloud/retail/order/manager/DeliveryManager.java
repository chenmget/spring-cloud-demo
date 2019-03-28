package com.iwhalecloud.retail.order.manager;

import com.iwhalecloud.retail.order.dto.model.DeliveryModel;
import com.iwhalecloud.retail.order.dto.resquest.SendGoodsRequest;
import com.iwhalecloud.retail.order.mapper.DeliveryMapper;
import com.iwhalecloud.retail.order.model.DeliverEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DeliveryManager {

    @Resource
    private DeliveryMapper deliveryMapper;


    public int saveDelivery(DeliverEntity dto) {
        return deliveryMapper.saveDelivery(dto);
    }

    public List<DeliveryModel> selectDeliverByOrderId(String orderID) {
        return deliveryMapper.selectDeliverByOrderId(orderID);
    }

    public int updateDeliveryInfo(SendGoodsRequest request){

        return  deliveryMapper.updateDeliveryInfo(request);
    }
}
