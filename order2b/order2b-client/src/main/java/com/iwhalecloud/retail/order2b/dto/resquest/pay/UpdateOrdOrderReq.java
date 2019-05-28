package com.iwhalecloud.retail.order2b.dto.resquest.pay;

import java.io.Serializable;

import lombok.Data;

@Data
public class UpdateOrdOrderReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private	String orderId;//订单编号
	private String payType;//支付方式 	1.翼支付 2.微信支付 3.支付宝
	private String paymentType;//	1.线上支付 2.线下支付
	private String paymentName;//支付名称
	private String payStatus;//付款情况	-2已退款，-1未付款，1已付款
	private String status;//初始状态：0 待审核：11 待卖家确认：12； 待支付：2； 待支付定金：13； 待支付尾款：14； 已支付、待受理：3 ； 卖家申请关闭：31 已受理待发货：4 ； 部分发货：41 已发货待确认：5 ； 确认收货：6 ； 已完成：10； 已退货：-1； 已退款：-2； 已换货：-3； 已关闭：-4； 审核不通过：-11 卖家确认不通过：-12 作废：-8 ； 撤单：-9 ； 取消订单：-10 ； 订单异常：99 ；
	private String payTime;

}
