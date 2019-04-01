package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/3/30
 */
@Data
public class PermissionApplyItemUpdateReq implements Serializable {
    private static final long serialVersionUID = 8375318018109035211L;

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
     * 权限规则ID(par_merchant_rules表主键, 操作类型是U:修改和D:删除时 有值)
     */
    @ApiModelProperty(value = "权限规则ID(par_merchant_rules表主键, 操作类型是U:修改和D:删除时 有值)")
    private String merchantRuleId;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限
     */
    @ApiModelProperty(value = "规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限")
    private String ruleType;

    /**
     * 对象类型: RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；   RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;  RULE_TYPE  是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；
     */
    @ApiModelProperty(value = "对象类型: RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；   RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;  RULE_TYPE  是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；")
    private String targetType;

    /**
     * 对象ID
     */
    @ApiModelProperty(value = "对象ID")
    private String targetId;

    /**
     * 记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效")
    private String statusCd;


    /**
     * 记录每次修改的用户标识
     */
    @ApiModelProperty(value = "记录每次修改的用户标识")
    private String updateStaff;

    /**
     * 记录每次修改的时间
     */
//    @ApiModelProperty(value = "记录每次修改的时间")
//    private java.util.Date updateDate;

    /**
     * 记录状态变更的时间
     */
//    @ApiModelProperty(value = "记录状态变更的时间")
//    private java.util.Date statusDate;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;

}
