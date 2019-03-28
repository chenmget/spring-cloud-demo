package com.iwhalecloud.retail.warehouse.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
public class MerchantInfByNbrModel implements Serializable {

    @NotEmpty(message = "省级供货商ID不能为空")
    @ApiModelProperty(value = "省级供货商ID")
    private String provSupplyId;

    @NotEmpty(message = "省级供货商名称不能为空")
    @ApiModelProperty(value = "省级供货商名称")
    private String provSupplyName;

    @ApiModelProperty(value = "地市级供货商ID")
    private String citySupplyId;

    @ApiModelProperty(value = "地市级供货商名称")
    private String citySupplyName;
}
