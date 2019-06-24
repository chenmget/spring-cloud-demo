package com.iwhalecloud.retail.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SupplierOperatingDay
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("rpt_supplier_operating_day")
@ApiModel(value = "对应模型rpt_supplier_operating_day, 对应实体ReportSupplierOperatingDay类")
public class SupplierOperatingDay implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "rpt_supplier_operating_day";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * itemId
     */
    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "itemId")
    private Integer itemId;

    /**
     * 指标日期
     */
    @ApiModelProperty(value = "指标日期")
    private java.util.Date itemDate;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 地市
     */
    @ApiModelProperty(value = "地市")
    private String cityId;

    /**
     * 区县
     */
    @ApiModelProperty(value = "区县")
    private String countyId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    /**
     * 型号ID
     */
    @ApiModelProperty(value = "型号ID")
    private String productBaseId;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String productBaseName;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 档位
     */
    @ApiModelProperty(value = "档位")
    private String priceLevel;

    /**
     * 总销量=地包发货出库量
     */
    @ApiModelProperty(value = "总销量=地包发货出库量")
    private Long sellNum;

    /**
     * 销售额=销量*进店价
     */
    @ApiModelProperty(value = "销售额=销量*进店价")
    private Long sellAmount;

    /**
     * 进货金额=交易进货量*省包供货价
     */
    @ApiModelProperty(value = "进货金额=交易进货量*省包供货价")
    private Long purchaseAmount;

    /**
     * 交易进货量
     */
    @ApiModelProperty(value = "交易进货量")
    private Long purchaseNum;

    /**
     * 手工录入量
     */
    @ApiModelProperty(value = "手工录入量")
    private Long manualNum;

    /**
     * 调入量
     */
    @ApiModelProperty(value = "调入量")
    private Long transInNum;

    /**
     * 调出量
     */
    @ApiModelProperty(value = "调出量")
    private Long transOutNum;

    /**
     * 退库量
     */
    @ApiModelProperty(value = "退库量")
    private Long returnNum;

    /**
     * 库存总量=入库总量—出库总量
     */
    @ApiModelProperty(value = "库存总量=入库总量—出库总量")
    private Long stockNum;

    /**
     * 库存金额
     */
    @ApiModelProperty(value = "库存金额")
    private Long stockAmount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String typeId;


    //属性 end

    /**
     * 字段名称枚举.
     */
    public enum FieldNames {
        /**
         * itemId.
         */
        itemId("itemId", "ITEM_ID"),

        /**
         * 指标日期.
         */
        itemDate("itemDate", "ITEM_DATE"),

        /**
         * 供应商ID.
         */
        supplierId("supplierId", "SUPPLIER_ID"),

        /**
         * 供应商编码.
         */
        supplierCode("supplierCode", "SUPPLIER_CODE"),

        /**
         * 供应商名称.
         */
        supplierName("supplierName", "SUPPLIER_NAME"),

        /**
         * 地市.
         */
        cityId("cityId", "CITY_ID"),

        /**
         * 区县.
         */
        countyId("countyId", "COUNTY_ID"),

        /**
         * 商品ID.
         */
        goodsId("goodsId", "GOODS_ID"),

        /**
         * 型号ID.
         */
        productBaseId("productBaseId", "PRODUCT_BASE_ID"),

        /**
         * 型号名称.
         */
        productBaseName("productBaseName", "PRODUCT_BASE_NAME"),

        /**
         * 产品ID.
         */
        productId("productId", "PRODUCT_ID"),

        /**
         * 产品名称.
         */
        productName("productName", "PRODUCT_NAME"),

        /**
         * 品牌ID.
         */
        brandId("brandId", "BRAND_ID"),

        /**
         * 品牌名称.
         */
        brandName("brandName", "BRAND_NAME"),

        /**
         * 档位.
         */
        priceLevel("priceLevel", "PRICE_LEVEL"),

        /**
         * 总销量=地包发货出库量.
         */
        sellNum("sellNum", "SELL_NUM"),

        /**
         * 销售额=销量*进店价.
         */
        sellAmount("sellAmount", "SELL_AMOUNT"),

        /**
         * 进货金额=交易进货量*省包供货价.
         */
        purchaseAmount("purchaseAmount", "PURCHASE_AMOUNT"),

        /**
         * 交易进货量.
         */
        purchaseNum("purchaseNum", "PURCHASE_NUM"),

        /**
         * 手工录入量.
         */
        manualNum("manualNum", "MANUAL_NUM"),

        /**
         * 调入量.
         */
        transInNum("transInNum", "TRANS_IN_NUM"),

        /**
         * 调出量.
         */
        transOutNum("transOutNum", "TRANS_OUT_NUM"),

        /**
         * 退库量.
         */
        returnNum("returnNum", "RETURN_NUM"),

        /**
         * 库存总量=入库总量—出库总量.
         */
        stockNum("stockNum", "STOCK_NUM"),

        /**
         * 库存金额.
         */
        stockAmount("stockAmount", "STOCK_AMOUNT"),

        /**
         * 创建时间.
         */
        createDate("createDate", "CREATE_DATE"),

        /**
         * 产品类型.
         */
        typeId("typeId", "TYPE_ID");

        private String fieldName;
        private String tableFieldName;

        FieldNames(String fieldName, String tableFieldName) {
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }

}
