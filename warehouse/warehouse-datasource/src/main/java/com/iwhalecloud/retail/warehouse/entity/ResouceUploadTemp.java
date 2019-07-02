package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("MKT_RES_UPLOAD_TEMP")
@ApiModel(value = "对应模型MKT_RES_UPLOAD_TEMP, ResouceUploadTemp")
public class ResouceUploadTemp implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_UPLOAD_TEMP";
  	private static final long serialVersionUID = 1L;


  	//属性 begin
  	/**
  	 * 营销资源导入流水号
  	 */
	@TableId(type= IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "营销资源导入流水号")
  	private String mktResUploadSeq;

  	/**
  	 * 营销资源导入批次。
  	 */
	@ApiModelProperty(value = "营销资源导入批次。")
  	private String mktResUploadBatch;

	/**
	 * 营销资源实例编码
	 */
	@ApiModelProperty(value = "营销资源实例编码")
	private String mktResInstNbr;

  	/**
  	 * 导入时间
  	 */
	@ApiModelProperty(value = "导入时间")
  	private java.util.Date uploadDate;

  	/**
  	 * 验证结果 1. 通过; 0. 不通过
  	 */
	@ApiModelProperty(value = "验证结果 1. 通过; 0. 不通过")
  	private String result;

  	/**
  	 * 验证描述，记录出错的原因
  	 */
	@ApiModelProperty(value = "验证描述，记录出错的原因")
  	private String resultDesc;

	/**
	 * 记录首次创建的用户标识。
	 */
	@ApiModelProperty(value = "首次创建的用户")
	private String createStaff;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
	private java.util.Date createDate;

	/**
	 * CT码
	 */
	@ApiModelProperty(value = "CT码")
	private String ctCode;

	/**
	 * SN码
	 */
	@ApiModelProperty(value = "SN码")
	private java.lang.String snCode;

	/**
	 * 网络终端（包含光猫、机顶盒、融合终端）记录MAC码
	 */
	@ApiModelProperty(value = "macCode")
	private java.lang.String macCode;

	@ApiModelProperty(value = "记录营销资源申请单明细标识")
	private String mktResReqDetailId;

	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
	private String statusCd;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 产品ID
	 */
	@ApiModelProperty(value = "产品ID")
	private String mktResId;


  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源导入流水号 */
		mktResUploadSeq("mktResUploadSeq","MKT_RES_UPLOAD_SEQ"),
		
		/** 营销资源导入批次 */
		mktResUploadBatch("mktResUploadBatch","MKT_RES_UPLOAD_BATCH"),
		
		/** 营销资源实例编码 */
		mktResInstNbr("mktResInstNbr","MKT_RES_INST_NBR"),
		
		/** 导入时间. */
		uploadDate("uploadDate","UPLOAD_DATE"),
		
		/** 验证结果 1. 通过; 0. 不通过. */
		result("result","RESULT"),
		
		/** 验证描述，记录出错的原因. */
		resultDesc("resultDesc","RESULT_DESC"),
		
		/** 首次创建的用户 */
		createStaff("createStaff","CREATE_STAFF"),

		/** 首次创建的时间 */
		createDate("createDate","CREATE_DATE"),

		/** ct码 */
		ctCode("ctCode","CT_CODE"),

		/**SN码. */
		snCode("snCode","sn_code"),

		/** 网络终端（包含光猫、机顶盒、融合终端）记录MAC码. */
		macCode("macCode","mac_code"),

		mktResReqDetailId("mktResReqDetailId","MKT_RES_REQ_DETAIL_ID"),

		statusCd("statusCd","STATUS_CD"),

		remark("remark","remark");

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
