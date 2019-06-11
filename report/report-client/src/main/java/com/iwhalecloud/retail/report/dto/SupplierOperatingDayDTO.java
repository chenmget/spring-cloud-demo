package com.iwhalecloud.retail.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ReportSupplierOperatingDay
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型rpt_supplier_operating_day, 对应实体ReportSupplierOperatingDay类")
public class SupplierOperatingDayDTO implements java.io.Serializable {
    private static final long serialVersionUID = 2018008013449981807L;

    /** 非表字段***/

    @ApiModelProperty(value = "地市名称（本地网）")
    private java.lang.String cityName;

    @ApiModelProperty(value = "区县名称")
    private java.lang.String countyName;

    /** 非表字段***/

    //属性 begin
    /**
     * itemId
     */
    @ApiModelProperty(value = "itemId")
    private java.lang.Integer itemId;

    /**
     * 指标日期
     */
    @ApiModelProperty(value = "指标日期")
    private java.util.Date itemDate;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "供应商ID")
    private java.lang.String supplierId;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supplierCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supplierName;

    /**
     * 地市
     */
    @ApiModelProperty(value = "地市")
    private java.lang.String cityId;

    /**
     * 区县
     */
    @ApiModelProperty(value = "区县")
    private java.lang.String countyId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private java.lang.String goodsId;

    /**
     * 型号ID
     */
    @ApiModelProperty(value = "型号ID")
    private java.lang.String productBaseId;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private java.lang.String productBaseName;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private java.lang.String productId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private java.lang.String productName;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private java.lang.String brandId;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private java.lang.String brandName;

    /**
     * 档位
     */
    @ApiModelProperty(value = "档位")
    private java.lang.String priceLevel;

    /**
     * 总销量=地包发货出库量
     */
    @ApiModelProperty(value = "总销量=地包发货出库量")
    private java.lang.Long sellNum;

    /**
     * 销售额=销量*进店价
     */
    @ApiModelProperty(value = "销售额=销量*进店价")
    private java.lang.Long sellAmount;

    /**
     * 进货金额=交易进货量*省包供货价
     */
    @ApiModelProperty(value = "进货金额=交易进货量*省包供货价")
    private java.lang.Long purchaseAmount;

    /**
     * 交易进货量
     */
    @ApiModelProperty(value = "交易进货量")
    private java.lang.Long purchaseNum;

    /**
     * 手工录入量
     */
    @ApiModelProperty(value = "手工录入量")
    private java.lang.Long manualNum;

    /**
     * 调入量
     */
    @ApiModelProperty(value = "调入量")
    private java.lang.Long transInNum;

    /**
     * 调出量
     */
    @ApiModelProperty(value = "调出量")
    private java.lang.Long transOutNum;

    /**
     * 退库量
     */
    @ApiModelProperty(value = "退库量")
    private java.lang.Long returnNum;

    /**
     * 库存总量=入库总量—出库总量
     */
    @ApiModelProperty(value = "库存总量=入库总量—出库总量")
    private java.lang.Long stockNum;

    /**
     * 库存金额
     */
    @ApiModelProperty(value = "库存金额")
    private java.lang.Long stockAmount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private java.lang.String typeId;


}
