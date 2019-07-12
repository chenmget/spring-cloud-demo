package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/5.
 */
@Data
@ApiModel(value = "B2B品牌查询商品返回参数")
public class ActivityGoodsDTO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "基本产品ID")
    private String productBaseId;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品分类")
    private String categoryId;

    @ApiModelProperty(value = "商品一级分类")
    private String parCategoryId;

    @ApiModelProperty(value = "商品一级分类名称")
    private String parCategoryName;

    @ApiModelProperty(value = "零售价")
    private String retailPrice;

    @ApiModelProperty(value = "供货价")
    private String wholeSalePrice;

    @ApiModelProperty(value = "供应商")
    private String supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "成交量")
    private String orderAmount;

    @ApiModelProperty(value = "缩略图地址")
    private String imageUrl;

    /**
     * 是否预售商品
     */
    @ApiModelProperty(value = "是否预售商品,商品是否为预售商品，预售商品可以无库存发布:1.是 0.否")
    private Integer isAdvanceSale;

    /**
     * 是否前置补贴
     */
    @ApiModelProperty(value = "是否前置补贴商品 1.是 0.否")
    private Integer isSubsidy;

}
