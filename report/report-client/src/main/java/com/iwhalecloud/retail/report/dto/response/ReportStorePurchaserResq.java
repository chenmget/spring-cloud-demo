package com.iwhalecloud.retail.report.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportStorePurchaserResq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String productName; //#产品名称
	private String productBaseName; //#产品型号
	private String productType; //# 产品类型
	private String brandName; //# 品牌
	private String priceLevel; //#机型档位
	private String lanIdName; // 地市
	private String totalInNum; //#入库总量
	private String totalOutNum; //#出库总量
	private String stockNum; //#库存总量
	private String purchaseNum; //#交易入库量
	private String manualNum; //#手工入库量
	private String sellNum; //#总销售量
	private String uncontractNum; //#手工核销量
	private String contractNum; //#CRM合约销量
	private String registerNum; //#自注册效率
	private String returnNum; //#退库量
	private String weekAvgSellNum; //#近7天日均销量

}
