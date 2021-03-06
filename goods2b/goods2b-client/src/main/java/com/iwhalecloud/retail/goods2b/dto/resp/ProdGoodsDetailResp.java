package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mzl
 * @date 2018/12/3
 */
@Data
@ApiModel(value = "商品详情查询返回")
public class ProdGoodsDetailResp implements Serializable {

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
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private String brandId;

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
     * 状态 1上架 0下架
     */
    @ApiModelProperty(value = "状态 1上架 0下架")
    private Integer marketEnable;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String intro;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date lastModify;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    /**
     * 购买数
     */
    @ApiModelProperty(value = "购买数")
    private Long buyCount;

    /**
     * sord
     */
    @ApiModelProperty(value = "sord")
    private Long sord;

    /**
     * 商品发布人
     */
    @ApiModelProperty(value = "商品发布人")
    private String creatorUser;

    /**
     * 供货商id
     */
    @ApiModelProperty(value = "供货商id")
    private String supperId;

    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String auditState;

    /**
     * 产品简称
     */
    @ApiModelProperty(value = "产品简称")
    private String simpleName;

    /**
     * 最小量
     */
    @ApiModelProperty(value = "最小量")
    private Long minNim;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * _商家_分类_品牌_商品名称_ search_key like %%
     */
    @ApiModelProperty(value = "_商家_分类_品牌_商品名称_ search_key like %%")
    private String searchKey;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;

    /**
     * 地市编码
     */
    @ApiModelProperty(value = "地市编码")
    private String regionId;

    /**
     * 地市名称
     */
    @ApiModelProperty(value = "地市名称")
    private String regionName;

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
     * 商品关联推荐
     */
    @ApiModelProperty(value = "商品关联推荐")
    private List<String> recommendList;

    /**
     * 合约计划关联终端
     */
    @ApiModelProperty(value = "合约计划关联终端")
    private List<String> terminalList;

    /**
     * 合约计划关联套餐
     */
    @ApiModelProperty(value = "合约计划关联套餐")
    private List<String> offerList;

    /**
     * 合约期
     */
    @ApiModelProperty(value = "合约期")
    private Long contractPeriod;

    /**
     * 商品标签
     */
    @ApiModelProperty(value = "商品标签")
    private List<TagsDTO> tagList;

    /**
     * 产品信息
     */
    @ApiModelProperty(value = "产品信息")
    private List<ProductDTO> productList;

    /**
     * 属性集合
     */
    @ApiModelProperty(value = "属性集合")
    private List<AttrSpecDTO> attrSpecList;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String catName;

    /**
     * 是否串码 1串码 0非串码
     */
    @ApiModelProperty(value = "是否串号 1串码 0非串码")
    private Integer isSerialNo;
}
