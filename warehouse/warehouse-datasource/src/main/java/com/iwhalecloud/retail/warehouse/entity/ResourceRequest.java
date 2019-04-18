package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResourceRequest  
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_request")
@ApiModel(value = "对应模型mkt_res_request, 对应实体ResourceRequest  类")
@KeySequence(value="seq_mkt_res_request_id",clazz = String.class)

public class ResourceRequest implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_request";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 记录营销资源申请单标识
  	 */
	@TableId
	@ApiModelProperty(value = "记录营销资源申请单标识")
  	private String mktResReqId;

  	/**
  	 * 申请单编码
  	 */
	@ApiModelProperty(value = "申请单编码")
  	private String reqCode;

  	/**
  	 * 申请单名称
  	 */
	@ApiModelProperty(value = "申请单名称")
  	private String reqName;
  	
  	/**
  	 * 申请单类型
            01 入库申请
            02 调拨申请
            03 退库申请
  	 */
	@ApiModelProperty(value = "申请单类型01 入库申请02 调拨申请 03 退库申请")
  	private java.lang.String reqType;
  	
  	/**
  	 * 申请单内容描述
  	 */
	@ApiModelProperty(value = "申请单内容描述")
  	private java.lang.String content;
  	
  	/**
  	 * 目标营销资源仓库标识
  	 */
	@ApiModelProperty(value = "源营销资源仓库")
  	private java.lang.String mktResStoreId;
  	
  	/**
  	 * 目标营销资源仓库
  	 */
	@ApiModelProperty(value = "目标营销资源仓库")
  	private java.lang.String destStoreId;
  	
  	/**
  	 * 记录要求完成时间
  	 */
	@ApiModelProperty(value = "记录要求完成时间")
  	private java.util.Date completeDate;
  	
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
  	 * 记录状态。LOVB=RES-C-0010
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-C-0010")
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
  	 * 记录每次修改的用户标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的用户标识。")
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
  	
  	/**
  	 * 商家ID
  	 */
	@ApiModelProperty(value = "商家ID")
  	private java.lang.String merchantId;
  	/**
  	 * 串码类型
  	 */
	@ApiModelProperty(value = "串码类型")
  	private java.lang.String mktResInstType;


  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 记录营销资源申请单标识. */
		mktResReqId("mktResReqId","MKT_RES_REQ_ID"),
		
		/** 申请单编码. */
		reqCode("reqCode","REQ_CODE"),
		
		/** 申请单名称. */
		reqName("reqName","REQ_NAME"),
		
		/** 申请单类型
            01 入库申请
            02 调拨申请
            03 退库申请. */
		reqType("reqType","REQ_TYPE"),
		
		/** 申请单内容描述. */
		content("content","CONTENT"),
		
		/** 目标营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 目标营销资源仓库. */
		destStoreId("destStoreId","DEST_STORE_ID"),
		
		/** 记录要求完成时间. */
		completeDate("completeDate","COMPLETE_DATE"),
		
		/** 本地网标识. */
		lanId("lanId","LAN_ID"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
		/** 记录状态。LOVB=RES-C-0010. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的用户标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的用户标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 备注. */
		remark("remark","REMARK"),
		/** 拓展字段. */
		extend("extend","EXTEND");

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
