package com.iwhalecloud.retail.rights.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 营销资源属性。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_ATTR")
@ApiModel(value = "对应模型MKT_RES_ATTR, 对应实体MktResAttr类")
public class MktResAttr implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_ATTR";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 记录营销资源属性标识。
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "记录营销资源属性标识。")
  	private java.lang.Long mktResAttrId;
  	
  	/**
  	 * 记录营销资源标识。
  	 */
	@ApiModelProperty(value = "记录营销资源标识。")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 记录属性标识。
  	 */
	@ApiModelProperty(value = "记录属性标识。")
  	private java.lang.Long attrId;
  	
  	/**
  	 * 记录默认值。
  	 */
	@ApiModelProperty(value = "记录默认值。")
  	private java.lang.String defaultValue;
  	
  	/**
  	 * 记录适用区域标识。
  	 */
	@ApiModelProperty(value = "记录适用区域标识。")
  	private java.lang.Long applyRegionId;
  	
  	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
  	
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
		/** 记录营销资源属性标识。. */
		mktResAttrId("mktResAttrId","MKT_RES_ATTR_ID"),
		
		/** 记录营销资源标识。. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 记录属性标识。. */
		attrId("attrId","ATTR_ID"),
		
		/** 记录默认值。. */
		defaultValue("defaultValue","DEFAULT_VALUE"),
		
		/** 记录适用区域标识。. */
		applyRegionId("applyRegionId","APPLY_REGION_ID"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
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
