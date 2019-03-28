package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MerchantAccount
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("par_merchant_account")
@ApiModel(value = "对应模型par_merchant_account, 对应实体MerchantAccount类")
@KeySequence(value = "seq_par_merchant_account_id", clazz = String.class)
public class MerchantAccount implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "par_merchant_account";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 账号ID
     */
    @TableId
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
     * 帐户名称/银行账户
     */
    @ApiModelProperty(value = "帐户名称/银行账户")
    private String bankAccount;


    //属性 end

    /**
     * 字段名称枚举.
     */
    public enum FieldNames {
        /**
         * 账号ID.
         */
        accountId("accountId", "ACCOUNT_ID"),

        /**
         * 商家ID.
         */
        merchantId("merchantId", "MERCHANT_ID"),

        /**
         * 账号类型:   1 翼支付   2 微信支付   3 支付宝   4 银行账户.
         */
        accountType("accountType", "ACCOUNT_TYPE"),

        /**
         * 账户.
         */
        account("account", "ACCOUNT"),

        /**
         * 是否默认:  1 是   0否.
         */
        isDefault("isDefault", "IS_DEFAULT"),

        /**
         * 状态:   	1 有效、 0失效.
         */
        state("state", "STATE"),

        /**
         * 开户银行.
         */
        bank("bank", "BANK"),

        /**
         * 账户名称.
         */
        bankAccount("bankAccount", "BANK_ACCOUNT");

        private String fieldName;
        private String tableFieldName;

        FieldNames(String fieldName, String tableFieldName) {
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }

}
