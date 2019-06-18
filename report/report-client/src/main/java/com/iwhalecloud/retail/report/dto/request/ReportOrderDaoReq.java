package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class ReportOrderDaoReq extends PageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createTimeStart;//下单时间开始
	private String createTimeEnd;//下单时间开始结束
	private String outTimeStart;//出库时间开始
	private String outTimeEnd;//出库时间结束
	private String status;// 订单状态
	private String businessEntityName;//经营主体名称
	private String paymentType;// 支付类型
	private String payType;// 支付方式
	private String activeName;// 营销活动
	private String sn;//25位编码
	private String brandName;//品牌
	private String productBaseId;// 机型
	private String orderType;// 交易类型
	private String orderCat;//订单类型	 当前都为普通分销
	private String suplierName;//供货商名称
	private String suplierCode;//供货商编码
	private String merchantName;//零售商名称
	private String merchantCode;//零售商编码
	private String orderId;//订单编码
	private String lanIdName;//地市
	private String city;//区县
	private String couponType; //优惠类型
	private String productName;//产品名称
	private String typeName;	//产品类型
	private String unitType;//产品型号
	
	private String userType;
	private String userId;
    

}
