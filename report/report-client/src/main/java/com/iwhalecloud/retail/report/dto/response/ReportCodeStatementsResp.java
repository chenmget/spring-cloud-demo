package com.iwhalecloud.retail.report.dto.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReportCodeStatementsResp implements Serializable  {
	
	/**
	 * 
	*/
	private static final long serialVersionUID = 1L;
	private String mktResInstNbr;//串码
	private String storageType;//在库状态
	private String mktResInstType;//串码类型
	private String sourceType;//串码来源
	private String productType;//产品类型
	private String brandId;//品牌
	private String productBaseName;//产品名称
	private String productName;//产品型号
	private String productCode;//产品25位编码
	private String orderId;//订单编号
	private String createTime;//下单时间
	private String supplierName;//供应商名称
	private String supplierCode;//供应商编码
	private String partnerName;//零售商名称
	private String partnerCode;//店中商编码（零售商编码）
	private String cityId;//店中商所属地市
	private String countyId;//店中商所属区县
	private String businessEntityName;//店中商所属经营主体名称
	private String receiveTime;//入库时间
	private String outTime;//出库时间
	private String stockAge;//库龄
	private String day30;//是否超过30天久库存
	private String day60;//是否超过60天久库存
	private String day90;//是否超过90天久库存
	private String destMerchantId;//串码流向
	private String destCityId;//串码流向所属地市
	private String destCountyId;//串码流向所属区县
	private String selfRegStatus;//自注册状态
	private	String typeId;	//产品类型
	private String mktResStoreId; //仓库ID
}