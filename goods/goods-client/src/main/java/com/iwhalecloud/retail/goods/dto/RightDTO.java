package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RightDTO implements Serializable {
    private static final long serialVersionUID = 7180979910048572263L;

    /**
     * 权益ID：rightsId
     */
    @ApiModelProperty(value = "rightsId")
    private java.lang.Long rightsId;

    /**
     * 权益名称：rightsName
     */
    @ApiModelProperty(value = "rightsName")
    private java.lang.String rightsName;
}
