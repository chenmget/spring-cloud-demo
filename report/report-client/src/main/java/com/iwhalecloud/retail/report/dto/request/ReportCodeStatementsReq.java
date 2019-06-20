package com.iwhalecloud.retail.report.dto.request;

import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportCodeStatementsReq extends PageVO  {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "xdCreateTimeStart")
	private String xdCreateTimeStart; // 下单起时间
	
	@ApiModelProperty(value = "xdCreateTimeEnd")
    private String xdCreateTimeEnd; // 下单止时间     
	
	@ApiModelProperty(value = "lanIdName")
    private List<String> lanIdName; // 地市
	
	@ApiModelProperty(value = "OrgName")
    private List<String> OrgName; // 经营单元
	
	@ApiModelProperty(value = "statusCd")
    private String statusCd; // 串码状态
	
	@ApiModelProperty(value = "createTimeStart")
    private String createTimeStart; // 入库起时间
	
	@ApiModelProperty(value = "createTimeEnd")
    private String createTimeEnd; // 入库止时间
	
	@ApiModelProperty(value = "mktResInstType")
    private String mktResInstType; // 串码类型
	
	@ApiModelProperty(value = "sourceType")
    private String sourceType; // 串码来源
	
	@ApiModelProperty(value = "selfRegStatus")
    private String selfRegStatus; // 自注册状态
	
	@ApiModelProperty(value = "outTimeStart")
    private String outTimeStart; // 出库起时间
	
	@ApiModelProperty(value = "outTimeEnd")
    private String outTimeEnd; // 出库止时间
	
	@ApiModelProperty(value = "productType")
    private String productType;//产品类型
	
	@ApiModelProperty(value = "brandName")
    private String brandName; // 品牌
	
	@ApiModelProperty(value = "crmStatus")
    private String crmStatus;//CRM状态
	
	@ApiModelProperty(value = "mktResInstNbr")
    private String mktResInstNbr; // 串码 
	
	@ApiModelProperty(value = "unitType")
    private String unitType;//产品型号
	
	@ApiModelProperty(value = "productName")
    private String productName; // 产品名称
	
	@ApiModelProperty(value = "orderId")
    private String orderId; // 订单号
	
	@ApiModelProperty(value = "productCode")
    private String productCode; // 营销资源编码
	
	@ApiModelProperty(value = "destLanIdName")
    private List<String> destLanIdName; // 串码流入地市
	
	@ApiModelProperty(value = "supplierName")
    private String supplierName; // 供应商名称
	
	@ApiModelProperty(value = "supplierCode")
    private String supplierCode; // 供应商编码
	
	@ApiModelProperty(value = "destOrgName")
    private List<String> destOrgName; // 串码流向经营单元
	
	@ApiModelProperty(value = "partnerName")
	private String partnerName; // 零售商名称
	
	@ApiModelProperty(value = "partnerCode")
    private String partnerCode; // 零售商编码
	
	@ApiModelProperty(value = "businessEntityName")
    private String businessEntityName; // 经营主体名称
	
	@ApiModelProperty(value = "mktResStoreId")
	private String mktResStoreId; //仓库ID
}
