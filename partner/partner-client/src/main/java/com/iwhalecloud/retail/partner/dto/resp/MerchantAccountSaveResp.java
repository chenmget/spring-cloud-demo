package com.iwhalecloud.retail.partner.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("新增商家账号出参")
public class MerchantAccountSaveResp implements Serializable {

    private static final long serialVersionUID = -8382180056808576875L;

    //属性 begin
    /**
     * 账号ID
     */
    @ApiModelProperty(value = "账号ID")
    private String accountId;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户
     */
    @ApiModelProperty(value = "账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户")
    private String accountType;

    /**
     * 账户
     */
    @ApiModelProperty(value = "账户")
    private String account;

    /**
     * 是否默认:  1 是   0否
     */
    @ApiModelProperty(value = "是否默认:  1 是   0否")
    private String isDefault;

    /**
     * 状态:   	1 有效、 0失效
     */
    @ApiModelProperty(value = "状态:   	1 有效、 0失效")
    private String state;

    /**
     * 开户银行
     */
    @ApiModelProperty(value = "开户银行")
    private String bank;

    /**
     * 银行账户
     */
    @ApiModelProperty(value = "银行账户")
    private String bankAccount;
}