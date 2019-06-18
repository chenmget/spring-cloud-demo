package com.iwhalecloud.retail.system.dto.request;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/17
 */
@Data
public class OrganizationListReq implements Serializable {
    private static final long serialVersionUID = 3244012526535286219L;

    @ApiModelProperty(value = "orgId")
    private List<String> orgIdList;

    @ApiModelProperty(value = "上级组织标识,直接记录组织的直接管理上级标识")
    private java.lang.String parentOrgId;

    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "regionId")
    private String regionId;

    @ApiModelProperty(value = "orgLevel")
    private Long orgLevel;

}
