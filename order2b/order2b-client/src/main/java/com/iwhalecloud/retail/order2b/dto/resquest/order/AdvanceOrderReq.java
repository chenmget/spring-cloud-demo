package com.iwhalecloud.retail.order2b.dto.resquest.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 预售订单请求体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月04日
 */
@Data
public class AdvanceOrderReq extends SelectOrderReq implements Serializable{

    @ApiModelProperty("支付尾款开始时间")
    private String restPayTimeStart;

    @ApiModelProperty("支付尾款结束时间")
    private String restPayTimeEnd;

    @ApiModelProperty("支付定金开始时间")
    private String advancePayTimeStart;

    @ApiModelProperty("支付定金结束时间")
    private String advancePayTimeEnd;
    private String merchantId;



}
