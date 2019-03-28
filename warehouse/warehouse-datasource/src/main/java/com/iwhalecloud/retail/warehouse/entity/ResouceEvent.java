package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResouceEvent
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_event")
@ApiModel(value = "对应模型mkt_res_event, 对应实体ResouceEvent类")
@KeySequence(value="seq_mkt_res_event_id",clazz = String.class)

public class ResouceEvent implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_event";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源库存变动事件标识
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源库存变动事件标识")
  	private java.lang.String mktResEventId;
  	
  	/**
  	 * 营销资源库存变动事件编码
  	 */
	@ApiModelProperty(value = "营销资源库存变动事件编码")
  	private java.lang.String mktResEventNbr;
  	
  	/**
  	 * 记录营销资源库存事件名称名称。
  	 */
	@ApiModelProperty(value = "记录营销资源库存事件名称名称。")
  	private java.lang.String mktResEventName;
  	
  	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
  	
  	/**
  	 * 目标营销资源仓库
  	 */
	@ApiModelProperty(value = "目标营销资源仓库")
  	private java.lang.String destStoreId;
  	
  	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 描述触发事件的对象类型：资源申请单,订单项等。LOVB=RES-C-0006
  	 */
	@ApiModelProperty(value = "描述触发事件的对象类型：资源申请单,订单项等。LOVB=RES-C-0006")
  	private java.lang.String objType;
  	
  	/**
  	 * 记录触发事件的资源申请单标识、订单项标识等20150325
  	 */
	@ApiModelProperty(value = "记录触发事件的资源申请单标识、订单项标识等20150325")
  	private java.lang.String objId;
  	
  	/**
  	 * 事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007
  	 */
	@ApiModelProperty(value = "事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007")
  	private java.lang.String eventType;
  	
  	/**
  	 * 记录事件描述信息
  	 */
	@ApiModelProperty(value = "记录事件描述信息")
  	private java.lang.String eventDesc;
  	
  	/**
  	 * 资源所属店中商ID
  	 */
	@ApiModelProperty(value = "资源所属店中商ID")
  	private java.lang.String merchantId;
  	
  	/**
  	 * 记录受理时间。
  	 */
	@ApiModelProperty(value = "记录受理时间。")
  	private java.util.Date acceptDate;
  	
  	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录状态。LOVB=RES-C-0008
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-C-0008")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 记录首次创建的系统岗位标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的系统岗位标识。")
  	private java.lang.String createPost;
  	
  	/**
  	 * 记录首次创建的组织机构标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的组织机构标识。")
  	private java.lang.String createOrgId;
  	
  	/**
  	 * 记录首次创建的用户标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的用户标识。")
  	private java.lang.String createStaff;
  	
  	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录每次修改的用户标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的用户标识。")
  	private java.lang.String updateStaff;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源库存变动事件标识. */
		mktResEventId("mktResEventId","MKT_RES_EVENT_ID"),
		
		/** 营销资源库存变动事件编码. */
		mktResEventNbr("mktResEventNbr","MKT_RES_EVENT_NBR"),
		
		/** 记录营销资源库存事件名称名称。. */
		mktResEventName("mktResEventName","MKT_RES_EVENT_NAME"),
		
		/** 营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 目标营销资源仓库. */
		destStoreId("destStoreId","DEST_STORE_ID"),
		
		/** 营销资源标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 描述触发事件的对象类型：资源申请单,订单项等。LOVB=RES-C-0006. */
		objType("objType","OBJ_TYPE"),
		
		/** 记录触发事件的资源申请单标识、订单项标识等20150325. */
		objId("objId","OBJ_ID"),
		
		/** 事件类型，记录入库、出库、调拨、订单等触发的事件类型。LOVB=RES-C-0007. */
		eventType("eventType","EVENT_TYPE"),
		
		/** 记录事件描述信息. */
		eventDesc("eventDesc","EVENT_DESC"),
		
		/** 资源所属店中商ID. */
		partnerId("partnerId","PARTNER_ID"),
		
		/** 记录受理时间。. */
		acceptDate("acceptDate","ACCEPT_DATE"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录状态。LOVB=RES-C-0008. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的系统岗位标识。. */
		createPost("createPost","CREATE_POST"),
		
		/** 记录首次创建的组织机构标识。. */
		createOrgId("createOrgId","CREATE_ORG_ID"),
		
		/** 记录首次创建的用户标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的用户标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 备注. */
		remark("remark","REMARK"),
		
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
