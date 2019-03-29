package com.iwhalecloud.retail.rights.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 优惠券领取规则,如领取次数、领取有效期、领取时间段（月循环、周循环）
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("COUPON_SUPPLY_RULE")
@ApiModel(value = "对应模型COUPON_SUPPLY_RULE, 对应实体CouponSupplyRule类")
public class CouponSupplyRule implements Serializable {
    /**表名常量*/
    public static final String TNAME = "COUPON_SUPPLY_RULE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券领取规则标识，主键
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "优惠券领取规则标识，主键")
  	private java.lang.String supplyRuleId;
  	
  	/**
  	 * 优惠券标识 主键
  	 */
	@ApiModelProperty(value = "优惠券标识 主键")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 可领取优惠券的客户范围，可以是客户分群、或自定义的范围
  	 */
	@ApiModelProperty(value = "可领取优惠券的客户范围，可以是客户分群、或自定义的范围")
  	private java.lang.String custRange;
  	
  	/**
  	 * 客户可领取的总次数
  	 */
	@ApiModelProperty(value = "客户可领取的总次数")
  	private java.lang.Long maxNum;
  	
  	/**
  	 * 客户在一个周期内可领取的次数
  	 */
	@ApiModelProperty(value = "客户在一个周期内可领取的次数")
  	private java.lang.Long supplyNum;
  	
  	/**
  	 * 领取的时间范围
  	 */
	@ApiModelProperty(value = "领取的时间范围")
  	private java.lang.String timePeriod;
  	
  	/**
  	 * 循环周期类型：月循环、周循环、不循环LOVB=RES-C-0052
  	 */
	@ApiModelProperty(value = "循环周期类型：月循环、周循环、不循环LOVB=RES-C-0052")
  	private java.lang.String cycleType;
  	
  	/**
  	 * 领取规则描述
  	 */
	@ApiModelProperty(value = "领取规则描述")
  	private java.lang.String supplyRuleDesc;
  	
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
  	
  	/**
  	 * beginTime
  	 */
	@ApiModelProperty(value = "beginTime")
  	private java.util.Date beginTime;
  	
  	/**
  	 * endTime
  	 */
	@ApiModelProperty(value = "endTime")
  	private java.util.Date endTime;

	/**
	 * 是不限制券总数量
	 1.限制
	 0.不限制
	 */
	@ApiModelProperty(value = "是不限制券总数量")
	private String numLimitFlg;

	/**
	 * 是否限制券的单用户领取数量
	 1. 限制
	 0. 不限制
	 */
	@ApiModelProperty(value = "是否限制券的单用户领取数量")
	private String supplyLimitFlg;

	/**
	 * 领取方式
	 * 1. 手工领取 2. 主动推送'
	 */
	@ApiModelProperty(value = "领取方式")
	private String releaseMode;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 优惠券领取规则标识，主键. */
		supplyRuleId("supplyRuleId","SUPPLY_RULE_ID"),
		
		/** 优惠券标识 主键. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 可领取优惠券的客户范围，可以是客户分群、或自定义的范围. */
		custRange("custRange","CUST_RANGE"),
		
		/** 客户可领取的总次数. */
		maxNum("maxNum","MAX_NUM"),
		
		/** 客户在一个周期内可领取的次数. */
		supplyNum("supplyNum","SUPPLY_NUM"),
		
		/** 领取的时间范围. */
		timePeriod("timePeriod","TIME_PERIOD"),
		
		/** 循环周期类型：月循环、周循环、不循环LOVB=RES-C-0052. */
		cycleType("cycleType","CYCLE_TYPE"),
		
		/** 领取规则描述. */
		supplyRuleDesc("supplyRuleDesc","SUPPLY_RULE_DESC"),
		
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
		remark("remark","REMARK"),
		
		/** beginTime. */
		beginTime("beginTime","BEGIN_TIME"),
		
		/** endTime. */
		endTime("endTime","END_TIME"),

		/** 是不限制券总数量
		 1.限制
		 0.不限制*/
		numLimitFlg("numLimitFlg","num_limit_flg"),

		/** 是否限制券的单用户领取数量
		 1. 限制
		 0. 不限制*/
		supplyLimitFlg("supplyLimitFlg","supply_limit_flg"),

		/**
		 * 领取方式
		 * 1. 手工领取 2. 主动推送'
		 */
		releaseMode("releaseMode","RELEASE_MODE");

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
