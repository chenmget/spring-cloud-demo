package com.iwhalecloud.retail.workflow.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ProcessParam
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_process_param")
@ApiModel(value = "对应模型wf_process_param, 对应实体ProcessParam类")
public class ProcessParam implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_process_param";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "id")
  	private String id;

	/**
	 * processId
	 */
	@ApiModelProperty(value = "processId")
	private String processId;
  	/**
  	 * attrId
  	 */
	@ApiModelProperty(value = "attrId")
  	private String attrId;

  	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;
  	
  	/**
  	 * createUserId
  	 */
	@ApiModelProperty(value = "createUserId")
  	private String createUserId;
  	
  	/**
  	 * updateTime
  	 */
	@ApiModelProperty(value = "updateTime")
  	private java.util.Date updateTime;
  	
  	/**
  	 * updateUserId
  	 */
	@ApiModelProperty(value = "updateUserId")
  	private String updateUserId;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** id */
		id("id","id"),
		/** processId. */
		processId("processId","process_id"),
		
		/** processName. */
		attrId("attrId","attr_id"),

		/** createTime. */
		createTime("createTime","create_time"),
		
		/** createUserId. */
		createUserId("createUserId","create_user_id"),
		
		/** updateTime. */
		updateTime("updateTime","update_time"),
		
		/** updateUserId. */
		updateUserId("updateUserId","update_user_id");

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
