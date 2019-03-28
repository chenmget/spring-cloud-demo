package com.iwhalecloud.retail.report.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReportOrderResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private String id;//序号
	private String orderId;//订单号
	private String status;//订单状态
	private String sourceFrom;//订单来源
	private String type;//订单类型
	private String orderType;//交易类型
	
	private String createTime;//下单时间
	//private String outTime;//出库时间
	
	private String paymentType;//支付类型
	private String payTime;//付款时间
	private String payType;//支付方式
	private String catId;//产品类型
	private String brandId;//产品品牌
	private String unitName;//产品名称
	private String unitType;//产品型号
	private String sn;//产品25位编码
	private String num;//数量(订单项表)
	private String price;//单价
	private String sumMoney;//总金额   单价*数量
	private String shipNum;//发货串码数量：关联订单项表取SHIP_NUM字段，该字段支持报表钻取，点击后展示发货串码列表，根据订单ID从订单项明细表（ORD_ORDER_ITEM_DETAIL）获取串码
	
	private String receiveTime;//串码出库时间
	
	private String couponActive;//营销活动
	private String couponType;//优惠类型
	private String couponMoney;//优惠额度
	private String merchantName;//供货商名称：通过SUPPLIER_ID关联商家表（par_merchant），取MERCHANT_NAME字段
	private String merchantCode;//供货商编码：通过SUPPLIER_ID关联商家表（par_merchant），取MERCHANT_CODE字段
	private String merchantName1;//店中商名称：通过USER_ID关联商家表（par_merchant），取MERCHANT_NAME字段
	private String merchantCode1;//店中商编码：通过USER_ID关联商家表（par_merchant），取MERCHANT_CODE字段
	private String businessEntityName;//店中商所属经营主体名称：通过USER_ID关联商家表（par_merchant），取BUSINESS_ENTITY_NAME
	private String lanId;//店中商所属地市：通过USER_ID关联商家表（par_merchant），取LAN_ID字段
	private String city;//店中商所属区县：通过USER_ID关联商家表（par_merchant），取CITY字段
	private String resNbr;
	private String userType;
	private String userId;
	private List<ReportOrderNbrResp> nbr;
}
