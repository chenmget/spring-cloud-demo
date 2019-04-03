package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * AccountItem
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT_ITEM")
@ApiModel(value = "对应模型ACCOUNT_ITEM, 对应实体AccountItem类")
@KeySequence(value="seq_retail_all_tables",clazz = String.class)

public class AccountItem implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT_ITEM";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 账目标识
  	 */
	@TableId
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
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {

		orderItemId("orderItemId","ORDER_ITEM_ID"),

		acctId("acctId","ACCT_ID"),
		/** 账目标识. */
		acctItemId("acctItemId","ACCT_ITEM_ID"),
		
		/** 账目类型. */
		acctItemType("acctItemType","ACCT_ITEM_TYPE"),
		
		/** 使用金额. */
		amount("amount","AMOUNT"),
		
		/** 使用的商家id. */
		merchantId("merchantId","MERCHANT_ID"),
		
		/** 商品标识. */
		goodsId("goodsId","GOODS_ID"),
		
		/** 使用机型ID. */
		productId("productId","PRODUCT_ID"),
		
		/** 使用的订单ID. */
		orderId("orderId","ORDER_ID"),
		
		/** 订单交易时间. */
		orderCreateTime("orderCreateTime","ORDER_CREATE_TIME"),
		
		/** 参与的营销活动id. */
		activityId("activityId","ACTIVITY_ID"),
		
		/** 参与的营销活动编码. */
		activityCode("activityCode","ACTIVITY_CODE"),
		
		/** 参与的营销活动名称. */
		activityName("activityName","ACTIVITY_NAME"),
		
		/** 生效时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE"),
		
		/** 状态; 1000 有效，1100 失效. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 明细状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录创建的员工。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录创建的时间。 . */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录修改的员工。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录备注信息。. */
		remark("remark","REMARK"),
		
		/** 账户类型，余额账户、返利账户、红包账户等. */
		acctType("acctType","ACCT_TYPE");

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
