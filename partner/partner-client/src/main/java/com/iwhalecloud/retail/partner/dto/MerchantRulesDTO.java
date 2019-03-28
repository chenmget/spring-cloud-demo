package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SupplierRules
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型par_merchant_rules, 对应实体MerchantRules类")
public class MerchantRulesDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

    /**
	 * 对象名称 冗余字段属性
	 */
	@ApiModelProperty(value = "对象名称")
	private java.lang.String targetName;


	//属性 begin
	@ApiModelProperty(value = "关联ID")
	private java.lang.String merchantRuleId;

	/**
	 * 供应商ID
	 */
	@ApiModelProperty(value = "商家ID")
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
	@ApiModelProperty(value = "对象类型:  " +
			" RULE_TYPE是1 经营权限时: TARGET_TYPE对应： 1 品牌，TARGET_ID存BRAND_ID  2 机型，TARGET_ID存PRODUCT_ID  3 区域,TARGET_ID存REGION_ID 4 商家TARGET_ID存MERCHANT_ID； " +
			" RULE_TYPE是2 绿色通道权限时: TARGET_TYPE对应： 1 产品，TARGET_ID存PRODUCT_BASE_ID,  2 机型，TARGET_ID存PRODUCT_ID ;" +
			" RULE_TYPE是3 调拨权限时:  TARGET_TYPE对应： 2 机型，TARGET_ID存PRODUCT_ID， 3 区域,TARGET_ID存REGION_ID 4 商家,TARGET_ID存MERCHANT_ID；")
	private java.lang.String targetType;
	/**
	 * 对象ID
	 */
	@ApiModelProperty(value = "对象ID")
	private java.lang.String targetId;

	/**
	 * 限额
	 */
//	@ApiModelProperty(value = "限额")
//	private java.lang.Long maxSerialNum;

	/**
	 * 本月已使用
	 */
//	@ApiModelProperty(value = "本月已使用")
//	private java.lang.Long serialNumUsed;

}
