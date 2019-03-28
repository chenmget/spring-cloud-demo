package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/28
 **/
@Data
public class ProductResourceResp implements Serializable {

    private static final long serialVersionUID = -1L;
    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;
    /**
     * 产品类别
     */
    @ApiModelProperty(value = "产品类别名称")
    private String catName;

    /**
     * 产品分类
     */
    @ApiModelProperty(value = "产品类型名称")
    private String typeName;
    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 产品基本表产品名称
     */
    @ApiModelProperty(value = "产品基本表产品名称")
    private String productName;
    /**
     * 产品表产品名称
     */
    @ApiModelProperty(value = "产品表产品名称")
    private String unitName;
    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String sn;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;

    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private String sourceFrom;

    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "容量")
    private String memory;

    @ApiModelProperty(value = "屏幕尺寸")
    private String inch;

    @ApiModelProperty(value = "规格1")
    private String attrValue1;

    @ApiModelProperty(value = "规格2")
    private String attrValue2;

    @ApiModelProperty(value = "规格3")
    private String attrValue3;

    @ApiModelProperty(value = "规格4")
    private String attrValue4;

    @ApiModelProperty(value = "规格5")
    private String attrValue5;

    @ApiModelProperty(value = "规格6")
    private String attrValue6;

    @ApiModelProperty(value = "规格7")
    private String attrValue7;

    @ApiModelProperty(value = "规格8")
    private String attrValue8;

    @ApiModelProperty(value = "规格9")
    private String attrValue9;

    @ApiModelProperty(value = "规格10")
    private String attrValue10;

    @ApiModelProperty(value = "规格")
    private String specName;
}
