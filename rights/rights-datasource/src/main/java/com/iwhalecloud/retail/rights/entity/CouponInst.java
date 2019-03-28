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
 * 记录每一个优惠券实例信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_INST")
@ApiModel(value = "对应模型COUPON_INST, 对应实体CouponInst类")
@KeySequence(value = "seq_coupon_inst_coupon_inst_id",clazz = String.class)
public class CouponInst implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_INST";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券实例标识
  	 */
	@TableId
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
  	
  	/**
  	 * 优惠券实例编码
  	 */
	@ApiModelProperty(value = "优惠券实例编码")
  	private java.lang.String couponInstNbr;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 记录优惠券使用后最终的优惠额度信息
  	 */
	@ApiModelProperty(value = "记录优惠券使用后最终的优惠额度信息")
  	private java.lang.Long couponAmount;
  	
  	/**
  	 * 优惠券赠送的客户统一账号
  	 */
	@ApiModelProperty(value = "优惠券赠送的客户统一账号")
  	private java.lang.String custAcctId;
  	
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
  	 * 状态LOVB=RES-C-0046
未使用、已使用、已过期
  	 */
	@ApiModelProperty(value = "状态LOVB=RES-C-0046未使用、已使用、已过期")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
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
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 优惠券实例标识. */
		couponInstId("couponInstId","COUPON_INST_ID"),
		
		/** 优惠券实例编码. */
		couponInstNbr("couponInstNbr","COUPON_INST_NBR"),
		
		/** 优惠券标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 记录优惠券使用后最终的优惠额度信息. */
		couponAmount("couponAmount","COUPON_AMOUNT"),
		
		/** 优惠券赠送的客户统一账号. */
		custAcctId("custAcctId","CUST_ACCT_ID"),
		
		/** 生效时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE"),
		
		/** 状态LOVB=RES-C-0046
未使用、已使用、已过期. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 备注. */
		remark("remark","REMARK"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE");

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
