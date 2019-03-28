package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * BusinessEntity
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("par_business_entity")
@ApiModel(value = "对应模型par_business_entity, 对应实体BusinessEntity类")
@KeySequence(value="seq_par_business_entity_id",clazz = String.class)
public class BusinessEntity implements Serializable {
    /**表名常量*/
    public static final String TNAME = "par_business_entity";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 经营主体ID
  	 */
	@TableId
	@ApiModelProperty(value = "经营主体ID")
  	private String businessEntityId;
  	
  	/**
  	 * 经营主体编码
  	 */
	@ApiModelProperty(value = "经营主体编码")
  	private String businessEntityCode;
  	
  	/**
  	 * 经营主体名称
  	 */
	@ApiModelProperty(value = "经营主体名称")
  	private String businessEntityName;
  	
  	/**
  	 * 经营主体简称
  	 */
	@ApiModelProperty(value = "经营主体简称")
  	private String businessEntityShortName;
  	
  	/**
  	 * 经营主体级别
  	 */
	@ApiModelProperty(value = "经营主体级别")
  	private String businessEntityLevel;
  	
  	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private String status;
  	
  	/**
  	 * 上级经营主体
  	 */
	@ApiModelProperty(value = "上级经营主体")
  	private String parentBusinessEntityCode;

	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人")
	private String linkman;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	private String phoneNo;

	/**
	 * 本地网
	 */
	@ApiModelProperty(value = "本地网")
	private String lanId;

	/**
	 * 组织机构
	 */
	@ApiModelProperty(value = "组织机构")
	private String orgCode;

	/**
	 * 上级组织
	 */
	@ApiModelProperty(value = "上级组织")
	private String parentOrgCode;

	/**
	 * 所属区域
	 */
	@ApiModelProperty(value = "所属区域")
	private String regionId;

	/**
	 * 关联账号
	 */
	@ApiModelProperty(value = "关联账号")
	private String userid;
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 经营主体ID. */
		businessEntityId("businessEntityId","BUSINESS_ENTITY_ID"),
		
		/** 经营主体编码. */
		businessEntityCode("businessEntityCode","BUSINESS_ENTITY_CODE"),
		
		/** 经营主体名称. */
		businessEntityName("businessEntityName","BUSINESS_ENTITY_NAME"),
		
		/** 经营主体简称. */
		businessEntityShortName("businessEntityShortName","BUSINESS_ENTITY_SHORT_NAME"),
		
		/** 经营主体级别. */
		businessEntityLevel("businessEntityLevel","BUSINESS_ENTITY_LEVEL"),
		
		/** 状态. */
		status("status","STATUS"),

		/** 上级经营主体. */
		parentBusinessEntityCode("parentBusinessEntityCode","PARENT_BUSINESS_ENTITY_CODE"),

		/** 联系人. */
		linkman("linkman","LINKMAN"),

		/** 联系电话. */
		phoneNo("phoneNo","PHONE_NO"),

		/** 本地网. */
		lanId("lanId","LAN_ID"),

		/** 组织机构. */
		orgCode("orgCode","ORG_CODE"),

		/** 上级组织. */
		parentOrgCode("parentOrgCode", "PARENT_ORG_CODE"),

		/** 所属区域. */
		regionId("regionId", "REGION_ID"),

		/** 关联账号. */
		userid("userid", "USERID"),
		;

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
