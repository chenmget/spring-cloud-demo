package com.iwhalecloud.retail.report.dto.response;

import lombok.Data;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@Data
public class ReportDeSaleDaoResq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "supplierName")
	private String supplierName;//地包商名称
	
	@ApiModelProperty(value = "supplierCode")
	private String supplierCode;//地包商编码
	
	@ApiModelProperty(value = "productName")
	private String productName;//产品名称
	  
	@ApiModelProperty(value = "productBaseName")
	private String productBaseName;//产品型号
	  
	@ApiModelProperty(value = "typeName")
	private String typeName;//产品类型
	  
	@ApiModelProperty(value = "brandName")
	private String brandName;//品牌
	  
	@ApiModelProperty(value = "priceLevel")
	private String priceLevel;//机型档位
	  
	@ApiModelProperty(value = "totalRu")
	private String totalRu;//入库总量
	  
	@ApiModelProperty(value = "totalChu")
	private String totalChu;//出库总量
	  
	@ApiModelProperty(value = "stockNum")
	private String stockNum;//库存总量
	  
	@ApiModelProperty(value = "purchaseNum")
	private String purchaseNum;//交易进货量
	  
	@ApiModelProperty(value = "purchaseAmount")
	private String purchaseAmount;//进货金额
	  
	@ApiModelProperty(value = "manualNum")
	private String manualNum;//手工入库量
	  
	@ApiModelProperty(value = "totalInnum")
	private String transInNum;//调拨入库
	  
	@ApiModelProperty(value = "sellNum")
	private String sellNum;//总销售量
	  
	@ApiModelProperty(value = "sellAmount")
	private String sellAmount;//总销售额
	  
	@ApiModelProperty(value = "totalOutNum")
	private String transOutNum;//调拨出库
	  
	@ApiModelProperty(value = "returnNum")
	private String returnNum;//退库量
	  
	@ApiModelProperty(value = "weekAvgSellNum")
	private String weekAvgSellNum;//近7天日均销量
	  
	@ApiModelProperty(value = "stockAmount")
	private String stockAmount;//库存金额
	  
	@ApiModelProperty(value = "turnoverRate")
	private String turnoverRate;//库存周转率
	  
	@ApiModelProperty(value = "stockWarning")
	private String stockWarning;//库存预警
	
	@ApiModelProperty(value = "cityId")
	private String cityId;//地市
	
	@ApiModelProperty(value = "typeId")
	private String typeId;//产品类型ID
	
	@ApiModelProperty(value = "brandId")
	private String brandId;//品牌ID
	
	@ApiModelProperty(value = "itemDate")
	private String itemDate;//统计时间
}
