package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/6/17
 */
@Data
public class OrganizationListReq implements Serializable {
    private static final long serialVersionUID = 3244012526535286219L;

    @ApiModelProperty(value = "orgId")
    private String orgId;

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
}
