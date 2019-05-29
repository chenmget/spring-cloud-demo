package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/28
 **/
@Data
public class ProductResp implements Serializable {

    /**
     * productId
     */
    @ApiModelProperty(value = "productId")
    private String productId;

    /**
     * 产品基本信息ID
     */
    @ApiModelProperty(value = "产品基本信息ID")
    private String productBaseId;
    /**
     * 地包供货价下限
     */
    @ApiModelProperty(value = "地包供货价下限")
    private Long localSupplyFeeLower;

    /**
     * 地包供货价上限
     */
    @ApiModelProperty(value = "地包供货价上限")
    private Long localSupplyFeeUpper;

    /**
     * 非地包供货价下限
     */
    @ApiModelProperty(value = "非地包供货价下限")
    private Long supplyFeeLower;

    /**
     * 非地包供货价上限
     */
    @ApiModelProperty(value = "非地包供货价上限")
    private Long supplyFeeUpper;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String sn;
    /**
     * 提货价
     */
    @ApiModelProperty(value = "提货价")
    private Double deliveryPrice;

    /**
     * 初始提货价
     */
    @ApiModelProperty(value = "初始提货价")
    private Double initialPrice;

    /**
     * 活动中的商品价格，前置补贴活动保存产品的地包强制供货价
     */
    @ApiModelProperty(value = "活动中的商品价格，前置补贴活动产品的地包强制供货价")
    private Long unifiedSupplierPrice;

    /**
     * 是否前置补贴
     */
    @ApiModelProperty(value = "是否前置补贴 1:是  0：否")
    private Integer isSubsidy;

    /**
     * 上架数量
     */
    @ApiModelProperty(value = "上架数量")
    private Long supplyNum;
    /**
     * 规格别名
     */
    @ApiModelProperty(value = "规格别名")
    private String specName;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String unitName;
    /**
     * 最小起批量
     */
    @ApiModelProperty(value = "最小起批量")
    private Long minNum;

    /**
     * 订购上限
     */
    @ApiModelProperty(value = "订购上限")
    private Long maxNum;
    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    private Long cost;

    @ApiModelProperty(value = "图片")
    private String img;

    /**
     * sku
     */
    @ApiModelProperty(value = "sku")
    private String sku;

    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;

    /**
     * 容量
     */
    @ApiModelProperty(value = "容量")
    private String memory;

    /**
     * 屏幕尺寸
     */
    @ApiModelProperty(value = "屏幕尺寸")
    private String inch;

    /**
     * 规格1
     */
    @ApiModelProperty(value = "规格1")
    private String attrValue1;

    /**
     * 规格2
     */
    @ApiModelProperty(value = "规格2")
    private String attrValue2;

    /**
     * 规格3
     */
    @ApiModelProperty(value = "规格3")
    private String attrValue3;

    /**
     * 规格4
     */
    @ApiModelProperty(value = "规格4")
    private String attrValue4;

    /**
     * 规格5
     */
    @ApiModelProperty(value = "规格5")
    private String attrValue5;

    /**
     * 规格6
     */
    @ApiModelProperty(value = "规格6")
    private String attrValue6;

    /**
     * 规格7
     */
    @ApiModelProperty(value = "规格7")
    private String attrValue7;

    /**
     * 规格8
     */
    @ApiModelProperty(value = "规格8")
    private String attrValue8;

    /**
     * 规格9
     */
    @ApiModelProperty(value = "规格9")
    private String attrValue9;

    /**
     * 规格10
     */
    @ApiModelProperty(value = "规格10")
    private String attrValue10;

    /**
     * 状态:01 待提交，02审核中，03 已挂网，04 已退市
     */
    @ApiModelProperty(value = "状态:01 待提交，02审核中，03 已挂网，04 已退市")
    private String status;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brandId;

    /**
     * 是否需要CT码
     */
    @ApiModelProperty(value = "是否需要CT码")
    private String isCtCode;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;
    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private String sourceFrom;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private String auditState;

    /**
     * productName
     */
    @ApiModelProperty(value = "productName")
    private String productName;

    /**
     * productCode
     */
    @ApiModelProperty(value = "productCode")
    private String productCode;

    /**
     * 活动预付款，如预售活动的定金。冗余用字段
     */
    @ApiModelProperty(value = "活动预付款，如预售活动的定金。冗余用字段")
    private Long advancePayAmount;

    /**
     * 采购类型
     */
    @ApiModelProperty(value = "采购类型")
    private String purchaseType;
    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String typeId;
    
    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String typeName;
    
    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    
    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;
    
}
