package com.iwhalecloud.retail.report.dto.response;

import lombok.Data;

import java.io.Serializable;
/**
 * 地市总进销存报表响应实体
 * @author
 */
@Data
public class ReportCityTotalInvResp implements Serializable {

	private static final long serialVersionUID = 1L;
	/**所属城市ID*/
	private String cityId;
	/**所属区县ID*/
	private String countyId;
	/**产品型号ID*/
	private String productBaseId;
	/**品牌ID*/
	private String brandId;
	/**所属城市*/
	private String cityName;
	/**所属区县*/
	private String countyName;
	/**机型*/
	private String productName;
	/**品牌*/
	private String brandName;
	/**机型档位*/
	private String priceLevel;
	/**入库总量*/
	private String totalInNum;
	/**出库总量*/
	private String totalOutNum;
	/**交易进货量*/
	private String purchaseNum;
	/**交易进货金额*/
	private String purchaseAmount;
	/**手工入库量*/
	private String manualNum;
	/**调拨入库量*/
	private String transInNum;
	/**总销售量*/
	private String totalSalesNum;
	/**总销售额*/
	private String totalSellAmount;
	/**手工核销量*/
	private String manualSalesNum;
	/**CRM合约销量*/
	private String crmContractNum;
	/**自注册销量*/
	private String registerNum;
	/**近7天日均销量*/
	private String avg7;
	/**库存总量*/
	private String stockNum;
	/**库存金额*/
	private String stockAmount;

	/**调拨出库量*/
	private String transOutNum;
	/**退库量*/
	private String returnNum;
}
