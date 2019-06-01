package com.iwhalecloud.retail.promo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ActivityChange
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_activity_change")
@ApiModel(value = "对应模型act_activity_change, 对应实体ActivityChange类")
public class ActivityChange implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_activity_change";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 产品变更业务id
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "产品变更业务id")
  	private java.lang.String changeId;
  	
  	/**
  	 * 记录产品的版本号
  	 */
	@ApiModelProperty(value = "记录产品的版本号")
  	private java.lang.Long verNum;
  	
  	/**
  	 * 营销活动id
  	 */
	@ApiModelProperty(value = "营销活动id")
  	private java.lang.String marketingActivityId;
  	
  	/**
  	 * 审核状态：1 待提交，2 审核中，3 审核通过，4 审核不通过
  	 */
	@ApiModelProperty(value = "审核状态：1 待提交，2 审核中，3 审核通过，4初审不通过，5 终审不通过")
  	private java.lang.String auditState;
  	
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
		/** 产品变更业务id. */
		changeId("changeId","change_id"),
		
		/** 记录产品的版本号. */
		verNum("verNum","ver_num"),
		
		/** 营销活动id. */
		marketingActivityId("marketingActivityId","marketing_activity_id"),
		
		/** 审核状态：1 待提交，2 审核中，3 审核通过，4 审核不通过. */
		auditState("auditState","audit_state"),
		
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
