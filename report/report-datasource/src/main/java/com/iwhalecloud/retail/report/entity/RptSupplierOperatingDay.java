package com.iwhalecloud.retail.report.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class RptSupplierOperatingDay implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String supplierName;//地包商名称
	  private String supplierId;//地包商编码
	  private String productName;//机型
	  private String brandName;//品牌
	  private String priceLevel;//机型档位
	  private String totalInnum;//入库总量
	  private String totalOutNum;//出库总量
	  private String sumPurchase;//交易进货量
	  private String amoutPurcchase;//进货金额
	  private String manualNum;//手工入库量
	  private String transInNum;//调拨入库量
	  private String sellNum;//总销售量
	  private String sellAmout;//总销售额
	  private String transOutNum;//调拨出库量
	  private String returnNum;//退库量
	  private String daySale;//近7天的发货出库量/7天
	  private String sumStockDay;//库存总量
	  private String sumStockAmoutDay;//库存金额
	  private String sevenDayLv;//库存周转率
	  private String redStatus;//库存预警
}
