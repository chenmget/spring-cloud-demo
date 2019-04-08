package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;
/**
 * 
 * @author Administrator
 *	供应商经营日报表表
 */
@Data
public class RptSupplierOperatingDayReq extends PageVO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemId;//主键流水号(指标id)
	private String itemDate;//指标日期
	private String supplierId;//供应商id
	private String supplierCode;//供应商编码
	private String supplierName;//供应商名称
	private String cityId;//地市
	private String countyId;//区县
	private String goodsId;//商品id
	private String productBaseId;//型号id
	private String productBaseName;//型号名称
	private String productId;//产品id
	private String productName;//产品名称
	private String brandId;//品牌id
	private String brandName;//品牌名称
	private String priceLevel;//档位
	private String sellNum;//总销量=地包发货出库量
	private String sellAmount;//销售额=销量*进店价
	private String purchaseAmount;//进货金额=交易进货量*省包供货价
	private String purchaseNum;//交易进货量
	private String manualNum;//手工录入量
	private String transInNum;//调入量
	private String transOutNum;//调出量
	private String returnNum;//退库量
	private String stockNum;//库存总量=入库总量—出库总量
	private String stockAmount;//库存金额
	private String createDate;//创建时间
	private String typeId;	//产品类型
}
