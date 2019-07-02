package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;

import io.swagger.annotations.ApiModelProperty;
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
	
	@ApiModelProperty(value = "itemId")
	private String itemId;//主键流水号(指标id)
	
	@ApiModelProperty(value = "itemDate")
	private String itemDate;//指标日期
	
	@ApiModelProperty(value = "supplierId")
	private String supplierId;//供应商id
	
	@ApiModelProperty(value = "supplierCode")
	private String supplierCode;//供应商编码
	
	@ApiModelProperty(value = "supplierName")
	private String supplierName;//供应商名称
	
	@ApiModelProperty(value = "cityId")
	private String cityId;//地市
	
	@ApiModelProperty(value = "countyId")
	private String countyId;//区县
	
	@ApiModelProperty(value = "goodsId")
	private String goodsId;//商品id
	
	@ApiModelProperty(value = "productBaseId")
	private String productBaseId;//型号id
	
	@ApiModelProperty(value = "productBaseName")
	private String productBaseName;//型号名称
	
	@ApiModelProperty(value = "productId")
	private String productId;//产品id
	
	@ApiModelProperty(value = "productName")
	private String productName;//产品名称
	
	@ApiModelProperty(value = "brandId")
	private String brandId;//品牌id
	
	@ApiModelProperty(value = "brandName")
	private String brandName;//品牌名称
	
	@ApiModelProperty(value = "priceLevel")
	private String priceLevel;//档位
	
	@ApiModelProperty(value = "sellNum")
	private String sellNum;//总销量=地包发货出库量
	
	@ApiModelProperty(value = "sellAmount")
	private String sellAmount;//销售额=销量*进店价
	
	@ApiModelProperty(value = "purchaseAmount")
	private String purchaseAmount;//进货金额=交易进货量*省包供货价
	
	@ApiModelProperty(value = "purchaseNum")
	private String purchaseNum;//交易进货量
	
	@ApiModelProperty(value = "manualNum")
	private String manualNum;//手工录入量
	
	@ApiModelProperty(value = "transInNum")
	private String transInNum;//调入量
	
	@ApiModelProperty(value = "transOutNum")
	private String transOutNum;//调出量
	
	@ApiModelProperty(value = "returnNum")
	private String returnNum;//退库量
	
	@ApiModelProperty(value = "stockNum")
	private String stockNum;//库存总量=入库总量—出库总量
	
	@ApiModelProperty(value = "stockAmount")
	private String stockAmount;//库存金额
	
	@ApiModelProperty(value = "createDate")
	private String createDate;//创建时间
	
	@ApiModelProperty(value = "typeId")
	private String typeId;	//产品类型
}