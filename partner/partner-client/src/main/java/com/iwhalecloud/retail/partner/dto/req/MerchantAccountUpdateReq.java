package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel("更新 商家账号")
public class MerchantAccountUpdateReq implements Serializable {
    private static final long serialVersionUID = -8208817282443005733L;

    /**
     * 账号ID
     */
    @ApiModelProperty(value = "账号ID")
    @NotEmpty(message = "账号ID不能为空")
    private String accountId;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    @NotEmpty(message = "商家ID不能为空")
    private String merchantId;

    /**
     * 账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户
     */
    @ApiModelProperty(value = "账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户")
    @NotEmpty(message = "账户类型不能为空")
    private String accountType;

    /**
     * 账户
     */
    @ApiModelProperty(value = "账户")
    @NotEmpty(message = "账户不能为空")
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
     * 帐户名称/银行账户
     */
    @ApiModelProperty(value = "帐户名称/银行账户")
    private String bankAccount;
    
    /**
     * 帐户名称/银行账户
     */
    @ApiModelProperty(value = "翼支付登录号")
    private String accountName;
}
