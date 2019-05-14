package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * @author mzl
 * @date 2018/12/24
 */
@Data
@ApiModel(value = "商品分页查询响应参数")
public class GoodsForPageQueryResp implements Serializable {

    //属性 begin
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private String goodsCatId;

    /**
     * 零售价
     */
    @ApiModelProperty(value = "零售价")
    private Double mktprice;

    /**
     * 提货价
     */
    @ApiModelProperty(value = "提货价")
    private Double deliveryPrice;


    /**
     * 供货商ID
     */
    @ApiModelProperty(value = "供货商ID")
    private String supplierId;

    /**
     * 供货商名称
     */
    @ApiModelProperty(value = "供货商名称")
    private String supplierName;

    /**
     * 成交量
     */
    @ApiModelProperty(value = "成交量")
    private Long buyCount;

    /**
     * 缩略图地址
     */
    @ApiModelProperty(value = "缩略图地址")
    private String imageUrl;

    /**
     * 是否已添加收藏
     */
    @ApiModelProperty(value = "是否已添加收藏")
    private Boolean isCollection;

    /**
     * 零售商标签
     */
    @ApiModelProperty(value = "零售商标签")
    private List<TagsDTO> tagList;

    /**
     * 卖点
     */
    @NotBlank
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;

    /**
     * 来源平台
     */
    @ApiModelProperty(value = "来源平台")
    private String sourceFrom;

    /**
     * 商家类型
     */
    @ApiModelProperty(value = "商家类型")
    private String merchantType;

    /**
     * 产品基本信息ID
     */
    @ApiModelProperty(value = "产品基本信息ID")
    private String productBaseId;

    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;

    @ApiModelProperty(value = "是否前置补贴活动")
    private Boolean isPresubsidy;

    @ApiModelProperty(value = "前置补贴直减价格")
    private Double presubsidyPrice;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "订购数量")
    private String supplyNum;

    /**
     * 是否预售商品
     */
    @ApiModelProperty(value = "是否预售商品,商品是否为预售商品，预售商品可以无库存发布\n" +
            "1.是 0.否")
    private Integer isAdvanceSale;

    /**
     * 是否预售商品
     */
    @ApiModelProperty(value = "是否前置补贴商品 1.是 0.否")
    private Integer isSubsidy;
    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private String payments;
}
