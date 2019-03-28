package com.iwhalecloud.retail.report.dto.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReportStSaleDaoResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String partnerCode;//零售商编码
		private String partnerName;//零售商名称
		private String businessEntityName;//所属经营主体
		private String cityId;//所属城市
		private String countryId;//所属区县
	//private String productName;//机型
	private String productBaseName;//机型
	private String brandName;//品牌
	private String priceLevel;//机型档位
	private String TheTotalInventory;//入库总量
	//private String totalOutNum;//出库总量
	private String TheTotalOutbound;//出库总量
	private String stockNum;//库存量
	//private String stockTotalNum;//库存总量
	private String purchaseNum;//交易进货量
	private String manualNum;//手工入库量
		private String transInNum;//调拨入库量
		private String purchaseAmount;//进货金额
	private String TotalSalesNum;//总销售量
		private String sellAmount;//总销售额
	//private String unContractNum;//手工核销
	private String ManualSalesNum;//手工核销
	private String CRMContractNum;//CRM合约销量
	private String registerNum;//自注册销量
	private String AverageDailySales;//自注册激活量与CRM合约销量并集/7天
		private String stockAmount;//库存金额
	//private String sumStockAmoutDay;//库存金额
	private String stockTurnover;//库存周转率
	private String InventoryWarning;//库存预警
	private String returnNum;//退库量
	private String transOutNum;//调拨出库量 
	private String userType;
	private String userId;
}
