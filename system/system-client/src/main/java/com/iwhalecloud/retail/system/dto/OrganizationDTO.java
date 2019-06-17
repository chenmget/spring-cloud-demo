package com.iwhalecloud.retail.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SYS_ORGANIZATION, 对应实体Organization类")
public class OrganizationDTO implements Serializable {

    @ApiModelProperty(value = "orgId")
    private String orgId;

//    @ApiModelProperty(value = "partnerOrgId")
//    private String partnerOrgId;
//
//    @ApiModelProperty(value = "partnerOrgName")
//    private String partnerOrgName;

    @ApiModelProperty(value = "上级组织标识,直接记录组织的直接管理上级标识")
    private java.lang.String parentOrgId;

    @ApiModelProperty(value = "上级组织标识,直接记录组织的直接管理上级标识名称")
    private java.lang.String parentOrgName;

    @ApiModelProperty(value = "层次路径编码")
    private java.lang.String pathCode;

    @ApiModelProperty(value = "orgCode")
    private String orgCode;

    @ApiModelProperty(value = "orgName")
    private String orgName;

    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "lan")
    private String lan;

    @ApiModelProperty(value = "regionId")
    private String regionId;

    @ApiModelProperty(value = "region")
    private String region;

    @ApiModelProperty(value = "orgLevel")
    private Long orgLevel;

    @ApiModelProperty(value = "statusDate")
    private Date statusDate;

    @ApiModelProperty(value = "statusCd")
    private String statusCd;

    @ApiModelProperty(value = "remark")
    private String remark;

    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    @ApiModelProperty(value = "createDate")
    private Date createDate;

    @ApiModelProperty(value = "updateStaff")
    private String updateStaff;

    @ApiModelProperty(value = "updateDate")
    private Date updateDate;

    @ApiModelProperty(value = "merchantCode")
    private String merchantCode;

}