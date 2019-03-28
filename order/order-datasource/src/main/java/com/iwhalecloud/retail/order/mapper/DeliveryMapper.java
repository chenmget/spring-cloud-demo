package com.iwhalecloud.retail.order.mapper;

import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order.dto.model.DeliveryModel;
import com.iwhalecloud.retail.order.dto.resquest.SendGoodsRequest;
import com.iwhalecloud.retail.order.model.DeliverEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryMapper {



    @WhaleCloudDBKeySequence
    int saveDelivery(DeliverEntity deliverEntityDTO);

    List<DeliveryModel> selectDeliverByOrderId(String orderID);

    int updateDeliveryInfo(SendGoodsRequest request);
}
