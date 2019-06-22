package com.iwhalecloud.retail.report.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RptPartnerOperatingDay implements Serializable {
	
	  private static final long serialVersionUID = 1L;
	  private String ProductBaseName;//机型
	  private String BrandName;//品牌
	  private String TheTotalInventory;//入库总量
	  private String TheTotalOutbound;//出库总量
	  private String StockTotalNum;//库存总量
	  private String PurchaseNum;//交易入库量
	  private String ManualNum;//手工入库量
	  private String TotalSalesNum;//总销售量
	  private String ManualSalesNum;//手工销售量
	  private String CRMContractNum;//CRM合约销量
	  private String RegisterNum;//自注册销量
	  private String ReturnNum;//退库量
	  private String AverageDailySales;//近7天日均销量
	  private String StockNum;//库存量
	  private String StockTurnover;//库存周转率
	  private String InventoryWarning;//库存预警
	  private String DATE;
	  private String STOCK_NUM;
	  private String PRODUCT_BASE_ID;
	  private String CONTRACT_NUM;
	  private String REGISTER_NUM;
}
