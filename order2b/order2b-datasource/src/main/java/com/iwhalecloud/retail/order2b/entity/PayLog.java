package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * PayLog
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("pay_log")
@KeySequence(value="seq_pay_log_id",clazz = String.class)
@ApiModel(value = "对应模型pay_log, 对应实体PayLog类")
public class PayLog implements Serializable {
    /**表名常量*/
    public static final String TNAME = "pay_log";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 支付流水ID 
  	 */
	@TableId
	@ApiModelProperty(value = "支付流水ID ")
  	private java.lang.String id;
  	
  	/**
  	 * 记录应用类型：B2B：hnyhj_b2b；B2C：hnyhj_b2c；收钱哆：hnsqd
  	 */
	@ApiModelProperty(value = "记录应用类型：B2B：hnyhj_b2b；B2C：hnyhj_b2c；收钱哆：hnsqd")
  	private java.lang.String platformType;
  	
  	/**
  	 * 记录支付类型：1001 线下支付，1002 扫码支付，1003 POS支付，1004 收银台支付，1005 云货架2B线下支付
  	 */
	@ApiModelProperty(value = "记录支付类型：1001 线下支付，1002 扫码支付，1003 POS支付，1004 收银台支付，1005 云货架2B线下支付")
  	private java.lang.String requestType;
  	
  	/**
  	 * 支付对应的订单编号
  	 */
	@ApiModelProperty(value = "支付对应的订单编号")
  	private java.lang.String orderId;
  	
  	/**
  	 * 用于区分支付对接平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版
  	 */
	@ApiModelProperty(value = "用于区分支付对接平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版")
  	private java.lang.String payPlatformId;
  	
  	/**
  	 * 记录支付对接平台流水
  	 */
	@ApiModelProperty(value = "记录支付对接平台流水")
  	private java.lang.String payPlatformOrderid;
  	
  	/**
  	 * 记录支付接口类型：1001 聚合支付码GET方式，1002 聚合支付码POST方式，1003 收银台支付（专用于云货架2B支付），1004 线下支付（专用于云货架）
  	 */
	@ApiModelProperty(value = "记录支付接口类型：1001 聚合支付码GET方式，1002 聚合支付码POST方式，1003 收银台支付（专用于云货架2B支付），1004 线下支付（专用于云货架）")
  	private java.lang.String payType;
  	
  	/**
  	 * 用于区分付费/退费：1001 收费，1002 退费，1003 预付费 
  	 */
	@ApiModelProperty(value = "用于区分付费/退费：1001 收费，1002 退费，1003 预付费 ")
  	private java.lang.String operationType;
  	
  	/**
  	 * 用于区分客户支付终端：1001 二维码，1002 POS，1003 其他
  	 */
	@ApiModelProperty(value = "用于区分客户支付终端：1001 二维码，1002 POS，1003 其他")
  	private java.lang.String terminalType;
  	
  	/**
  	 * 记录支付终端设备编号
  	 */
	@ApiModelProperty(value = "记录支付终端设备编号")
  	private java.lang.String terminalId;
  	
  	/**
  	 * 创建该日志的操作员编号
  	 */
	@ApiModelProperty(value = "创建该日志的操作员编号")
  	private java.lang.String createStaff;
  	
  	/**
  	 * 支付商家在收钱哆/云货架登记的商家编码
  	 */
	@ApiModelProperty(value = "支付商家在收钱哆/云货架登记的商家编码")
  	private java.lang.String commercialId;
  	
  	/**
  	 * 支付商家在收钱哆/云货架登记的翼支付账号
  	 */
	@ApiModelProperty(value = "支付商家在收钱哆/云货架登记的翼支付账号")
  	private java.lang.String wingId;
  	
  	/**
  	 * 支付商家在翼支付登记的商家编码
  	 */
	@ApiModelProperty(value = "支付商家在翼支付登记的商家编码")
  	private java.lang.String merchantId;
  	
  	/**
  	 * 使用的付款账号
  	 */
	@ApiModelProperty(value = "使用的付款账号")
  	private java.lang.String winpayId;
  	
  	/**
  	 * 订单实际金额
  	 */
	@ApiModelProperty(value = "订单实际金额")
  	private java.lang.Long orderMoney;
  	
  	/**
  	 * 支付平台支付时产生的手续费
  	 */
	@ApiModelProperty(value = "支付平台支付时产生的手续费")
  	private java.lang.Long chargeMoney;
  	
  	/**
  	 * 默认使用CNY（人民币）
  	 */
	@ApiModelProperty(value = "默认使用CNY（人民币）")
  	private java.lang.String moneyType;
  	
  	/**
  	 * 用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成
  	 */
	@ApiModelProperty(value = "用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成")
  	private java.lang.String payStatus;
  	
  	/**
  	 * 支付日志创建时间
  	 */
	@ApiModelProperty(value = "支付日志创建时间")
  	private java.util.Date createDate;
  	
  	/**
  	 * 支付日志更新时间，首次创建时等于创建时间
  	 */
	@ApiModelProperty(value = "支付日志更新时间，首次创建时等于创建时间")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 登记请求信息
  	 */
	@ApiModelProperty(value = "登记请求信息")
  	private java.lang.String payData;
  	
  	/**
  	 * 加密后的请求信息
  	 */
	@ApiModelProperty(value = "加密后的请求信息")
  	private java.lang.String payDataMd;
  	
  	/**
  	 * 支付日志更新时所使用的用户ID，首次创建时为创建用户
  	 */
	@ApiModelProperty(value = "支付日志更新时所使用的用户ID，首次创建时为创建用户")
  	private java.lang.String updateStaff;

	/**
	 * 接收方银行编码
	 */
	@ApiModelProperty(value = "接收方银行编码")
	private java.lang.String recBankId;

	/**
	 * 接收方银行账号
	 */
	@ApiModelProperty(value = "接收方银行账号")
	private java.lang.String recAccount;

	/**
	 * 接收方账号名称
	 */
	@ApiModelProperty(value = "接收方账号名称")
	private java.lang.String recAccountName;




	private String sourceFrom;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 支付流水ID . */
		id("id","ID"),
		
		/** 记录应用类型：B2B：hnyhj_b2b；B2C：hnyhj_b2c；收钱哆：hnsqd. */
		platformType("platformType","PLATFORM_TYPE"),
		
		/** 记录支付类型：1001 线下支付，1002 扫码支付，1003 POS支付，1004 收银台支付，1005 云货架2B线下支付. */
		requestType("requestType","REQUEST_TYPE"),
		
		/** 支付对应的订单编号. */
		orderId("orderId","ORDER_ID"),
		
		/** 用于区分支付对接平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版. */
		payPlatformId("payPlatformId","PAY_PLATFORM_ID"),
		
		/** 记录支付对接平台流水. */
		payPlatformOrderid("payPlatformOrderid","PAY_PLATFORM_ORDERID"),
		
		/** 记录支付接口类型：1001 聚合支付码GET方式，1002 聚合支付码POST方式，1003 收银台支付（专用于云货架2B支付），1004 线下支付（专用于云货架）. */
		payType("payType","PAY_TYPE"),
		
		/** 用于区分付费/退费：1001 收费，1002 退费，1003 预付费 . */
		operationType("operationType","OPERATION_TYPE"),
		
		/** 用于区分客户支付终端：1001 二维码，1002 POS，1003 其他. */
		terminalType("terminalType","TERMINAL_TYPE"),
		
		/** 记录支付终端设备编号. */
		terminalId("terminalId","TERMINAL_ID"),
		
		/** 创建该日志的操作员编号. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 支付商家在收钱哆/云货架登记的商家编码. */
		commercialId("commercialId","COMMERCIAL_ID"),
		
		/** 支付商家在收钱哆/云货架登记的翼支付账号. */
		wingId("wingId","WING_ID"),
		
		/** 支付商家在翼支付登记的商家编码. */
		merchantId("merchantId","MERCHANT_ID"),
		
		/** 使用的付款账号. */
		winpayId("winpayId","WINPAY_ID"),
		
		/** 订单实际金额. */
		orderMoney("orderMoney","ORDER_MONEY"),
		
		/** 支付平台支付时产生的手续费. */
		chargeMoney("chargeMoney","CHARGE_MONEY"),
		
		/** 默认使用CNY（人民币）. */
		moneyType("moneyType","MONEY_TYPE"),
		
		/** 用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成. */
		payStatus("payStatus","PAY_STATUS"),
		
		/** 支付日志创建时间. */
		createDate("createDate","CREATE_DATE"),
		
		/** 支付日志更新时间，首次创建时等于创建时间. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 登记请求信息. */
		payData("payData","PAY_DATA"),
		
		/** 加密后的请求信息. */
		payDataMd("payDataMd","PAY_DATA_MD"),
		
		/** 支付日志更新时所使用的用户ID，首次创建时为创建用户. */
		updateStaff("updateStaff","UPDATE_STAFF");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

}
