package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/27.
 */
@Data
@ApiModel
public class InfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "zGoodsId")
    private String zGoodsId;

    @ApiModelProperty(value = "complexInfo")
    private String complexInfo;
}
