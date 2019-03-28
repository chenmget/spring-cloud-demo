package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "供应商列表查询")
public class SupplierListReq implements Serializable {

    @ApiModelProperty(value = "供应商名称，模糊查询")
    private String supplierName;

    @ApiModelProperty(value = "状态")
    private String supplierState;

    @ApiModelProperty(value = "供应商类型")
    private String supplierType;

}
