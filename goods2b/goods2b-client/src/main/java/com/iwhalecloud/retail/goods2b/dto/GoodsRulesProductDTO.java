package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/****
 * @author gs_10010
 * @date 2019/7/9 20:26
 */
@Data
public class GoodsRulesProductDTO extends GoodsRulesDTO implements Serializable {

    /**
     * 规格1
     */
    @ApiModelProperty(value = "规格1")
    private String attrValue1;

    /**
     * 规格2
     */
    @ApiModelProperty(value = "规格2")
    private String attrValue2;

    /**
     * 规格3
     */
    @ApiModelProperty(value = "规格3")
    private String attrValue3;
}
