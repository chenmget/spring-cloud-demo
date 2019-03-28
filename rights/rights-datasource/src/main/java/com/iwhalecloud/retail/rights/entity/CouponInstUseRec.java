package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录优惠券实例的使用记录信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_INST_USE_REC")
@ApiModel(value = "对应模型COUPON_INST_USE_REC, 对应实体CouponInstUseRec类")
@KeySequence(value = "seq_coupon_inst_use_rec",clazz = String.class)
public class CouponInstUseRec implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_INST_USE_REC";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券实例使用记录标识
  	 */
	@TableId
	@ApiModelProperty(value = "优惠券实例使用记录标识")
  	private java.lang.String couponInstUseId;
  	
  	/**
  	 * 优惠券实例标识
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
  	
  	/**
  	 * 使用对象类型LOVB=RES-C-0048
客户、用户、翼支付账户等
  	 */
	@ApiModelProperty(value = "使用对象类型LOVB=RES-C-0048客户、用户、翼支付账户等")
  	private java.lang.String useObjType;
  	
  	/**
  	 * 使用对象标识
  	 */
	@ApiModelProperty(value = "使用对象标识")
  	private java.lang.String useObjId;
  	
  	/**
  	 * 记录能使用优惠券的系统单号
  	 */
	@ApiModelProperty(value = "记录能使用优惠券的系统单号")
  	private java.lang.String useOrderId;
  	
  	/**
  	 * 使用记录描述
  	 */
	@ApiModelProperty(value = "使用记录描述")
  	private java.lang.String useDesc;
  	
  	/**
  	 * 使用时间
  	 */
	@ApiModelProperty(value = "使用时间")
  	private java.util.Date useDate;
  	
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
		/** 优惠券实例使用记录标识. */
		couponInstUseId("couponInstUseId","COUPON_INST_USE_ID"),
		
		/** 优惠券实例标识. */
		couponInstId("couponInstId","COUPON_INST_ID"),
		
		/** 使用对象类型LOVB=RES-C-0048
客户、用户、翼支付账户等. */
		useObjType("useObjType","USE_OBJ_TYPE"),
		
		/** 使用对象标识. */
		useObjId("useObjId","USE_OBJ_ID"),
		
		/** 记录能使用优惠券的系统单号. */
		useOrderId("useOrderId","USE_ORDER_ID"),
		
		/** 使用记录描述. */
		useDesc("useDesc","USE_DESC"),
		
		/** 使用时间. */
		useDate("useDate","USE_DATE"),
		
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
