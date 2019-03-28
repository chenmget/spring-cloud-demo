package com.iwhalecloud.retail.order2b.dto.resquest.pay;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ToPayReq extends OrderRequest implements Serializable{

    @ApiModelProperty("订单号")
    private String orderId      ;

    @ApiModelProperty("支付金额，单位：分")
    private String orderAmount      ;
    @ApiModelProperty("操作类型 用于区分付费/退费：1001  收费，1002  退费，1003  预付费,  DJZF  定金支付,  WKZF  尾款支付,C  支付")
    private String operationType      ;

}
