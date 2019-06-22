package com.iwhalecloud.retail.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * AttrValueSpec
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_attr_value_spec")
@ApiModel(value = "对应模型wf_attr_value_spec, 对应实体AttrValueSpec类")
public class AttrValueSpec implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_attr_value_spec";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 属性ID
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "属性ID")
  	private String attrId;
  	
  	/**
  	 * 属性值ID
  	 */
	@ApiModelProperty(value = "属性值ID")
  	private String attrValueId;
  	
  	/**
  	 * 属性值的文本名称
  	 */
	@ApiModelProperty(value = "属性值的文本名称")
  	private String attrValueName;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private String statusCd;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
  	
  	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private String createStaff;
  	
  	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private String updateStaff;
  	
  	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 属性ID. */
		attrId("attrId","attr_id"),
		
		/** 属性值ID. */
		attrValueId("attrValueId","attr_value_id"),
		
		/** 属性值的文本名称. */
		attrValueName("attrValueName","attr_value_name"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","status_cd"),
		
		/** 备注. */
		remark("remark","remark"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","create_staff"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","create_date"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","update_staff"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","update_date");

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
