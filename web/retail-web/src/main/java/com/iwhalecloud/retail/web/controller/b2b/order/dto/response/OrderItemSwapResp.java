package com.iwhalecloud.retail.web.controller.b2b.order.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class OrderItemSwapResp extends OrderItemDTO {

    private List<PromotionDTO> promotionList;

    @ApiModelProperty("默认0，1:不可以退货")
    private String isRetrun;

}
