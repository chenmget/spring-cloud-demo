package com.iwhalecloud.retail.order2b.dto.model.order;


import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public  class OrderApplyDTO extends AfterSaleResp implements Serializable {

    @ApiModelProperty("订单状态")
    private String orderStatus;

    private String orderStatusName;

    @ApiModelProperty("下单时间")
    private String orderCreateTime;

    private String userName;

    public String getOrderCreateTime() {
        return timeFormat(orderCreateTime);
    }

    public String getOrderStatusName() {
        return OrderAllStatus.matchOpCode(orderStatusName).getName();
    }
}
