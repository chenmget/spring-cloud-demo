package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ReportCodeStatementsReq extends PageVO  {
	
	private static final long serialVersionUID = 1L;
	private String xdCreateTimeStart; // 下单起时间
    private String xdCreateTimeEnd; // 下单止时间     
    private String createTimeStart; // 入库起时间
    private String createTimeEnd; // 入库止时间
    private String outTimeStart; // 出库起时间
    private String outTimeEnd; // 出库止时间
    private String statusCd; // 串码状态
    private String sourceType; // 串码来源
    private String selfRegStatus; // 自注册状态
    private String brandName; // 品牌
    private String productBaseId; // 机型
	private String destCityId; // 串码流向区县
	private String destCountyId; // 串码流入地市
	private String partnerName; // 零售商名称
    private String partnerCode; // 零售商编码
    private String businessEntityName; // 经营主体名称
    private String orderId; // 订单号
    private String productCode; // 产品编码
    private String supplierName; // 供应商名称
    private String supplierCode; // 供应商编码
    private String countyId; // 区县
    private String cityId; // 地市
    private String mktResInstNbr; // 串码 
	private String mktResInstType; // 串码类型
    private String productType;//产品类型
    private String mktResStoreId;//仓库ID


}
