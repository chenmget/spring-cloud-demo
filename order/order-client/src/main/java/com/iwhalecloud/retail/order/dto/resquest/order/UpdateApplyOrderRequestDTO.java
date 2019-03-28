package com.iwhalecloud.retail.order.dto.resquest.order;

import com.iwhalecloud.retail.order.dto.base.ModifyBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateApplyOrderRequestDTO extends ModifyBaseRequest implements Serializable {

    @ApiModelProperty(value = "shth:审核退货单;qrth:退货确认;shhh:审核换货单;qrhh:换货确认")
    private String action;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "申请单号")
    private String applyId; 
    @ApiModelProperty(value = "1同意，2拒绝")
    private String auditingStatus; 


}
