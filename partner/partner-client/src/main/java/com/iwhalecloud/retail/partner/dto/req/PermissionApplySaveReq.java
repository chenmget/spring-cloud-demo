package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商家权限申请单 新增请求对象（单表）
 * @author wenlong.zhong
 * @date 2019/3/29
 */
@Data
public class PermissionApplySaveReq implements Serializable {
    private static final long serialVersionUID = 7187855686286850145L;

    //属性 begin
    /**
     * 申请单ID(主键)
     */
//    @ApiModelProperty(value = "申请单ID(主键)")
//    private String applyId;

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

    /**
     * 本地网ID
     */
    @ApiModelProperty(value = "本地网ID")
    private String lanId;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    private String regionId;

    /**
     * 授权书附件,附件路径,以逗号分隔
     */
    @ApiModelProperty(value = "授权书附件,附件路径,以逗号分隔")
    private String authorizeFile;

    /**
     * 售后服务承诺附件,附件路径,以逗号分隔
     */
    @ApiModelProperty(value = "售后服务承诺附件,附件路径,以逗号分隔")
    private String promiseFile;

    /**
     * 其他附件,附件路径,以逗号分隔
     */
    @ApiModelProperty(value = "其他附件,附件路径,以逗号分隔")
    private String fileUrl;

    /**
     * 记录状态。1001 未提交，1002 审核中，1003 审核通过，1004 审核不通过
     */
    @ApiModelProperty(value = "记录状态。1001 未提交，1002 审核中，1003 审核通过，1004 审核不通过")
    private String statusCd;

    /**
     * 记录首次创建的用户标识
     */
    @ApiModelProperty(value = "记录首次创建的用户标识")
    private String createStaff;

    /**
     * 记录首次创建的时间
     */
//    @ApiModelProperty(value = "记录首次创建的时间")
//    private java.util.Date createDate;

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

}
