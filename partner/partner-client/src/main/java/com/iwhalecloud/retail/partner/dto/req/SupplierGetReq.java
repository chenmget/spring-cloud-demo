package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找供应商")
public class SupplierGetReq implements Serializable {

    private static final long serialVersionUID = -2699424610513284171L;
    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id")
    private String supplierId;

    /**
     * 供应商关联的用户ID
     */
    @ApiModelProperty(value = "供应商关联的用户ID")
    private String userId;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;

}
