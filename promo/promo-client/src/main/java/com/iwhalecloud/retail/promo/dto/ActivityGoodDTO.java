package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Data
@ApiModel("活动商品")
public class ActivityGoodDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**基本产品ID */
    @ApiModelProperty(value = "基本产品ID")
    private String productBaseId;
    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 商品分类
     */
    @ApiModelProperty("商品分类")
    private String categoryId;

    /**
     * 商品一级分类
     */
    @ApiModelProperty("商品一级分类")
    private String parCategoryId;

    /**
     * 商品一级分类名称
     */
    @ApiModelProperty(value = "商品一级分类名称")
    private String parCategoryName;

    /**
     * 零售价
     */
    @ApiModelProperty("零售价")
    private String retailPrice;

    /**
     * 供货价
     */
    @ApiModelProperty("供货价")
    private String wholeSalePrice;

    /**
     * 供应商
     */
    @ApiModelProperty("供应商")
    private String supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty("供应商名称")
    private String supplierName;

    /**
     * 成交量
     */
    @ApiModelProperty("成交量")
    private String orderAmount;

    /**
     * 缩略图地址
     */
    @ApiModelProperty("缩略图地址")
    private String imageUrl;
}
