package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录营销资源属性选用值。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_ATTR_VALUE")
@ApiModel(value = "对应模型MKT_RES_ATTR_VALUE, 对应实体MktResAttrValue类")
public class MktResAttrValue implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_ATTR_VALUE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 记录营销资源属性选用值标识。
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "记录营销资源属性选用值标识。")
  	private java.lang.Long mktResAttrValId;
  	
  	/**
  	 * 记录营销资源属性标识。
  	 */
	@ApiModelProperty(value = "记录营销资源属性标识。")
  	private java.lang.Long mktResAttrId;
  	
  	/**
  	 * 记录属性值标识。
  	 */
	@ApiModelProperty(value = "记录属性值标识。")
  	private java.lang.Long attrValueId;
  	
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
		/** 记录营销资源属性选用值标识。. */
		mktResAttrValId("mktResAttrValId","MKT_RES_ATTR_VAL_ID"),
		
		/** 记录营销资源属性标识。. */
		mktResAttrId("mktResAttrId","MKT_RES_ATTR_ID"),
		
		/** 记录属性值标识。. */
		attrValueId("attrValueId","ATTR_VALUE_ID"),
		
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
