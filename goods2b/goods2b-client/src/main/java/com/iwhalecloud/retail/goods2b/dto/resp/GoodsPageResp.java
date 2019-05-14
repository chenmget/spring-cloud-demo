package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.GoodsRegionRelDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "对应模型prod_goods, 对应实体ProdGoods类")
public class GoodsPageResp implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "分类ID")
    private String goodsCatId;

    @ApiModelProperty(value = "分类名称")
    private String goodsCatName;

    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    @ApiModelProperty(value = "供应商级别")
    private String supplierType;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "营销活动名称")
    private List<String> actNames;

    @ApiModelProperty(value = "商品状态，0：下架，1：上架")
    private Integer marketEnable;

    @ApiModelProperty(value = "所关联产品最低零售价")
    private Double goodsLowestPrice;

    @ApiModelProperty(value = "库存数量")
    private Integer storeCount;

    @ApiModelProperty(value = "商品图片url（以','隔开）")
    private String imagesUrl;

    @ApiModelProperty(value = "审批状态")
    private String auditState;

    /**
     * 是否分货
     */
    @ApiModelProperty(value = "是否分货")
    private Integer isAllot;

    /**
     * 来源平台
     */
    @ApiModelProperty(value = "来源平台")
    private String sourceFrom;

    @ApiModelProperty(value = "产品ID")
    private String productBaseId;

    @ApiModelProperty(value = "产品型号")
    private String unitType;

    @ApiModelProperty(value = "产品编码")
    private String sn;

    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private String payments;

    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktprice;

    @ApiModelProperty(value = "供应商类型")
    private String merchantType;

    @ApiModelProperty(value = "商品发布对象类型")
    private String targetType;

    @ApiModelProperty(value = "商品发布范围")
    private List<GoodsRegionRelDTO> goodsRegionRels;

    @ApiModelProperty(value = "商品发布对象")
    private List<String> goodsTargetRels;

    @ApiModelProperty(value = "供货价")
    private String deliveryPrice;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "类型Id")
    private String typeId;

    @ApiModelProperty(value = "颜色")
    private Double color;

    @ApiModelProperty(value = "内存")
    private Double memory;
}
