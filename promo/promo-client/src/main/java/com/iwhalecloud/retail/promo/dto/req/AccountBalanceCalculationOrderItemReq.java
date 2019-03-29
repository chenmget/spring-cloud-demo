package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/25 15:05
 */
@Data
public class AccountBalanceCalculationOrderItemReq implements Serializable {
    @NotEmpty(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID")
    private String orderId;
    @NotEmpty(message = "订单项编号不能为空")
    @ApiModelProperty(value = "订单项编号")
    private String orderItemId;
    @NotEmpty(message = "活动ID不能为空")
    @ApiModelProperty(value = "活动ID")
    private String actId;
    @NotEmpty(message = "活动名称不能为空")
    @ApiModelProperty(value = "活动名称")
    private String actName;
    @NotEmpty(message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID")
    private String productId;
    @NotEmpty(message = "参与活动数量不能为空")
    @ApiModelProperty(value = "参与活动数量")
    private String actNum;
    @NotEmpty(message = "卖家ID不能为空")
    @ApiModelProperty(value = "卖家ID")
    private String supplierId;
}
