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
 * 记录优惠券领用后的可以使用的有效期规则
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_EFF_EXP_RULE")
@KeySequence(value = "seq_coupon_eff_exp_rule", clazz = String.class)
@ApiModel(value = "对应模型COUPON_EFF_EXP_RULE, 对应实体CouponEffExpRule类")
public class CouponEffExpRule implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_EFF_EXP_RULE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券生失效规则标识，唯一主键
  	 */
	@TableId
	@ApiModelProperty(value = "优惠券生失效规则标识，唯一主键")
  	private java.lang.String effExpRuleId;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 优惠券领用后，可以使用的有效期时长
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的有效期时长")
  	private java.lang.Long periodTime;
  	
  	/**
  	 * 有效期时间单位。LOVB=OFF-C-0036
  	 */
	@ApiModelProperty(value = "有效期时间单位。LOVB=OFF-C-0036")
  	private java.lang.String periodUnit;
  	
  	/**
  	 * 优惠券领用后，可以使用的开始时间
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的开始时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 优惠券领用后，可以使用的截止时间
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的截止时间")
  	private java.util.Date expDate;
  	
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
		/** 优惠券生失效规则标识，唯一主键. */
		effExpRuleId("effExpRuleId","EFF_EXP_RULE_ID"),
		
		/** 优惠券标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 优惠券领用后，可以使用的有效期时长. */
		periodTime("periodTime","PERIOD_TIME"),
		
		/** 有效期时间单位。LOVB=OFF-C-0036. */
		periodUnit("periodUnit","PERIOD_UNIT"),
		
		/** 优惠券领用后，可以使用的开始时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 优惠券领用后，可以使用的截止时间. */
		expDate("expDate","EXP_DATE"),
		
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
