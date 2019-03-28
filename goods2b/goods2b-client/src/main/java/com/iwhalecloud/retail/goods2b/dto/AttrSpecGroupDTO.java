package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "对应模型PROD_ATTR_SPEC_GROUP, 对应实体AttrSpecGroup类")
public class AttrSpecGroupDTO implements Serializable {

    @ApiModelProperty(value = "typeId")
    private String typeId;

    @ApiModelProperty(value = "attrGroupName")
    private String attrGroupName;

    @ApiModelProperty(value = "attrGroupId")
    private String attrGroupId;

    @ApiModelProperty(value = "statusCd")
    private String statusCd;

    @ApiModelProperty(value = "remark")
    private String remark;

}
