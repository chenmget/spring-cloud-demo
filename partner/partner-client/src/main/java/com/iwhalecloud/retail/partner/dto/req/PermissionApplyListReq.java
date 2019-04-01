package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/3/29
 */
@Data
public class PermissionApplyListReq implements Serializable {
    private static final long serialVersionUID = -1732104229091855605L;

    /**
     * 申请单ID(主键)
     */
    @ApiModelProperty(value = "申请单ID(主键)")
    private String applyId;

    /**
     * 申请单编码
     */
    @ApiModelProperty(value = "申请单编码")
    private String applyCode;

    /**
     * 申请单名称
     */
    @ApiModelProperty(value = "申请单名称")
    private String applyName;

    /**
     * 申请单类型：10 权限申请
     */
    @ApiModelProperty(value = "申请单类型：10 权限申请")
    private String applyType;

    /**
     * 申请单内容描述
     */
    @ApiModelProperty(value = "申请单内容描述")
    private String content;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 申请单类型: 为10权限申请时: 1 经营权限  2 绿色通道权限   3 调拨权限
     */
    @ApiModelProperty(value = "申请单类型: 为10权限申请时: 1 经营权限  2 绿色通道权限   3 调拨权限")
    private String ruleType;

}
