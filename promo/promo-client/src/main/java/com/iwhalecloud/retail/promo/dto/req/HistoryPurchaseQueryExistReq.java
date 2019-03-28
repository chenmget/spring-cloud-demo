package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月14日
 * @Description: 根据orderId和productId查询是否参与补贴入参
 **/
@Data
@ApiModel(value = "根据orderId和productId查询是否参与补贴入参")
public class HistoryPurchaseQueryExistReq implements Serializable {

    private static final long serialVersionUID = -9180387918024208772L;
    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private java.lang.String orderId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private java.lang.String productId;
}
