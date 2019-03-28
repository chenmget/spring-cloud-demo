package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 记录优惠券的使用规则信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_DISCOUNT_RULE")
@KeySequence(value = "seq_coupon_discount_rule", clazz = String.class)
@ApiModel(value = "对应模型COUPON_DISCOUNT_RULE, 对应实体CouponDiscountRule类")
public class CouponDiscountRule implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_DISCOUNT_RULE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 抵扣规则标识
  	 */
	@TableId
	@ApiModelProperty(value = "抵扣规则标识")
  	private java.lang.String discountRuleId;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 记录抵扣的固定金额或折扣
  	 */
	@ApiModelProperty(value = "记录抵扣的固定金额或折扣")
  	private java.lang.Double discountValue;
  	
  	/**
  	 * 记录抵扣的上限
  	 */
	@ApiModelProperty(value = "记录抵扣的上限")
  	private java.lang.Double maxValue;
  	
  	/**
  	 * 记录抵扣的下限
  	 */
	@ApiModelProperty(value = "记录抵扣的下限")
  	private java.lang.Double minValue;
  	
  	/**
  	 * 记录使用优惠券需要满足的条件
  	 */
	@ApiModelProperty(value = "记录使用优惠券需要满足的条件")
  	private java.lang.Double ruleAmount;
  	
  	/**
  	 * 记录优惠券的使用类型LOVB=RES-C-0045

商品、商铺内、总额
  	 */
	@ApiModelProperty(value = "记录优惠券的使用类型LOVB=RES-C-0045商品、商铺内、总额")
  	private java.lang.String useType;
  	
  	/**
  	 * 标志同一个优惠券的多个优惠券实例是否可叠加使用。LOVB=PUB-C-0006
  	 */
	@ApiModelProperty(value = "标志同一个优惠券的多个优惠券实例是否可叠加使用。LOVB=PUB-C-0006")
  	private java.lang.String reuseFlag;
  	
  	/**
  	 * 标志不同优惠券的多个实例是否可混合使用。LOVB=PUB-C-0006
  	 */
	@ApiModelProperty(value = "标志不同优惠券的多个实例是否可混合使用。LOVB=PUB-C-0006")
  	private java.lang.String mixUseFlag;

	/**
	 * 标志不同优惠券的多个实例是否可循环使用。LOVB=PUB-C-0006
	 */
	@ApiModelProperty(value = "标志不同优惠券的多个实例是否可循环使用。LOVB=PUB-C-0006")
	private java.lang.String recycleFlag;
  	
  	/**
  	 * 抵扣规则描述
  	 */
	@ApiModelProperty(value = "抵扣规则描述")
  	private java.lang.String discountRuleDesc;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private java.lang.String createStaff;
  	
  	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private java.lang.String updateStaff;
  	
  	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 抵扣规则标识. */
		discountRuleId("discountRuleId","DISCOUNT_RULE_ID"),
		
		/** 优惠券标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 记录抵扣的固定金额或折扣. */
		discountValue("discountValue","DISCOUNT_VALUE"),
		
		/** 记录抵扣的上限. */
		maxValue("maxValue","MAX_VALUE"),
		
		/** 记录抵扣的下限. */
		minValue("minValue","MIN_VALUE"),
		
		/** 记录使用优惠券需要满足的条件. */
		ruleAmount("ruleAmount","RULE_AMOUNT"),
		
		/** 记录优惠券的使用类型LOVB=RES-C-0045

商品、商铺内、总额. */
		useType("useType","USE_TYPE"),
		
		/** 标志同一个优惠券的多个优惠券实例是否可叠加使用。LOVB=PUB-C-0006. */
		reuseFlag("reuseFlag","REUSE_FLAG"),
		
		/** 标志不同优惠券的多个实例是否可混合使用。LOVB=PUB-C-0006. */
		mixUseFlag("mixUseFlag","MIX_USE_FLAG"),

		/** 标志不同优惠券的多个实例是否可循环使用。LOVB=PUB-C-0006.*/
		recycleFlag("recycleFlag","recycle_FLAG"),

		/** 抵扣规则描述. */
		discountRuleDesc("discountRuleDesc","DISCOUNT_RULE_DESC"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),

		/** 备注. */
		remark("remark","REMARK");

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
