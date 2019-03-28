package com.iwhalecloud.retail.goods.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/12/3
 */
@Data
@ApiModel(value = "根据产品id查询商品返回")
public class QryGoodsByProductIdResp implements Serializable {

    //属性 begin
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码")
    private String sn;

    /**
     * 商品目录
     */
    @ApiModelProperty(value = "商品目录")
    private String catId;

    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID")
    private String typeId;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private Double weight;

    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Double price;

    /**
     * 成本价
     */
    @ApiModelProperty(value = "成本价")
    private Double cost;

    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktprice;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;

    /**
     * 默认图片
     */
    @ApiModelProperty(value = "默认图片")
    private String defaultImage;

    /**
     * 详情图片
     */
    @ApiModelProperty(value = "详情图片")
    private String detailImage;

    /**
     * 轮播图片
     */
    @ApiModelProperty(value = "轮播图片")
    private String rollImage;

    /**
     * 商品规格
     */
    @ApiModelProperty(value = "商品规格")
    private String specs;

    /**
     * 状态 1上架 0下架
     */
    @ApiModelProperty(value = "状态 1上架 0下架")
    private Integer marketEnable;
}
