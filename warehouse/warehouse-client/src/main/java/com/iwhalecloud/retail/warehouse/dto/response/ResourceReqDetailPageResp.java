package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceReqDetailPageResp implements Serializable {

    @ApiModelProperty(value = "记录营销资源申请单标识")
    private String mktResReqId;

    @ApiModelProperty(value = "营销资源标识")
    private String mktResId;

    @ApiModelProperty(value = "产品分类")
    private String typeName;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "串码")
    private String mktResInstNbr;

    @ApiModelProperty(value = "机型名称")
    private String unitName;

    @ApiModelProperty(value = "产品型号")
    private String unitType;

    @ApiModelProperty(value = "ct码")
    private String ctCode;

    @ApiModelProperty(value = "产品编码")
    private String sn;
}
