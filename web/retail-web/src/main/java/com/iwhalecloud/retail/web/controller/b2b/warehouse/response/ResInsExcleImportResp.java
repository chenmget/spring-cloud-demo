package com.iwhalecloud.retail.web.controller.b2b.warehouse.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "导入excle返回值")
public class ResInsExcleImportResp implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 记录营销资源实例编码。
     */
    @ApiModelProperty(value = "记录营销资源实例编码。")
    private String mktResInstNbr;


    /**
     * ctCode
     */
    @ApiModelProperty(value = "ctCode")
    private String ctCode;
}
