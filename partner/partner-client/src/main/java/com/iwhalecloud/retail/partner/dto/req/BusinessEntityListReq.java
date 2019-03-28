package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("经营主体分页查询请求对象")
@Data
public class BusinessEntityListReq implements Serializable {

    private static final long serialVersionUID = 3814283336300372460L;

    @ApiModelProperty(value = "经营主体名称, 模糊查询")
    private String businessEntityName;

    @ApiModelProperty(value = "经营主体编码，模糊查询")
    private String businessEntityCode;

    @ApiModelProperty(value = "状态  1:有效   0:无效")
    private String status;

}