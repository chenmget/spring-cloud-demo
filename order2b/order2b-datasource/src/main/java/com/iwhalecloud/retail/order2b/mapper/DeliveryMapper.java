package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.dto.model.order.SettleRecordOrderDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.DeliveryReq;
import com.iwhalecloud.retail.order2b.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeliveryMapper extends BaseMapper<Delivery>{

    Delivery selectLastByOrderId(Delivery orderID);

    Delivery selectLastByOrderApplyId(Delivery orderApplyId);

    List<Delivery> selectDeliveryListByOrderId(Delivery delivery);

    List<Delivery> selectDeliveryListByOrderIdAndBatchId(DeliveryReq deliveryReq);

    List<SettleRecordOrderDTO> getSettleRecordOrder(@Param("ids")List<String> orderIds,@Param("lanId")String lanId);
}
