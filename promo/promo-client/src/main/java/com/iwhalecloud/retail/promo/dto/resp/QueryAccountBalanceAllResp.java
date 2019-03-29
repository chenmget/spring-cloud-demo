package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/27 16:40
 */
@Data
public class QueryAccountBalanceAllResp implements Serializable {
    @ApiModelProperty(value = "账户ID")
    private String acctId;
    @ApiModelProperty(value = "账本ID")
    private String accountBalanceId;
    @ApiModelProperty(value = "商家ID")
    private String custId;
    @ApiModelProperty(value = "余额类型标识")
    private String balanceTypeId;
    @ApiModelProperty(value = "余额类型名称")
    private String balanceTypeName;
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "可用余额")
    private String amount;
    @ApiModelProperty(value = "未生效金额")
    private String uneffectiveAmount;
    @ApiModelProperty(value = "失效金额")
    private String invaildAmount;
    @ApiModelProperty(value = "总金额")
    private String totalAmount;
    @ApiModelProperty(value = "限额类型")
    private String cycleType;
    @ApiModelProperty(value = "使用上限金额")
    private String cycleUpper;
    @ApiModelProperty(value = "使用下限金额")
    private String cycleLower;
    @ApiModelProperty(value = "生效时间")
    private String effDate;
    @ApiModelProperty(value = "失效时间")
    private String expDate;

}
