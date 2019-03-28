package com.iwhalecloud.retail.rights.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 记录营销资源的应用分组，简化配置要求。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_GRP")
@ApiModel(value = "对应模型MKT_RES_GRP, 对应实体MktResGrp类")
public class MktResGrp implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_GRP";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源组标识，主键。
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "营销资源组标识，主键。")
  	private java.lang.Long mktResGrpId;
  	
  	/**
  	 * 营销资源组标准编码
  	 */
	@ApiModelProperty(value = "营销资源组标准编码")
  	private java.lang.String mktResGrpNbr;
  	
  	/**
  	 * 营销资源组名称
  	 */
	@ApiModelProperty(value = "营销资源组名称")
  	private java.lang.String mktResGrpName;
  	
  	/**
  	 * 营销资源组描述
  	 */
	@ApiModelProperty(value = "营销资源组描述")
  	private java.lang.String mktResGrpDesc;
  	
  	/**
  	 * 记录适用的区域范围，来源COMMON_REGION_ID。

  	 */
	@ApiModelProperty(value = "记录适用的区域范围，来源COMMON_REGION_ID。")
  	private java.lang.Long applyRegionId;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。

  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 记录创建的员工。

  	 */
	@ApiModelProperty(value = "记录创建的员工。")
  	private java.lang.String createStaff;
  	
  	/**
  	 * 记录修改的员工。

  	 */
	@ApiModelProperty(value = "记录修改的员工。")
  	private java.lang.String updateStaff;
  	
  	/**
  	 * 记录创建的时间。

  	 */
	@ApiModelProperty(value = "记录创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 状态变更的时间。

  	 */
	@ApiModelProperty(value = "状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录修改的时间。

  	 */
	@ApiModelProperty(value = "记录修改的时间。")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源组标识，主键。. */
		mktResGrpId("mktResGrpId","MKT_RES_GRP_ID"),
		
		/** 营销资源组标准编码. */
		mktResGrpNbr("mktResGrpNbr","MKT_RES_GRP_NBR"),
		
		/** 营销资源组名称. */
		mktResGrpName("mktResGrpName","MKT_RES_GRP_NAME"),
		
		/** 营销资源组描述. */
		mktResGrpDesc("mktResGrpDesc","MKT_RES_GRP_DESC"),
		
		/** 记录适用的区域范围，来源COMMON_REGION_ID。
. */
		applyRegionId("applyRegionId","APPLY_REGION_ID"),
		
		/** 记录状态。LOVB=PUB-C-0001。
. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录创建的员工。
. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录修改的员工。
. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录创建的时间。
. */
		createDate("createDate","CREATE_DATE"),
		
		/** 状态变更的时间。
. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录修改的时间。
. */
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
