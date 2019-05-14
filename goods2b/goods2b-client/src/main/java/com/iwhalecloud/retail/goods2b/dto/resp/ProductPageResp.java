package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
public class ProductPageResp implements Serializable {

    @ApiModelProperty(value = "productId")
    private String productId;

    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

    @ApiModelProperty(value = "产品名称（base表）")
    private String productName;

    @ApiModelProperty(value = "产品编码（base表）")
    private String productCode;

    @ApiModelProperty(value = "产品编码")
    private String sn;

    @ApiModelProperty(value = "机型名称")
    private String unitName;

    @ApiModelProperty(value = "销售价")
    private Double cost;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "状态:01 待提交，02审核中，03 已挂网，04 已退市")
    private String status;

    @ApiModelProperty(value = "归属厂家ID")
    private String manufacturerId;

    @ApiModelProperty(value = "归属厂家名称")
    private String manufacturerName;

    @ApiModelProperty(value = "产品型号")
    private String unitType;

    @ApiModelProperty(value = "产品型号名称")
    private String unitTypeName;

    @ApiModelProperty(value = "产品类别ID")
    private String catId;

    @ApiModelProperty(value = "产品类别名称")
    private String catName;

    @ApiModelProperty(value = "默认图")
    private String defaultImages;

    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    @ApiModelProperty(value = "规格别名")
    private String specName;

    @ApiModelProperty(value = "来源")
    private String sourceFrom;

    @ApiModelProperty(value = "非地包供货价下限")
    private String supplyFeeLower;

    @ApiModelProperty(value = "非地包供货价上限")
    private String supplyFeeUpper;

    @ApiModelProperty(value = "产品生效日期")
    private String effDate;

    @ApiModelProperty(value = "产品失效日期")
    private String expDate;

    @ApiModelProperty(value = "容量")
    private String memory;

    @ApiModelProperty(value = "屏幕尺寸")
    private String inch;

    @ApiModelProperty(value = "规格1")
    private String attrValue1;

    @ApiModelProperty(value = "规格2")
    private String attrValue2;

    @ApiModelProperty(value = "规格3")
    private String attrValue3;

    @ApiModelProperty(value = "规格4")
    private String attrValue4;

    @ApiModelProperty(value = "规格5")
    private String attrValue5;

    @ApiModelProperty(value = "规格6")
    private String attrValue6;

    @ApiModelProperty(value = "规格7")
    private String attrValue7;

    @ApiModelProperty(value = "规格8")
    private String attrValue8;

    @ApiModelProperty(value = "规格9")
    private String attrValue9;

    @ApiModelProperty(value = "规格10")
    private String attrValue10;

    @ApiModelProperty(value = "审核状态")
    private String auditState;

    @ApiModelProperty(value = "是否有串码")
    private String isImei;

    @ApiModelProperty(value = "是否推送ITMS")
    private String isItms;

    @ApiModelProperty(value = "是否需要CT码")
    private String isCtCode;

    @ApiModelProperty(value = "是否需要抽检")
    private String isInspection;

    @ApiModelProperty(value = "是否固网终端")
    private String isFixedLine;

    @ApiModelProperty(value = "采购类型")
    private String purchaseType;

    @ApiModelProperty(value = "非地包供货价下限")
    private String localSupplyFeeLower;

    @ApiModelProperty(value = "非地包供货价上限")
    private String localSupplyFeeUpper;
}
