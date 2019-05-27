package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/3/9
 */
@Data
public class GoodsAttrResp implements Serializable {

    private static final long serialVersionUID = 6800790657831962820L;


    /**
     * 规格名
     */
    @ApiModelProperty(value = "规格名")
    private String attrName;
    /**
     * 商品规格值
     */
    @ApiModelProperty(value = "商品规格值")
    private String attrValue;

    @ApiModelProperty(value = "是否展示")
    private String isDisplay;
}
