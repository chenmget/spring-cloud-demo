package com.iwhalecloud.retail.report.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ReportOrderResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productName;//产品名称
	private String unitType;//产品型号
	private String typeName;//产品类型
	private String brandName;//产品品牌	
	private String attrValue1;//规格1
	private String attrValue2;//规格2
	private String attrValue3;//规格3
	private String orderId;//订单编号
	private String status;//订单状态
	private String sourceFrom;//订单来源
	private String orderCat;//订单类型
	private String orderType;//交易类型
	private String paymentType;//支付类型
	private String sn;//产品25位编码
	private int num;//数量(订单项表)
	private double price;//单价
	private double totalMoney;//总金额   单价*数量
	private int shipNum;//发货串码数量：关联订单项表取SHIP_NUM字段，该字段支持报表钻取，点击后展示发货串码列表，根据订单ID从订单项明细表（ORD_ORDER_ITEM_DETAIL）获取串码
	private String createTime;//下单时间
	private String payTime;//付款时间
	private String receiveTime;//发货时间
	private String activeName;//营销活动
	private String couponType;//优惠类型
	private double couponMoney;//优惠单价
	private double totalCouponMoney;//优惠总额
	private String supplierName;//供货商名称
	private String supplierCode;//供货商编码
	private String merchantName;//店中商名称
	private String merchantCode;//店中商编码
	private String businessEntityName;//店中商所属经营主体名称
	private String lanId;//店中商所属地市
	private String orgName;//经营单元
	private String itemId;
	private List<ReportOrderNbrResp> nbr;
}
