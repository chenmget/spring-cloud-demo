package com.iwhalecloud.retail.partner.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "商家权限查询反参")
public class MerchantRulesDetailPageResp implements Serializable {
    private static final long serialVersionUID = 5670607190359230849L;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    /**
     * 商家本地网
     */
    @ApiModelProperty(value = "商家本地网")
    private String lanId;

    /**
     * 商家本地网
     */
    @ApiModelProperty(value = "商家本地网")
    private String lanName;

    /**
     * 商家区县
     */
    @ApiModelProperty(value = "商家区县")
    private String city;

    /**
     * 商家区县
     */
    @ApiModelProperty(value = "商家区县")
    private String cityName;

    /**
     * 商家类型
     */
    @ApiModelProperty(value = "商家类型")
    private String merchantType;

    /**
     * 渠道状态
     */
    @ApiModelProperty(value = "渠道状态")
    private String status;

    /**
     * 规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限
     */
    @ApiModelProperty(value = "规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限")
    private String ruleType;

    /**
     * 对象类型:
     * RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；
     * RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID, 2 机型，TARGET_ID填写PRODUCT_ID ;
     * RULE_TYPE是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；
     */
    @ApiModelProperty(value = "对象类型:  " +
            " RULE_TYPE是1 经营权限时: TARGET_TYPE对应： 1 品牌，TARGET_ID存BRAND_ID  2 机型，TARGET_ID存PRODUCT_ID  3 区域,TARGET_ID存REGION_ID 4 商家TARGET_ID存MERCHANT_ID； " +
            " RULE_TYPE是2 绿色通道权限时: TARGET_TYPE对应： 1 产品，TARGET_ID存PRODUCT_BASE_ID,  2 机型，TARGET_ID存PRODUCT_ID ;" +
            " RULE_TYPE是3 调拨权限时:  TARGET_TYPE对应： 2 机型，TARGET_ID存PRODUCT_ID， 3 区域,TARGET_ID存REGION_ID 4 商家,TARGET_ID存MERCHANT_ID；")
    private String targetType;
    /**
     * 对象ID
     */
    @ApiModelProperty(value = "对象ID")
    private String targetId;

    /**
     * 对象名称 冗余字段属性
     */
    @ApiModelProperty(value = "对象名称")
    private String targetName;

    /**
     * 对象编码 冗余字段属性
     */
    @ApiModelProperty(value = "对象编码")
    private String targetCode;

    /**
     * 限额
     */
    @ApiModelProperty(value = "限额")
    private Long maxSerialNum;

    /**
     * 本月已使用
     */
    @ApiModelProperty(value = "本月已使用")
    private Long serialNumUsed;
}
