package com.iwhalecloud.retail.report.dto.request;

import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportOrderDaoReq extends PageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "createTimeStart")
	private String createTimeStart;//下单时间开始
	
	@ApiModelProperty(value = "createTimeEnd")
	private String createTimeEnd;//下单时间开始结束
	
	@ApiModelProperty(value = "outTimeStart")
	private String outTimeStart;//出库时间开始
	
	@ApiModelProperty(value = "outTimeEnd")
	private String outTimeEnd;//出库时间结束
	
	@ApiModelProperty(value = "status")
	private String status;// 订单状态
	
	@ApiModelProperty(value = "businessEntityName")
	private String businessEntityName;//经营主体名称
	
	@ApiModelProperty(value = "paymentType")
	private String paymentType;// 支付类型
	
	@ApiModelProperty(value = "payType")
	private String payType;// 支付方式
	
	@ApiModelProperty(value = "activeName")
	private String activeName;// 营销活动
	
	@ApiModelProperty(value = "sn")
	private String sn;//25位编码
	
	@ApiModelProperty(value = "brandName")
	private String brandName;//品牌
	
	@ApiModelProperty(value = "productBaseId")
	private String productBaseId;// 机型
	
	@ApiModelProperty(value = "orderType")
	private String orderType;// 交易类型
	
	@ApiModelProperty(value = "orderCat")
	private String orderCat;//订单类型	 当前都为普通分销
	
	@ApiModelProperty(value = "suplierName")
	private String suplierName;//供货商名称
	
	@ApiModelProperty(value = "suplierCode")
	private String suplierCode;//供货商编码
	
	@ApiModelProperty(value = "merchantName")
	private String merchantName;//零售商名称
	
	@ApiModelProperty(value = "merchantCode")
	private String merchantCode;//零售商编码
	
	@ApiModelProperty(value = "orderId")
	private String orderId;//订单编码
	
	@ApiModelProperty(value = "lanIdName")
	private List<String> lanIdList;//地市
	
	@ApiModelProperty(value = "orgName")
	private String orgName;//经营单元
	
	@ApiModelProperty(value = "productName")
	private String productName;//产品名称
	
	@ApiModelProperty(value = "typeName")
	private String typeName;	//产品类型
	
	@ApiModelProperty(value = "unitType")
	private String unitType;//产品型号

}
