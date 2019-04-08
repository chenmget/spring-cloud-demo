package com.iwhalecloud.retail.report.dto.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReportDeSaleDaoResq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String supplierName;//地包商名称
	  private String supplierCode;//地包商编码
	  private String productName;//机型
	  private String brandName;//品牌
	  private String priceLevel;//机型档位
	  private String totalInnum;//入库总量
	  private String totalOutNum;//出库总量
	  private String sumPurchase;//交易进货量
	  private String amoutPurcchase;//进货金额
	  private String sumMANUAL;//手工入库量
	  private String sumTransIn;//调拨入库量
	  private String sumSellNum;//总销售量
	  private String sumSellAmout;//总销售额
	  private String sumTransOut;//调拨出库量
	  private String sumReturn;//退库量
	  private String daySale;//近7天的发货出库量/7天
	  private String sumStockDay;//库存总量
	  private String sumStockAmoutDay;//库存金额
	  private String sevenDayLv;//库存周转率
	  private String redStatus;//库存预警
	  private String userType;
	  private String userId;
	  private String typeId;//产品类型

}
