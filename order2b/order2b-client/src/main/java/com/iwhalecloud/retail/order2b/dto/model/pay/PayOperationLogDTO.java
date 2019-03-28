package com.iwhalecloud.retail.order2b.dto.model.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * PayOperationLog
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型pay_operationlog, 对应实体PayOperationLog类")
public class PayOperationLogDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 操作日志编号
  	 */
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
	
  	
}
