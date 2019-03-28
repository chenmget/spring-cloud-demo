package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderSelectResp extends OrderDTO implements Serializable {

    @ApiModelProperty("订单项信息")
    List<OrderItemDTO> orderItems;

    private List<PromotionDTO> promotionList;


}
