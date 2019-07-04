package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/3/30
 */
@Data
public class PermissionApplyItemListReq implements Serializable {
    private static final long serialVersionUID = 5778773930337560661L;

    /**
     * 申请单项ID(主键)
     */
    @ApiModelProperty(value = "申请单项ID(主键)")
    private String applyItemId;

    /**
     * 申请单ID(par_permission_apply)表主键
     */
    @ApiModelProperty(value = "申请单ID(par_permission_apply)表主键")
    private String applyId;

    /**
     * 操作类型: A:新增  U:修改  D:删除
     */
    @ApiModelProperty(value = "操作类型: A:新增  U:修改  D:删除")
    private String operationType;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效")
    private String statusCd;

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

}
