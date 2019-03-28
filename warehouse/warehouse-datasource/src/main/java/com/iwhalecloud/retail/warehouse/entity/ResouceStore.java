package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResouceStore
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_store")
@ApiModel(value = "对应模型mkt_res_store, 对应实体ResouceStore类")
//@KeySequence(value="seq_mkt_res_store_id",clazz = String.class)

public class ResouceStore implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_store";
  	private static final long serialVersionUID = 1L;
  
  	
  	/**
  	 * 营销资源仓库标识
  	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
  	
  	/**
  	 * 记录营销资源仓库编码编码。
  	 */
	@ApiModelProperty(value = "记录营销资源仓库编码编码。")
  	private java.lang.String mktResStoreNbr;
  	
  	/**
  	 * 记录仓库名称。
  	 */
	@ApiModelProperty(value = "记录仓库名称。")
  	private java.lang.String mktResStoreName;
  	
  	/**
  	 * 记录盘存时间。
  	 */
	@ApiModelProperty(value = "记录盘存时间。")
  	private java.util.Date checkDate;
  	
  	/**
  	 * 记录上级库存标识。UP_STORE_ID -->PAR_STORE_ID。修改为上级仓库标识。
  	 */
	@ApiModelProperty(value = "记录上级库存标识。UP_STORE_ID -->PAR_STORE_ID。修改为上级仓库标识。")
  	private java.lang.String parStoreId;
  	
  	/**
  	 * 记录资源回收的目标库存标识。待讨论。
  	 */
	@ApiModelProperty(value = "记录资源回收的目标库存标识。待讨论。")
  	private java.lang.String recStoreId;
  	
  	/**
  	 * 记录号码的回收方式：本地网回收，管理机构回收，回收池回收，回收池回收并回放，默认管理机构回收。LOVB=RES-C-0015
  	 */
	@ApiModelProperty(value = "记录号码的回收方式：本地网回收，管理机构回收，回收池回收，回收池回收并回放，默认管理机构回收。LOVB=RES-C-0015")
  	private java.lang.String recType;
  	
  	/**
  	 * 记录号码回收期限：必须输入（天数，默认90天）@20050414
  	 */
	@ApiModelProperty(value = "记录号码回收期限：必须输入（天数，默认90天）@20050414")
  	private java.lang.Long recDay;
  	
  	/**
  	 * 记录营销资源仓库的类型，LOVB=RES-0001
  	 */
	@ApiModelProperty(value = "记录营销资源仓库的类型，LOVB=RES-0001")
  	private java.lang.String storeType;
  	
  	/**
  	 * 记录营销资源仓库的小类型，LOVB=RES-C-0003
  	 */
	@ApiModelProperty(value = "记录营销资源仓库的小类型，LOVB=RES-C-0003")
  	private java.lang.String storeSubType;
  	
  	/**
  	 * 仓库层级，LOVB=RES-C-0016
  	 */
	@ApiModelProperty(value = "仓库层级，LOVB=RES-C-0016")
  	private java.lang.String storeGrade;
  	
  	/**
  	 * 记录仓库正式启用的时间
  	 */
	@ApiModelProperty(value = "记录仓库正式启用的时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 记录仓库正式失效的时间。
  	 */
	@ApiModelProperty(value = "记录仓库正式失效的时间。")
  	private java.util.Date expDate;
  	
  	/**
  	 * 记录营销资源仓库状态。LOVB=RES-0002
  	 */
	@ApiModelProperty(value = "记录营销资源仓库状态。LOVB=RES-0002")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 备注。
  	 */
	@ApiModelProperty(value = "备注。")
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
  	 * 本地网标识
  	 */
	@ApiModelProperty(value = "本地网标识")
  	private java.lang.String lanId;
  	
  	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.String regionId;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 记录营销资源仓库编码编码。. */
		mktResStoreNbr("mktResStoreNbr","MKT_RES_STORE_NBR"),
		
		/** 记录仓库名称。. */
		mktResStoreName("mktResStoreName","MKT_RES_STORE_NAME"),
		
		/** 记录盘存时间。. */
		checkDate("checkDate","CHECK_DATE"),
		
		/** 记录上级库存标识。UP_STORE_ID -->PAR_STORE_ID。修改为上级仓库标识。. */
		parStoreId("parStoreId","PAR_STORE_ID"),
		
		/** 记录资源回收的目标库存标识。待讨论。. */
		recStoreId("recStoreId","REC_STORE_ID"),
		
		/** 记录号码的回收方式：本地网回收，管理机构回收，回收池回收，回收池回收并回放，默认管理机构回收。LOVB=RES-C-0015. */
		recType("recType","REC_TYPE"),
		
		/** 记录号码回收期限：必须输入（天数，默认90天）@20050414. */
		recDay("recDay","REC_DAY"),
		
		/** 记录营销资源仓库的类型，LOVB=RES-0001. */
		storeType("storeType","STORE_TYPE"),
		
		/** 记录营销资源仓库的小类型，LOVB=RES-C-0003. */
		storeSubType("storeSubType","STORE_SUB_TYPE"),
		
		/** 仓库层级，LOVB=RES-C-0016. */
		storeGrade("storeGrade","STORE_GRADE"),
		
		/** 记录仓库正式启用的时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 记录仓库正式失效的时间。. */
		expDate("expDate","EXP_DATE"),
		
		/** 记录营销资源仓库状态。LOVB=RES-0002. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 备注。. */
		remark("remark","REMARK"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 本地网标识. */
		lanId("lanId","LAN_ID"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID");

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
