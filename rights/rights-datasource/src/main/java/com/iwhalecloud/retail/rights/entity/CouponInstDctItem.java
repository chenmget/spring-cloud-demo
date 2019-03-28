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
 * 记录优惠券实例使用时的优惠物品清单
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_INST_DCT_ITEM")
@ApiModel(value = "对应模型COUPON_INST_DCT_ITEM, 对应实体CouponInstDctItem类")
@KeySequence(value = "seq_coupon_inst_dct_item",clazz = String.class)
public class CouponInstDctItem implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_INST_DCT_ITEM";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠优惠项目标识
  	 */
	@TableId
	@ApiModelProperty(value = "优惠优惠项目标识")
  	private java.lang.Long dctItemId;
  	
  	/**
  	 * 优惠券实例使用记录标识
  	 */
	@ApiModelProperty(value = "优惠券实例使用记录标识")
  	private java.lang.Long couponInstUseId;
  	
  	/**
  	 * 优惠券实例标识
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.Long couponInstId;
  	
  	/**
  	 * 使用对象类型LOVB=RES-C-0044
客户、用户、翼支付账户等
  	 */
	@ApiModelProperty(value = "使用对象类型LOVB=RES-C-0044客户、用户、翼支付账户等")
  	private java.lang.String objType;
  	
  	/**
  	 * 使用对象标识
  	 */
	@ApiModelProperty(value = "使用对象标识")
  	private java.lang.String objId;
  	
  	/**
  	 * 记录项目的优惠额度信息
  	 */
	@ApiModelProperty(value = "记录项目的优惠额度信息")
  	private java.lang.Long couponAmount;
  	
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
		/** 优惠优惠项目标识. */
		dctItemId("dctItemId","DCT_ITEM_ID"),
		
		/** 优惠券实例使用记录标识. */
		couponInstUseId("couponInstUseId","COUPON_INST_USE_ID"),
		
		/** 优惠券实例标识. */
		couponInstId("couponInstId","COUPON_INST_ID"),
		
		/** 使用对象类型LOVB=RES-C-0044
客户、用户、翼支付账户等. */
		objType("objType","OBJ_TYPE"),
		
		/** 使用对象标识. */
		objId("objId","OBJ_ID"),
		
		/** 记录项目的优惠额度信息. */
		couponAmount("couponAmount","COUPON_AMOUNT"),
		
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
