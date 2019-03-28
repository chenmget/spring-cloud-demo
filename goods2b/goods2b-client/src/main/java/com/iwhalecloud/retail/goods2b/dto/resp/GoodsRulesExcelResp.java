package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "excel导入操作回复类")
public class GoodsRulesExcelResp implements Serializable {

    @ApiModelProperty(value = "数据库影响行数")
    private Integer effectRow;

    @ApiModelProperty(value = "excel总记录行数")
    private Integer totalRow;

    @ApiModelProperty(value = "操作结果")
    private Boolean operateResult;

    @ApiModelProperty(value = "操作信息")
    private String operateMessage;
}
