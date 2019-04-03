package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lhr 2019-04-01 09:36:30
 */
@Data
public class OrderItemDetailReBateResp {

    /**
     * 串码
     */
    @ApiModelProperty(value = "串码")
    private String resNbr;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    /**
     * 订单号Id
     */
    @ApiModelProperty(value = "订单号Id")
    private String orderId;

    /**
     * 订单时间
     */
    @ApiModelProperty(value = "订单时间")
    private Date updateTime;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "产品Id")
    private String productId;

    /**
     * 订单项Id
     */
    @ApiModelProperty(value = "订单项Id")
    private String itemId;

    /**
     * 买家Id
     */
    @ApiModelProperty(value = "买家Id")
    private String merchantId;

    /**
     * 收货时间
     */
    @ApiModelProperty(value = "收货时间")
    private Date receiveTime;
}
