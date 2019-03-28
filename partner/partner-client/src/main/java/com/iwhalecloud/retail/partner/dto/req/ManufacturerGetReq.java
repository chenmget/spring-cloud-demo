package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找厂商信息")
public class ManufacturerGetReq implements Serializable {

    @ApiModelProperty(value = "厂商id")
    private String manufacturerId;

    @ApiModelProperty(value = "厂商编码")
    private String manufacturerCode;
}
