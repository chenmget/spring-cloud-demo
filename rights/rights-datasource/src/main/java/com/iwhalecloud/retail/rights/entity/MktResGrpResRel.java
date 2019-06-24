package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述营销资源组与营销资源之间的关系
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_GRP_RES_REL")
@ApiModel(value = "对应模型MKT_RES_GRP_RES_REL, 对应实体MktResGrpResRel类")
@KeySequence(value="seq_mkt_res_grp_res_rel",clazz = String.class)
public class MktResGrpResRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_GRP_RES_REL";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源组成员主键
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源组成员主键")
  	private java.lang.Long grpResRelId;
  	
  	/**
  	 * 营销资源组标识
  	 */
	@ApiModelProperty(value = "营销资源组标识")
  	private java.lang.Long mktResGrpId;
  	
  	/**
  	 * 记录营销资源标识。
餐，3-基础销售品，0-品牌类销售品
  	 */
	@ApiModelProperty(value = "记录营销资源标识。餐，3-基础销售品，0-品牌类销售品")
  	private java.lang.String mktResId;
  	
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
		/** 营销资源组成员主键. */
		grpResRelId("grpResRelId","GRP_RES_REL_ID"),
		
		/** 营销资源组标识. */
		mktResGrpId("mktResGrpId","MKT_RES_GRP_ID"),
		
		/** 记录营销资源标识。
餐，3-基础销售品，0-品牌类销售品. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 记录适用的区域范围，来源COMMON_REGION_ID。
. */
		applyRegionId("applyRegionId","APPLY_REGION_ID"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
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
