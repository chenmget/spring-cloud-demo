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
 * 记录优惠券实例的发放记录信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_INST_PROV_REC")
@ApiModel(value = "对应模型COUPON_INST_PROV_REC, 对应实体CouponInstProvRec类")
@KeySequence(value = "seq_coupon_inst_prov_rec",clazz = String.class)
public class CouponInstProvRec implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_INST_PROV_REC";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 发放记录标识
  	 */
	@TableId
	@ApiModelProperty(value = "发放记录标识")
  	private java.lang.String provRecId;
  	
  	/**
  	 * 优惠券实例标识
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
  	
  	/**
  	 * 发放渠道类型LOVB=RES-C-0047
网厅、微信、短信
  	 */
	@ApiModelProperty(value = "发放渠道类型LOVB=RES-C-0047网厅、微信、短信")
  	private java.lang.String provChannelType;
  	
  	/**
  	 * 记录发放订单标识，对应外部单号
  	 */
	@ApiModelProperty(value = "记录发放订单标识，对应外部单号")
  	private java.lang.String provOrderId;
  	
  	/**
  	 * 发放对象类型LOVB=RES-C-0048
客户、用户、翼支付账户等
  	 */
	@ApiModelProperty(value = "发放对象类型LOVB=RES-C-0048客户、用户、翼支付账户等")
  	private java.lang.String provObjType;
  	
  	/**
  	 * 发放对象标识
  	 */
	@ApiModelProperty(value = "发放对象标识")
  	private java.lang.String provObjId;
  	
  	/**
  	 * 发放记录描述
  	 */
	@ApiModelProperty(value = "发放记录描述")
  	private java.lang.String provDesc;
  	
  	/**
  	 * 发放时间
  	 */
	@ApiModelProperty(value = "发放时间")
  	private java.util.Date provDate;
  	
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
		/** 发放记录标识. */
		provRecId("provRecId","PROV_REC_ID"),
		
		/** 优惠券实例标识. */
		couponInstId("couponInstId","COUPON_INST_ID"),
		
		/** 发放渠道类型LOVB=RES-C-0047
网厅、微信、短信. */
		provChannelType("provChannelType","PROV_CHANNEL_TYPE"),
		
		/** 记录发放订单标识，对应外部单号. */
		provOrderId("provOrderId","PROV_ORDER_ID"),
		
		/** 发放对象类型LOVB=RES-C-0048
客户、用户、翼支付账户等. */
		provObjType("provObjType","PROV_OBJ_TYPE"),
		
		/** 发放对象标识. */
		provObjId("provObjId","PROV_OBJ_ID"),
		
		/** 发放记录描述. */
		provDesc("provDesc","PROV_DESC"),
		
		/** 发放时间. */
		provDate("provDate","PROV_DATE"),
		
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
