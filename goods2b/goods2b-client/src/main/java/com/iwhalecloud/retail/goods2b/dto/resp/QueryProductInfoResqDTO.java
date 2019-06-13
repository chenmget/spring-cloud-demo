package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年02月24日
 * @Description:
 */
@Data
@ApiModel(value = "根据产品表id查询产品信息")
public class QueryProductInfoResqDTO implements Serializable {

    private static final long serialVersionUID = 2807183227824414904L;

    /**
     * 产品表主键
     */
    @ApiModelProperty(value = "产品表主键")
    private String productId;

    /**
     * 产品基础信息表主键
     */
    @ApiModelProperty(value = "产品基础信息表主键")
    private String productBaseId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;

    /**
     * 25位编码
     */
    @ApiModelProperty(value = "25位编码")
    private String sn;

    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Double cost;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brandName;

    /**
     * 产品规格
     */
    @ApiModelProperty(value = "产品规格")
    private String specName;

    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private String sourceFrom;

    /**
     * 产品规格
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;
    
    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;
    
    /**
     * 内存
     */
    @ApiModelProperty(value = "内存")
    private String memory;
    
    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String typeName;

    @ApiModelProperty(value = "产品类型")
    private String typeId;

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
}
