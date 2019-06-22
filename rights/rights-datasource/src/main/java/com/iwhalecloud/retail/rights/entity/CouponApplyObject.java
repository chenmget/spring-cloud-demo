package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录优惠券与能优惠的对象，如商品、销售品、产品、积分等。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_APPLY_OBJECT")
@KeySequence(value = "seq_coupon_apply_object", clazz = String.class)
@ApiModel(value = "对应模型COUPON_APPLY_OBJECT, 对应实体CouponApplyObject类")
public class CouponApplyObject implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_APPLY_OBJECT";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券适用对象标识
  	 */
	@TableId
	@ApiModelProperty(value = "优惠券适用对象标识")
  	private java.lang.String applyObjectId;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 适用对象类型LOVB=RES-C-0044
商品、销售品、积分等
  	 */
	@ApiModelProperty(value = "适用对象类型LOVB=RES-C-0044商品、销售品、积分、产品、商家等")
  	private java.lang.String objType;
  	
  	/**
  	 * 适用对象标识
       兑换关系：记录A端营销资源能够兑换的Z端营销资源，如优惠券适用兑换的商品。
  	 */
	@ApiModelProperty(value = "适用对象标识兑换关系：记录A端营销资源能够兑换的Z端营销资源，如优惠券适用兑换的商品。")
  	private java.lang.String objId;
  	
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
		/** 优惠券适用对象标识. */
		applyObjectId("applyObjectId","APPLY_OBJECT_ID"),
		
		/** 优惠券标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 适用对象类型LOVB=RES-C-0044商品、销售品、积分等. */
		objType("objType","OBJ_TYPE"),
		
		/**
		 *  适用对象标识兑换关系：记录A端营销资源能够兑换的Z端营销资源，如优惠券适用兑换的商品。.
		 */
		objId("objId","OBJ_ID"),
		
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
