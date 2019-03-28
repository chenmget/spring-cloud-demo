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
 * ResourceReqItem
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_req_item")
@ApiModel(value = "对应模型mkt_res_req_item, 对应实体ResourceReqItem类")
@KeySequence(value="seq_mkt_res_req_item_id",clazz = String.class)


public class ResourceReqItem implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_req_item";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源申请单项标识，主键
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源申请单项标识，主键")
  	private java.lang.String mktResReqItemId;
  	
  	/**
  	 * 记录营销资源申请单标识
  	 */
	@ApiModelProperty(value = "记录营销资源申请单标识")
  	private java.lang.String mktResReqId;
  	
  	/**
  	 * 营销资源标识，记录product_id
  	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
  	
  	/**
  	 * 记录营销资源实例的数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量")
  	private java.lang.Long quantity;
  	
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
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
  	
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
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源申请单项标识，主键. */
		mktResReqItemId("mktResReqItemId","MKT_RES_REQ_ITEM_ID"),
		
		/** 记录营销资源申请单标识. */
		mktResReqId("mktResReqId","MKT_RES_REQ_ID"),
		
		/** 营销资源标识，记录product_id. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 记录营销资源实例的数量. */
		quantity("quantity","QUANTITY"),
		
		/** 本地网标识. */
		lanId("lanId","LAN_ID"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的用户标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
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
