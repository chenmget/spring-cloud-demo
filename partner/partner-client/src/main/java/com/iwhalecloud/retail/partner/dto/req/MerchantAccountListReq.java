package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("根据条件查找 商家账号 列表")
public class MerchantAccountListReq implements Serializable {
    private static final long serialVersionUID = 5577261561201445248L;

    //属性 begin
    /**
     * 账号ID
     */
    @ApiModelProperty(value = "账号ID")
    private String accountId;

    /**
     * 账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户
     */
    @ApiModelProperty(value = "账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户")
    private String accountType;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    @NotEmpty(message = "商家ID不能为空")
    private String merchantId;

    /**
     * 商家ID集合
     */
    @ApiModelProperty(value = "商家ID集合")
    private List<String> merchantIdList;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 帐户名称/银行账户
     */
    @ApiModelProperty(value = "帐户名称/银行账户")
    private String bankAccount;
    
    /**
     * 翼支付登录号
     */
    @ApiModelProperty(value = "翼支付登录号")
    private String accountName;
}