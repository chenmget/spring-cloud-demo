package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.DeliveryDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AfterSaleResNbrResp extends AfterSaleResp implements Serializable {

    List<OrderItemDetailDTO> resNbrs;

    DeliveryDTO deliveryInfo;
}
