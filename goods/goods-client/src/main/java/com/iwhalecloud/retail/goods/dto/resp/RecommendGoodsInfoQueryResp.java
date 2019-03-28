package com.iwhalecloud.retail.goods.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/8
 */
@Data
@ApiModel(value = "推荐商品查询结果")
public class RecommendGoodsInfoQueryResp implements Serializable {

    private static final long serialVersionUID = 8597704148894885017L;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;
    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktPrice;
    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Double price;
    /**
     * PC版图片URL
     */
    @ApiModelProperty(value = "PC版图片URL")
    private String rollImageFile;
}
