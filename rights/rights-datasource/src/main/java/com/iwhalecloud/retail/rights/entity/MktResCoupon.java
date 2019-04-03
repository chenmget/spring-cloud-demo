package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义优惠券相关的基本信息,如优惠方式、管理类型、使用系统等
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_COUPON")
@ApiModel(value = "对应模型MKT_RES_COUPON, 对应实体MktResCoupon类")
public class MktResCoupon implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_COUPON";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券标识 主键
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "优惠券标识 主键")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 记录营销资源名称。
  	 */
	@ApiModelProperty(value = "记录营销资源名称。")
  	private java.lang.String mktResName;
  	
  	/**
  	 * 记录营销资源编码。
  	 */
	@ApiModelProperty(value = "记录营销资源编码。")
  	private java.lang.String mktResNbr;
  	
  	/**
  	 * 营销资源类别标识
  	 */
	@ApiModelProperty(value = "营销资源类别标识")
  	private java.lang.Long mktResTypeId;
  	
  	/**
  	 * 优惠方式LOVB=RES-C-0041

定额、折扣、随机等
  	 */
	@ApiModelProperty(value = "优惠方式LOVB=RES-C-0041定额、折扣、随机等")
  	private java.lang.String discountType;
  	
  	/**
  	 * 管理类型LOVB=RES-C-0042
内部、外部
  	 */
	@ApiModelProperty(value = "管理类型LOVB=RES-C-0042内部、外部")
  	private java.lang.String manageType;
  	
  	/**
  	 * 优惠券使用系统标识,翼支付、支付宝等
  	 */
	@ApiModelProperty(value = "优惠券使用系统标识,翼支付、支付宝等")
  	private java.lang.Long useSysId;
  	
  	/**
  	 * 记录红包的展示面额信息。固定类展示为固定额；不固定类展示最大优惠额度或折扣率
  	 */
	@ApiModelProperty(value = "记录红包的展示面额信息。固定类展示为固定额；不固定类展示最大优惠额度或折扣率")
  	private java.lang.Long showAmount;
  	
  	/**
  	 * 状态LOVB=PUB-C-0001
  	 */
	@ApiModelProperty(value = "状态LOVB=PUB-C-0001")
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
  	
  	/**
  	 * partnerId
  	 */
	@ApiModelProperty(value = "partnerId")
  	private java.lang.String partnerId;

	/**
	 * 优化券类: 1:平台优惠券  2:商家优惠券 3:产品优惠券
	 */
	@ApiModelProperty(value = "优化券类: 1:平台优惠券  2:商家优惠券 3:产品优惠券")
	private java.lang.String couponType;

	/**
	 * 活动id
	 */
	@ApiModelProperty(value = "活动id")
	private String marketingActivityId;

	/**
	 * 优惠券种类
	 */
	@ApiModelProperty(value = "券种类:优惠券的种类 1. 无条件券2. 满减券（满X元减Y元）3. 循环满减券（每满X元减Y元）")
	private String couponKind;


	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 优惠券标识 主键. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 记录营销资源名称。. */
		mktResName("mktResName","MKT_RES_NAME"),
		
		/** 记录营销资源编码。. */
		mktResNbr("mktResNbr","MKT_RES_NBR"),
		
		/** 营销资源类别标识. */
		mktResTypeId("mktResTypeId","MKT_RES_TYPE_ID"),
		
		/** 优惠方式LOVB=RES-C-0041

定额、折扣、随机等. */
		discountType("discountType","DISCOUNT_TYPE"),
		
		/** 管理类型LOVB=RES-C-0042
内部、外部. */
		manageType("manageType","MANAGE_TYPE"),
		
		/** 优惠券使用系统标识,翼支付、支付宝等. */
		useSysId("useSysId","USE_SYS_ID"),
		
		/** 记录红包的展示面额信息。固定类展示为固定额；不固定类展示最大优惠额度或折扣率. */
		showAmount("showAmount","SHOW_AMOUNT"),
		
		/** 状态LOVB=PUB-C-0001. */
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
		updateDate("updateDate","UPDATE_DATE"),
		
		/** partnerId. */
		partnerId("partnerId","PARTNER_ID"),

		/** couponType. */
		couponType("couponType","COUPON_TYPE"),

		/** marketingActivityId. */
		marketingActivityId("marketingActivityId","marketing_activity_id"),

		/**券种类*/
		couponKind("couponKind","coupon_kind");

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
