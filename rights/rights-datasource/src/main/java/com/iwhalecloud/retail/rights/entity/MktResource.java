package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 记录电信涉及的各类营销资源规格定义信息，可以按类别、品牌、型号、颜色等维度根据业务需要组合定义。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RESOURCE")
@KeySequence(value="seq_mkt_resource_mkt_res_id",clazz = String.class)
@ApiModel(value = "对应模型MKT_RESOURCE, 对应实体MktResource类")
public class MktResource implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RESOURCE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源标识
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 记录营销资源编码。
  	 */
	@ApiModelProperty(value = "记录营销资源编码。")
  	private java.lang.String mktResNbr;
  	
  	/**
  	 * 营销资源类别标识
  	 */
	@ApiModelProperty(value = "营销资源类别标识")
  	private java.lang.Long mktResTypeId;
  	
  	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.Long mktResExttypeId;
  	
  	/**
  	 * 记录营销资源名称。
  	 */
	@ApiModelProperty(value = "记录营销资源名称。")
  	private java.lang.String mktResName;
  	
  	/**
  	 * 记录营销资源描述。
  	 */
	@ApiModelProperty(value = "记录营销资源描述。")
  	private java.lang.String mktResDesc;
  	
  	/**
  	 * 记录计费域的营销资源定价计划标识。20150421。
  	 */
	@ApiModelProperty(value = "记录计费域的营销资源定价计划标识。20150421。")
  	private java.lang.Long mktPricingPlanId;
  	
  	/**
  	 * 记录营销资源实例的数量单位。LOVB=RES-C-0001
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量单位。LOVB=RES-C-0001")
  	private java.lang.String unit;
  	
  	/**
  	 * 记录序列化标识，以区分有序资源和无序资源。LOVB=RES-0004
  	 */
	@ApiModelProperty(value = "记录序列化标识，以区分有序资源和无序资源。LOVB=RES-0004")
  	private java.lang.String orderedFlag;
  	
  	/**
  	 * 记录管理区域标识。
  	 */
	@ApiModelProperty(value = "记录管理区域标识。")
  	private java.lang.Long manageRegionId;
  	
  	/**
  	 * 记录生效时间。
  	 */
	@ApiModelProperty(value = "记录生效时间。")
  	private java.util.Date effDate;
  	
  	/**
  	 * 记录失效日期。
  	 */
	@ApiModelProperty(value = "记录失效日期。")
  	private java.util.Date expDate;
  	
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
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
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
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 记录营销资源编码。. */
		mktResNbr("mktResNbr","MKT_RES_NBR"),
		
		/** 营销资源类别标识. */
		mktResTypeId("mktResTypeId","MKT_RES_TYPE_ID"),
		
		/** 营销资源标识. */
		mktResExttypeId("mktResExttypeId","MKT_RES_EXTTYPE_ID"),
		
		/** 记录营销资源名称。. */
		mktResName("mktResName","MKT_RES_NAME"),
		
		/** 记录营销资源描述。. */
		mktResDesc("mktResDesc","MKT_RES_DESC"),
		
		/** 记录计费域的营销资源定价计划标识。20150421。. */
		mktPricingPlanId("mktPricingPlanId","MKT_PRICING_PLAN_ID"),
		
		/** 记录营销资源实例的数量单位。LOVB=RES-C-0001. */
		unit("unit","UNIT"),
		
		/** 记录序列化标识，以区分有序资源和无序资源。LOVB=RES-0004. */
		orderedFlag("orderedFlag","ORDERED_FLAG"),
		
		/** 记录管理区域标识。. */
		manageRegionId("manageRegionId","MANAGE_REGION_ID"),
		
		/** 记录生效时间。. */
		effDate("effDate","EFF_DATE"),
		
		/** 记录失效日期。. */
		expDate("expDate","EXP_DATE"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 备注. */
		remark("remark","REMARK"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE");

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
