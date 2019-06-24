package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/18
 */
@Data
@ApiModel(value = "根据orgId集合查询下属的子组织")
public class OrganizationChildListReq implements Serializable {

    private static final long serialVersionUID = 8510208082950041675L;

    @ApiModelProperty(value = "orgId集合")
    private List<String> orgIdList;

    @ApiModelProperty(value = "orgLevel组织级别")
    private Long orgLevel;
}
