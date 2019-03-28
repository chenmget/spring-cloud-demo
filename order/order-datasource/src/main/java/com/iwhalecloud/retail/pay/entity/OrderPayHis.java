package com.iwhalecloud.retail.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单支付流水
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ORDER_PAY_HIS")
@ApiModel(value = "对应模型ORDER_PAY, 对应实体OrderPay类")
public class OrderPayHis implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ORDER_PAY_HIS";
  	private static final long serialVersionUID = 1L;


  	//属性 begin
  	/**
  	 * transactionId
  	 */
	@ApiModelProperty(value = "transactionId")
  	private String transactionId;

  	/**
  	 * 状态:插入_1000;支付成功_1100;支付失败_1200 :
  	 */
	@ApiModelProperty(value = "状态:插入_1000;支付成功_1100;支付失败_1200 :")
  	private String status;

  	/**
  	 * 支付渠道编码(支付平台分配）
BEST：翼支付
ALI：支付宝
WX：微信
TEN: 财付通
JT ： 集团电渠
说明：如需使用该字段，需向电渠支付网关管理负责人申请开通。

  	 */
	@ApiModelProperty(value = "支付渠道编码(支付平台分配） BEST：翼支付 ALI：支付宝 WX：微信 TEN: 财付通 JT ： 集团电渠 说明：如需使用该字段，需向电渠支付网关管理负责人申请开通。")
  	private String payChannel;

  	/**
  	 * 由业务渠道提供，建议：yyyyMMddhhmmss + 6位随机数
  	 */
	@ApiModelProperty(value = "由业务渠道提供，建议：yyyyMMddhhmmss + 6位随机数")
  	private String requestSeq;

  	/**
  	 * 支付类型编码（支付平台定义）WEB：web网关
WAP：wap网关
NATIVE：扫码
JSAPI : 微信公众号
MWEB：微信H5
INST：分期支付
  	 */
	@ApiModelProperty(value = "支付类型编码（支付平台定义）WEB：web网关 WAP：wap网关 NATIVE：扫码 JSAPI : 微信公众号 MWEB：微信H5 INST：分期支付")
  	private String payType;

  	/**
  	 * 请求时间
  	 */
	@ApiModelProperty(value = "请求时间")
  	private java.util.Date requestTime;

  	/**
  	 * 支付金额 单位：分
  	 */
	@ApiModelProperty(value = "支付金额 单位：分")
  	private Long payAmount;

  	/**
  	 * 前台返回地址:用于接收交易返回的前台url。支付类型为WEB,WAP时此字段必填
  	 */
	@ApiModelProperty(value = "前台返回地址:用于接收交易返回的前台url。支付类型为WEB,WAP时此字段必填")
  	private String returnUrl;

  	/**
  	 * 后台返回地址:用于接收交易返回的后台url
  	 */
	@ApiModelProperty(value = "后台返回地址:用于接收交易返回的后台url")
  	private String notifyUrl;

  	/**
  	 * 银行编码:此参数对应银行编码表
  	 */
	@ApiModelProperty(value = "银行编码:此参数对应银行编码表")
  	private String bankId;

	/**
	 * 订单ID
	 */
	@ApiModelProperty(value = "订单ID")
	private String orderId;

  	/**
  	 * 分账明细 分账填写,不分账为空。格式：account1:800|account2:100|account3:100
  	 */
	@ApiModelProperty(value = "分账明细 分账填写,不分账为空。格式：account1:800|account2:100|account3:100")
  	private String divDetails;

  	/**
  	 * 分期数 只有选择银行分期支付时，此项才会校验
中国工商银行:分期期数：3，6，9，12，18，24
 中国银行: 分期期数：3，6，12，24

  	 */
	@ApiModelProperty(value = "分期数 只有选择银行分期支付时，此项才会校验 中国工商银行:分期期数：3，6，9，12，18，24 中国银行: 分期期数：3，6，12，24 ")
  	private String pedCnt;

  	/**
  	 * 客户端IP 用户IP
  	 */
	@ApiModelProperty(value = "客户端IP 用户IP")
  	private String clientIp;

  	/**
  	 * 指定支付方式
  	 */
	@ApiModelProperty(value = "指定支付方式")
  	private String limitPay;

  	/**
  	 * 订单信息域
  	 */
	@ApiModelProperty(value = "订单信息域")
  	private String orderInfo;

  	/**
  	 * 附加信息
  	 */
	@ApiModelProperty(value = "附加信息")
  	private String attachInfo;

	/**
	 * 支付单id
	 */
	@ApiModelProperty(value = "支付单id 支付平台生成的支付单主ID，业务渠道保存")
	private String payId;

	/**
	 * 支付平台请求支付渠道交易流水
	 */
	@ApiModelProperty(value = "支付平台请求支付渠道交易流水")
	private String transSeq;

	/**
	 * 支付渠道流水号
	 */
	@ApiModelProperty(value = "支付渠道流水号")
	private String upTransSeq;

	/**
	 * 支付渠道请求银行流水号
	 */
	@ApiModelProperty(value = "支付渠道请求银行流水号")
	private String payChanlReqTransSeq;

	/**
	 * 银行流水号
	 */
	@ApiModelProperty(value = "银行流水号")
	private String bankTransSeq;

	private java.util.Date createDate;

	private java.util.Date updateDate;
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** transactionId. */
		transactionId("transactionId","TRANSACTION_ID"),
		
		/** 状态:插入_1000;支付成功_1100;支付失败_1200 :. */
		status("status","STATUS"),
		
		/** 支付渠道编码(支付平台分配）
BEST：翼支付
ALI：支付宝
WX：微信
TEN: 财付通
JT ： 集团电渠
说明：如需使用该字段，需向电渠支付网关管理负责人申请开通。
. */
		payChannel("payChannel","PAY_CHANNEL"),
		
		/** 由业务渠道提供，建议：yyyyMMddhhmmss + 6位随机数. */
		requestSeq("requestSeq","REQUEST_SEQ"),
		
		/** 支付类型编码（支付平台定义）WEB：web网关
WAP：wap网关
NATIVE：扫码
JSAPI : 微信公众号
MWEB：微信H5
INST：分期支付. */
		payType("payType","PAY_TYPE"),
		
		/** 请求时间. */
		requestTime("requestTime","REQUEST_TIME"),
		
		/** 支付金额 单位：分. */
		payAmount("payAmount","PAY_AMOUNT"),
		
		/** 前台返回地址:用于接收交易返回的前台url。支付类型为WEB,WAP时此字段必填. */
		returnUrl("returnUrl","RETURN_URL"),
		
		/** 后台返回地址:用于接收交易返回的后台url. */
		notifyUrl("notifyUrl","NOTIFY_URL"),
		
		/** 银行编码:此参数对应银行编码表. */
		bankId("bankId","BANK_ID"),
		
		/** 分账明细 分账填写,不分账为空。格式：account1:800|account2:100|account3:100. */
		divDetails("divDetails","DIV_DETAILS"),
		
		/** 分期数 只有选择银行分期支付时，此项才会校验
中国工商银行:分期期数：3，6，9，12，18，24
 中国银行: 分期期数：3，6，12，24
. */
		pedCnt("pedCnt","PED_CNT"),
		
		/** 客户端IP 用户IP. */
		clientIp("clientIp","CLIENT_IP"),
		
		/** 指定支付方式. */
		limitPay("limitPay","LIMIT_PAY"),
		
		/** 订单信息域. */
		orderInfo("orderInfo","ORDER_INFO"),
		
		/** 附加信息. */
		attachInfo("attachInfo","ATTACH_INFO");

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
