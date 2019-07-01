package com.iwhalecloud.retail.web.controller.b2b.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateOrganizationReq implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * orgId
     */
    @ApiModelProperty(value = "ID")
    private java.lang.String orgId;

//    /**
//     * partnerOrgId
//     */
//    @ApiModelProperty(value = "父级ID")
//    private java.lang.String partnerOrgId;
//
//    /**
//     * partnerOrgName
//     */
//    @ApiModelProperty(value = "父级名称")
//    private java.lang.String partnerOrgName;

    @ApiModelProperty(value = "上级组织标识,直接记录组织的直接管理上级标识")
    private java.lang.String parentOrgId;

    @ApiModelProperty(value = "上级组织标识,直接记录组织的直接管理上级标识名称")
    private java.lang.String parentOrgName;

    @ApiModelProperty(value = "层次路径编码")
    private java.lang.String pathCode;

    /**
     * orgCode
     */
    @ApiModelProperty(value = "编码")
    private java.lang.String orgCode;

    /**
     * orgName
     */
    @ApiModelProperty(value = "名称")
    private java.lang.String orgName;

    /**
     * lanId
     */
    @ApiModelProperty(value = "本地网ID")
    private java.lang.String lanId;

    /**
     * lan
     */
    @ApiModelProperty(value = "本地网")
    private java.lang.String lan;

    /**
     * regionId
     */
    @ApiModelProperty(value = "区域ID")
    private java.lang.String regionId;

    /**
     * region
     */
    @ApiModelProperty(value = "区域名称")
    private java.lang.String region;

    /**
     * orgLevel
     */
    @ApiModelProperty(value = "等级")
    private java.lang.Long orgLevel;

    /**
     * 记录状态变更的时间。
     */
    @ApiModelProperty(value = "记录状态变更的时间。")
    private java.util.Date statusDate;

    /**
     * 记录状态。LOVB=PUB-C-0001。
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
    private java.lang.String statusCd;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

    /**
     * 记录首次创建的员工标识。
     */
    @ApiModelProperty(value = "记录首次创建的员工标识。")
    private java.lang.String createStaff;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录每次修改的员工标识。
     */
    @ApiModelProperty(value = "记录每次修改的员工标识。")
    private java.lang.String updateStaff;

    /**
     * 记录每次修改的时间。
     */
    @ApiModelProperty(value = "记录每次修改的时间。")
    private java.util.Date updateDate;


}