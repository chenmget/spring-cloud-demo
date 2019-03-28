package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResourceChngEvtDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_chng_evt_detail")
@ApiModel(value = "对应模型mkt_res_chng_evt_detail, 对应实体ResourceChngEvtDetail类")
@KeySequence(value="seq_mkt_res_chng_evt_detail_id",clazz = String.class)
public class ResourceChngEvtDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_chng_evt_detail";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源库存变动事件明细ID，修改字段名
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源库存变动事件明细ID，修改字段名")
  	private java.lang.String mktResChngEvtDetailId;
  	
  	/**
  	 * 营销资源库存变动事件标识
  	 */
	@ApiModelProperty(value = "营销资源库存变动事件标识")
  	private java.lang.String mktResEventId;
  	
  	/**
  	 * 营销资源仓库标识，记录事件影响的仓库
  	 */
	@ApiModelProperty(value = "营销资源仓库标识，记录事件影响的仓库")
  	private java.lang.String mktResStoreId;
  	
  	/**
  	 * 营销资源实例标识
  	 */
	@ApiModelProperty(value = "营销资源实例标识")
  	private java.lang.String mktResInstId;

	/**
	 * 营销资源实例编码
	 */
	@ApiModelProperty(value = "营销资源实例编码")
	private java.lang.String mktResInstNbr;
  	
  	/**
  	 * 记录出入库类型，LOVB=RES-0006
  	 */
	@ApiModelProperty(value = "记录出入库类型，LOVB=RES-0006")
  	private java.lang.String chngType;
  	
  	/**
  	 * 记录出入库操作的数量
  	 */
	@ApiModelProperty(value = "记录出入库操作的数量")
  	private java.lang.Long quantity;
  	
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
  	 * 本地网
  	 */
	@ApiModelProperty(value = "本地网")
  	private java.lang.String lanId;
  	
  	/**
  	 * city
  	 */
	@ApiModelProperty(value = "city")
  	private java.lang.String city;
  	
  	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.String regionId;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源库存变动事件明细ID，修改字段名. */
		mktResChngEvtDetailId("mktResChngEvtDetailId","MKT_RES_CHNG_EVT_DETAIL_ID"),
		
		/** 营销资源库存变动事件标识. */
		mktResEventId("mktResEventId","MKT_RES_EVENT_ID"),
		
		/** 营销资源仓库标识，记录事件影响的仓库. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 营销资源实例标识. */
		mktResInstId("mktResInstId","MKT_RES_INST_ID"),
		
		/** 记录出入库类型，LOVB=RES-0006. */
		chngType("chngType","CHNG_TYPE"),
		
		/** 记录出入库操作的数量. */
		quantity("quantity","QUANTITY"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 本地网. */
		lanId("lanId","LAN_ID"),
		
		/** city. */
		city("city","CITY"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
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
