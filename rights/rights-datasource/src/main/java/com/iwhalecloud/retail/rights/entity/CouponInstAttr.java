package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录优惠券实例属性信息。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_INST_ATTR")
@ApiModel(value = "对应模型COUPON_INST_ATTR, 对应实体CouponInstAttr类")
@KeySequence(value = "seq_coupon_inst_attr",clazz = String.class)
public class CouponInstAttr implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_INST_ATTR";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 记录优惠券实例属性ID
  	 */
	@TableId
	@ApiModelProperty(value = "记录优惠券实例属性ID")
  	private java.lang.Long couponInstAttrId;
  	
  	/**
  	 * 优惠券实例ID的标识，主键
  	 */
	@ApiModelProperty(value = "优惠券实例ID的标识，主键")
  	private java.lang.Long couponInstId;
  	
  	/**
  	 * 记录属性ID。
  	 */
	@ApiModelProperty(value = "记录属性ID。")
  	private java.lang.Long attrId;
  	
  	/**
  	 * 属性值标识。
  	 */
	@ApiModelProperty(value = "属性值标识。")
  	private java.lang.Long attrValueId;
  	
  	/**
  	 * 属性值。
  	 */
	@ApiModelProperty(value = "属性值。")
  	private java.lang.String attrValue;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001")
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
  	 * 本地网
  	 */
	@ApiModelProperty(value = "本地网")
  	private java.lang.Long lanId;
  	
  	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.Long regionId;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 记录优惠券实例属性ID. */
		couponInstAttrId("couponInstAttrId","COUPON_INST_ATTR_ID"),
		
		/** 优惠券实例ID的标识，主键. */
		couponInstId("couponInstId","COUPON_INST_ID"),
		
		/** 记录属性ID。. */
		attrId("attrId","ATTR_ID"),
		
		/** 属性值标识。. */
		attrValueId("attrValueId","ATTR_VALUE_ID"),
		
		/** 属性值。. */
		attrValue("attrValue","ATTR_VALUE"),
		
		/** 记录状态。LOVB=PUB-C-0001. */
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
		
		/** 本地网. */
		lanId("lanId","LAN_ID"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
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
