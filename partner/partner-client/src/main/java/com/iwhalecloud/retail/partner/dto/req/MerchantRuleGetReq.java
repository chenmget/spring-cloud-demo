package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取商家权限规则入参
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月22日
 */
@Data
public class MerchantRuleGetReq {

    @ApiModelProperty("商家is")
    private String merchantId;

    @ApiModelProperty(value = "规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限")
    private String ruleType;

    @ApiModelProperty(value = "对象类型:  " +
        " RULE_TYPE是1 经营权限时: TARGET_TYPE对应： 1 品牌，TARGET_ID存BRAND_ID  2 机型，TARGET_ID存PRODUCT_ID  3 区域,TARGET_ID存REGION_ID 4 商家TARGET_ID存MERCHANT_ID； " +
        " RULE_TYPE是2 绿色通道权限时: TARGET_TYPE对应： 1 产品，TARGET_ID存PRODUCT_BASE_ID,  2 机型，TARGET_ID存PRODUCT_ID ;" +
        " RULE_TYPE是3 调拨权限时:  TARGET_TYPE对应： 2 机型，TARGET_ID存PRODUCT_ID， 3 区域,TARGET_ID存REGION_ID 4 商家,TARGET_ID存MERCHANT_ID；")
    private List<String> targetTypeList;
}
