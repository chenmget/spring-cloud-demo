package com.iwhalecloud.retail.report.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenlong.zhong
 * @date 2019/6/11
 */
@Data
@ApiModel(value = "地包进销存 数据 汇总 分页查询（按地包商的维度）返回对象，对应模型rpt_supplier_operating_day, 对应实体ReportSupplierOperatingDay类")
public class SummarySaleBySupplierPageResp implements java.io.Serializable {

    @ApiModelProperty(value = "供应商ID")
    private java.lang.String supplierId;

    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supplierName;

    @ApiModelProperty(value = "总销量=地包发货出库量")
    private java.lang.Long sellNum;

    @ApiModelProperty(value = "销售额=销量*进店价")
    private java.lang.Long sellAmount;

    @ApiModelProperty(value = "进货金额=交易进货量*省包供货价")
    private java.lang.Long purchaseAmount;

    @ApiModelProperty(value = "交易进货量")
    private java.lang.Long purchaseNum;

    @ApiModelProperty(value = "手工录入量")
    private java.lang.Long manualNum;

    @ApiModelProperty(value = "调入量")
    private java.lang.Long transInNum;

    @ApiModelProperty(value = "调出量")
    private java.lang.Long transOutNum;

    @ApiModelProperty(value = "退库量")
    private java.lang.Long returnNum;

    @ApiModelProperty(value = "库存总量=入库总量—出库总量")
    private java.lang.Long stockNum;

    @ApiModelProperty(value = "库存金额")
    private java.lang.Long stockAmount;

    @ApiModelProperty(value = "入库总量= 手工录入量 + 调入量 + 交易进货量")
    private java.lang.Long totalInNum;

    @ApiModelProperty(value = "出库总量 = 退库量 + 调出量 + 总销量")
    private java.lang.Long totalOutNum;

    @ApiModelProperty(value = "近7天日均销量")
    private Float averageDailySales;

}
