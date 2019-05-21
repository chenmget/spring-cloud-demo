package com.iwhalecloud.retail.promo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ActivityChangeDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_activity_change_detail")
@ApiModel(value = "对应模型act_activity_change_detail, 对应实体ActivityChangeDetail类")
public class ActivityChangeDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_activity_change_detail";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 产品变更明细id
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "产品变更明细id")
  	private java.lang.String changeDetailId;
  	
  	/**
  	 * 产品变更业务id
  	 */
	@ApiModelProperty(value = "产品变更业务id")
  	private java.lang.String changeId;
  	
  	/**
  	 * 操作类型，add：新增，mod：修改，del：删除
  	 */
	@ApiModelProperty(value = "操作类型，add：新增，mod：修改，del：删除")
  	private java.lang.String operType;
  	
  	/**
  	 * 记录产品的版本号
  	 */
	@ApiModelProperty(value = "记录产品的版本号")
  	private java.lang.Long verNum;
  	
  	/**
  	 * 记录变更的业务表名
  	 */
	@ApiModelProperty(value = "记录变更的业务表名")
  	private java.lang.String tableName;
  	
  	/**
  	 * 记录变更的字段名
  	 */
	@ApiModelProperty(value = "记录变更的字段名")
  	private java.lang.String changeField;
  	
  	/**
  	 * 记录变更的字段名
  	 */
	@ApiModelProperty(value = "记录变更的字段名")
  	private java.lang.String changeFieldName;
  	
  	/**
  	 * 记录变更字段的类型：1. 字符和数字，2. 时间，3. 图片
  	 */
	@ApiModelProperty(value = "记录变更字段的类型：1. 字符和数字，2. 时间，3. 图片")
  	private java.lang.String fieldType;
  	
  	/**
  	 * 原始值
  	 */
	@ApiModelProperty(value = "原始值")
  	private java.lang.String oldValue;
  	
  	/**
  	 * 变更值
  	 */
	@ApiModelProperty(value = "变更值")
  	private java.lang.String newValue;
  	
  	/**
  	 * 业务id
  	 */
	@ApiModelProperty(value = "业务id")
  	private java.lang.String keyValue;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String createStaff;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 产品变更明细id. */
		changeDetailId("changeDetailId","change_detail_id"),
		
		/** 产品变更业务id. */
		changeId("changeId","change_id"),
		
		/** 操作类型，add：新增，mod：修改，del：删除. */
		operType("operType","oper_type"),
		
		/** 记录产品的版本号. */
		verNum("verNum","ver_num"),
		
		/** 记录变更的业务表名. */
		tableName("tableName","table_name"),
		
		/** 记录变更的字段名. */
		changeField("changeField","change_field"),
		
		/** 记录变更的字段名. */
		changeFieldName("changeFieldName","change_field_name"),
		
		/** 记录变更字段的类型：1. 字符和数字，2. 时间，3. 图片. */
		fieldType("fieldType","field_type"),
		
		/** 原始值. */
		oldValue("oldValue","old_value"),
		
		/** 变更值. */
		newValue("newValue","new_value"),
		
		/** 业务id. */
		keyValue("keyValue","key_value"),
		
		/** 创建时间. */
		createDate("createDate","create_date"),
		
		/** 创建人. */
		createStaff("createStaff","create_staff");

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
