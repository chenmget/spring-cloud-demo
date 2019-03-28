package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Organization
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_organization")
@ApiModel(value = "对应模型sys_organization, 对应实体Organization类")
@KeySequence(value="seq_organization_org_id",clazz = String.class)

public class Organization implements Serializable {
    /**表名常量*/
    public static final String TNAME = "sys_organization";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * orgId
  	 */
	@TableId
	@ApiModelProperty(value = "orgId")
  	private java.lang.String orgId;
  	
  	/**
  	 * partnerOrgId
  	 */
	@ApiModelProperty(value = "partnerOrgId")
  	private java.lang.String partnerOrgId;

	/**
	 * partnerOrgName
	 */
	@ApiModelProperty(value = "partnerOrgName")
	private java.lang.String partnerOrgName;
  	
  	/**
  	 * orgCode
  	 */
	@ApiModelProperty(value = "orgCode")
  	private java.lang.String orgCode;
  	
  	/**
  	 * orgName
  	 */
	@ApiModelProperty(value = "orgName")
  	private java.lang.String orgName;
  	
  	/**
  	 * lanId
  	 */
	@ApiModelProperty(value = "lanId")
  	private java.lang.String lanId;

	/**
	 * lan
	 */
	@ApiModelProperty(value = "lan")
	private java.lang.String lan;
  	
  	/**
  	 * regionId
  	 */
	@ApiModelProperty(value = "regionId")
  	private java.lang.String regionId;

	/**
	 * regionId
	 */
	@ApiModelProperty(value = "region")
	private java.lang.String region;
  	
  	/**
  	 * orgLevel
  	 */
	@ApiModelProperty(value = "orgLevel")
  	private java.lang.Long orgLevel;
  	
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

	/**
	 * 记录商家编码。
	 */
	@ApiModelProperty(value = "记录商家编码。")
	private java.lang.String merchantCode;
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** orgId. */
		orgId("orgId","ORG_ID"),
		
		/** partnerOrgId. */
		partnerOrgId("partnerOrgId","PARTNER_ORG_ID"),
		
		/** orgCode. */
		orgCode("orgCode","ORG_CODE"),
		
		/** orgName. */
		orgName("orgName","ORG_NAME"),
		
		/** lanId. */
		lanId("lanId","LAN_ID"),
		
		/** regionId. */
		regionId("regionId","REGION_ID"),
		
		/** orgLevel. */
		orgLevel("orgLevel","ORG_LEVEL"),
		
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
		updateDate("updateDate","UPDATE_DATE"),

		/** 记录商家编码。. */
		merchantCode("merchantCode","MERCHANT_CODE");

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
