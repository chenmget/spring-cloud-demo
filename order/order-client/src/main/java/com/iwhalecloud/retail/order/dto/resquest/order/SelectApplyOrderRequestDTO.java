package com.iwhalecloud.retail.order.dto.resquest.order;

import com.iwhalecloud.retail.order.dto.base.SelectBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelectApplyOrderRequestDTO extends SelectBaseRequest implements Serializable {

    @ApiModelProperty(value = "4 退货,3 换货，2 退款")
    private String serviceType;  
    @ApiModelProperty(value = "订单号")
    private String orderId; 
    @ApiModelProperty(value = "申请单号")
    private String applyId; 
    @ApiModelProperty(value = "开始时间")
    String startTime;
    @ApiModelProperty(value = "结束时间")
    String endTime;


}
