package com.iwhalecloud.retail.system.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = " 对应实体Organization类")
public class OrganizationRegionResp implements Serializable {

    @ApiModelProperty(value = "orgId")
    private String orgId;

    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "orgName")
    private String orgName;

    @ApiModelProperty(value = "lanId")
    private String regionId;

}