package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分销商
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("PAR_PARTNER")
@ApiModel(value = "对应模型PAR_PARTNER, 对应实体Partner类")
@KeySequence(value="seq_par_partner_id",clazz = String.class)

public class Partner implements Serializable {
    /**表名常量*/
    public static final String TNAME = "PAR_PARTNER";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 分销商id
  	 */
	@TableId
	@ApiModelProperty(value = "分销商id")
  	private java.lang.String partnerId;
  	
  	/**
  	 * 分销商编码
  	 */
	@ApiModelProperty(value = "分销商编码")
  	private java.lang.String partnerCode;
  	
  	/**
  	 * 分销商名称
  	 */
	@ApiModelProperty(value = "分销商名称")
  	private java.lang.String partnerName;
  	
  	/**
  	 * 联系人
  	 */
	@ApiModelProperty(value = "联系人")
  	private java.lang.String linkman;
  	
  	/**
  	 * 联系电话
  	 */
	@ApiModelProperty(value = "联系电话")
  	private java.lang.String phoneNo;
  	
  	/**
  	 * 状态 0申请、1正常、2冻结、3注销,-1审核不通过
  	 */
	@ApiModelProperty(value = "状态 0申请、1正常、2冻结、3注销,-1审核不通过")
  	private java.lang.String state;
  	
  	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
  	
  	/**
  	 * 最后更新时间
  	 */
	@ApiModelProperty(value = "最后更新时间")
  	private java.util.Date lastUpdateDate;

  	/**
  	 * 所属区域
  	 */
	@ApiModelProperty(value = "所属区域")
  	private java.lang.String regionId;
  	
  	/**
  	 * 本地网
  	 */
	@ApiModelProperty(value = "本地网")
  	private java.lang.String lanId;
  	
  	/**
  	 * 进驻厅店
  	 */
	@ApiModelProperty(value = "进驻厅店")
  	private java.lang.String partnerShopId;
  	
  	/**
  	 * 驻店商归属经营主体名称
  	 */
	@ApiModelProperty(value = "驻店商归属经营主体名称")
  	private java.lang.String businessEntityName;
  	
  	/**
  	 * 驻店商归属经营主体编码
  	 */
	@ApiModelProperty(value = "驻店商归属经营主体编码")
  	private java.lang.String businessEntityCode;
  	
  	/**
  	 * MSS代理商编码
  	 */
	@ApiModelProperty(value = "MSS代理商编码")
  	private java.lang.String mssPartnerCode;
  	
  	/**
  	 * 客户编码
  	 */
	@ApiModelProperty(value = "客户编码")
  	private java.lang.String customerCode;
  	
  	/**
  	 * 统一组织编码
  	 */
	@ApiModelProperty(value = "统一组织编码")
  	private java.lang.String orgCode;
  	
  	/**
  	 * 组织类型
  	 */
	@ApiModelProperty(value = "组织类型")
  	private java.lang.String orgType;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 分销商id. */
		partnerId("partnerId","PARTNER_ID"),
		
		/** 分销商编码. */
		partnerCode("partnerCode","PARTNER_CODE"),
		
		/** 分销商名称. */
		partnerName("partnerName","PARTNER_NAME"),
		
		/** 联系人. */
		linkman("linkman","LINKMAN"),
		
		/** 联系电话. */
		phoneNo("phoneNo","PHONE_NO"),
		
		/** 状态 0申请、1正常、2冻结、3注销,-1审核不通过. */
		state("state","STATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE"),
		
		/** 关联adminuser表userid. */
		userid("userid","USERID"),
		
		/** 最后更新时间. */
		lastUpdateDate("lastUpdateDate","LAST_UPDATE_DATE"),
		
		/** 平台. */
		sourceFrom("sourceFrom","SOURCE_FROM"),
		
		/** 所属区域. */
		regionId("regionId","REGION_ID"),
		
		/** 本地网. */
		lanId("lanId","LAN_ID"),
		
		/** 进驻厅店. */
		partnerShopId("partnerShopId","PARTNER_SHOP_ID"),
		
		/** 驻店商归属经营主体名称. */
		businessEntityName("businessEntityName","BUSINESS_ENTITY_NAME"),
		
		/** 驻店商归属经营主体编码. */
		businessEntityCode("businessEntityCode","BUSINESS_ENTITY_CODE"),
		
		/** MSS代理商编码. */
		mssPartnerCode("mssPartnerCode","MSS_PARTNER_CODE"),
		
		/** 客户编码. */
		customerCode("customerCode","CUSTOMER_CODE"),
		
		/** 统一组织编码. */
		orgCode("orgCode","ORG_CODE"),
		
		/** 组织类型. */
		orgType("orgType","ORG_TYPE");

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
