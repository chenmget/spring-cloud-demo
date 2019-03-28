package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找经营主体")
public class BusinessEntityGetReq implements Serializable {

    /**
     * 经营主体id
     */
    @ApiModelProperty(value = "经营主体id")
    private String businessEntityId;

    /**
     * 经营主体编码
     */
    @ApiModelProperty(value = "经营主体编码")
    private String businessEntityCode;
}
