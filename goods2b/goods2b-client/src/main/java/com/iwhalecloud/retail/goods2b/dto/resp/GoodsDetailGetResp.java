package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mzl
 * @date 2019/1/11
 */
@Data
public class GoodsDetailGetResp implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码")
    private String sn;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private String goodsCatId;

    @ApiModelProperty(value = "类别名称")
    private String catName;

    /**
     * 上下架状态
     */
    @ApiModelProperty(value = "上下架状态")
    private Integer marketEnable;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    /**
     * 购买次数
     */
    @ApiModelProperty(value = "购买次数")
    private Long buyCount;
    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktprice;
    /**
     * 供货商ID
     */
    @ApiModelProperty(value = "供货商ID")
    private String supplierId;
    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supplierName;

    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String auditState;

    /**
     * 搜索关键字
     */
    @ApiModelProperty(value = "搜索关键字")
    private String searchKey;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;
    /**
     * regionName
     */
    @ApiModelProperty(value = "regionNames")
    private List<String> regionNames;
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
     * 商品轮播视频
     */
    @ApiModelProperty(value = "商品轮播视频")
    private String rollVideo;
    /**
     * 营销活动列表
     */
    private List<GoodActRelResp> goodActRelResps;
    /**
     * 产品列表
     */
    private List<ProductResp> productResps;
    /**
     * 商品规格列表
     */
    private List<AttrSpecResp> attrSpecResps;
    /**
     * 产品规格列表
     */
    private List<AttrSpecResp.GoodsSpec> goodsSpecList;
}
