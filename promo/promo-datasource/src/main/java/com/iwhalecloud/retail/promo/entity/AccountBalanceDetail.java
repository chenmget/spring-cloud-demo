package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * AccountBalanceDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT_BALANCE_DETAIL")
@ApiModel(value = "对应模型ACCOUNT_BALANCE_DETAIL, 对应实体AccountBalanceDetail类")


public class AccountBalanceDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT_BALANCE_DETAIL";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。
  	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。")
  	private String operIncomeId;
  	
  	/**
  	 * 账户余额标识
  	 */
	@ApiModelProperty(value = "账户余额标识")
  	private String accountBalanceId;
  	
  	/**
  	 * 为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。
  	 */
	@ApiModelProperty(value = "为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。")
  	private String acctId;
  	
  	/**
  	 * 账户类型，余额账户、返利账户、红包账户等
  	 */
	@ApiModelProperty(value = "账户类型，余额账户、返利账户、红包账户等")
  	private String acctType;
  	
  	/**
  	 * 操作类型，1 存（收入）；2 转（收入）；3 补（收入）；4 冲正；5 调帐 
  	 */
	@ApiModelProperty(value = "操作类型，1 存（收入）；2 转（收入）；3 补（收入）；4 冲正；5 调帐 ")
  	private String operType;
  	
  	/**
  	 * 入账金额
  	 */
	@ApiModelProperty(value = "入账金额")
  	private Long amount;
  	
  	/**
  	 * 操作后账户余额
  	 */
	@ApiModelProperty(value = "操作后账户余额")
  	private Long balance;
  	
  	/**
  	 * 可用余额
  	 */
	@ApiModelProperty(value = "可用余额")
  	private Long curAmount;
  	
  	/**
  	 * 未生效余额
  	 */
	@ApiModelProperty(value = "未生效余额")
  	private Long uneffectiveAmount;
  	
  	/**
  	 * 失效余额
  	 */
	@ApiModelProperty(value = "失效余额")
  	private Long invalidAmount;
  	
  	/**
  	 * 来源类型标识，LOVB=ACC-C-0012
  	 */
	@ApiModelProperty(value = "来源类型标识，LOVB=ACC-C-0012")
  	private String balanceSourceTypeId;
  	
  	/**
  	 * 订单项ID
  	 */
	@ApiModelProperty(value = "订单项ID")
  	private String orderItemId;
  	
  	/**
  	 * orderId
  	 */
	@ApiModelProperty(value = "orderId")
  	private String orderId;
  	
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
  	 * 当前使用时间
  	 */
	@ApiModelProperty(value = "当前使用时间")
  	private java.util.Date curStatusDate;
  	
  	/**
  	 * 明细的状态。ACC-C-0203
  	 */
	@ApiModelProperty(value = "明细的状态。ACC-C-0203")
  	private Long statusCd;
  	
  	/**
  	 * 明细状态变更的时间。
  	 */
	@ApiModelProperty(value = "明细状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录创建的员工
  	 */
	@ApiModelProperty(value = "记录创建的员工")
  	private String createStaff;
  	
  	/**
  	 * 记录创建的时间。
  	 */
	@ApiModelProperty(value = "记录创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 和payment的付款流水号对应
  	 */
	@ApiModelProperty(value = "和payment的付款流水号对应")
  	private String paymentId;
  	
  	/**
  	 * 余额存入时的来源描述，用于查询、数据统计
  	 */
	@ApiModelProperty(value = "余额存入时的来源描述，用于查询、数据统计")
  	private String sourceDesc;



	@ApiModelProperty(value = "产品ID")
	private String productId;

	/**
	 * 余额存入时的来源描述，用于查询、数据统计
	 */
	@ApiModelProperty(value = "返利单价")
	private String rewardPrice;
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。. */
		operIncomeId("operIncomeId","OPER_INCOME_ID"),
		
		/** 账户余额标识. */
		accountBalanceId("accountBalanceId","ACCOUNT_BALANCE_ID"),
		
		/** 为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。. */
		acctId("acctId","ACCT_ID"),
		
		/** 账户类型，余额账户、返利账户、红包账户等. */
		acctType("acctType","ACCT_TYPE"),
		
		/** 操作类型，1 存（收入）；2 转（收入）；3 补（收入）；4 冲正；5 调帐 . */
		operType("operType","OPER_TYPE"),
		
		/** 入账金额. */
		amount("amount","AMOUNT"),
		
		/** 操作后账户余额. */
		balance("balance","BALANCE"),
		
		/** 可用余额. */
		curAmount("curAmount","CUR_AMOUNT"),
		
		/** 未生效余额. */
		uneffectiveAmount("uneffectiveAmount","UNEFFECTIVE_AMOUNT"),
		
		/** 失效余额. */
		invalidAmount("invalidAmount","INVALID_AMOUNT"),
		
		/** 来源类型标识，LOVB=ACC-C-0012. */
		balanceSourceTypeId("balanceSourceTypeId","BALANCE_SOURCE_TYPE_ID"),
		
		/** 订单项ID. */
		orderItemId("orderItemId","ORDER_ITEM_ID"),
		
		/** orderId. */
		orderId("orderId","ORDER_ID"),
		
		/** 生效时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE"),
		
		/** 当前使用时间. */
		curStatusDate("curStatusDate","CUR_STATUS_DATE"),
		
		/** 明细的状态。ACC-C-0203. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 明细状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录创建的员工. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 和payment的付款流水号对应. */
		paymentId("paymentId","PAYMENT_ID"),

		productId("productId","PRODUCT_IDId"),

		rewardPrice("rewardPrice","REWARD_PRICE"),
		
		/** 余额存入时的来源描述，用于查询、数据统计. */
		sourceDesc("sourceDesc","SOURCE_DESC");

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
