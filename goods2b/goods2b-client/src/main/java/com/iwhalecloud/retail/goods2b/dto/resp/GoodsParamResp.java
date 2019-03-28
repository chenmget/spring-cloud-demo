package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/3/9
 */
@Data
public class GoodsParamResp implements Serializable {

    private static final long serialVersionUID = 4305719488339218244L;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 属性名
     */
    @ApiModelProperty(value = "属性名")
    private String paramName;
    /**
     * 商品属性值
     */
    @ApiModelProperty(value = "商品属性值")
    private String paramValue;

    /**
     * 属性所属的分组
     */
    @ApiModelProperty(value = "属性所属的分组")
    private java.lang.String attrGroupId;
}
