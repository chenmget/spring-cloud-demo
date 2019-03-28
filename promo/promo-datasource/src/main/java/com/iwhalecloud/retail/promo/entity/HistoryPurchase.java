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
 * HistoryPurchase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_history_purchase")
@KeySequence(value = "seq_act_history_purchase_id", clazz = String.class)
@ApiModel(value = "对应模型act_history_purchase, 对应实体HistoryPurchase类")
public class HistoryPurchase implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_history_purchase";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
  	
  	/**
  	 * 营销活动编号
  	 */
	@ApiModelProperty(value = "营销活动编号")
	private java.lang.String marketingActivityId;
  	
  	/**
  	 * 商家类型
  	 */
	@ApiModelProperty(value = "商家类型")
  	private java.lang.String merchantType;

	/**
	 * 活动类型
	 */
	@ApiModelProperty(value = "活动类型")
	private java.lang.String activityTypeCode;

  	/**
  	 * 商家编码
  	 */
	@ApiModelProperty(value = "商家编码")
  	private java.lang.String merchantCode;
  	
  	/**
  	 * 商家名称
  	 */
	@ApiModelProperty(value = "商家名称")
  	private java.lang.String merchantName;
  	
  	/**
  	 * 参加活动时间
  	 */
	@ApiModelProperty(value = "参加活动时间")
  	private java.util.Date purchaseTime;
  	
  	/**
  	 * 支付预付价格时间
  	 */
	@ApiModelProperty(value = "支付预付价格时间")
  	private java.util.Date prePayTime;
  	
  	/**
  	 * 支付支付价格时间
  	 */
	@ApiModelProperty(value = "支付支付价格时间")
  	private java.util.Date payTime;
  	
  	/**
  	 * 订单ID
  	 */
	@ApiModelProperty(value = "订单ID")
  	private java.lang.String orderId;
  	
  	/**
  	 * 订单类型
  	 */
	@ApiModelProperty(value = "订单类型")
  	private java.lang.String orderType;
  	
  	/**
  	 * 商品ID
  	 */
	@ApiModelProperty(value = "商品ID")
  	private java.lang.String goodsId;
  	
  	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private java.lang.String productId;
  	
  	/**
  	 * 价格
  	 */
	@ApiModelProperty(value = "价格")
  	private java.lang.String price;
  	
  	/**
  	 * 预付价格
  	 */
	@ApiModelProperty(value = "预付价格")
  	private java.lang.String prePrice;
  	
  	/**
  	 * 优惠价格
  	 */
	@ApiModelProperty(value = "优惠价格")
  	private java.lang.String promotionPrice;
  	
  	/**
  	 * 支付价格
  	 */
	@ApiModelProperty(value = "支付价格")
  	private java.lang.String payMoney;
  	
  	/**
  	 * 优惠券实例id
  	 */
	@ApiModelProperty(value = "优惠券实例id")
  	private java.lang.String couponInstId;
  	
  	/**
  	 * 支付类型
  	 */
	@ApiModelProperty(value = "支付类型")
  	private java.lang.String payType;
  	
  	/**
  	 * 数量
  	 */
	@ApiModelProperty(value = "数量")
  	private java.lang.Integer num;
  	
  	/**
  	 * 供应商编码
  	 */
	@ApiModelProperty(value = "供应商编码")
  	private java.lang.String supplierCode;

	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;
	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;

	/**
	 * 订单项标识
	 */
	@ApiModelProperty("订单项标识")
	private String orderItemId;
	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 营销活动编号. */
		marketingActivityCode("marketingActivityId","MARKETING_ACTIVITY_ID"),
		
		/** 商家类型. */
		merchantType("merchantType","MERCHANT_TYPE"),

		/** 活动类型. */
		activityTypeCode("activityTypeCode","ACTIVITY_TYPE_CODE"),


//		/** 商家编码. */
//		merchantId("merchantId","MERCHANT_ID"),
		
		/** 商家编码. */
		merchantCode("merchantCode","MERCHANT_CODE"),
		
		/** 商家名称. */
		merchantName("merchantName","MERCHANT_NAME"),
		
		/** 参加活动时间. */
		purchaseTime("purchaseTime","PURCHASE_TIME"),
		
		/** 支付预付价格时间. */
		prePayTime("prePayTime","PRE_PAY_TIME"),
		
		/** 支付支付价格时间. */
		payTime("payTime","PAY_TIME"),
		
		/** 订单ID. */
		orderId("orderId","ORDER_ID"),
		
		/** 订单类型. */
		orderType("orderType","ORDER_TYPE"),
		
		/** 商品ID. */
		goodsId("goodsId","GOODS_ID"),
		
		/** 产品ID. */
		productId("productId","PRODUCT_ID"),
		
		/** 价格. */
		price("price","PRICE"),
		
		/** 预付价格. */
		prePrice("prePrice","PRE_PRICE"),
		
		/** 优惠价格. */
		promotionPrice("promotionPrice","PROMOTION_PRICE"),
		
		/** 支付价格. */
		payMoney("payMoney","PAY_MONEY"),
		
		/** 优惠券实例id. */
		couponInstId("couponInstId","COUPON_INST_ID"),
		
		/** 支付类型. */
		payType("payType","PAY_TYPE"),
		
		/** 数量. */
		num("num","NUM"),
		
		/** 供应商编码. */
		supplierCode("supplierCode","SUPPLIER_CODE"),
		
//		/** 记录首次创建的员工标识。. */
//		createStaff("createStaff","CREATE_STAFF"),
//
//		/** 记录首次创建的时间。. */
//		createDate("createDate","CREATE_DATE"),
//
//		/** 记录每次修改的员工标识。. */
//		updateStaff("updateStaff","UPDATE_STAFF"),
//
//		/** 记录每次修改的时间。. */
//		updateDate("updateDate","UPDATE_DATE");
		/** 是否删除：0未删、1删除。. */
		isDeleted("isDeleted","IS_DELETED"),
		/** 记录首次创建的员工标识。. */
		creator("creator","CREATOR"),

		/** 记录首次创建的时间。. */
		gmtCreate("gmtCreate","GMT_CREATE"),

		/** 记录每次修改的员工标识。. */
		modifier("modifier","MODIFIER"),

		/** 记录每次修改的时间。. */
		gmtModified("gmtModified","GMT_MODIFIED"),

		/** 订单项标识 */
		orderItemId("orderItemId","ORDER_ITEM_ID");

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
