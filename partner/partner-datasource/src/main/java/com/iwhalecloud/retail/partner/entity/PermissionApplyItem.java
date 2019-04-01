package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * PermissionApplyItem
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("par_permission_apply_item")
@ApiModel(value = "对应模型par_permission_apply_item, 对应实体PermissionApplyItem类")
@KeySequence(value="seq_par_permission_apply_item_id",clazz = String.class)
public class PermissionApplyItem implements Serializable {
    /**表名常量*/
    public static final String TNAME = "par_permission_apply_item";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 申请单项ID(主键)
  	 */
	@TableId
	@ApiModelProperty(value = "申请单项ID(主键)")
  	private String applyItemId;
  	
  	/**
  	 * 申请单ID(par_permission_apply)表主键
  	 */
	@ApiModelProperty(value = "申请单ID(par_permission_apply)表主键")
  	private String applyId;
  	
  	/**
  	 * 操作类型: A:新增  U:修改  D:删除
  	 */
	@ApiModelProperty(value = "操作类型: A:新增  U:修改  D:删除")
  	private String operationType;
  	
  	/**
  	 * 权限规则ID(par_merchant_rules表主键, 操作类型是U:修改和D:删除时 有值)
  	 */
	@ApiModelProperty(value = "权限规则ID(par_merchant_rules表主键, 操作类型是U:修改和D:删除时 有值)")
  	private String merchantRuleId;
  	
  	/**
  	 * 商家ID
  	 */
	@ApiModelProperty(value = "商家ID")
  	private String merchantId;
  	
  	/**
  	 * 规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限
  	 */
	@ApiModelProperty(value = "规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限")
  	private String ruleType;
  	
  	/**
  	 * 对象类型: RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；   RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;  RULE_TYPE  是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；
  	 */
	@ApiModelProperty(value = "对象类型: RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；   RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;  RULE_TYPE  是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；")
  	private String targetType;
  	
  	/**
  	 * 对象ID
  	 */
	@ApiModelProperty(value = "对象ID")
  	private String targetId;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效")
  	private String statusCd;
  	
  	/**
  	 * 记录首次创建的用户标识
  	 */
	@ApiModelProperty(value = "记录首次创建的用户标识")
  	private String createStaff;
  	
  	/**
  	 * 记录首次创建的时间
  	 */
	@ApiModelProperty(value = "记录首次创建的时间")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录每次修改的用户标识
  	 */
	@ApiModelProperty(value = "记录每次修改的用户标识")
  	private String updateStaff;
  	
  	/**
  	 * 记录每次修改的时间
  	 */
	@ApiModelProperty(value = "记录每次修改的时间")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 记录状态变更的时间
  	 */
	@ApiModelProperty(value = "记录状态变更的时间")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 备注信息
  	 */
	@ApiModelProperty(value = "备注信息")
  	private String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 申请单项ID(主键). */
		applyItemId("applyItemId","APPLY_ITEM_ID"),
		
		/** 申请单ID(par_permission_apply)表主键. */
		applyId("applyId","APPLY_ID"),
		
		/** 操作类型: A:新增  U:修改  D:删除. */
		operationType("operationType","OPERATION_TYPE"),
		
		/** 权限规则ID(par_merchant_rules表主键, 操作类型是U:修改和D:删除时 有值). */
		merchantRuleId("merchantRuleId","MERCHANT_RULE_ID"),
		
		/** 商家ID. */
		merchantId("merchantId","MERCHANT_ID"),
		
		/** 规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限. */
		ruleType("ruleType","RULE_TYPE"),
		
		/** 对象类型: RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；   RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;  RULE_TYPE  是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；. */
		targetType("targetType","TARGET_TYPE"),
		
		/** 对象ID. */
		targetId("targetId","TARGET_ID"),
		
		/** 记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的用户标识. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的用户标识. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录状态变更的时间. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 备注信息. */
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
