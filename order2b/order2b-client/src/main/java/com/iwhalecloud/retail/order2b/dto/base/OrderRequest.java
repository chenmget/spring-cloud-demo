package com.iwhalecloud.retail.order2b.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRequest implements Serializable {

    @ApiModelProperty(value = "幂等控制", hidden = true)
    private long httpId;

    @ApiModelProperty(value = "分片字段")
    private String lanId;

    private String sourceFrom;



}
