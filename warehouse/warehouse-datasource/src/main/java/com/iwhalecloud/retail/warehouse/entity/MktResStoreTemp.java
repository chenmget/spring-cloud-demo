package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MktResStoreTemp
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_STORE_TEMP")
@ApiModel(value = "对应模型MKT_RES_STORE_TEMP, 对应实体MktResStoreTemp类")
public class MktResStoreTemp implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_STORE_TEMP";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源仓库标识
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "营销资源仓库标识")
  	private String mktResStoreId;
  	
  	/**
  	 * 仓库名称
  	 */
	@ApiModelProperty(value = "仓库名称")
  	private String mktResStoreName;
  	
  	/**
  	 * 盘存时间
  	 */
	@ApiModelProperty(value = "盘存时间")
  	private java.util.Date checkDate;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
  	
  	/**
  	 * 上级仓库标识
  	 */
	@ApiModelProperty(value = "上级仓库标识")
  	private Long parStoreId;
  	
  	/**
  	 * 组织标识
  	 */
	@ApiModelProperty(value = "组织标识")
  	private Long orgId;
  	
  	/**
  	 * 营销资源仓库管理员ID
  	 */
	@ApiModelProperty(value = "营销资源仓库管理员ID")
  	private Long staffId;
  	
  	/**
  	 * 区域标识
  	 */
	@ApiModelProperty(value = "区域标识")
  	private Long regionId;
  	
  	/**
  	 * 渠道业务编码
  	 */
	@ApiModelProperty(value = "渠道业务编码")
  	private Long channelId;
  	
  	/**
  	 * 仓库类型
  	 */
	@ApiModelProperty(value = "仓库类型")
  	private String storeType;
  	
  	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private String statusCd;
  	
  	/**
  	 * 状态时间
  	 */
	@ApiModelProperty(value = "状态时间")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
  	
  	/**
  	 * 仓库编码
  	 */
	@ApiModelProperty(value = "仓库编码")
  	private String mktResStoreNbr;
  	
  	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String storageCode1;
  	
  	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String storageCode2;
  	
  	/**
  	 * 本地网标识
  	 */
	@ApiModelProperty(value = "本地网标识")
  	private Long lanId;
  	
  	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private Long operId;
  	
  	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String address;
  	
  	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String vbatchcode;
  	
  	/**
  	 * 物资类型
  	 */
	@ApiModelProperty(value = "物资类型")
  	private Long rcType;
  	
  	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private Long familyId;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private Long createStaff;
  	
  	/**
  	 * 普通(0)、县级直供中心(1)、新小偏网点(2)
  	 */
	@ApiModelProperty(value = "普通(0)、县级直供中心(1)、新小偏网点(2)")
  	private String directSupply;
  	
  	/**
  	 *  2.0多出无名氏
  	 */
	@ApiModelProperty(value = " 2.0多出无名氏")
  	private String provider;
  	
  	/**
  	 * 2.0多出无名氏
  	 */
	@ApiModelProperty(value = "2.0多出无名氏")
  	private String providerName;
  	
  	/**
  	 * 回收仓库标识
  	 */
	@ApiModelProperty(value = "回收仓库标识")
  	private Long recStoreId;
  	
  	/**
  	 * 回收方式
  	 */
	@ApiModelProperty(value = "回收方式")
  	private String recType;
  	
  	/**
  	 * 回收期限
  	 */
	@ApiModelProperty(value = "回收期限")
  	private Long recDay;
  	
  	/**
  	 * 仓库细类
  	 */
	@ApiModelProperty(value = "仓库细类")
  	private String storeSubType;
  	
  	/**
  	 *  仓库层级
  	 */
	@ApiModelProperty(value = " 仓库层级")
  	private String storeGrade;
  	
  	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
  	
  	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private Long updateStaff;
  	
  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 同步状态:1已同步，0未同步
  	 */
	@ApiModelProperty(value = "同步状态:1已同步，0未同步")
  	private String synStatus;
  	
  	/**
  	 * synDate
  	 */
	@ApiModelProperty(value = "synDate")
  	private java.util.Date synDate;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 仓库名称. */
		mktResStoreName("mktResStoreName","MKT_RES_STORE_NAME"),
		
		/** 盘存时间. */
		checkDate("checkDate","CHECK_DATE"),
		
		/** 创建时间. */
		createDate("createDate","CREATE_DATE"),
		
		/** 上级仓库标识. */
		parStoreId("parStoreId","PAR_STORE_ID"),
		
		/** 组织标识. */
		orgId("orgId","ORG_ID"),
		
		/** 营销资源仓库管理员ID. */
		staffId("staffId","STAFF_ID"),
		
		/** 区域标识. */
		regionId("regionId","REGION_ID"),
		
		/** 渠道业务编码. */
		channelId("channelId","CHANNEL_ID"),
		
		/** 仓库类型. */
		storeType("storeType","STORE_TYPE"),
		
		/** 状态. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 状态时间. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 备注. */
		remark("remark","REMARK"),
		
		/** 仓库编码. */
		mktResStoreNbr("mktResStoreNbr","MKT_RES_STORE_NBR"),
		
		/** 2.0多出无名氏. */
		storageCode1("storageCode1","STORAGE_CODE1"),
		
		/** 2.0多出无名氏. */
		storageCode2("storageCode2","STORAGE_CODE2"),
		
		/** 本地网标识. */
		lanId("lanId","LAN_ID"),
		
		/** 2.0多出无名氏. */
		operId("operId","OPER_ID"),
		
		/** 2.0多出无名氏. */
		address("address","ADDRESS"),
		
		/** 2.0多出无名氏. */
		vbatchcode("vbatchcode","VBATCHCODE"),
		
		/** 物资类型. */
		rcType("rcType","RC_TYPE"),
		
		/** 2.0多出无名氏. */
		familyId("familyId","FAMILY_ID"),
		
		/** 创建人. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 普通(0)、县级直供中心(1)、新小偏网点(2). */
		directSupply("directSupply","DIRECT_SUPPLY"),
		
		/**  2.0多出无名氏. */
		provider("provider","PROVIDER"),
		
		/** 2.0多出无名氏. */
		providerName("providerName","PROVIDER_NAME"),
		
		/** 回收仓库标识. */
		recStoreId("recStoreId","REC_STORE_ID"),
		
		/** 回收方式. */
		recType("recType","REC_TYPE"),
		
		/** 回收期限. */
		recDay("recDay","REC_DAY"),
		
		/** 仓库细类. */
		storeSubType("storeSubType","STORE_SUB_TYPE"),
		
		/**  仓库层级. */
		storeGrade("storeGrade","STORE_GRADE"),
		
		/** 生效时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE"),
		
		/** 修改人. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 修改时间. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 同步状态:1已同步，0未同步. */
		synStatus("synStatus","SYN_STATUS"),
		
		/** synDate. */
		synDate("synDate","SYN_DATE");

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
