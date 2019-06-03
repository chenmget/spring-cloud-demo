package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MerchantRules
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("par_merchant_rules")
@ApiModel(value = "对应模型par_merchant_rules, 对应实体MerchantRules类")
@KeySequence(value = "seq_par_merchant_rules_id", clazz = String.class)
public class MerchantRules implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "par_merchant_rules";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 关联ID
     */
    @TableId
    @ApiModelProperty(value = "关联ID")
    private java.lang.String merchantRuleId;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "供应商ID")
    private java.lang.String merchantId;

    /**
     * 规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限
     */
    @ApiModelProperty(value = "规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限")
    private java.lang.String ruleType;

    /**
     * 对象类型:
     * RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；
     * RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;
     * RULE_TYPE是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；
     */
    @ApiModelProperty(value = "对象类型:   	RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；   RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;  RULE_TYPE  是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；")
    private java.lang.String targetType;

    /**
     * 对象ID
     */
    @ApiModelProperty(value = "对象ID")
    private java.lang.String targetId;

//	/**
//	 * 限额
//	 */
//	@ApiModelProperty(value = "限额")
//	private java.lang.Long maxSerialNum;
//
//	/**
//	 * 本月已使用
//	 */
//	@ApiModelProperty(value = "本月已使用")
//	private java.lang.Long serialNumUsed;


    //属性 end

    /**
     * 字段名称枚举.
     */
    public enum FieldNames {
        /**
         * 关联ID.
         */
        merchantRuleId("merchantRuleId", "MERCHANT_RULE_ID"),

        /**
         * 供应商ID.
         */
        merchantId("merchantId", "MERCHANT_ID"),

        /**
         * 规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限.
         */
        ruleType("ruleType", "RULE_TYPE"),

        /**
         * 对象类型:  RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；   RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;  RULE_TYPE  是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；.
         */
        targetType("targetType", "TARGET_TYPE"),

        /**
         * 对象ID.
         */
        targetId("targetId", "TARGET_ID");

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
