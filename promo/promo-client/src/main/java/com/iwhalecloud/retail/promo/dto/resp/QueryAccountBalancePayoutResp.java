package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴良勇
 * @date 2019/3/30 14:04
 */
@Data
public class QueryAccountBalancePayoutResp  implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "供应商Id")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商账号")
    private String supplierLoginName;
    @ApiModelProperty(value = "使用金额（分）")
    private String amount;

    @ApiModelProperty(value = "当前金额（分）")
    private String balance;
    @ApiModelProperty(value = "使用金额(元)")
    private String amountYuan;

    @ApiModelProperty(value = "当前金额(元)")
    private String balanceYuan;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "产品ID")
    private String productId;
    @ApiModelProperty(value = "产品名称")
    private String productName;


    @ApiModelProperty(value = "规格型号")
    private String specName;

    @ApiModelProperty(value = "供应商地市")
    private String lanId;
    @ApiModelProperty(value = "供应商地市名称")
    private String lanName;

    @ApiModelProperty(value = "供应商区县ID")
    private String regionId;

    @ApiModelProperty(value = "供应商区县名称")
    private String regionName;


    @ApiModelProperty(value = "返利使用时间")
    private String operDate;

    @ApiModelProperty(value = "支出描述")
    private String payoutDesc;
}
