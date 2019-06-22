package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ActivityParticipant
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_activity_participant")
@KeySequence(value = "seq_act_activity_participant_id", clazz = String.class)
@ApiModel(value = "对应模型act_activity_participant, 对应实体ActivityParticipant类")
public class ActivityParticipant implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_activity_participant";
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
	 * Code 改为ID
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private java.lang.String marketingActivityId;
  	
  	/**
  	 * 地市
  	 */
	@ApiModelProperty(value = "地市")
  	private java.lang.String lanId;
  	
  	/**
  	 * 市县
  	 */
	@ApiModelProperty(value = "市县")
  	private java.lang.String city;
  	
  	/**
  	 * 商家类型
  	 */
	@ApiModelProperty(value = "商家类型")
  	private java.lang.String merchantType;
  	
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
  	 * 销售点编码
  	 */
	@ApiModelProperty(value = "销售点编码")
  	private java.lang.String shopCode;
  	
  	/**
  	 * 销售点名称
  	 */
	@ApiModelProperty(value = "销售点名称")
  	private java.lang.String shopName;

	/**
	 * 筛选类型
	 */
	@ApiModelProperty(value = "筛选类型")
	private java.lang.String filterType;

	/**
	 * 筛选条件json值
	 */
	@ApiModelProperty(value = "筛选条件json值")
	private java.lang.String filterValue;


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
	 * 状态,0：待审核,1：有效,-1：审核不通过
	 */
	@ApiModelProperty(value = "状态,0：待审核,1：有效,-1：审核不通过")
	private java.lang.String status;
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 营销活动编号. */
		marketingActivityId("marketingActivityId","MARKETING_ACTIVITY_ID"),
		
		/** 地市. */
		lanId("lanId","LAN_ID"),
		
		/** 市县. */
		city("city","CITY"),
		
		/** 商家类型. */
		merchantType("merchantType","MERCHANT_TYPE"),
		
		/** 商家编码. */
		merchantCode("merchantCode","MERCHANT_CODE"),
		
		/** 商家名称. */
		merchantName("merchantName","MERCHANT_NAME"),
		
		/** 销售点编码. */
		shopCode("shopCode","SHOP_CODE"),
		
		/** 销售点名称. */
		shopName("shopName","SHOP_NAME"),

		/** 筛选类型. */
		filterType("filterType","filter_type"),

		/** 筛选条件json值 */
		filterValue("filterValue","filter_value"),

		/** 是否删除：0未删、1删除。. */
		isDeleted("isDeleted","IS_DELETED"),

		/** 状态,0：待审核,1：有效,-1：审核不通过. */
		status("status","STATUS"),
		
		/** 记录首次创建的员工标识。. */
		creator("creator","CREATOR"),

		/** 记录首次创建的时间。. */
		gmtCreate("gmtCreate","GMT_CREATE"),

		/** 记录每次修改的员工标识。. */
		modifier("modifier","MODIFIER"),

		/** 记录每次修改的时间。. */
		gmtModified("gmtModified","GMT_MODIFIED");

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
