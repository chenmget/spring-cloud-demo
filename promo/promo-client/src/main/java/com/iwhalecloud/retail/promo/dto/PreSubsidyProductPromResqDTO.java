package com.iwhalecloud.retail.promo.dto;

import com.iwhalecloud.retail.promo.dto.resp.MktResRegionRespDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "前置补贴产品列表查询")
public class PreSubsidyProductPromResqDTO implements Serializable {

    private static final long serialVersionUID = -7964366115092352712L;

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
     * 优惠券数量
     */
    @ApiModelProperty(value = "优惠券数量")
    private Long num;

    /**
     * 优惠方式为直减时，保存减免的金额
     */
    @ApiModelProperty(value = "优惠方式为直减时，保存减免的金额")
    private java.lang.Long discountAmount;

    /**
     * 优惠券信息
     */
    @ApiModelProperty(value = "优惠券信息")
    List<MktResRegionRespDTO> mktResRegionRespDTOS;

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

    /**
     * 	活动中的商品价格 前置补贴活动保存产品的地包强制供货价 限时抢购活动保存产品的抢购价
     */
    @ApiModelProperty(value = "活动中的商品价格 前置补贴活动保存产品的地包强制供货价 限时抢购活动保存产品的抢购价")
    private java.lang.Long price;
    
    /**
	 * 活动图片应用方式
	 */
	@ApiModelProperty(value = "活动图片应用方式")
	private String productPicUseType;
	
	/**
	 * 活动图片
	 */
	@ApiModelProperty(value = "活动图片")
	private String productPic;
    
}
