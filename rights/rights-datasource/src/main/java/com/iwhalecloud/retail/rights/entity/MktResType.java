package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录了营销资源的类别。MKT_RESOURCE_TYPE--》MKT_RES_TYPE
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_TYPE")
@ApiModel(value = "对应模型MKT_RES_TYPE, 对应实体MktResType类")
@KeySequence(value="seq_mkt_res_type_mkt_res_type_id",clazz = String.class)
public class MktResType implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_TYPE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源CD的标识，主键
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源CD的标识，主键")
  	private java.lang.Long mktResTypeId;
  	
  	/**
  	 * 记录营销资源类别编码，LOVB=RES-0003
  	 */
	@ApiModelProperty(value = "记录营销资源类别编码，LOVB=RES-0003")
  	private java.lang.String mktResTypeNbr;
  	
  	/**
  	 * 营销资源类别名称
  	 */
	@ApiModelProperty(value = "营销资源类别名称")
  	private java.lang.String mktResTypeName;
  	
  	/**
  	 * 记录营销资源类别描述。
  	 */
	@ApiModelProperty(value = "记录营销资源类别描述。")
  	private java.lang.String mktResTypeDesc;
  	
  	/**
  	 * 记录序列化标识，以区分有序资源和无序资源。LOVB=RES-0004
  	 */
	@ApiModelProperty(value = "记录序列化标识，以区分有序资源和无序资源。LOVB=RES-0004")
  	private java.lang.String orderedFlag;
  	
  	/**
  	 * 记录营销资源上级类别CD
  	 */
	@ApiModelProperty(value = "记录营销资源上级类别CD")
  	private java.lang.Long parTypeId;
  	
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
  	 * 记录营销资源类别的状态，LOVB=PUB-C-0001
  	 */
	@ApiModelProperty(value = "记录营销资源类别的状态，LOVB=PUB-C-0001")
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
		/** 营销资源CD的标识，主键. */
		mktResTypeId("mktResTypeId","MKT_RES_TYPE_ID"),
		
		/** 记录营销资源类别编码，LOVB=RES-0003. */
		mktResTypeNbr("mktResTypeNbr","MKT_RES_TYPE_NBR"),
		
		/** 营销资源类别名称. */
		mktResTypeName("mktResTypeName","MKT_RES_TYPE_NAME"),
		
		/** 记录营销资源类别描述。. */
		mktResTypeDesc("mktResTypeDesc","MKT_RES_TYPE_DESC"),
		
		/** 记录序列化标识，以区分有序资源和无序资源。LOVB=RES-0004. */
		orderedFlag("orderedFlag","ORDERED_FLAG"),
		
		/** 记录营销资源上级类别CD. */
		parTypeId("parTypeId","PAR_TYPE_ID"),
		
		/** 记录管理区域标识。. */
		manageRegionId("manageRegionId","MANAGE_REGION_ID"),
		
		/** 记录生效时间。. */
		effDate("effDate","EFF_DATE"),
		
		/** 记录失效日期。. */
		expDate("expDate","EXP_DATE"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录营销资源类别的状态，LOVB=PUB-C-0001. */
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
