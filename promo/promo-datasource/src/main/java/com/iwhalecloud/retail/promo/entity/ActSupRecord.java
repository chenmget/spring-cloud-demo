package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ActSupRecord
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACT_SUPPLEMENTARY_RECORD")
@KeySequence(value = "seq_act_supplementary_record_id", clazz = String.class)
@ApiModel(value = "对应模型ACT_SUPPLEMENTARY_RECORD, 对应实体ActSupRecode类")
public class ActSupRecord implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACT_SUPPLEMENTARY_RECORD";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId
	@ApiModelProperty(value = "ID")
  	private String recordId;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
  	
  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private String creator;
  	
  	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private String modifier;
  	
  	/**
  	 * 营销活动id
  	 */
	@ApiModelProperty(value = "营销活动id")
  	private String marketingActivityId;
  	
  	/**
  	 * 用来保存上传的补录证明文件URL，多URL用逗号分隔
  	 */
	@ApiModelProperty(value = "用来保存上传的补录证明文件URL，多URL用逗号分隔")
  	private String applyProof;
  	
  	/**
  	 * 补录的描述说明
  	 */
	@ApiModelProperty(value = "补录的描述说明")
  	private String description;
  	
  	/**
  	 * 待审核：0
            审核不通过：-1
            审核通过：1
            
            已取消：-9
  	 */
	@ApiModelProperty(value = "待审核：0 审核不通过：-1 审核通过：1 已取消：-9")
  	private java.lang.String status;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		recordId("recordId","RECORD_ID"),
		
		/** 创建时间. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** 修改时间. */
		gmtModified("gmtModified","GMT_MODIFIED"),
		
		/** 创建人. */
		creator("creator","CREATOR"),
		
		/** 修改人. */
		modifier("modifier","MODIFIER"),
		
		/** 营销活动id. */
		marketingActivityId("marketingActivityId","MARKETING_ACTIVITY_ID"),
		
		/** 用来保存上传的补录证明文件URL，多URL用逗号分隔. */
		applyProof("applyProof","APPLY_PROOF"),
		
		/** 补录的描述说明. */
		description("description","DESCRIPTION"),
		
		/** 待审核：0
            审核不通过：-1
            审核通过：1
            
            已取消：-9. */
		status("status","STATUS");

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
