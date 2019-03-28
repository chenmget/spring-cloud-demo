package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

;

/**
 * @author mzl
 * @date 2018/11/27
 */
@Data
@ApiModel(value = "添加商品请求参数")
public class ProdGoodsAddReq implements Serializable {

    private static final long serialVersionUID = 530327664119035894L;

    /**
     * 商品名称
     */
    @NotBlank
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;

    /**
     * sord
     */
    @NotNull
    @ApiModelProperty(value = "sord")
    private Long sord;

    /**
     * _商家_分类_品牌_商品名称_ search_key like %%
     */
    @NotBlank
    @ApiModelProperty(value = "_商家_分类_品牌_商品名称_ search_key like %%")
    private String searchKey;

    /**
     * 分类ID
     */
    @NotBlank
    @ApiModelProperty(value = "分类ID")
    private String catId;

    /**
     * 类型ID
     */
    @NotBlank
    @ApiModelProperty(value = "类型ID")
    private String typeId;

    /**
     * 分类名称
     */
    @NotBlank
    @ApiModelProperty(value = "分类名称")
    private String catName;

    /**
     * 地市编码
     */
    @NotBlank
    @ApiModelProperty(value = "地市编码")
    private String regionId;

    /**
     * 地市名称
     */
    @NotBlank
    @ApiModelProperty(value = "地市名称")
    private String regionName;

    /**
     * 供货商id
     */
    @NotBlank
    @ApiModelProperty(value = "供货商id")
    private String supperId;

    /**
     * 品牌id
     */
    @NotBlank
    @ApiModelProperty(value = "品牌id")
    private String brandId;

    /**
     * 品牌名称
     */
    @NotBlank
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private String[] tagId;

    /**
     * 规格价格
     */
    @ApiModelProperty(value = "规格价格")
    private String[] specPrices;

    /**
     * 规格值名称
     */
    @ApiModelProperty(value = "规格值名称")
    private String[] specValues;

    /**
     * 规格库存
     */
    @ApiModelProperty(value = "规格库存")
    private String[] stores;

    /**
     * 商品详情图片
     */
    @ApiModelProperty(value = "商品详情图片")
    private String detailImageFile;

    /**
     * 商品轮播图
     */
    @ApiModelProperty(value = "商品轮播图")
    private String rollImageFile;

    /**
     * 商品轮播视频
     */
    @ApiModelProperty(value = "商品轮播视频")
    private String rollVideo;

    /**
     * 销售价
     */
    @NotNull
    @ApiModelProperty(value = "销售价")
    private Double price;

    /**
     * 市场价
     */
    @NotNull
    @ApiModelProperty(value = "市场价")
    private Double mktprice;

    /**
     * 商品关联推荐
     */
    @ApiModelProperty(value = "商品关联推荐")
    private String[] recommendList;

    /**
     * 合约计划关联终端
     */
    @ApiModelProperty(value = "合约计划关联终端")
    private String[] terminalList;

    /**
     * 合约计划关联套餐
     */
    @ApiModelProperty(value = "合约计划关联套餐")
    private String[] offerList;

    /**
     * 合约期
     */
    @ApiModelProperty(value = "合约期")
    private Long contractPeriod;

    /**
     * 是否串码 1串码 0非串码
     */
    @ApiModelProperty(value = "是否串号 1串码 0非串码")
    private Integer isSerialNo;
}
