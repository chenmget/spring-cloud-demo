package com.iwhalecloud.retail.report.dto.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReportStSaleDaoResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String partnerName; 	//零售商名称：取PARTNER_NAME字段；
	private String partnerCode; 	//店中商编码：取PARTNER_CODE字段
	private String businessEntityName; //零售商所属经营主体名称：取BUSINESS_ENTITY_NAME字段；
	private String cityId; 		//零售商所属地市
	private String orgName; 		//零售商所属经营单元
	private String productName;   //产品名称
	private String productBaseName; //产品型号
	private String productType; 		//产品类型
	private String brandName; 		//品牌：取BRAND_NAME字段
	private String priceLevel; 		//机型档位：取PRICE_LEVEL字段
	private String totalInNum; 	    //总入库量：取TOTAL_IN_NUM字段求和
	private String totalOutNum; 	//出库总量：取TOTAL_OUT_NUM字段求和
	private String stockNum; 		//总库存量：取STOCK_NUM字段
	private String purchaseNum; 	//交易入库量：取PURCHASE_NUM字段求和
	private String manualNum; 		//手工入库量：取MANUAL_NUM字段求和
	private String transInNum; 	//调拨入库量：取TRANS_IN_NUM字段求和
	private String purchaseAmount; 	//进货金额：取PURCHASE_AMOUNT字段求和
	private String sellNum; 		//总销售量：取SELL_NUM字段求和
	private String sellAmount; 		//总销售额：总销售量*零售价
	private String uncontractNum; 	//手工核销（已售未补贴）：取UNCONTRACT_NUM字段求和
	private String contractNum; 	//CRM合约销量：取CONTRACT_NUM字段求和
	private String registerNum; 	//自注册销量：取REGISTER_NUM字段求和
	private String weekAvgSellNum; 	//近7天日均销量：取WEEK_AVG_SELL_NUM字段
	private String stockAmount; 	//库存金额：取STOCK_AMOUNT字段
	private String turnoverRate; 	//库存周转率：取TURNOVER_RATE字段		
	private String stockWarning; 	//库存预警：库存周转率>=10为充裕；5<=库存周转率<10为缺货 库存周转率<5为严重缺货
	
}
