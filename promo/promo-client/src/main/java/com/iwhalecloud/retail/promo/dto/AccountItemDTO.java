package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * AccountItem
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACCOUNT_ITEM, 对应实体AccountItem类")
public class AccountItemDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 账目标识
  	 */
	@ApiModelProperty(value = "账目标识")
  	private String acctItemId;
	
	/**
  	 * 账目类型
  	 */
	@ApiModelProperty(value = "账目类型")
  	private String acctItemType;
	
	/**
  	 * 使用金额
  	 */
	@ApiModelProperty(value = "使用金额")
  	private java.math.BigDecimal amount;
	
	/**
  	 * 使用的商家id
  	 */
	@ApiModelProperty(value = "使用的商家id")
  	private String merchantId;
	
	/**
  	 * 商品标识
  	 */
	@ApiModelProperty(value = "商品标识")
  	private String goodsId;
	
	/**
  	 * 使用机型ID
  	 */
	@ApiModelProperty(value = "使用机型ID")
  	private String productId;
	
	/**
  	 * 使用的订单ID
  	 */
	@ApiModelProperty(value = "使用的订单ID")
  	private String orderId;
	
	/**
  	 * 订单交易时间
  	 */
	@ApiModelProperty(value = "订单交易时间")
  	private java.util.Date orderCreateTime;
	
	/**
  	 * 参与的营销活动id
  	 */
	@ApiModelProperty(value = "参与的营销活动id")
  	private String activityId;
	
	/**
  	 * 参与的营销活动编码
  	 */
	@ApiModelProperty(value = "参与的营销活动编码")
  	private String activityCode;
	
	/**
  	 * 参与的营销活动名称
  	 */
	@ApiModelProperty(value = "参与的营销活动名称")
  	private String activityName;
	
	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
	
	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
	
	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
	
	/**
  	 * 明细状态变更的时间。
  	 */
	@ApiModelProperty(value = "明细状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 记录创建的员工。
  	 */
	@ApiModelProperty(value = "记录创建的员工。")
  	private Long createStaff;
	
	/**
  	 * 记录创建的时间。 
  	 */
	@ApiModelProperty(value = "记录创建的时间。 ")
  	private java.util.Date createDate;
	
	/**
  	 * 记录修改的员工。
  	 */
	@ApiModelProperty(value = "记录修改的员工。")
  	private Long updateStaff;
	
	/**
  	 * 记录修改的时间。
  	 */
	@ApiModelProperty(value = "记录修改的时间。")
  	private java.util.Date updateDate;
	
	/**
  	 * 记录备注信息。
  	 */
	@ApiModelProperty(value = "记录备注信息。")
  	private String remark;
	
	/**
  	 * 账户类型，余额账户、返利账户、红包账户等
  	 */
	@ApiModelProperty(value = "账户类型，余额账户、返利账户、红包账户等")
  	private Long acctType;

	@ApiModelProperty(value = "账户Id")
	private String acctId;

	@ApiModelProperty(value = "订单项ID")
	private String orderItemId;
}
