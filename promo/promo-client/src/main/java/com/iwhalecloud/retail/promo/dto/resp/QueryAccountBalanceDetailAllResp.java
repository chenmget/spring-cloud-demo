package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 20:30
 */
@Data
public class QueryAccountBalanceDetailAllResp implements Serializable {


    @ApiModelProperty(value = "账户ID")
    private String acctId;
    @ApiModelProperty(value = "账户名称")
    private String acctName;
    @ApiModelProperty(value = "账户类型")
    private String acctType;
    @ApiModelProperty(value = "账本ID")
    private String accountBalanceId;
    @ApiModelProperty(value = "收入流水")
    private String operIncomeId;

    @ApiModelProperty(value = "操作类型")
    private String operType;

    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商地市")
    private String lanId;
    @ApiModelProperty(value = "供应商地市名称")
    private String lanName;
    @ApiModelProperty(value = "营销活动Id")
    private String actId;
    @ApiModelProperty(value = "营销活动名称")
    private String actName;
    @ApiModelProperty(value = "入账金额")
    private String amount;
    @ApiModelProperty(value = "状态")
    private String statusCd;
    @ApiModelProperty(value = "生效时间")
    private String effDate;
    @ApiModelProperty(value = "失效时间")
    private String expDate;
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "订单项号")
    private String orderItemId;
    @ApiModelProperty(value = "来源描述")
    private String sourceDesc;

    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品型号")
    private String unitType;

    @ApiModelProperty(value = "规格型号")
    private String specName;

    @ApiModelProperty(value = "机型编码")
    private String productSn;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "返利单价")
    private String rewardPrice;
    @ApiModelProperty(value = "支出描述")
    private String payoutDesc;




}
