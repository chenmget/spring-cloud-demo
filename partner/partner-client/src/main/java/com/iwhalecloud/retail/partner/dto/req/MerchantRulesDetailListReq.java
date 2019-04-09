package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "商家权限规则详情 获取列表请求对象，对应模型par_merchant_rules, 对应实体MerchantRules类")
public class MerchantRulesDetailListReq implements Serializable {
    private static final long serialVersionUID = -5236617148186781365L;

    @ApiModelProperty(value = "商家ID")
    private java.lang.String merchantId;

//    @ApiModelProperty(value = "对象id")
//    private String targetId;

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

    /******  查机型 或 产品 用到的字段   ***/
    /**
     * prod_product表字段unit_name
     */
    @ApiModelProperty(value = "产品名称")
    private java.lang.String unitName;

    /**
     * prod_product_base表字段product_name
     */
    @ApiModelProperty(value = "产品名称")
    private java.lang.String productName;
    /**
     * prod_product表字段sn
     */
    @ApiModelProperty(value = "产品编码")
    private java.lang.String sn;

    /**
     * prod_product表字段unit_type
     */
    @ApiModelProperty(value = "产品类型")
    private java.lang.String unitType;

    @ApiModelProperty(value = "品牌id")
    private java.lang.String brandId;

    /******  查机型 或 产品 用到的字段   ***/


    /******  查 商家 用到的字段   ***/
    /**
     * par_merchant表字段merchant_type
     */
    @ApiModelProperty(value = "商家类型")
    private java.lang.String merchantType;

    @ApiModelProperty(value = "商家名称")
    private java.lang.String merchantName;

    @ApiModelProperty(value = "商家编码")
    private java.lang.String merchantCode;

    @ApiModelProperty(value = "商家本地网")
    private java.lang.String lanId;

    @ApiModelProperty(value = "商家市县")
    private java.lang.String city;

    /**
     * prod_merchant_tag_rel表字段
     */
    @ApiModelProperty(value = "标签")
    private java.lang.String tagId;


    /**
     * sys_user表字段
     */
    @ApiModelProperty(value = "系统账号")
    private java.lang.String loginName;

    /****** 查 商家 用到的字段   ******/

}