package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * PayOperationLog
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("pay_operationlog")
@KeySequence(value="seq_pay_operationlog_id",clazz = String.class)
@ApiModel(value = "对应模型pay_operationlog, 对应实体PayOperationLog类")
public class PayOperationLog implements Serializable {
    /**表名常量*/
    public static final String TNAME = "pay_operationlog";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 操作日志编号
  	 */
	@TableId
	@ApiModelProperty(value = "操作日志编号")
  	private java.lang.String id;
  	
  	/**
  	 * B2B：hnyhj_b2b B2C：hnyhj_b2c 收钱哆：hnsqd 
  	 */
	@ApiModelProperty(value = "B2B：hnyhj_b2b B2C：hnyhj_b2c 收钱哆：hnsqd ")
  	private java.lang.String platformType;
  	
  	/**
  	 * 订单编号
  	 */
	@ApiModelProperty(value = "订单编号")
  	private java.lang.String orderId;
  	
  	/**
  	 * 商户在收钱哆/云货架注册编码
  	 */
	@ApiModelProperty(value = "商户在收钱哆/云货架注册编码")
  	private java.lang.String commercialId;
  	
  	/**
  	 * 商户在翼支付平台的商家编码
  	 */
	@ApiModelProperty(value = "商户在翼支付平台的商家编码")
  	private java.lang.String merchantId;
  	
  	/**
  	 * 用于区分对方平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版 
  	 */
	@ApiModelProperty(value = "用于区分对方平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版 ")
  	private java.lang.String payPlatform;
  	
  	/**
  	 * 支付平台交易流水号
  	 */
	@ApiModelProperty(value = "支付平台交易流水号")
  	private java.lang.String payPlatformOrderid;
  	
  	/**
  	 * 支付平台门店编码
  	 */
	@ApiModelProperty(value = "支付平台门店编码")
  	private java.lang.String storeId;
  	
  	/**
  	 * 用于记录平台操作日志：1001 操作员登录，1002 个人商户新增，1003 个人商户修改，1004 个人商户查询，1005 个人商户信息查询，1006 附件上传，1007 个人商户审批结果通知
  	 */
	@ApiModelProperty(value = "用于记录平台操作日志：1001 操作员登录，1002 个人商户新增，1003 个人商户修改，1004 个人商户查询，1005 个人商户信息查询，1006 附件上传，1007 个人商户审批结果通知")
  	private java.lang.String operationType;
  	
  	/**
  	 * createStaff
  	 */
	@ApiModelProperty(value = "createStaff")
  	private java.lang.String createStaff;
  	
  	/**
  	 * 用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成
  	 */
	@ApiModelProperty(value = "用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成")
  	private java.lang.String payStatus;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
  	
  	/**
  	 * 发给外部平台数据
  	 */
	@ApiModelProperty(value = "发给外部平台数据")
  	private java.lang.String payData;
  	
  	/**
  	 * 外部平台接收数据
  	 */
	@ApiModelProperty(value = "外部平台接收数据")
  	private java.lang.String resultData;

	/**
	 * 支付订单号
	 */
	@ApiModelProperty(value = "支付订单号")
	private java.lang.String payId;

	private String sourceFrom;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 操作日志编号. */
		id("id","ID"),
		
		/** B2B：hnyhj_b2b B2C：hnyhj_b2c 收钱哆：hnsqd . */
		platformType("platformType","PLATFORM_TYPE"),
		
		/** 订单编号. */
		orderId("orderId","ORDER_ID"),
		
		/** 商户在收钱哆/云货架注册编码. */
		commercialId("commercialId","COMMERCIAL_ID"),
		
		/** 商户在翼支付平台的商家编码. */
		merchantId("merchantId","MERCHANT_ID"),
		
		/** 用于区分对方平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版 . */
		payPlatform("payPlatform","PAY_PLATFORM"),
		
		/** 支付平台交易流水号. */
		payPlatformOrderid("payPlatformOrderid","PAY_PLATFORM_ORDERID"),
		
		/** 支付平台门店编码. */
		storeId("storeId","STORE_ID"),
		
		/** 用于记录平台操作日志：1001 操作员登录，1002 个人商户新增，1003 个人商户修改，1004 个人商户查询，1005 个人商户信息查询，1006 附件上传，1007 个人商户审批结果通知. */
		operationType("operationType","OPERATION_TYPE"),
		
		/** createStaff. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成. */
		payStatus("payStatus","PAY_STATUS"),
		
		/** 创建时间. */
		createDate("createDate","CREATE_DATE"),
		
		/** 发给外部平台数据. */
		payData("payData","PAY_DATA"),
		
		/** 外部平台接收数据. */
		resultData("resultData","RESULT_DATA");

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
