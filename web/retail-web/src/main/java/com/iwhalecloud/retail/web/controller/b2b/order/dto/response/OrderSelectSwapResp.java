package com.iwhalecloud.retail.web.controller.b2b.order.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import lombok.Data;

import java.util.List;

/**
 * OrderSelectResp类转换
 */
@Data
public class OrderSelectSwapResp extends OrderDTO {

    private List<OrderItemSwapResp> orderItems;




}
