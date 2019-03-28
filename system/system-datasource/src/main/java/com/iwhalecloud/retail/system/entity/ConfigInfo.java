package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ConfigInfo
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("SYS_CONFIG_INFO")
@KeySequence(value="seq_sys_config_info_cf_id",clazz = String.class)
@ApiModel(value = "对应模型SYS_CONFIG_INFO, 对应实体ConfigInfo类")
public class ConfigInfo implements Serializable {
    /**表名常量*/
    public static final String TNAME = "SYS_CONFIG_INFO";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * cfId
  	 */
	@TableId
	@ApiModelProperty(value = "cfId")
  	private String cfId;
  	
  	/**
  	 * cfDesc
  	 */
	@ApiModelProperty(value = "cfDesc")
  	private String cfDesc;
  	
  	/**
  	 * cfValue
  	 */
	@ApiModelProperty(value = "cfValue")
  	private String cfValue;
  	
  	/**
  	 * subSystem
  	 */
	@ApiModelProperty(value = "subSystem")
  	private String subSystem;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** cfId. */
		cfId("cfId","CF_ID"),
		
		/** cfDesc. */
		cfDesc("cfDesc","CF_DESC"),
		
		/** cfValue. */
		cfValue("cfValue","CF_VALUE"),
		
		/** subSystem. */
		subSystem("subSystem","SUB_SYSTEM");

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
